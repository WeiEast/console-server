package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.management.console.biz.service.MerchantService;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商户管理
 * Created by haojiahong on 2017/6/21.
 */
@RestController
@RequestMapping("/saas/console/merchant")
public class MerchantController {
    private static final Logger logger = LoggerFactory.getLogger(AppLicenseController.class);

    @Autowired
    private MerchantService merchantService;
    @Autowired
    EcommerceMonitorService ecommerceMonitorService;

    @RequestMapping(value = "list", produces = "application/json")
    public SaasResult<Map<String, Object>> getMerchantList(PageRequest request) {
        SaasResult<Map<String, Object>> result = merchantService.getMerchantList(request);
        return result;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public SaasResult<Map<String, Object>> addMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        Map<String, Object> map = merchantService.addMerchant(merchantBaseVO);
        return Results.newSuccessResult(map);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public SaasResult<Boolean> updateMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        merchantService.updateMerchant(merchantBaseVO);
        return Results.newSuccessResult(Boolean.TRUE);
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<MerchantBaseVO> getMerchantById(@PathVariable Long id) {
        MerchantBaseVO merchantBaseVO = merchantService.getMerchantById(id);
        return Results.newSuccessResult(merchantBaseVO);

    }

    @RequestMapping(value = "reset/pwd/{id}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<String> resetPassword(@PathVariable Long id) {
        String plainTextPwd = merchantService.resetPassWord(id);
        return Results.newSuccessResult(plainTextPwd);
    }

    @RequestMapping(value = "toggle/active", method = RequestMethod.POST, produces = "application/json")
    public SaasResult<Boolean> disableMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        Byte zero = new Byte("0");
        Byte one = new Byte("1");
        if (StringUtils.isBlank(merchantBaseVO.getAppId())) {
            throw new BizException("appId不能为空!");
        }
        if (merchantBaseVO.getIsActive() == null) {
            throw new BizException("app名称不能为空!");
        }
        if ((!zero.equals(merchantBaseVO.getIsActive()) && !one.equals(merchantBaseVO.getIsActive()))) {
            throw  new BizException("isActive不合法");
        }



        return merchantService.toggleMerchant(merchantBaseVO.getAppId(),merchantBaseVO.getIsActive());
    }

    /**
     * 获取简单入住的app列表
     *
     * @return
     */
    @RequestMapping(value = "simple/list", produces = "application/json")
    public SaasResult<List<MerchantSimpleVO>> getMerchantBaseList() {
        List<MerchantSimpleVO> result = merchantService.getMerchantBaseList();
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "reset/key/{id}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Boolean> resetKey(@PathVariable Long id) {
        merchantService.resetAppLicenseKey(id);
        return Results.newSuccessResult(Boolean.TRUE);

    }

    @RequestMapping(value = "generate/appid", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<String> generateAppId() {
        String result = merchantService.autoGenerateAppId();
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "generate/pwd/{str}", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<String> getCipherTextPassword(@PathVariable String str) {
        String result = merchantService.generateCipherTextPassword(str);
        return Results.newSuccessResult(result);
    }


    @RequestMapping(value = "stat/merchant/list", method = RequestMethod.GET)
    public Object queryAllEcommerceMonitor(Integer bizType) {
        if (bizType == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        logger.info("根据bizType电商列表查询   传入参数为{}", bizType);
        return ecommerceMonitorService.queryAllEcommerceListByBizType(bizType);
    }


}
