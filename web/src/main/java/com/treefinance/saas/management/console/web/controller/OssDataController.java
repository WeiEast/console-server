package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.OssDataService;
import com.treefinance.saas.management.console.common.domain.request.OssDataRequest;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by haojiahong on 2017/11/21.
 */
@RestController
@RequestMapping("/saas/console/oss/data")
public class OssDataController {

    @Autowired
    private OssDataService ossDataService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getOssList(OssDataRequest request) {
        if (request == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "参数不能为空");
        }
        if (request.getType() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "参数type不能为空");
        }
        if (StringUtils.isBlank(request.getAccountNo()) && request.getTaskId() == null &&
                StringUtils.isBlank(request.getAppName()) && StringUtils.isBlank(request.getUniqueId())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "uniqueId、accountNo、taskId和appName不能同时为空");
        }
        return ossDataService.getOssCallbackDataList(request);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Object downloadOssData(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "id不能为空");
        }
        return ossDataService.downloadOssData(id, request, response);
    }


}
