package com.treefinance.saas.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.console.common.domain.request.OperatorStatRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:40
 */
@RestController
@RequestMapping("/saas/console/ecommerce/stat/")
public class EcommerceStatController {

    private static Logger logger = LoggerFactory.getLogger(EcommerceStatController.class);

    @Autowired
    private EcommerceMonitorService ecommerceMonitorService;



    @RequestMapping(value = "all/detail/list", method = RequestMethod.GET, produces = "application/json")
    public Object queryDivisionEcommerceMonitor(OperatorStatRequest request) {

        logger.info("电商分时详细查询 Controller层  传入参数为{}", JSON.toJSONString(request));
        if (request.getDataDate() == null || request.getStatType() == null ||request.getSourceType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }

        return ecommerceMonitorService.queryDivisionEcommerceMonitorList(request);

    }
    @RequestMapping(value ="all/day/list", method = RequestMethod.GET, produces = "application/json")
    public Object queryAllEcommerceMonitor(OperatorStatRequest request) {

        logger.info("电商整体查询 Controller层  传入参数为{}", JSON.toJSONString(request));
        if (request.getStartDate() == null ||request.getEndDate() == null ||request.getSourceType() == null || request.getStatType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }

        return ecommerceMonitorService.queryAllEcommerceMonitorList(request);

    }






}
