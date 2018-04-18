package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
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
    public SaasResult<List<AppBizTypeVO>> getBizList() {
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
    public SaasResult<List<AppBizTypeVO>> getBizList(@PathVariable("appId") String appId) {
        List<AppBizTypeVO> result = appBizTypeService.getBizListByAppId(appId);
        return Results.newSuccessResult(result);
    }

    /**
     * 系统总任务量监控所用下拉
     *
     * @return
     */
    @RequestMapping(value = "task/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeVO>> getMerchantBaseList() {
        List<AppBizTypeVO> result = appBizTypeService.getTaskBizTypeList();
        return Results.newSuccessResult(result);
    }

    /**
     * 系统总访问量监控所用下拉
     *
     * @return
     */
    @RequestMapping(value = "task/access/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeVO>> getMerchantBaseAccessList() {
        List<AppBizTypeVO> result = appBizTypeService.getAccessTaskBizTypeList();
        return Results.newSuccessResult(result);
    }

    /**
     * 商户任务总览详情页中的下拉
     *
     * @return
     */
    @RequestMapping(value = "task/access/detail/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeVO>> getMerchantBaseDetailAccessList() {
        List<AppBizTypeVO> result = appBizTypeService.getAccessTaskDetailBizTypeList();
        return Results.newSuccessResult(result);
    }


}
