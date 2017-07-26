package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.management.console.common.CallbackSecureHandler;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.monitor.common.utils.RemoteDataDownloadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by yh-treefinance on 2017/7/18.
 */
@RestController
@RequestMapping("/saas/console/data/")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    protected CallbackSecureHandler callbackSecureHandler;

    /**
     * @param dataUrl
     * @param key
     * @return
     */
    @RequestMapping(value = "download", produces = "application/json", method = RequestMethod.POST)
    public Result<String> downloadData(@RequestParam("dataUrl") String dataUrl,
                                       @RequestParam("key") String key) {
        // oss 下载数据
        byte[] result = new byte[0];
        try {
            result = RemoteDataDownloadUtils.download(dataUrl, byte[].class);
            // 数据体默认使用商户密钥加密
            String data = callbackSecureHandler.decryptByAES(result, key);
            return Results.newSuccessResult(data);
        } catch (Exception e) {
            logger.error("downloadData failed", e);
            return Results.newFailedResult(e.getMessage(), CommonStateCode.FAILURE);
        }
    }

    @RequestMapping(value = "/rsa/decrypt", produces = "application/json", method = RequestMethod.POST)
    public Result<Object> decryptRSAData(@RequestParam("data") String data,
                                         @RequestParam("key") String key) {
        try {
            data = URLDecoder.decode(data, "utf-8");
            data = callbackSecureHandler.decrypt(data, key);
            return Results.newSuccessResult(JSON.parse(data));
        } catch (Exception e) {
            logger.error("downloadData failed", e);
            return Results.newFailedResult(e.getMessage(), CommonStateCode.FAILURE);
        }
    }

}
