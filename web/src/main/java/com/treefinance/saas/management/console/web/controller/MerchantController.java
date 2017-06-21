package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.MerchantService;
import com.treefinance.saas.management.console.common.domain.Result;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商户管理
 * Created by haojiahong on 2017/6/21.
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private static final Logger logger = LoggerFactory.getLogger(AppLicenseController.class);

    @Autowired
    private MerchantService merchantService;

    @RequestMapping("list")
    public Result<List<MerchantBaseVO>> getMerchantList() {
        return new Result<>(merchantService.getMerchantList());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result<Boolean> addMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        merchantService.addMerchant(merchantBaseVO);
        return new Result<>();
    }



}
