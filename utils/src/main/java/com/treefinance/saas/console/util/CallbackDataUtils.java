package com.treefinance.saas.console.util;

import com.treefinance.b2b.saas.util.AesUtils;
import com.treefinance.b2b.saas.util.DataUtils;
import com.treefinance.toolkit.util.crypto.exception.CryptoException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luoyihua
 * @date 2017/5/10.
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
    public static String encrypt(String data, String publicKey) throws CallbackDataCryptoException {
        if (StringUtils.isEmpty(data)) {
            return StringUtils.EMPTY;
        }

        try {
            String encryptedData = DataUtils.encryptBeanAsBase64StringByRsa(data, publicKey);

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
    public static String decrypt(String data, String privateKey) throws CallbackDataCryptoException {
        if (StringUtils.isEmpty(data)) {
            return StringUtils.EMPTY;
        }

        try {
            String decryptedData = DataUtils.decryptWithBase64AsStringByRsa(data, privateKey);

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
            return AesUtils.decryptAsString(data, dataKey);
        } catch (CryptoException e) {
            throw new CallbackDataCryptoException("decryptByAES exception", e);
        }
    }

}
