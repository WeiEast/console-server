package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
@RestController
@RequestMapping("/saas/console/biz")
public class AppBizTypeController {

    @Autowired
    private AppBizTypeService appBizTypeService;

    @RequestMapping(value = "list", produces = "application/json", method = RequestMethod.GET)
    public Result<List<AppBizTypeVO>> getMerchantBaseList() {
        List<AppBizTypeVO> result = appBizTypeService.getBizList();
        return Results.newSuccessResult(result);
    }

}
