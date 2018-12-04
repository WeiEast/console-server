package com.treefinance.saas.console.util;

import com.treefinance.toolkit.util.crypto.AES;
import com.treefinance.toolkit.util.crypto.core.EnhancedDecryptor;
import com.treefinance.toolkit.util.crypto.core.EnhancedEncryptor;
import com.treefinance.toolkit.util.crypto.exception.CryptoException;

/**
 * @author Jerry
 * @date 2018/11/28 00:31
 */
public final class AesUtils {

    private AesUtils() {}

    private static final AES FACTORY = AES.of("CBC", "PKCS5Padding");

    /**
     * 加密
     *
     * @param key the key used to encrypt data
     * @param data the data need to encrypt
     * @return the encrypted data byte array
     * @throws CryptoException when invoking encryption failure
     */
    public static byte[] encrypt(String key, byte[] data) throws CryptoException {
        EnhancedEncryptor encryptor = FACTORY.getEncryptor(key);

        return encryptor.encrypt(data);
    }

    /**
     * 解密
     *
     * @param key the key used to decrypt data
     * @param data the data need to decrypt
     * @return the decrypted data string
     * @throws CryptoException when invoking decryption failure
     */
    public static String decrypt(String key, byte[] data) throws CryptoException {
        EnhancedDecryptor decryptor = FACTORY.getDecryptor(key);

        return decryptor.decryptAsString(data);
    }
}
