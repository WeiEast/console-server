package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.MerchantService;
import com.treefinance.saas.management.console.common.domain.Result;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
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
@RequestMapping("/saas/backend/merchant")
public class MerchantController {
    private static final Logger logger = LoggerFactory.getLogger(AppLicenseController.class);

    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "list", produces = "application/json")
    public Result<List<MerchantBaseVO>> getMerchantList() {
        return new Result<>(merchantService.getMerchantList());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Map<String, Object>> addMerchant(@RequestBody MerchantBaseVO merchantBaseVO) {
        Map<String, Object> map = merchantService.addMerchant(merchantBaseVO);
        return new Result<>(map);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public Result<Boolean> updateMerchant(@RequestBody MerchantBaseVO merchantBaseVO, @PathVariable Long id) {
        merchantService.updateMerchant(merchantBaseVO, id);
        return new Result<>(Boolean.TRUE);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public Result<MerchantBaseVO> getMerchantById(@PathVariable Long id) {
        MerchantBaseVO merchantBaseVO = merchantService.getMerchantById(id);
        return new Result<>(merchantBaseVO);

    }


}
