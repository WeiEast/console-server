package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowAllotVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户流量分配配置
 * Created by haojiahong on 2017/9/28.
 */
@RestController
@RequestMapping("/saas/console/merchant/flow")
@Api(description = "商户流量配置相关文档")
public class MerchantFlowConfigController {

    @Autowired
    private MerchantFlowConfigService merchantFlowConfigService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json",method = {RequestMethod.GET,RequestMethod.POST})
    public Object getList() {
        List<MerchantFlowConfigVO> list = merchantFlowConfigService.getList();
        return Results.newSuccessResult(list);
    }

    /**
     * 批量更新
     *
     * @param list
     */
    @RequestMapping(value = "/update", produces = "application/json", method = RequestMethod.POST)
    public Object batchUpdate(@RequestBody List<MerchantFlowConfigVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("parameter is error");
        }
        for (MerchantFlowConfigVO vo : list) {
            if (vo.getId() == null || StringUtils.isBlank(vo.getServiceTag())) {
                throw new IllegalArgumentException("parameter is error");
            }
        }
        merchantFlowConfigService.batchUpdate(list);
        return Results.newSuccessResult(true);
    }

    /**
     * 初始化,将为配置的商户初始化为product的serviceTag
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public Object init() {
        merchantFlowConfigService.init();
        return Results.newSuccessResult(true);
    }

    @ApiOperation(value = "获取商户流量配置列表")
    @RequestMapping(value = "/allot/list",method = {RequestMethod.GET,RequestMethod.POST})
    public SaasResult<List<MerchantFlowAllotVO>> getAllotList() {




        return Results.newSuccessResult(new ArrayList<>());
    }
    @ApiOperation(value = "获取商户流量配置列表")
    @RequestMapping(value = "/allot/update",method = {RequestMethod.POST})
    @ApiImplicitParam(name = "merchantFlowAllotVO",value = "",required = true)
    public SaasResult<List<MerchantFlowAllotVO>> updateMerchantAllot(@RequestBody MerchantFlowAllotVO merchantFlowAllotVO) {





        return Results.newSuccessResult(new ArrayList<>());
    }

}
