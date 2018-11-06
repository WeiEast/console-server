package com.treefinance.saas.management.console.web.controller.tool;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.tool.ToolService;
import com.treefinance.saas.management.console.common.domain.vo.tool.KnifeCryptoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加解密
 * Created by haojiahong on 2017/11/29.
 */
@RestController
@RequestMapping("/saas/console/tool")
public class ToolController {

    private static final Logger logger = LoggerFactory.getLogger(ToolController.class);

    @Autowired
    private ToolService toolService;

    @RequestMapping(value = "/knife/crypto/encrypt", method = RequestMethod.POST)
    public Object encryptDataList(@RequestBody KnifeCryptoVO paramVO) {
        if (paramVO == null || StringUtils.isBlank(paramVO.getParam())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "param不能为空");
        }
        logger.info("小工具-加解密:param={}", JSON.toJSONString(paramVO));
        return toolService.cryptoEncryptDataList(paramVO.getParam());
    }

    @RequestMapping(value = "/knife/crypto/decrypt", method = RequestMethod.POST)
    public Object decryptDataList(@RequestBody KnifeCryptoVO paramVO) {
        if (paramVO == null || StringUtils.isBlank(paramVO.getParam())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "param不能为空");
        }
        logger.info("小工具-加解密:param={}", JSON.toJSONString(paramVO));
        return toolService.cryptoDecryptDataList(paramVO.getParam());
    }

}
