package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.management.console.common.result.Results;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商户流量分配配置
 * Created by haojiahong on 2017/9/28.
 */
@RestController
@RequestMapping("/saas/console/merchant/flow")
public class MerchantFlowConfigController {

    @Autowired
    private MerchantFlowConfigService merchantFlowConfigService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json")
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
    public void batchUpdate(@RequestBody List<MerchantFlowConfigVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("parameter is error");
        }
        for (MerchantFlowConfigVO vo : list) {
            if (vo.getId() == null || StringUtils.isBlank(vo.getAppId()) || StringUtils.isBlank(vo.getServiceTag())) {
                throw new IllegalArgumentException("parameter is error");
            }
        }
        merchantFlowConfigService.batchUpdate(list);
    }

    /**
     * 初始化,所有商户初始化为product的serviceTag
     */
    @RequestMapping(value = "/init")
    public void init() {
        merchantFlowConfigService.init();
    }


}