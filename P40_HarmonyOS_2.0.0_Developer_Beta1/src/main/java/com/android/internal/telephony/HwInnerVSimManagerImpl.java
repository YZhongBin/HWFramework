package com.android.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.telephony.SubscriptionManager;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import com.android.internal.telephony.uicc.HwVSimUiccController;
import com.android.internal.telephony.vsim.HwVSimConstants;
import com.android.internal.telephony.vsim.HwVSimController;
import com.android.internal.telephony.vsim.HwVSimUtilsImpl;
import com.android.internal.telephony.vsim.HwVSimUtilsInner;
import com.huawei.android.telephony.RlogEx;
import com.huawei.android.telephony.data.ApnSettingEx;
import com.huawei.hwparttelephonyvsim.BuildConfig;
import com.huawei.internal.telephony.CommandsInterfaceEx;
import com.huawei.internal.telephony.PhoneExt;
import com.huawei.internal.telephony.PhoneNotifierEx;
import com.huawei.internal.telephony.ServiceStateTrackerEx;
import com.huawei.internal.telephony.uicc.IccCardStatusExt;
import com.huawei.internal.telephony.uicc.IccRecordsEx;
import com.huawei.internal.telephony.uicc.UiccCardApplicationEx;
import com.huawei.internal.telephony.uicc.UiccCardExt;
import com.huawei.internal.telephony.vsim.HwVSimBaseController;
import com.huawei.internal.telephony.vsim.HwVSimControllerGetter;
import com.huawei.internal.telephony.vsim.HwVSimMtkController;
import com.huawei.internal.telephony.vsim.IGetVsimServiceCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

public class HwInnerVSimManagerImpl extends DefaultHwInnerVSimManager {
    private static final int INVALID_SLOT_ID = -1;
    private static final int IS_RADIO_AVAILABLE = 7;
    private static final int IS_VSIM_CAUSE_CARD_RELOAD = 8;
    private static final int IS_VSIM_ENABLED = 9;
    private static final int IS_VSIM_IN_PROCESS = 2;
    private static final int IS_VSIM_ON = 1;
    private static final String LOG_TAG = "HwInnerVSimMngrImpl";
    private static final int NEED_BLOCK_PIN = 10;
    private static final int NEED_BLOCK_UNRESERVED_SUBID = 11;
    private static final int PHONE_COUNT = 2;
    private static volatile HwInnerVSimManagerImpl sInstance;
    private static final Object sLock = new Object();
    private CommandsInterfaceEx[] mCommandsInterfaceExs;
    private Context mContext;
    private boolean[] mLegacyMarkTable = new boolean[2];
    private PhoneNotifierEx mPhoneNotifier;
    private PhoneExt[] mPhones;

    private HwInnerVSimManagerImpl() {
    }

    public static HwInnerVSimManagerImpl getDefault() {
        if (sInstance == null) {
            synchronized (HwInnerVSimManagerImpl.class) {
                if (sInstance == null) {
                    sInstance = new HwInnerVSimManagerImpl();
                }
            }
        }
        return sInstance;
    }

    public void createHwVSimService(Context context) {
        if (HwVSimService.getDefault(context) == null) {
            RlogEx.e(LOG_TAG, "create vsim service not success");
        }
        for (int i = 0; i < 2; i++) {
            this.mLegacyMarkTable[i] = false;
        }
    }

    public void makeVSimPhoneFactory(Context context, PhoneNotifierEx notifier, PhoneExt[] pps, CommandsInterfaceEx[] cis) {
        if (HwVSimUtilsInner.isSupportedVsimDynamicStartStop()) {
            this.mContext = context;
            this.mPhoneNotifier = notifier;
            this.mPhones = pps;
            this.mCommandsInterfaceExs = cis;
            if (!HwVSimUtilsInner.isVsimEnabledByDatabase(context)) {
                logi("support dynamic start and stop, delay init.");
                return;
            }
        }
        HwVSimPhoneFactory.make(context, notifier, pps, cis);
    }

    public void lazyInit(IGetVsimServiceCallback callback) throws RemoteException {
        HwVSimService hwVsimService = HwVSimService.getInstance();
        if (!HwVSimUtilsInner.isSupportedVsimDynamicStartStop()) {
            logi("lazyinit, not support, return");
            notifyInitResult(callback, hwVsimService);
        } else if (hwVsimService != null) {
            logi("lazyinit, has got vsim service, return");
            notifyInitResult(callback, hwVsimService);
        } else if (Stream.of(this.mContext, this.mPhoneNotifier, this.mPhones, this.mCommandsInterfaceExs).anyMatch($$Lambda$wLIh0GiBW9398cTP8uaTH8KoGwo.INSTANCE)) {
            logi("lazyinit, some variable is null, return");
            notifyInitResult(callback, null);
        } else {
            synchronized (sLock) {
                HwVSimService hwVsimService2 = HwVSimService.getInstance();
                if (hwVsimService2 != null) {
                    logi("lazyinit, lock return hwVsimService");
                    notifyInitResult(callback, hwVsimService2);
                    return;
                }
                HwVSimPhoneFactory.make(this.mContext, this.mPhoneNotifier, this.mPhones, this.mCommandsInterfaceExs);
                HwVSimService hwVsimService3 = HwVSimService.getInstance();
                if (!HwVSimPhoneFactory.isInitiated() || hwVsimService3 == null) {
                    notifyInitResult(callback, null);
                } else {
                    logi("lazyinit, notify callback");
                    notifyInitResult(callback, hwVsimService3);
                }
            }
        }
    }

