package com.treefinance.saas.management.console.biz.service.tool.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.management.console.biz.service.tool.ToolService;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/11/29.
 */
@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;

    @Override
    public Object cryptoEncryptDataList(String param) {
        List<String> result = Lists.newArrayList();
        List<String> paramStrList = Splitter.on(",").splitToList(param);
        if (CollectionUtils.isEmpty(paramStrList)) {
            return Results.newSuccessResult(result);
        }
        Map<String, String> map = iSecurityCryptoService.batchEncrypt(paramStrList, EncryptionIntensityEnum.NORMAL);
        for (String str : paramStrList) {
            String encryptStr = map.get(str);
            result.add(encryptStr);
        }

        return Results.newSuccessResult(result);
    }

    @Override
    public Object cryptoDecryptDataList(String param) {
        List<String> result = Lists.newArrayList();
        List<String> paramStrList = Splitter.on(",").splitToList(param);
        if (CollectionUtils.isEmpty(paramStrList)) {
            return Results.newSuccessResult(result);
        }
        Map<String, String> map = iSecurityCryptoService.batchDecrypt(paramStrList, EncryptionIntensityEnum.NORMAL);
        for (String str : paramStrList) {
            String decryptStr = map.get(str);
            result.add(decryptStr);
        }
        return Results.newSuccessResult(result);

    }
}
