package com.huawei.security;

public class HwCredentials {
    public static final String CA_CERTIFICATE = "CACERT_";
    public static final String CERTIFICATE_CHAIN = "CERTCHAIN_";
    private static final String TAG = "HwCredentials";
    public static final String USER_CERTIFICATE = "USRCERT_";
    public static final String USER_PRIVATE_KEY = "USRPKEY_";

    public static boolean deleteAllTypesForAlias(HwKeystoreManager keystore, String alias) {
        return deleteAllTypesForAlias(keystore, alias, -1);
    }

    public static boolean deleteAllTypesForAlias(HwKeystoreManager keystore, String alias, int uid) {
        return deletePrivateKeyTypeForAlias(keystore, alias, uid) && deleteCertificateTypesForAlias(keystore, alias, uid);
    }

    public static boolean deleteCertificateTypesForAlias(HwKeystoreManager keystore, String alias) {
        return deleteCertificateTypesForAlias(keystore, alias, -1);
    }

    public static boolean deleteCertificateTypesForAlias(HwKeystoreManager keystore, String alias, int uid) {
        return keystore.delete(CERTIFICATE_CHAIN + alias, uid);
    }

    public static boolean deletePrivateKeyTypeForAlias(HwKeystoreManager keystore, String alias) {
        return deletePrivateKeyTypeForAlias(keystore, alias, -1);
    }

    public static boolean deletePrivateKeyTypeForAlias(HwKeystoreManager keystore, String alias, int uid) {
        return keystore.delete(USER_PRIVATE_KEY + alias, uid);
    }
}
