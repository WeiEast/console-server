package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.MerchantService;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "list", produces = "application/json")
    public Result<Map<String, Object>> getMerchantList(PageRequest request) {
        //todo  分页
        Map<String, Object> map = merchantService.getMerchantList(request);
        return Results.newSuccessPageResult(request, 1, map.get("data"));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Map<String, Object>> addMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        Map<String, Object> map = merchantService.addMerchant(merchantBaseVO);
        return Results.newSuccessResult(map);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Boolean> updateMerchant(@RequestBody MerchantBaseVO merchantBaseVO, @PathVariable Long id) {
        merchantService.updateMerchant(merchantBaseVO, id);
        return Results.newSuccessResult(Boolean.TRUE);
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET, produces = "application/json")
    public Result<MerchantBaseVO> getMerchantById(@PathVariable Long id) {
        MerchantBaseVO merchantBaseVO = merchantService.getMerchantById(id);
        return Results.newSuccessResult(merchantBaseVO);

    }

    @RequestMapping(value = "reset/pwd/{id}", method = RequestMethod.GET, produces = "application/json")
    public Result<String> resetPassword(@PathVariable Long id) {
        String plainTextPwd = merchantService.resetPassWord(id);
        return Results.newSuccessResult(plainTextPwd);

    }


}
