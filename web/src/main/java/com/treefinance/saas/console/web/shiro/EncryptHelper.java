package com.treefinance.saas.console.web.shiro;


import com.treefinance.toolkit.util.crypto.AES;
import com.treefinance.toolkit.util.crypto.exception.CryptoException;

/**
 * Created by haojiahong on 2017/7/4.
 */
public class EncryptHelper {

    private static String M_KEY = "UcWjc8tEsOvitxszgsdUdw==";

    public static String encrypt(String text) {
        String encryptText = null;
        try {
            encryptText = AES.createEncryptor(M_KEY).encryptAsBase64String(text);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    public static String decrypt(String text) {
        String decryptText = null;
        try {
            decryptText = AES.createDecryptor(M_KEY).decryptWithBase64AsString(text);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return decryptText;
    }

}