    private void notifyInitResult(IGetVsimServiceCallback callback, IHwVSim vsimService) throws RemoteException {
        if (callback != null) {
            callback.onComplete(vsimService);
        }
    }

    public void dumpVSimPhoneFactory(FileDescriptor fd, PrintWriter pw, String[] args) {
        HwVSimPhoneFactory.dump(fd, pw, args);
    }

    public ServiceStateTrackerEx makeVSimServiceStateTracker(PhoneExt phone, CommandsInterfaceEx ci) {
        return HwVSimPhoneFactory.makeVSimServiceStateTracker(phone, ci);
    }

    public PhoneExt getVSimPhone() {
        return HwVSimPhoneFactory.getVSimPhone();
    }

    public boolean isVSimPhone(PhoneExt phone) {
        return phone != null && phone.getPhoneId() == getVSimSlot();
    }

    public boolean isVSimInStatus(int type, int subId) {
        boolean result = false;
        if (type == 7) {
            result = true;
        }
        HwVSimBaseController controller = getVSimBaseController();
        if (!HwVSimControllerGetter.valid(controller)) {
            return result;
        }
        if (type == 1) {
            return controller.isVSimOn();
        }
        if (type == 2) {
            return controller.isVSimInProcess();
        }
        switch (type) {
            case 7:
                if (!HwVSimUtilsImpl.getInstance().isPlatformTwoModems()) {
                    return result;
                }
                return HwVSimUtilsImpl.getInstance().isRadioAvailable(subId);
            case 8:
                return controller.isVSimCauseCardReload();
            case 9:
                return controller.isVSimEnabled();
            case 10:
                return controller.needBlockPin(subId);
            case 11:
                return controller.needBlockUnReservedForVsim(subId);
            default:
                RlogEx.i(LOG_TAG, "isVSimInStatus type " + type + "not support");
                return result;
        }
    }

    public void registerForIccChanged(Handler h, int what, Object obj) {
        if (HwVSimUiccController.isInstantiated()) {
            HwVSimUiccController.getInstance().registerForIccChanged(h, what, obj);
        }
    }

    public void unregisterForIccChanged(Handler h) {
        if (HwVSimUiccController.isInstantiated()) {
            HwVSimUiccController.getInstance().unregisterForIccChanged(h);
        }
    }

    public IccRecordsEx fetchVSimIccRecords(int family) {
        if (!HwVSimUiccController.isInstantiated()) {
            return null;
        }
        return HwVSimUiccController.getInstance().getIccRecords(family);
    }

    public UiccCardApplicationEx getVSimUiccCardApplication(int appFamily) {
        if (!HwVSimUiccController.isInstantiated()) {
            return null;
        }
        return HwVSimUiccController.getInstance().getUiccCardApplication(appFamily);
    }

    public void setMarkForCardReload(int subId, boolean value) {
        HwVSimBaseController controller = getVSimBaseController();
        if (HwVSimControllerGetter.valid(controller)) {
            if (controller.isVSimCauseCardReload()) {
                setVSimLegacyReloadMark(subId, true);
            }
            controller.setMarkForCardReload(subId, value);
        }
    }

    public String getPendingDeviceInfoFromSP(String prefKey) {
        HwVSimBaseController controller = getVSimBaseController();
        if (HwVSimControllerGetter.valid(controller) && HwVSimUtilsImpl.getInstance().isPlatformTwoModems()) {
            return controller.getPendingDeviceInfoFromSP(prefKey);
        }
        return null;
    }

    public int getTopPrioritySubscriptionId() {
        if (!HwVSimController.isInstantiated()) {
            return SubscriptionManager.getDefaultDataSubscriptionId();
        }
        return HwVSimPhoneFactory.getTopPrioritySubscriptionId();
    }

    public ArrayList<ApnSetting> createVSimApnList() {
        ArrayList<ApnSetting> result = new ArrayList<>();
        result.add(makeVSimApnSetting());
        logi("createVSimApnList: X result=" + result);
        return result;
    }

    private ApnSetting makeVSimApnSetting() {
        return ApnSettingEx.makeApnSetting(0, "00000", HwVSimConstants.INTENT_KEY_VSIM, "apn", BuildConfig.FLAVOR, -1, (Uri) null, BuildConfig.FLAVOR, -1, BuildConfig.FLAVOR, BuildConfig.FLAVOR, -1, ApnSettingEx.getApnTypesBitmaskFromString("default,supl"), ApnSettingEx.getProtocolIntFromString("IP"), ApnSettingEx.getProtocolIntFromString("IP"), true, 0, 0, false, 0, 0, 0, 0, ApnSettingEx.getMvnoTypeIntFromString(BuildConfig.FLAVOR), BuildConfig.FLAVOR, 0, -1, -1);
    }

