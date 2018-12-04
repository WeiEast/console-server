package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.domain.BizTypeInfo;
import com.treefinance.saas.console.biz.domain.IdentifiedBizType;
import com.treefinance.saas.console.biz.domain.MonitoringBizType;
import com.treefinance.saas.console.biz.enums.MonitorTypeEnum;
import com.treefinance.saas.console.biz.service.AppBizTypeService;
import com.treefinance.saas.console.share.adapter.AbstractDomainObjectAdapter;
import com.treefinance.saas.console.web.vo.AppBizTypeNameValueVO;
import com.treefinance.saas.console.web.vo.AppBizTypeVO;
import com.treefinance.saas.console.web.vo.MonitoringBizTypeVO;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.toolkit.util.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/4.
 */
@RestController
@RequestMapping("/saas/console/biz")
public class AppBizTypeController extends AbstractDomainObjectAdapter {

    private final AppBizTypeService bizTypeService;

    @Autowired
    public AppBizTypeController(AppBizTypeService bizTypeService) {
        this.bizTypeService = bizTypeService;
    }

    @RequestMapping(value = "list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeVO>> listBizTypes() {
        List<IdentifiedBizType> list = bizTypeService.listIdentifiedBizTypes();

        List<AppBizTypeVO> result = this.convert(list, AppBizTypeVO.class);

        return Results.newSuccessResult(result);
    }

    /**
     * 查询此app拥有的服务权限类型
     *
     * @param appId appId
     * @return
     */
    @RequestMapping(value = "list/{appId}", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<AppBizTypeNameValueVO>> listBizTypeNameValues(@PathVariable("appId") String appId) {
        Preconditions.notBlank("appId", appId);
        List<BizTypeInfo> list = bizTypeService.listBizTypeInfosByAppId(appId);

        List<AppBizTypeNameValueVO> result = this.convert(list, AppBizTypeNameValueVO.class);

        return Results.newSuccessResult(result);
    }

    /**
     * 系统总任务量监控所用下拉
     *
     * @return
     */
    @RequestMapping(value = "task/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<MonitoringBizTypeVO>> listMonitoringBizTypes() {
        List<MonitoringBizType> list = bizTypeService.listMonitoringBizTypes(MonitorTypeEnum.TASK);

        List<MonitoringBizTypeVO> result = transformToMonitorBizTypeVO(list);

        return Results.newSuccessResult(result);
    }

    /**
     * 系统总访问量监控所用下拉
     *
     * @return
     */
    @RequestMapping(value = "task/access/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<MonitoringBizTypeVO>> getMerchantBaseAccessList() {
        List<MonitoringBizType> list = bizTypeService.listMonitoringBizTypes(MonitorTypeEnum.ACCESS);

        List<MonitoringBizTypeVO> result = transformToMonitorBizTypeVO(list);

        return Results.newSuccessResult(result);
    }

    /**
     * 商户任务总览详情页中的下拉
     *
     * @return
     */
    @RequestMapping(value = "task/access/detail/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<List<MonitoringBizTypeVO>> getMerchantBaseDetailAccessList() {
        List<MonitoringBizType> list = bizTypeService.listMonitoringBizTypes(MonitorTypeEnum.TOTAL);

        List<MonitoringBizTypeVO> result = transformToMonitorBizTypeVO(list);

        return Results.newSuccessResult(result);
    }

    private List<MonitoringBizTypeVO> transformToMonitorBizTypeVO(List<MonitoringBizType> list) {
        List<MonitoringBizTypeVO> result;
        if (CollectionUtils.isEmpty(list)) {
            result = Collections.emptyList();
        } else {
            result = list.stream().map(item -> {
                MonitoringBizTypeVO vo = new MonitoringBizTypeVO();
                vo.setBizName(item.getName());
                vo.setBizType(item.getBizType());
                return vo;
            }).collect(Collectors.toList());
        }
        return result;
    }


}
