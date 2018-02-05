package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.OperatorStatService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.*;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.monitor.facade.domain.request.OperatorStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorAllStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorAllStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatDayAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.OperatorStatAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/11/1.
 */
@Service
public class OperatorStatServiceImpl implements OperatorStatService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorStatService.class);
    @Autowired
    private OperatorStatAccessFacade operatorStatAccessFacade;
    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;


    @Override
    public Object queryAllOperatorStatDayAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        MonitorResult<List<OperatorAllStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryAllOperatorStatDayAccessListWithPage(rpcRequest);
        List<AllOperatorStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, result);
        }
        result = BeanUtils.convertList(rpcResult.getData(), AllOperatorStatDayAccessVO.class);
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryAllOperatorStatAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(DateUtils.getTodayBeginDate(request.getDataDate()));
        rpcRequest.setEndDate(DateUtils.getTodayEndDate(request.getDataDate()));
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setIntervalMins(30);
        MonitorResult<List<OperatorAllStatAccessRO>> rpcResult = operatorStatAccessFacade.queryAllOperatorStaAccessList(rpcRequest);
        List<AllOperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessResult(result);
        }
        result = BeanUtils.convertList(rpcResult.getData(), AllOperatorStatAccessVO.class);
        return Results.newSuccessResult(result);
    }

    @Override
    public Object queryAllOperatorStatAccessSomeTimeList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setDataDate(request.getDataTime());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setIntervalMins(30);
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatHourAccessListWithPage(rpcRequest);
        List<OperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, result);
        }
        result = BeanUtils.convertList(rpcResult.getData(), OperatorStatAccessVO.class);
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryOperatorStatDayAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setDataDate(request.getDataDate());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setGroupName(request.getGroupName());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        MonitorResult<List<OperatorStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatDayAccessListWithPage(rpcRequest);
        List<OperatorStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, result);
        }
        result = BeanUtils.convertList(rpcResult.getData(), OperatorStatDayAccessVO.class);
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryOperatorStatDayDetailAccessList(OperatorStatRequest request) {

        OperatorStatAccessRequest rpcDayRequest = new OperatorStatAccessRequest();
        rpcDayRequest.setStartDate(request.getStartDate());
        rpcDayRequest.setEndDate(request.getEndDate());
        rpcDayRequest.setPageSize(request.getPageSize());
        rpcDayRequest.setPageNumber(request.getPageNumber());
        rpcDayRequest.setGroupCode(request.getGroupCode());
        rpcDayRequest.setStatType(request.getStatType());
        rpcDayRequest.setAppId(request.getAppId());
        MonitorResult<List<OperatorStatDayAccessRO>> rpcDayResult = operatorStatAccessFacade.queryOneOperatorStatDayAccessListWithPage(rpcDayRequest);
        if (CollectionUtils.isEmpty(rpcDayResult.getData())) {
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setGroupCode(request.getGroupCode());
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setIntervalMins(60);
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessList(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            logger.error("查询具体运营商详细信息有误,存在日统计数据,缺失小时统计数据,request={}", JSON.toJSONString(request));
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        Map<String, List<OperatorStatDayAccessDetailVO>> map = Maps.newHashMap();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (OperatorStatAccessRO ro : rpcResult.getData()) {
            String dateStr = df.format(ro.getDataTime());
            List<OperatorStatDayAccessDetailVO> list = map.get(dateStr);
            if (CollectionUtils.isEmpty(list)) {
                list = Lists.newArrayList();
            }
            OperatorStatDayAccessDetailVO vo = new OperatorStatDayAccessDetailVO();
            BeanUtils.convert(ro, vo);
            vo.setDataTimeStr(DateUtils.date2SimpleHm(vo.getDataTime()));
            list.add(vo);

            map.put(dateStr, list);
        }
        List<OperatorStatDayAccessDetailVO> result = Lists.newArrayList();
        for (OperatorStatDayAccessRO ro : rpcDayResult.getData()) {
            String dateStr = df.format(ro.getDataTime());
            OperatorStatDayAccessDetailVO vo = new OperatorStatDayAccessDetailVO();
            BeanUtils.convert(ro, vo);
            List<OperatorStatDayAccessDetailVO> detailList = map.get(dateStr);
            if (CollectionUtils.isNotEmpty(detailList)) {
                detailList = detailList.stream().sorted((o1, o2) -> o2.getDataTime().compareTo(o1.getDataTime())).collect(Collectors.toList());
            }
            vo.setDataTimeStr(dateStr);
            vo.setChildren(detailList);
            result.add(vo);
        }
        return Results.newSuccessPageResult(request, rpcDayResult.getTotalCount(), result);
    }

    @Override
    public Object queryMerchantsHasOperatorAuth() {
        List<MerchantSimpleVO> result = Lists.newArrayList();
        MerchantSimpleVO totalVO = new MerchantSimpleVO();
        totalVO.setAppId("virtual_total_stat_appId");
        totalVO.setAppName("所有商户");
        result.add(totalVO);
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andBizTypeEqualTo(EBizType.OPERATOR.getCode());
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        if (CollectionUtils.isEmpty(appBizLicenseList)) {
            return Results.newSuccessResult(result);
        }
        List<String> appIdList = appBizLicenseList.stream().map(AppBizLicense::getAppId).distinct().collect(Collectors.toList());
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return Results.newSuccessResult(result);
        }
        merchantBaseList = merchantBaseList.stream()
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());
        for (MerchantBase merchantBase : merchantBaseList) {
            MerchantSimpleVO vo = new MerchantSimpleVO();
            vo.setAppId(merchantBase.getAppId());
            vo.setAppName(merchantBase.getAppName());
            result.add(vo);
        }
        return Results.newSuccessResult(result);
    }
}
