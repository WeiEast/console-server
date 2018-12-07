package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.b2b.saas.util.SaasDateUtils;
import com.treefinance.saas.console.biz.enums.EBizTypeEnum;
import com.treefinance.saas.console.biz.service.OperatorStatService;
import com.treefinance.saas.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.console.common.domain.vo.AllOperatorStatAccessVO;
import com.treefinance.saas.console.common.domain.vo.AllOperatorStatDayAccessVO;
import com.treefinance.saas.console.common.domain.vo.CallbackFailureReasonVO;
import com.treefinance.saas.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.console.common.domain.vo.OperatorStatAccessVO;
import com.treefinance.saas.console.common.domain.vo.OperatorStatDayAccessDetailVO;
import com.treefinance.saas.console.common.domain.vo.OperatorStatDayAccessVO;
import com.treefinance.saas.console.common.domain.vo.OperatorStatDayConvertRateVo;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.dao.entity.MerchantBase;
import com.treefinance.saas.console.manager.BizLicenseInfoManager;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.merchant.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.monitor.facade.domain.request.CallbackFailureReasonStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.OperatorStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.CallbackFailureReasonStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.CallbackFailureReasonStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorAllStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorAllStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatDayAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.CallbackFailureReasonStatAccessFacade;
import com.treefinance.saas.monitor.facade.service.stat.OperatorStatAccessFacade;
import com.treefinance.toolkit.util.DateUtils;
import com.treefinance.toolkit.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/11/1.
 */
@Service
public class OperatorStatServiceImpl extends AbstractService implements OperatorStatService {

    @Autowired
    private OperatorStatAccessFacade operatorStatAccessFacade;

    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Autowired
    private CallbackFailureReasonStatAccessFacade callbackFailureReasonStatAccessFacade;
    @Autowired
    private BizLicenseInfoManager bizLicenseInfoManager;

