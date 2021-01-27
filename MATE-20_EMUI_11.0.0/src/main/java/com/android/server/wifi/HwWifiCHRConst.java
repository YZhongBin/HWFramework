package com.android.server.wifi;

public class HwWifiCHRConst {
    public static final int AKMP_NOT_VALID = 20;
    public static final int APKACTION_ACQUIRE_WIFI_LOCK = 22;
    public static final int APKACTION_ADD_NETWORK = 15;
    public static final int APKACTION_ADD_SUGGESTION = 20;
    public static final int APKACTION_AWAYS_SCAN_ENABLE = 3;
    public static final int APKACTION_AWAYS_SCAN_TRIGGER = 4;
    public static final int APKACTION_DISABLE_NETWORK = 2;
    public static final int APKACTION_DISCONNECT_WIFI = 8;
    public static final int APKACTION_ENABLE_WIFI_FALSE = 1;
    public static final int APKACTION_ENABLE_WIFI_TRUE = 5;
    public static final int APKACTION_FOREGROUND_SCAN_TRIGGER = 9;
    public static final int APKACTION_FORGET_WIFINETWORK = 11;
    public static final int APKACTION_REASSOC_WIFI = 7;
    public static final int APKACTION_RECONNECT_WIFI = 6;
    public static final int APKACTION_RELEASE_WIFI_LOCK = 23;
    public static final int APKACTION_REMOVE_NETWORK = 17;
    public static final int APKACTION_REMOVE_PASSPOINT_CONF = 18;
    public static final int APKACTION_REMOVE_SUGGESTION = 21;
    public static final int APKACTION_REMOVE_WIFINETWORK = 12;
    public static final int APKACTION_SELECT_WIFINETWORK = 10;
    public static final int APKACTION_SET_HIDE_AP = 24;
    public static final int APKACTION_SET_P2P_CH_LIST = 26;
    public static final int APKACTION_SET_P2P_DEVICE_NAME = 25;
    public static final int APKACTION_SET_WIFI_AP_CONF = 14;
    public static final int APKACTION_UPDATE_NETWORK = 19;
    public static final int APKACTION_UPDATE_PASSPOINT_CONF = 16;
    public static final int APP_DISABLED_ABNORMAL_TYPE = 13;
    public static final int APP_DISABLED_SC_SUCC_TYPE = 17;
    public static final String ARP_REASSOC_OK = "ARP_REASSOC_OK";
    public static final String ARP_UNREACHABLE = "ARP_UNREACHABLE";
    public static final int ASSOC_REJECTED_ABNORMAL_TYPE = 11;
    public static final int ASSOC_REJECTED_SC_SUCC_TYPE = 15;
    public static final int AUTH_FAILED_ABNORMAL_TYPE = 10;
    public static final int AUTH_FAILED_SC_SUCC_TYPE = 14;
    public static final int BLACK_LIST_ABNORMAL_TYPE = 24;
    public static final int BLACK_LIST_SC_SUCC_TYPE = 23;
    public static final int CHR_CURRENT_STAT = 0;
    public static final int CHR_PREVIOUS_STAT = 1;
    public static final int CIPHER_SUITE_REJECTED = 24;
    public static final int CLASS2_FRAME_FROM_NONAUTH_STA = 6;
    public static final int CLASS3_FRAME_FROM_NONASSOC_STA = 7;
    public static final String CONNECT_INTERNET_INITIAL = "CONNECT_INTERNET_INITIAL";
    public static final String CONNECT_TYPE_APKCONNECT = "APKCONNECT_TO_SUCCESS";
    public static final String CONNECT_TYPE_BACKTOSERVICEZONE = "BACK_SERVICEZONE_TO_SUCCESS";
    public static final String CONNECT_TYPE_MANUALCONNECT = "MANUALCONNECT_TO_SUCCESS";
    public static final String CONNECT_TYPE_SCREENON = "SCREENON_TO_SUCCESS";
    public static final String CONNECT_TYPE_WIFION = "WIFION_TO_SUCCESS";
    public static final int CURRENT_CONNECT_TO_CELLULAR = 2;
    public static final int CURRENT_CONNECT_TO_WLAN = 1;
    public static final int DEAUTH_STA_IS_LEFING = 3;
    public static final int DEFAULT_REASON_CODE = -10;
    public static final int DHCP_FAILED_ABNORMAL_TYPE = 12;
    public static final int DHCP_FAILED_SC_SUCC_TYPE = 16;
    public static final int DHCP_FAILED_STATIC_SC_SUCC_TYPE = 25;
    public static final int DISASSOC_AP_BUSY = 5;
    public static final int DISASSOC_DUE_TO_INACTIVITY = 4;
    public static final int DISASSOC_LOW_ACK = 34;
    public static final int DISASSOC_STA_HAS_LEFT = 8;
    public static final int DNS_ABNORMAL_TYPE = 0;
    public static final String DNS_PARSE_FAILED = "DNS_PARSE_FAILED";
    public static final int DNS_RESET_SC_SUCC_TYPE = 20;
    public static final int DNS_SC_SUCC_TYPE = 4;
    public static final String FIRST_CONNECT_INTERNET_FAILED = "FIRST_CONNECT_INTERNET_FAILED";
    public static final int FOURWAY_HANDSHAKE_TIMEOUT = 15;
    public static final int GATEWAY_ABNORMAL_TYPE = 3;
    public static final int GROUP_CIPHER_NOT_VALID = 18;
    public static final int GROUP_KEY_UPDATE_TIMEOUT = 16;
    public static final int GW_RESET_SC_SUCC_TYPE = 22;
    public static final int HIDATA_USER_CLOSE_WIFI_USING_SETTINGS = 4;
    public static final int HIDATA_USER_CLOSE_WIFI_USING_UI = 3;
    public static final int HIDATA_USER_CONNECT_ANOTHER_AP_USING_SETTINGS = 11;
    public static final int HIDATA_USER_CONNECT_WIFI_USING_SETTINGS = 9;
    public static final int HIDATA_USER_DISCONECT_WIFI_USING_SETTINGS = 10;
    public static final int HIDATA_USER_OPEN_WIFI_USING_SETTINGS = 2;
    public static final int HIDATA_USER_OPEN_WIFI_USING_UI = 1;
    public static final int HW_DHCP_AUTO_IP = 16;
    public static final int HW_DHCP_FAIL = 4;
    public static final int HW_DHCP_RENEW_FAIL = 5;
    public static final int HW_DHCP_RENEW_START = 10;
    public static final int HW_DHCP_RENEW_SUCC = 3;
    public static final int HW_DHCP_START = 0;
    public static final int HW_DHCP_STATIC_IP = 8;
    public static final int HW_DHCP_STATIC_IP_SUCC = 9;
    public static final int HW_DHCP_STOP = 1;
    public static final int HW_DHCP_SUCC = 2;
    public static final int IEEE_802_1X_AUTH_FAILED = 23;
    public static final int IE_IN_4WAY_DIFFERS = 17;
    public static final int INVALID_IE = 13;
    public static final int INVALID_IP_ABNORMAL_TYPE = 29;
    public static final int INVALID_IP_SC_SUCC_TYPE = 30;
    public static final int INVALID_MICHAEL_MIC_FAILURE = 14;
    public static final int INVALID_RSN_IE_CAPAB = 22;
    public static final int NOT_CONNECT_TO_NETWORK = 0;
    public static final String ONLY_THE_TX_NO_RX = "ONLY_THE_TX_NO_RX";
    public static final int PAIRWISE_CIPHER_NOT_VALID = 19;
    public static final int PREV_AUTH_NOT_VALID = 2;
    public static final int PWR_CAPABILITY_NOT_VALID = 10;
    public static final int REASSOC_SC_CONNECT_FAILED_TYPE = 18;
    public static final int REASSOC_SC_SUCC_TYPE = 7;
    public static final int RESET_SC_CONNECT_FAILED_TYPE = 19;
    public static final int RESET_SC_SUCC_TYPE = 8;
    public static final int RE_DHCP_SC_SUCC_TYPE = 5;
    public static final int ROAMING_ABNORMAL_TYPE = 2;
    public static final int ROAMING_RESET_SC_SUCC_TYPE = 21;
    public static final int ROUTER_DISPLAY_NO_INTERNET_TYPE = 28;
    public static final int ROUTER_UNREACHABLE_TYPE = 27;
    public static final int STATIC_IP_CONFLICTED_SC_TYPE = 26;
    public static final int STATIC_IP_SC_SUCC_TYPE = 6;
    public static final int STA_REQ_ASSOC_WITHOUT_AUTH = 9;
    public static final int SUPPORTED_CHANNEL_NOT_VALID = 11;
    public static final int TCP_RX_ABNORMAL_TYPE = 1;
    public static final int TDLS_TEARDOWN_UNREACHABLE = 25;
    public static final int TDLS_TEARDOWN_UNSPECIFIED = 26;
    public static final int TRIGGER_APK_CONNECT = 6;
    public static final int TRIGGER_CONNECT_OTHER_AP = 1;
    public static final int TRIGGER_COUNT_BEYOND_THRESHOLD = 5;
    public static final int TRIGGER_DHCP_SUCCESS = 4;
    public static final int TRIGGER_DISABLE_NETWORK = 7;
    public static final int TRIGGER_OTHER = 0;
    public static final int TRIGGER_TIMEOUT = 8;
    public static final int TRIGGER_USER_FORGET_SSID = 2;
    public static final int TRIGGER_WIFI_OFF = 3;
    public static final int UNSPECIFIED = 1;
    public static final int UNSUPPORTED_RSN_IE_VERSION = 21;
    public static final int USER_ENABLE_STATIC_IP_TYPE = 9;
    public static final int WEAK_SIGNAL_THRESHOLD = -82;
    public static final int WIFI_ABS_ASSOC_FAILED_EVENT = 909002034;
    public static final int WIFI_ABS_STATISTICS_EVENT = 909002035;
    public static final int WIFI_ACCESS_INTERNET_FAILED = 909002024;
    public static final int WIFI_AP_INFO_COLLECT = 909002029;
    public static final int WIFI_CONNECT_ASSOC_FAILED = 909009004;
    public static final int WIFI_CONNECT_AUTH_FAILED = 909009003;
    public static final int WIFI_CONNECT_DHCP_FAILED = 909009002;
    public static final int WRKRND_SUB_TLS12 = 1;
}