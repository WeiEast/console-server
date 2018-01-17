package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:40
 */
@RestController
@RequestMapping("/saas/console/ecommerce/stat/")
public class EcmmerceStatController {

    private static Logger logger = LoggerFactory.getLogger(EcmmerceStatController.class);

    @Autowired
    EcommerceMonitorService ecommerceMonitorService;



    @RequestMapping(value = "all/detail/list", method = RequestMethod.POST, produces = "application/json")
    public Object queryDivisionEcommerceMonitor(@RequestBody OperatorStatRequest request) {

        logger.info("电商分时详细查询 Controller层  传入参数为{}", request.toString());
        return ecommerceMonitorService.queryDivisionEcommerceMonitorList(request);

    }
    @RequestMapping(value ="all/day/list", method = RequestMethod.POST, produces = "application/json")
    public Object queryAllEcommerceMonitor(@RequestBody OperatorStatRequest request) {

        logger.info("电商整体查询 Controller层  传入参数为{}", request.toString());
        return ecommerceMonitorService.queryAllEcommerceMonitorList(request);

    }
    @RequestMapping(value ="merchant/list" ,method = RequestMethod.GET)
    public Object queryAllEcommerceMonitor(Integer bizType) {

        logger.info("电商列表查询 Controller层  传入参数为{}", bizType);
        return ecommerceMonitorService.queryAllEcommerceListByBizType(bizType);

    }






}
