package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;
import com.huawei.android.statistical.StatisticalConstant;
import com.huawei.pgmng.log.LogPower;
import com.nxp.nfc.gsma.internal.NxpNfcController;

public class CheckedTextView extends TextView implements Checkable {
    private static final int[] CHECKED_STATE_SET = null;
    private int mBasePadding;
    private Drawable mCheckMarkDrawable;
    private int mCheckMarkGravity;
    private int mCheckMarkResource;
    private ColorStateList mCheckMarkTintList;
    private Mode mCheckMarkTintMode;
    private int mCheckMarkWidth;
    private boolean mChecked;
    private boolean mHasCheckMarkTint;
    private boolean mHasCheckMarkTintMode;
    private boolean mNeedRequestlayout;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = null;
        boolean checked;

        static {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.widget.CheckedTextView.SavedState.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:263)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: jadx.core.utils.exceptions.DecodeException:  in method: android.widget.CheckedTextView.SavedState.<clinit>():void
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:46)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:98)
	... 6 more
Caused by: java.lang.IllegalArgumentException: bogus opcode: 0073
	at com.android.dx.io.OpcodeInfo.get(OpcodeInfo.java:1197)
	at com.android.dx.io.OpcodeInfo.getFormat(OpcodeInfo.java:1212)
	at com.android.dx.io.instructions.DecodedInstruction.decode(DecodedInstruction.java:72)
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:43)
	... 7 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.CheckedTextView.SavedState.<clinit>():void");
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.checked = ((Boolean) in.readValue(null)).booleanValue();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(Boolean.valueOf(this.checked));
        }

        public String toString() {
            return "CheckedTextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }

    static {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.widget.CheckedTextView.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:113)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
Caused by: jadx.core.utils.exceptions.DecodeException:  in method: android.widget.CheckedTextView.<clinit>():void
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:46)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:98)
	... 5 more
