package com.treefinance.saas.management.console.web.shiro;

import com.datatrees.toolkits.util.crypto.AES;
import com.datatrees.toolkits.util.crypto.exception.CryptoException;

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


    public static void main(String[] args) {
        String text = "123456";
//加密
        String encrptText = null;
        try {
            encrptText = AES.createEncryptor(M_KEY).encryptAsBase64String(text);
            System.out.println(encrptText);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
//解密
        try {
            String text2 = AES.createDecryptor(M_KEY).decryptWithBase64AsString(encrptText);
            System.out.println(text2);
        } catch (CryptoException e) {
            e.printStackTrace();
        }

    }
}
