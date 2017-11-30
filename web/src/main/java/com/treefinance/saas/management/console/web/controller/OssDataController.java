package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.OssDataService;
import com.treefinance.saas.management.console.common.domain.request.OssDataRequest;
import com.treefinance.saas.management.console.common.exceptions.BizException;
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
            throw new BizException("参数不能为空");
        }
        if (request.getType() == null) {
            throw new BizException("参数type不能为空");
        }
        if (StringUtils.isBlank(request.getAccountNo()) && request.getTaskId() == null &&
                StringUtils.isBlank(request.getAppName()) && StringUtils.isBlank(request.getUniqueId())) {
            throw new BizException("uniqueId、accountNo、taskId和appName不能同时为空");
        }
        return ossDataService.getOssCallbackDataList(request);
    }


    /**
     * 判断是否可以正确下载
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/download/check", method = RequestMethod.GET)
    public Object downloadOssDataCheck(Long id) {
        if (id == null) {
            throw new BizException("id不能为空");
        }
        return ossDataService.downloadOssDataCheck(id);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Object downloadOssData(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (id == null) {
            throw new BizException("id不能为空");
        }
        return ossDataService.downloadOssData(id, request, response);
    }

}
