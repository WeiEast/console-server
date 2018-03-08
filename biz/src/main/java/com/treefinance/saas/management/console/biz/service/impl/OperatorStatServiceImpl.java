package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.datatrees.crawler.core.processor.format.unit.TimeUnit;
import com.datatrees.toolkits.util.Objects;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.*;
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
    public Object queryAllOperatorStatConvertRateList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcDayRequest = new OperatorStatAccessRequest();

        if (Objects.isEmpty(request.getEndDate())) {
            request.setEndDate(new Date());
        }
        if (Objects.isEmpty(request.getStartDate())) {
            request.setStartDate(DateUtils.getSpecificDayDate(request.getEndDate(), -3,
                    TimeUnit.MONTH));
        }

        List<OperatorStatDayConvertRateVo> result = new ArrayList<>();

        rpcDayRequest.setStartDate(DateUtils.getTodayBeginDate(DateUtils.getFirstDayOfMonth(request.getStartDate())));
        rpcDayRequest.setEndDate(DateUtils.getTodayEndDate(DateUtils.getLastDayOfMonth(request.getEndDate())));
        rpcDayRequest.setStatType(request.getStatType() == null?1:request.getStatType());
        rpcDayRequest.setAppId("virtual_total_stat_appId");
        MonitorResult<List<OperatorAllStatDayAccessRO>> rpcDayResult = operatorStatAccessFacade.queryAllOperatorStatDayAccessList(rpcDayRequest);
        if (CollectionUtils.isEmpty(rpcDayResult.getData())) {
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }

        List<OperatorAllStatDayAccessRO> list = rpcDayResult.getData();

        Map<String, List<OperatorAllStatDayAccessRO>> map = list.stream().filter(operatorAllStatDayAccessRO ->
                "virtual_total_stat_appId".equals(operatorAllStatDayAccessRO.getAppId()))
                .collect(Collectors.groupingBy
                (operatorAllStatDayAccessRO -> DateUtils.date2SimpleYm(operatorAllStatDayAccessRO.getDataTime())));

        for (String key : map.keySet()) {
            List<OperatorAllStatDayAccessRO> value = map.get(key);
            Date firstTenDay = DateUtils.string2Date(key + "-01 00:00:00");
            Date midTenDay = DateUtils.string2Date(key + "-11 00:00:00");
            Date lastTenDay = DateUtils.string2Date(key + "-21 00:00:00");

            //展示用的key 表示的是yyyy-MM + -dd
            String firstKey = key + "-01";
            String midKey = key + "-11";
            String lastKey = key + "-21";

            List<OperatorAllStatDayAccessRO> firstTenDayList = value.stream().filter(operatorAllStatDayAccessRO ->
                    operatorAllStatDayAccessRO.getDataTime()
                            .compareTo
                                    (firstTenDay) >= 0 && operatorAllStatDayAccessRO.getDataTime().compareTo
                            (midTenDay) < 0).collect(Collectors.toList());

            calcTenDayRate(result, firstKey, firstTenDayList);

            List<OperatorAllStatDayAccessRO> midTenDayList = value.stream().filter(operatorAllStatDayAccessRO ->
                    operatorAllStatDayAccessRO.getDataTime()
                            .compareTo
                                    (midTenDay) >= 0 && operatorAllStatDayAccessRO.getDataTime().compareTo
                            (lastTenDay) < 0).collect(Collectors.toList());

            calcTenDayRate(result, midKey, midTenDayList);

            List<OperatorAllStatDayAccessRO> lastTenDayList = value.stream().filter(operatorAllStatDayAccessRO ->
                    operatorAllStatDayAccessRO.getDataTime()
                            .compareTo(lastTenDay) >= 0).collect(Collectors.toList());

            calcTenDayRate(result, lastKey, lastTenDayList);

        }
        result = result.stream().sorted(Comparator.comparing(OperatorStatDayConvertRateVo::getDataTime)).collect(Collectors
                .toList());

        return Results.newSuccessResult(result);
    }

    private void calcTenDayRate(List<OperatorStatDayConvertRateVo> result, String date,
                                List<OperatorAllStatDayAccessRO> filteredList) {
        //如果比今天大 就不展示
        Date day = DateUtils.ymdString2Date(date);
        if(day == null || new Date().compareTo(day)<0){
            return;
        }

        int entryCount = 0,succCount = 0;

        logger.info("统计"+date+"后十天的数据");
        for (OperatorAllStatDayAccessRO ro:filteredList) {
            entryCount += ro.getEntryCount();
            succCount += ro.getCallbackSuccessCount();
        }
        logger.info("此时段内 成功回调数量："+succCount+" 任务总数："+ entryCount);

        OperatorStatDayConvertRateVo firstTenDayRate = new OperatorStatDayConvertRateVo();

        BigDecimal rate = entryCount == 0?BigDecimal.ZERO:new BigDecimal(succCount).divide(new BigDecimal
                (entryCount),4, RoundingMode
                .HALF_UP);

        firstTenDayRate.setDataValue(rate);
        firstTenDayRate.setDataTime(date);

        result.add(firstTenDayRate);
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
        List<String> keys = DateUtils.getIntervalDateStrRegion(request.getStartTime(), request.getEndTime(), 5);
        List<String> groupNameList = Lists.newArrayList("中国联通", "广东移动", "浙江移动", "江苏移动", "福建移动", "山东移动", "河南移动", "湖南移动", "广西移动", "湖北移动", "其他");
        map.put("keys", keys);
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(DateUtils.getIntervalDateTime(request.getStartTime(), 5));
        rpcRequest.setEndDate(DateUtils.getIntervalDateTime(request.getEndTime(), 5));
        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setIntervalMins(5);
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessListByExample(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            map.put("values", Maps.newHashMap());
        }
        List<OperatorStatAccessRO> dataList = rpcResult.getData();
        Map<Date, List<OperatorStatAccessRO>> dateMap = dataList.stream().collect(Collectors.groupingBy(OperatorStatAccessRO::getDataTime));
        List<Date> dateList = DateUtils.getIntervalDateRegion(rpcRequest.getStartDate(), rpcRequest.getEndDate(), 5);
        //<时间,<运营商名称,数值>>
        Map<String, Map<String, String>> everyOneMap = Maps.newHashMap();
        for (Date date : dateList) {
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.date2SimpleHm(date));
            Date mediTime = org.apache.commons.lang3.time.DateUtils.addMinutes(date, 5);
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
                    i += ro.getConfirmMobileCount();
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
        List<OperatorFiveMinNumberRatioVO> resultList = Lists.newArrayList();
        for (String groupName : groupNameList) {
            OperatorFiveMinNumberRatioVO vo = new OperatorFiveMinNumberRatioVO();
            vo.setGroupName(groupName);
            vo.setTime0(valueMap.get(keys.get(0)).get(groupName));
            vo.setTime5(valueMap.get(keys.get(1)).get(groupName));
            vo.setTime10(valueMap.get(keys.get(2)).get(groupName));
            vo.setTime15(valueMap.get(keys.get(3)).get(groupName));
            vo.setTime20(valueMap.get(keys.get(4)).get(groupName));
            vo.setTime25(valueMap.get(keys.get(5)).get(groupName));
            vo.setTime30(valueMap.get(keys.get(6)).get(groupName));
            vo.setTime35(valueMap.get(keys.get(7)).get(groupName));
            vo.setTime40(valueMap.get(keys.get(8)).get(groupName));
            vo.setTime45(valueMap.get(keys.get(9)).get(groupName));
            vo.setTime50(valueMap.get(keys.get(10)).get(groupName));
            vo.setTime55(valueMap.get(keys.get(11)).get(groupName));
            resultList.add(vo);
        }
        map.put("values", resultList);
        return Results.newSuccessResult(map);
    }

}
