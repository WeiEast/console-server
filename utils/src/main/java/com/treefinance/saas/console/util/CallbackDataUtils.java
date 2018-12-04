package com.treefinance.saas.console.util;

import com.alibaba.fastjson.JSON;
import com.treefinance.toolkit.util.crypto.RSA;
import com.treefinance.toolkit.util.crypto.core.Decryptor;
import com.treefinance.toolkit.util.crypto.core.Encryptor;
import com.treefinance.toolkit.util.crypto.exception.CryptoException;
import com.treefinance.toolkit.util.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luoyihua on 2017/5/10.
 */
public final class CallbackDataUtils {
    private static final Logger logger = LoggerFactory.getLogger(CallbackDataUtils.class);

    private CallbackDataUtils() {}

    /**
     * RSA 加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws CallbackDataCryptoException
     */
    public static String encrypt(Object data, String publicKey) throws CallbackDataCryptoException {
        if (data == null) {
            return null;
        }

        try {
            byte[] json = Jackson.toJSONByteArray(data);

            String encryptedData = getEncryptor(publicKey).encryptAsBase64String(json);
            logger.debug("Finish encrypting callback for encryptedData '{}'.", encryptedData);

            return encryptedData;
        } catch (CryptoException e) {
            throw new CallbackDataCryptoException("encryptByRSA exception", e);
        }
    }

    /**
     * RSA 解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws CallbackDataCryptoException
     */
    public static String decrypt(Object data, String privateKey) throws CallbackDataCryptoException {
        if (data == null) {
            return null;
        }

        try {
            byte[] json = Jackson.toJSONByteArray(data);

            String decryptedData = getDecryptor(privateKey).decryptWithBase64AsString(json);
            logger.debug("Finish decrypting callback for decryptedData '{}'.", decryptedData);

            return decryptedData;
        } catch (CryptoException e) {
            throw new CallbackDataCryptoException("decryptByRSA exception", e);
        }
    }

    /**
     * AES 解密
     *
     * @param data
     * @param dataKey
     * @return
     */
    public static String decryptByAES(byte[] data, String dataKey) throws CallbackDataCryptoException {
        try {
            return AesUtils.decrypt(dataKey, data);
        } catch (CryptoException e) {
            throw new CallbackDataCryptoException("decryptByAES exception", e);
        }
    }

    /**
     * AES 加密
     *
     * @param data
     * @param dataKey
     * @return
     */
    public static String encryptByAES(Object data, String dataKey) throws CallbackDataCryptoException {
        try {
            byte[] input = JSON.toJSONBytes(data);
            byte[] result = AesUtils.encrypt(dataKey, input);
            return new String(result);
        } catch (CryptoException e) {
            throw new CallbackDataCryptoException("encryptByAES exception", e);
        }
    }

    private static Encryptor getEncryptor(String publicKey) {
        if (StringUtils.isEmpty(publicKey)) {
            throw new IllegalArgumentException("Can not find commercial tenant's public key.");
        }

        return RSA.createEncryptor(publicKey);
    }

    private static Decryptor getDecryptor(String privateKey) {
        if (StringUtils.isEmpty(privateKey)) {
            throw new IllegalArgumentException("Can not find commercial tenant's private key.");
        }

        return RSA.createDecryptor(privateKey);
    }

}
