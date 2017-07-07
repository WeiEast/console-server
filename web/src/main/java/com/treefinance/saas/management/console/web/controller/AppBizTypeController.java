package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Result<List<AppBizTypeVO>> getBizList() {
        List<AppBizTypeVO> result = appBizTypeService.getBizList();
        return Results.newSuccessResult(result);
    }

    /**
     * 查询此app拥有的服务权限类型
     *
     * @param appId appId
     * @return
     */
    @RequestMapping(value = "list/{appId}", produces = "application/json", method = RequestMethod.GET)
    public Result<List<AppBizTypeVO>> getBizList(@PathVariable("appId") String appId) {
        List<AppBizTypeVO> result = appBizTypeService.getBizListByAppId(appId);
        return Results.newSuccessResult(result);
    }

    /**
     * 系统总任务量监控所用下拉
     *
     * @return
     */
    @RequestMapping(value = "task/list", produces = "application/json", method = RequestMethod.GET)
    public Result<List<AppBizTypeVO>> getMerchantBaseList() {
        List<AppBizTypeVO> result = appBizTypeService.getTaskBizTypeList();
        return Results.newSuccessResult(result);
    }


}