    private boolean isNeedBroadcastCardAbsentState(String value, int phoneId) {
        if (HuaweiTelephonyConfigs.isHisiPlatform() || !"ABSENT".equals(value)) {
            return false;
        }
        HwVSimBaseController controller = getVSimBaseController();
        if (HwVSimControllerGetter.valid(controller) && controller.isVSimEnabled() && controller.getVSimOccupiedSlotId() == phoneId) {
            return controller.isNeedBroadcastVSimAbsentState();
        }
        return false;
    }

    private boolean isIccStateChangedByVSimReload(String value, int phoneId) {
        if (!"ABSENT".equals(value) && !"READY".equals(value) && !"IMSI".equals(value) && !"LOADED".equals(value)) {
            return false;
        }
        HwVSimBaseController controller = getVSimBaseController();
        if (!HwVSimControllerGetter.valid(controller)) {
            return false;
        }
        if (controller.isVSimEnabled() || controller.isVSimCauseCardReload()) {
            if ("LOADED".equals(value)) {
                setVSimLegacyReloadMark(phoneId, false);
            }
            return true;
        } else if (!isVSimLegacyReloadMark(phoneId)) {
            return false;
        } else {
            setVSimLegacyReloadMark(phoneId, false);
            return true;
        }
    }

    private boolean isVSimLegacyReloadMark(int subId) {
        if (subId < 0 || subId >= 2) {
            return false;
        }
        return this.mLegacyMarkTable[subId];
    }

    private void setVSimLegacyReloadMark(int subId, boolean value) {
        if (subId >= 0 && subId < 2) {
            logi("set sLegacyMarkTable[" + subId + "] = " + value);
            this.mLegacyMarkTable[subId] = value;
        }
    }

    public void putVSimExtraForIccStateChanged(Intent intent, int phoneId, String value) {
        if (intent != null && value != null) {
            if (isIccStateChangedByVSimReload(value, phoneId)) {
                logi("vsim add extra param for ACTION_SIM_STATE_CHANGED as vsim reload");
                intent.putExtra(HwVSimConstants.INTENT_KEY_VSIM, HwVSimConstants.INTENT_VALUE_VSIM_RELOAD);
            }
            if (isNeedBroadcastCardAbsentState(value, phoneId)) {
                logi("vsim add extra param for modem restart");
                intent.putExtra(HwVSimConstants.INTENT_KEY_RILD_RESTART, 1);
            }
        }
    }

    public void disposeSSTForVSim() {
        logi("disposeSSTForVSim");
        HwVSimBaseController controller = HwVSimControllerGetter.get();
        if (controller instanceof HwVSimMtkController) {
            ((HwVSimMtkController) controller).dispose();
            return;
        }
        PhoneExt vsimPhone = getVSimPhone();
        if (vsimPhone == null) {
            logi("disposeSSTForVSim, phone is null, return.");
            return;
        }
        ServiceStateTrackerEx serviceStateTrackerEx = vsimPhone.getServiceStateTracker();
        if (serviceStateTrackerEx instanceof HwVSimServiceStateTracker) {
            logi("disposeSSTForVSim try to dispose");
            ((HwVSimServiceStateTracker) serviceStateTrackerEx).dispose();
        }
    }

    public String changeSpnForVSim(String spn) {
        HwVSimBaseController vSimBaseController = getVSimBaseController();
        if (!HwVSimControllerGetter.valid(vSimBaseController)) {
            return spn;
        }
        String spnFromApk = vSimBaseController.getSpn();
        logi("changeSpnForVSim, spnFromApk " + spnFromApk + " instead of " + spn);
        return !TextUtils.isEmpty(spnFromApk) ? spnFromApk : spn;
    }

    public int changeRuleForVSim(int rule) {
        HwVSimBaseController vSimBaseController = getVSimBaseController();
        if (!HwVSimControllerGetter.valid(vSimBaseController)) {
            return rule;
        }
        int ruleFromApk = vSimBaseController.getRule();
        logi("changeRuleForVSim, ruleFromApk " + ruleFromApk + " instead of " + rule);
        return ruleFromApk != -1 ? ruleFromApk : rule;
    }

    public IccCardStatusExt.CardStateEx getVSimCardState() {
        if (!HwVSimUiccController.isInstantiated()) {
            return IccCardStatusExt.CardStateEx.CARDSTATE_ABSENT;
        }
        UiccCardExt uiccCard = HwVSimUiccController.getInstance().getUiccCard();
        if (uiccCard == null) {
            return IccCardStatusExt.CardStateEx.CARDSTATE_ABSENT;
        }
        return uiccCard.getCardState();
    }

    public int getVSimSlot() {
        HwVSimBaseController vSimBaseController = getVSimBaseController();
        if (!HwVSimControllerGetter.valid(vSimBaseController)) {
            return -1;
        }
        return vSimBaseController.getVsimSlotId();
    }

    private void logi(String msg) {
        RlogEx.i(LOG_TAG, msg);
    }

    private HwVSimBaseController getVSimBaseController() {
        return HwVSimControllerGetter.get();
    }
}