    @Override
    public Object queryAllOperatorStatDayAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        MonitorResult<List<OperatorAllStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryAllOperatorStatDayAccessListWithPage(rpcRequest);
        List<AllOperatorStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, result);
        }
        result = this.convertList(rpcResult.getData(), AllOperatorStatDayAccessVO.class);
        return Results.newPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryAllOperatorStatAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(DateUtils.getStartTimeOfDay(request.getDataDate()));
        rpcRequest.setEndDate(DateUtils.getEndTimeOfDay(request.getDataDate()));
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setIntervalMins(30);
        MonitorResult<List<OperatorAllStatAccessRO>> rpcResult = operatorStatAccessFacade.queryAllOperatorStaAccessList(rpcRequest);
        List<AllOperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessResult(result);
        }
        result = this.convertList(rpcResult.getData(), AllOperatorStatAccessVO.class);
        return Results.newSuccessResult(result);
    }

    @Override
    public Object queryAllOperatorStatAccessSomeTimeList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setDataDate(request.getDataTime());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setIntervalMins(30);
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatHourAccessListWithPage(rpcRequest);
        List<OperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, result);
        }
        result = this.convertList(rpcResult.getData(), OperatorStatAccessVO.class);
        return Results.newPageResult(request, rpcResult.getTotalCount(), result);
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
        rpcRequest.setSaasEnv(request.getSaasEnv());
        MonitorResult<List<OperatorStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatDayAccessListWithPage(rpcRequest);
        List<OperatorStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, result);
        }
        result = this.convertList(rpcResult.getData(), OperatorStatDayAccessVO.class);
        return Results.newPageResult(request, rpcResult.getTotalCount(), result);
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
        rpcDayRequest.setSaasEnv(request.getSaasEnv());
        MonitorResult<List<OperatorStatDayAccessRO>> rpcDayResult = operatorStatAccessFacade.queryOneOperatorStatDayAccessListWithPage(rpcDayRequest);
        if (CollectionUtils.isEmpty(rpcDayResult.getData())) {
            return Results.newPageResult(request, 0, Lists.newArrayList());
        }
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setGroupCode(request.getGroupCode());
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setIntervalMins(60);
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessList(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            logger.error("查询具体运营商详细信息有误,存在日统计数据,缺失小时统计数据,request={}", JSON.toJSONString(request));
            return Results.newPageResult(request, 0, Lists.newArrayList());
        }
        Map<String, List<OperatorStatDayAccessDetailVO>> map = Maps.newHashMap();
        for (OperatorStatAccessRO ro : rpcResult.getData()) {
            if (ro == null) {
                continue;
            }

            String dateStr = DateUtils.formatDate(ro.getDataTime());
            List<OperatorStatDayAccessDetailVO> list = map.get(dateStr);
            if (CollectionUtils.isEmpty(list)) {
                list = Lists.newArrayList();
            }
            OperatorStatDayAccessDetailVO vo = new OperatorStatDayAccessDetailVO();
            this.copyProperties(ro, vo);
            vo.setDataTimeStr(DateUtils.formatHm(vo.getDataTime()));
            list.add(vo);

            map.put(dateStr, list);
        }
        List<OperatorStatDayAccessDetailVO> result = Lists.newArrayList();
        for (OperatorStatDayAccessRO ro : rpcDayResult.getData()) {
            if (ro == null) {
                continue;
            }

            String dateStr = DateUtils.formatDate(ro.getDataTime());
            OperatorStatDayAccessDetailVO vo = new OperatorStatDayAccessDetailVO();
            this.copyProperties(ro, vo);
            List<OperatorStatDayAccessDetailVO> detailList = map.get(dateStr);
            if (CollectionUtils.isNotEmpty(detailList)) {
                detailList = detailList.stream().sorted((o1, o2) -> o2.getDataTime().compareTo(o1.getDataTime())).collect(Collectors.toList());
            }
            vo.setDataTimeStr(dateStr);
            vo.setChildren(detailList);
            result.add(vo);
        }
        return Results.newPageResult(request, rpcDayResult.getTotalCount(), result);
    }

    @Override
    public Object queryAllOperatorStatConvertRateList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcDayRequest = new OperatorStatAccessRequest();

        if (Objects.isEmpty(request.getEndDate())) {
            request.setEndDate(new Date());
        }
        if (Objects.isEmpty(request.getStartDate())) {
            request.setStartDate(DateUtils.minusMonths(request.getEndDate(), 3));
        }

        List<OperatorStatDayConvertRateVo> result = new ArrayList<>();

        rpcDayRequest.setStartDate(DateUtils.getStartTimeOfDay(DateUtils.getFirstDayOfMonth(request.getStartDate())));
        rpcDayRequest.setEndDate(DateUtils.getEndTimeOfDay(DateUtils.getLastDayOfMonth(request.getEndDate())));
        rpcDayRequest.setStatType(request.getStatType() == null ? 1 : request.getStatType());
        rpcDayRequest.setAppId("virtual_total_stat_appId");
        MonitorResult<List<OperatorAllStatDayAccessRO>> rpcDayResult = operatorStatAccessFacade.queryAllOperatorStatDayAccessList(rpcDayRequest);
        if (CollectionUtils.isEmpty(rpcDayResult.getData())) {
            return Results.newPageResult(request, 0, Lists.newArrayList());
        }

        List<OperatorAllStatDayAccessRO> list = rpcDayResult.getData();
        // 根据年月分组
        Map<String, List<OperatorAllStatDayAccessRO>> map =
            list.stream().filter(operatorAllStatDayAccessRO -> "virtual_total_stat_appId".equals(operatorAllStatDayAccessRO.getAppId()))
                .collect(Collectors.groupingBy(operatorAllStatDayAccessRO -> DateUtils.formatYm(operatorAllStatDayAccessRO.getDataTime())));

        for (String key : map.keySet()) {
            List<OperatorAllStatDayAccessRO> value = map.get(key);

            calcRate(result, key, value);
        }
        result = result.stream().sorted(Comparator.comparing(OperatorStatDayConvertRateVo::getDataTime)).collect(Collectors.toList());

        return Results.newSuccessResult(result);
    }

    private void calcRate(List<OperatorStatDayConvertRateVo> result, String date, List<OperatorAllStatDayAccessRO> filteredList) {

        int entryCount = 0, succCount = 0;

        logger.info("统计" + date + "内的数据");
        for (OperatorAllStatDayAccessRO ro : filteredList) {
            entryCount += ro.getEntryCount();
            succCount += ro.getCallbackSuccessCount();
        }
        logger.info("此时段内 成功回调数量：" + succCount + " 任务总数：" + entryCount);

        OperatorStatDayConvertRateVo rateVO = new OperatorStatDayConvertRateVo();

        BigDecimal rate = entryCount == 0 ? BigDecimal.ZERO : new BigDecimal(succCount).divide(new BigDecimal(entryCount), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        rateVO.setDataValue(rate);
        rateVO.setDataTime(date);

        result.add(rateVO);
    }

    @Override
    public Object queryMerchantsHasOperatorAuth() {
        List<MerchantSimpleVO> result = Lists.newArrayList();
        MerchantSimpleVO totalVO = new MerchantSimpleVO();
        totalVO.setAppId("virtual_total_stat_appId");
        totalVO.setAppName("所有商户");
        result.add(totalVO);

        List<String> appIds = bizLicenseInfoManager.listAppIdsByBizType(EBizTypeEnum.OPERATOR.getCode());

        if (CollectionUtils.isEmpty(appIds)) {
            return Results.newSuccessResult(result);
        }

        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(appIds);
        MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = this.convert(listMerchantResult.getData(), MerchantBase.class);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return Results.newSuccessResult(result);
        }
        merchantBaseList = merchantBaseList.stream().sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());
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
        List<String> keys = SaasDateUtils.getIntervalDateStrRegion(request.getStartTime(), request.getEndTime(), request.getIntervalMins());
        List<String> groupNameList = Lists.newArrayList("中国联通", "广东移动", "浙江移动", "江苏移动", "福建移动", "山东移动", "河南移动", "湖南移动", "广西移动", "湖北移动", "其他");
        map.put("keys", keys);
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(SaasDateUtils.getIntervalDateTime(request.getStartTime(), request.getIntervalMins()));
        rpcRequest.setEndDate(SaasDateUtils.getLaterIntervalDateTime(request.getEndTime(), request.getIntervalMins()));
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setIntervalMins(request.getIntervalMins());
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessListByExample(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            map.put("values", Maps.newHashMap());
        }
        List<OperatorStatAccessRO> dataList = rpcResult.getData();
        Map<Date, List<OperatorStatAccessRO>> dateMap = dataList.stream().collect(Collectors.groupingBy(OperatorStatAccessRO::getDataTime));
        List<Date> dateList = SaasDateUtils.getIntervalDateRegion(rpcRequest.getStartDate(), rpcRequest.getEndDate(), request.getIntervalMins(), 1);
        // <时间,<运营商名称,数值>>
        Map<String, Map<String, String>> everyOneMap = Maps.newHashMap();
        for (Date date : dateList) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.formatHm(date));
            Date mediTime = DateUtils.plusMinutes(date, request.getIntervalMins());
            sb.append("-").append(DateUtils.formatHm(mediTime));

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
                        sb.append("0").append(" | ").append("NA");
                        itemValueMap.put(groupName, sb.toString());
                    } else {
                        if (total == 0) {
                            sb.append("0").append(" | ").append("NA");
                            itemValueMap.put(groupName, sb.toString());
                        } else {
                            BigDecimal rate = new BigDecimal(valueStr).multiply(new BigDecimal(100)).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
                            sb.append(valueStr).append(" | ").append(rate).append("%");
                            itemValueMap.put(groupName, sb.toString());
                        }
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("0").append(" | ").append("NA");
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

    @Override
    public Object initAlarmHistoryData(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(request.getStartTime());
        rpcRequest.setEndDate(request.getEndTime());
        MonitorResult<Boolean> rpcResult = operatorStatAccessFacade.initHistoryData4OperatorStatAccess(rpcRequest);
        return Results.newSuccessResult(rpcResult.getData());
    }

    @Override
    public Object queryDayCallbackFailureReason(OperatorStatRequest request) {
        CallbackFailureReasonStatAccessRequest rpcRequest = new CallbackFailureReasonStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setBizType(EBizTypeEnum.OPERATOR.getCode());
        rpcRequest.setDataType(request.getStatType());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setGroupCode(request.getGroupCode());
        rpcRequest.setStartTime(DateUtils.getStartTimeOfDay(request.getDataDate()));
        rpcRequest.setEndTime(DateUtils.getEndTimeOfDay(request.getDataDate()));
        MonitorResult<List<CallbackFailureReasonStatDayAccessRO>> rpcResult = callbackFailureReasonStatAccessFacade.queryCallbackFailureReasonStatDayAccessList(rpcRequest);
        List<CallbackFailureReasonVO> result = Lists.newArrayList();
        List<CallbackFailureReasonStatDayAccessRO> rpcDataList = rpcResult.getData();
        if (CollectionUtils.isEmpty(rpcDataList)) {
            return Results.newSuccessResult(result);
        }
        for (CallbackFailureReasonStatDayAccessRO rpcData : rpcDataList) {
            CallbackFailureReasonVO vo = new CallbackFailureReasonVO(rpcData.getTotalCount(), rpcData.getUnKnownReasonCount(), rpcData.getPersonalReasonCount());
            result.add(vo);
        }
        return Results.newSuccessResult(result);
    }

    @Override
    public Object queryCallbackFailureReason(OperatorStatRequest request) {
        CallbackFailureReasonStatAccessRequest rpcRequest = new CallbackFailureReasonStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setBizType(EBizTypeEnum.OPERATOR.getCode());
        rpcRequest.setDataType(request.getStatType());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setGroupCode(request.getGroupCode());
        rpcRequest.setStartTime(request.getDataTime());
        rpcRequest.setEndTime(DateUtils.plusMinutes(request.getDataTime(), 30));
        rpcRequest.setIntervalMins(30);
        MonitorResult<List<CallbackFailureReasonStatAccessRO>> rpcResult = callbackFailureReasonStatAccessFacade.queryCallbackFailureReasonStatAccessList(rpcRequest);
        List<CallbackFailureReasonVO> result = Lists.newArrayList();
        List<CallbackFailureReasonStatAccessRO> rpcDataList = rpcResult.getData();
        if (CollectionUtils.isEmpty(rpcDataList)) {
            return Results.newSuccessResult(result);
        }
        for (CallbackFailureReasonStatAccessRO rpcData : rpcDataList) {
            CallbackFailureReasonVO vo = new CallbackFailureReasonVO(rpcData.getTotalCount(), rpcData.getUnKnownReasonCount(), rpcData.getPersonalReasonCount());
            result.add(vo);
        }
        return Results.newSuccessResult(result);
    }
}
