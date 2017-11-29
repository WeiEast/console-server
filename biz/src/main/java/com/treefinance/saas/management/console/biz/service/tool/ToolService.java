package com.treefinance.saas.management.console.biz.service.tool;

/**
 * Created by haojiahong on 2017/11/29.
 */
public interface ToolService {
    /**
     * 数据加密
     *
     * @param param
     * @return
     */
    Object cryptoEncryptDataList(String param);

    /**
     * 数据解密
     *
     * @param param
     * @return
     */
    Object cryptoDecryptDataList(String param);
}
