package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.DataApiConfigService;
import com.treefinance.saas.management.console.biz.service.DsDataApiRawResultSerivce;
import com.treefinance.saas.management.console.biz.service.TpApiConfigService;
import com.treefinance.saas.management.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/saas/console/ds/")
public class DataServerController {

    private static final Logger logger = LoggerFactory.getLogger(DataServerController.class);

    @Autowired
    private DsDataApiRawResultSerivce dsDataApiRawResultSerivce;
    @Autowired
    private DataApiConfigService dataApiConfigService;
    @Autowired
    private TpApiConfigService tpApiConfigService;

    @RequestMapping(value = "/query/dataapirawresult", method = {RequestMethod.GET})
    public Object dsDataApiRawResults(DsDataApiRequest dsDataApiRequest) {
        if (dsDataApiRequest == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (dsDataApiRequest.getDataApiNameType() == null){
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK,"接口类型不能为空");
        }
        if (dsDataApiRequest.getStartDate() == null || dsDataApiRequest.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        return dsDataApiRawResultSerivce.findPageByExample(dsDataApiRequest);
    }

    @RequestMapping(value = "/dataapiconfig", method = {RequestMethod.GET})
    public Object dataApiConfig() {
      return dataApiConfigService.getDsApiConfigType();
    }

    @RequestMapping(value = "/tpapiconfig", method = {RequestMethod.GET})
    public Object tpApiConfig() {
       return tpApiConfigService.getTpApiConfigType();
    }


}
