package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.b2b.saas.util.RemoteDataUtils;
import com.treefinance.saas.console.util.CallbackDataUtils;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * @param dataUrl
     * @param key
     * @return
     */
    @RequestMapping(value = "download", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<String> downloadData(@RequestParam("dataUrl") String dataUrl,
                                           @RequestParam("key") String key) {
        // oss 下载数据
        try {
            byte[] result = RemoteDataUtils.download(dataUrl, byte[].class);
            // 数据体默认使用商户密钥加密
            String data = CallbackDataUtils.decryptByAES(result, key);
            return Results.newSuccessResult(data);
        } catch (Exception e) {
            logger.error("downloadData failed", e);
            return Results.newFailedResult(e.getMessage(), CommonStateCode.FAILURE);
        }
    }

    @RequestMapping(value = "/rsa/decrypt", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Object> decryptRSAData(@RequestParam("data") String data,
                                             @RequestParam("key") String key) {
        try {
            data = URLDecoder.decode(data, "utf-8");
            data = CallbackDataUtils.decrypt(data, key);
            return Results.newSuccessResult(JSON.parse(data));
        } catch (Exception e) {
            logger.error("decryptRSAData failed", e);
            return Results.newFailedResult(e.getMessage(), CommonStateCode.FAILURE);
        }
    }

    @RequestMapping(value = "/rsa/encrypt", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Object> encryptRSAData(@RequestParam("data") String data,
                                             @RequestParam("key") String key) {
        try {
            data = CallbackDataUtils.encrypt(data, key);
            return Results.newSuccessResult(URLEncoder.encode(data, "utf-8"));
        } catch (Exception e) {
            logger.error("encryptRSAData failed", e);
            return Results.newFailedResult(e.getMessage(), CommonStateCode.FAILURE);
        }
    }


}
