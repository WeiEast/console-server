package com.treefinance.saas.management.console.web.controller.tool;

import com.treefinance.saas.management.console.biz.service.tool.ToolService;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haojiahong on 2017/11/29.
 */
@RestController
@RequestMapping("/saas/console/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;


    @RequestMapping(value = "/knife/crypto/encrypt", method = RequestMethod.POST)
    public Object encryptDataList(@RequestParam("param") String param) {
        if (StringUtils.isBlank(param)) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "param不能为空");
        }
        return toolService.cryptoEncryptDataList(param);
    }


    @RequestMapping(value = "/knife/crypto/decrypt", method = RequestMethod.POST)
    public Object decryptDataList(@RequestParam("param") String param) {
        if (StringUtils.isBlank(param)) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "param不能为空");
        }
        return toolService.cryptoDecryptDataList(param);
    }



}
