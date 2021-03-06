package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUDebug;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RBBIRuleBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int U_BRK_ASSIGN_ERROR = 66054;
    static final int U_BRK_ERROR_LIMIT = 66064;
    static final int U_BRK_ERROR_START = 66048;
    static final int U_BRK_HEX_DIGITS_EXPECTED = 66050;
    static final int U_BRK_INIT_ERROR = 66059;
    static final int U_BRK_INTERNAL_ERROR = 66049;
    static final int U_BRK_MALFORMED_RULE_TAG = 66062;
    static final int U_BRK_MALFORMED_SET = 66063;
    static final int U_BRK_MISMATCHED_PAREN = 66056;
    static final int U_BRK_NEW_LINE_IN_QUOTED_STRING = 66057;
    static final int U_BRK_RULE_EMPTY_SET = 66060;
    static final int U_BRK_RULE_SYNTAX = 66052;
    static final int U_BRK_SEMICOLON_EXPECTED = 66051;
    static final int U_BRK_UNCLOSED_SET = 66053;
    static final int U_BRK_UNDEFINED_VARIABLE = 66058;
    static final int U_BRK_UNRECOGNIZED_OPTION = 66061;
    static final int U_BRK_VARIABLE_REDFINITION = 66055;
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    boolean fChainRules;
    String fDebugEnv;
    int fDefaultTree = 0;
    RBBITableBuilder fForwardTables;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    RBBITableBuilder fReverseTables;
    List<Integer> fRuleStatusVals;
    String fRules;
    RBBITableBuilder fSafeFwdTables;
    RBBITableBuilder fSafeRevTables;
    RBBIRuleScanner fScanner;
    RBBISetBuilder fSetBuilder;
    Map<Set<Integer>, Integer> fStatusSets = new HashMap();
    RBBINode[] fTreeRoots = new RBBINode[4];
    List<RBBINode> fUSetNodes;

    RBBIRuleBuilder(String rules) {
        this.fDebugEnv = ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null;
        this.fRules = rules;
        this.fUSetNodes = new ArrayList();
        this.fRuleStatusVals = new ArrayList();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }

    static final int align8(int i) {
        return (i + 7) & -8;
    }

    /* access modifiers changed from: package-private */
    public void flattenData(OutputStream os) throws IOException {
        short[] tableData;
        OutputStream outputStream = os;
        DataOutputStream dos = new DataOutputStream(outputStream);
        String strippedRules = RBBIRuleScanner.stripRules(this.fRules);
        int forwardTableSize = align8(this.fForwardTables.getTableSize());
        int reverseTableSize = align8(this.fReverseTables.getTableSize());
        int safeRevTableSize = align8(this.fSafeRevTables.getTableSize());
        int trieSize = align8(this.fSetBuilder.getTrieSize());
        int statusTableSize = align8(this.fRuleStatusVals.size() * 4);
        int rulesSize = align8(strippedRules.length() * 2);
        int i = 96 + forwardTableSize + 0 + 0;
        int i2 = safeRevTableSize > 0 ? safeRevTableSize : reverseTableSize;
        int outputPos = 0;
        ICUBinary.writeHeader(1114794784, 67108864, 0, dos);
        int[] header = new int[24];
        header[0] = 45472;
        header[1] = 67108864;
        header[2] = i + i2 + statusTableSize + trieSize + rulesSize;
        header[3] = this.fSetBuilder.getNumCharCategories();
        header[4] = 96;
        header[5] = forwardTableSize;
        header[6] = header[4] + forwardTableSize;
        header[7] = 0;
        header[8] = header[6] + 0;
        header[9] = 0;
        header[10] = header[8] + 0;
        if (safeRevTableSize > 0) {
            header[11] = safeRevTableSize;
        } else {
            header[11] = reverseTableSize;
        }
        header[12] = header[10] + header[11];
        header[13] = this.fSetBuilder.getTrieSize();
        header[16] = header[12] + header[13];
        header[17] = statusTableSize;
        header[14] = header[16] + statusTableSize;
        header[15] = strippedRules.length() * 2;
        for (int writeInt : header) {
            dos.writeInt(writeInt);
            outputPos += 4;
        }
        short[] tableData2 = this.fForwardTables.exportTable();
        Assert.assrt(outputPos == header[4]);
        for (short writeShort : tableData2) {
            dos.writeShort(writeShort);
            outputPos += 2;
        }
        Assert.assrt(outputPos == header[10]);
        if (safeRevTableSize > 0) {
            tableData = this.fSafeRevTables.exportTable();
        } else {
            tableData = this.fReverseTables.exportTable();
        }
        for (short writeShort2 : tableData) {
            dos.writeShort(writeShort2);
            outputPos += 2;
        }
        Assert.assrt(outputPos == header[12]);
        this.fSetBuilder.serializeTrie(outputStream);
        int outputPos2 = outputPos + header[13];
        while (outputPos2 % 8 != 0) {
            dos.write(0);
            outputPos2++;
        }
        Assert.assrt(outputPos2 == header[16]);
        for (Integer val : this.fRuleStatusVals) {
            dos.writeInt(val.intValue());
            outputPos2 += 4;
            OutputStream outputStream2 = os;
        }
        while (outputPos2 % 8 != 0) {
            dos.write(0);
            outputPos2++;
        }
        Assert.assrt(outputPos2 == header[14]);
        dos.writeChars(strippedRules);
        for (int outputPos3 = outputPos2 + (strippedRules.length() * 2); outputPos3 % 8 != 0; outputPos3++) {
            dos.write(0);
        }
    }

    static void compileRules(String rules, OutputStream os) throws IOException {
        RBBIRuleBuilder builder = new RBBIRuleBuilder(rules);
        builder.fScanner.parse();
        builder.fSetBuilder.build();
        builder.fForwardTables = new RBBITableBuilder(builder, 0);
        builder.fReverseTables = new RBBITableBuilder(builder, 1);
        builder.fSafeFwdTables = new RBBITableBuilder(builder, 2);
        builder.fSafeRevTables = new RBBITableBuilder(builder, 3);
        builder.fForwardTables.build();
        builder.fReverseTables.build();
        builder.fSafeFwdTables.build();
        builder.fSafeRevTables.build();
        if (builder.fDebugEnv != null && builder.fDebugEnv.indexOf("states") >= 0) {
            builder.fForwardTables.printRuleStatusTable();
        }
        builder.flattenData(os);
    }
}
