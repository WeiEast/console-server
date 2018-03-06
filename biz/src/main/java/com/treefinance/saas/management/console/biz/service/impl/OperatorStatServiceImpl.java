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
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public Object queryNumberRatio(OperatorStatRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        List<String> keys = DateUtils.getIntervalDateStrRegion(request.getStartTime(), request.getEndTime(), request.getIntervalMins());
        List<String> groupNameList = Lists.newArrayList("中国联通", "广东移动", "浙江移动", "江苏移动", "福建移动", "山东移动", "河南移动", "湖南移动", "广西移动", "湖北移动", "其他");
        map.put("keys", keys);
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(DateUtils.getIntervalDateTime(request.getStartTime(), request.getIntervalMins()));
        rpcRequest.setEndDate(DateUtils.getIntervalDateTime(request.getEndTime(), request.getIntervalMins()));
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setIntervalMins(request.getIntervalMins());
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessListByExample(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            map.put("values", Maps.newHashMap());
        }
        List<OperatorStatAccessRO> dataList = rpcResult.getData();
        Map<Date, List<OperatorStatAccessRO>> dateMap = dataList.stream().collect(Collectors.groupingBy(OperatorStatAccessRO::getDataTime));
        List<Date> dateList = DateUtils.getIntervalDateRegion(rpcRequest.getStartDate(), rpcRequest.getEndDate(), request.getIntervalMins());
        //<时间,<运营商名称,数值>>
        Map<String, Map<String, String>> everyOneMap = Maps.newHashMap();
        for (Date date : dateList) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.date2SimpleHm(date));
            Date mediTime = org.apache.commons.lang3.time.DateUtils.addMinutes(date, request.getIntervalMins());
            sb.append("-").append(DateUtils.date2SimpleHm(mediTime));

            List<OperatorStatAccessRO> dateDataList = dateMap.get(date);
            if (CollectionUtils.isEmpty(dateDataList)) {
                everyOneMap.put(sb.toString(), Maps.newHashMap());
                continue;
            }
            Map<String, List<OperatorStatAccessRO>> dateDataMap = dateDataList.stream().collect(Collectors.groupingBy(OperatorStatAccessRO::getGroupName));
            Map<String, String> dateCountMap = Maps.newHashMap();

            for (Map.Entry<String, List<OperatorStatAccessRO>> entry : dateDataMap.entrySet()) {
                int i = 0;
                for (OperatorStatAccessRO ro : entry.getValue()) {
                    i += ro.getCallbackSuccessCount();
                }
                if (groupNameList.contains(entry.getKey())) {
                    dateCountMap.put(entry.getKey(), i + "");
                } else {
                    if (dateCountMap.get("其他") == null) {
                        dateCountMap.put("其他", i + "");
                    } else {
                        i = i + Integer.valueOf(dateCountMap.get("其他"));
                        dateCountMap.put("其他", i + "");
                    }
                }
            }

            everyOneMap.put(sb.toString(), dateCountMap);
        }
        Map<String, Map<String, String>> valueMap = Maps.newLinkedHashMap();
        for (String timeKey : keys) {
            Map<String, String> itemCountMap = everyOneMap.get(timeKey);
            Map<String, String> itemValueMap = Maps.newLinkedHashMap();
            int total = 0;
            if (MapUtils.isNotEmpty(itemCountMap)) {
                for (Map.Entry<String, String> entry : itemCountMap.entrySet()) {
                    total += Integer.valueOf(entry.getValue());
                }
                for (String groupName : groupNameList) {
                    String valueStr = itemCountMap.get(groupName);
                    StringBuilder sb = new StringBuilder();
                    if (StringUtils.isBlank(valueStr)) {
                        sb = sb.append("0").append(" | ").append("NA");
                        itemValueMap.put(groupName, sb.toString());
                    } else {
                        if (total == 0) {
                            sb = sb.append("0").append(" | ").append("NA");
                            itemValueMap.put(groupName, sb.toString());
                        } else {
                            BigDecimal rate = new BigDecimal(valueStr).multiply(new BigDecimal(100)).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
                            sb = sb.append(valueStr).append(" | ").append(rate).append("%");
                            itemValueMap.put(groupName, sb.toString());
                        }
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb = sb.append("0").append(" | ").append("NA");
                for (String groupName : groupNameList) {
                    itemValueMap.put(groupName, sb.toString());
                }
            }
            valueMap.put(timeKey, itemValueMap);
        }
        List<Object> resultList = Lists.newArrayList();
        for (String groupName : groupNameList) {
            Map<String, String> objMap = Maps.newLinkedHashMap();
            objMap.put("groupName", groupName);
            for (String key : keys) {
                objMap.put(key, valueMap.get(key).get(groupName));
            }
            resultList.add(objMap);
        }
        map.put("values", resultList);
        return Results.newSuccessResult(map);
    }

}
