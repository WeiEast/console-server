package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.service.DataApiConfigService;
import com.treefinance.saas.console.biz.service.DsDataApiRawResultSerivce;
import com.treefinance.saas.console.biz.service.TpApiConfigService;
import com.treefinance.saas.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/saas/console/ds/")
public class DataServerController {

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
        if(dsDataApiRequest.getStartDate().after(dsDataApiRequest.getEndDate())){
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "开始时间不能晚于结束时间");
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

    @RequestMapping(value = "/dsappid", method = {RequestMethod.GET})
    public Object dsappid(String dataApiNameType) {
        if (dataApiNameType == null){
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK,"接口类型不能为空");
        }
        return dataApiConfigService.getDsAppid(dataApiNameType);
    }



}