Caused by: java.lang.IllegalArgumentException: bogus opcode: 0073
	at com.android.dx.io.OpcodeInfo.get(OpcodeInfo.java:1197)
	at com.android.dx.io.OpcodeInfo.getFormat(OpcodeInfo.java:1212)
	at com.android.dx.io.instructions.DecodedInstruction.decode(DecodedInstruction.java:72)
	at jadx.core.dex.instructions.InsnDecoder.decodeInsns(InsnDecoder.java:43)
	... 6 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.CheckedTextView.<clinit>():void");
    }

    public CheckedTextView(Context context) {
        this(context, null);
    }

    public CheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkedTextViewStyle);
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCheckMarkTintList = null;
        this.mCheckMarkTintMode = null;
        this.mHasCheckMarkTint = false;
        this.mHasCheckMarkTintMode = false;
        this.mCheckMarkGravity = Gravity.END;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckedTextView, defStyleAttr, defStyleRes);
        Drawable d = a.getDrawable(1);
        if (d != null) {
            setCheckMarkDrawable(d);
        }
        if (a.hasValue(3)) {
            this.mCheckMarkTintMode = Drawable.parseTintMode(a.getInt(3, -1), this.mCheckMarkTintMode);
            this.mHasCheckMarkTintMode = true;
        }
        if (a.hasValue(2)) {
            this.mCheckMarkTintList = a.getColorStateList(2);
            this.mHasCheckMarkTint = true;
        }
        this.mCheckMarkGravity = a.getInt(4, Gravity.END);
        setChecked(a.getBoolean(0, false));
        a.recycle();
        applyCheckMarkTint();
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }

    @ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            refreshDrawableState();
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public void setCheckMarkDrawable(int resId) {
        if (resId == 0 || resId != this.mCheckMarkResource) {
            setCheckMarkDrawableInternal(resId != 0 ? getContext().getDrawable(resId) : null, resId);
        }
    }

    public void setCheckMarkDrawable(Drawable d) {
        setCheckMarkDrawableInternal(d, 0);
    }

    private void setCheckMarkDrawableInternal(Drawable d, int resId) {
        boolean z;
        boolean z2 = true;
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.setCallback(null);
            unscheduleDrawable(this.mCheckMarkDrawable);
        }
        if (d != this.mCheckMarkDrawable) {
            z = true;
        } else {
            z = false;
        }
        this.mNeedRequestlayout = z;
        if (d != null) {
            d.setCallback(this);
            if (getVisibility() != 0) {
                z2 = false;
            }
            d.setVisible(z2, false);
            d.setState(CHECKED_STATE_SET);
            setMinHeight(d.getIntrinsicHeight());
            this.mCheckMarkWidth = d.getIntrinsicWidth();
            d.setState(getDrawableState());
        } else {
            this.mCheckMarkWidth = 0;
        }
        this.mCheckMarkDrawable = d;
        this.mCheckMarkResource = resId;
        applyCheckMarkTint();
        resolvePadding();
    }

    public void setCheckMarkTintList(ColorStateList tint) {
        this.mCheckMarkTintList = tint;
        this.mHasCheckMarkTint = true;
        applyCheckMarkTint();
    }

    public ColorStateList getCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    public void setCheckMarkTintMode(Mode tintMode) {
        this.mCheckMarkTintMode = tintMode;
        this.mHasCheckMarkTintMode = true;
        applyCheckMarkTint();
    }

    public Mode getCheckMarkTintMode() {
        return this.mCheckMarkTintMode;
    }

    private void applyCheckMarkTint() {
        if (this.mCheckMarkDrawable == null) {
            return;
        }
        if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
            this.mCheckMarkDrawable = this.mCheckMarkDrawable.mutate();
            if (this.mHasCheckMarkTint) {
                this.mCheckMarkDrawable.setTintList(this.mCheckMarkTintList);
            }
            if (this.mHasCheckMarkTintMode) {
                this.mCheckMarkDrawable.setTintMode(this.mCheckMarkTintMode);
            }
            if (this.mCheckMarkDrawable.isStateful()) {
                this.mCheckMarkDrawable.setState(getDrawableState());
            }
        }
    }

    @RemotableViewMethod
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.mCheckMarkDrawable != null) {
            boolean z;
            Drawable drawable = this.mCheckMarkDrawable;
            if (visibility == 0) {
                z = true;
            } else {
                z = false;
            }
            drawable.setVisible(z, false);
        }
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.jumpToCurrentState();
        }
    }

    protected boolean verifyDrawable(Drawable who) {
        return who != this.mCheckMarkDrawable ? super.verifyDrawable(who) : true;
    }

    public Drawable getCheckMarkDrawable() {
        return this.mCheckMarkDrawable;
    }

    protected void internalSetPadding(int left, int top, int right, int bottom) {
        super.internalSetPadding(left, top, right, bottom);
        setBasePadding(isCheckMarkAtStart());
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        updatePadding();
    }

    private void updatePadding() {
        int i = 1;
        resetPaddingToInitialValues();
        int newPadding = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding : this.mBasePadding;
        boolean z;
        if (isCheckMarkAtStart()) {
            z = this.mNeedRequestlayout;
            if (this.mPaddingLeft == newPadding) {
                i = 0;
            }
            this.mNeedRequestlayout = i | z;
            this.mPaddingLeft = newPadding;
        } else {
            z = this.mNeedRequestlayout;
            if (this.mPaddingRight == newPadding) {
                i = 0;
            }
            this.mNeedRequestlayout = i | z;
            this.mPaddingRight = newPadding;
        }
        if (this.mNeedRequestlayout) {
            requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    private void setBasePadding(boolean checkmarkAtStart) {
        if (checkmarkAtStart) {
            this.mBasePadding = this.mPaddingLeft;
        } else {
            this.mBasePadding = this.mPaddingRight;
        }
    }

    private boolean isCheckMarkAtStart() {
        return (Gravity.getAbsoluteGravity(this.mCheckMarkGravity, getLayoutDirection()) & 7) == 3;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable checkMarkDrawable = this.mCheckMarkDrawable;
        if (checkMarkDrawable != null) {
            int left;
            int right;
            int verticalGravity = getGravity() & LogPower.APP_PROCESS_EXIT;
            int height = checkMarkDrawable.getIntrinsicHeight();
            int y = 0;
            switch (verticalGravity) {
                case NxpNfcController.PROTOCOL_ISO_DEP /*16*/:
                    y = (getHeight() - height) / 2;
                    break;
                case StatisticalConstant.TYPE_SCREEN_SHOT_END /*80*/:
                    y = getHeight() - height;
                    break;
            }
            boolean checkMarkAtStart = isCheckMarkAtStart();
            int width = getWidth();
            int top = y;
            int bottom = y + height;
            if (checkMarkAtStart) {
                left = this.mBasePadding;
                right = left + this.mCheckMarkWidth;
            } else {
                right = width - this.mBasePadding;
                left = right - this.mCheckMarkWidth;
            }
            checkMarkDrawable.setBounds(this.mScrollX + left, top, this.mScrollX + right, bottom);
            checkMarkDrawable.draw(canvas);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(this.mScrollX + left, top, this.mScrollX + right, bottom);
            }
        }
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable checkMarkDrawable = this.mCheckMarkDrawable;
        if (checkMarkDrawable != null && checkMarkDrawable.isStateful() && checkMarkDrawable.setState(getDrawableState())) {
            invalidateDrawable(checkMarkDrawable);
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.setHotspot(x, y);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return CheckedTextView.class.getName();
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.checked = isChecked();
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked);
        requestLayout();
    }

    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);
        event.setChecked(this.mChecked);
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        info.setCheckable(true);
        info.setChecked(this.mChecked);
    }

    protected void encodeProperties(ViewHierarchyEncoder stream) {
        super.encodeProperties(stream);
        stream.addProperty("text:checked", isChecked());
    }
}
