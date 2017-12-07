package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.gateway.servicefacade.enums.TaskStepEnum;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.management.console.biz.service.MerchantStatService;
import com.treefinance.saas.management.console.common.domain.dto.SaasErrorStepDayStatDTO;
import com.treefinance.saas.management.console.common.domain.request.StatDayRequest;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.*;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.EBizType4Monitor;
import com.treefinance.saas.management.console.common.enumeration.ETaskErrorStep;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.*;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatDayAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.SaasErrorStepDayStatRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.SaasErrorStepDayStatRO;
import com.treefinance.saas.monitor.facade.service.stat.MerchantStatAccessFacade;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/5.
 */
@Service
public class MerchantStatServiceImpl implements MerchantStatService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerchantStatAccessFacade merchantStatAccessFacade;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Autowired
    private MerchantUserMapper merchantUserMapper;
    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private AppBizTypeMapper appBizTypeMapper;
    @Autowired
    private TaskAttributeMapper taskAttributeMapper;


    @Override
    public Result<Map<String, Object>> queryDayAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setPageNumber(request.getPageNumber());
        statRequest.setPageSize(request.getPageSize());
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            logger.error("merchantStatAccessFacade.queryDayAccessList()为空 : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        Map<String, MerchantStatDayAccessRO> dayAccessROMap = this.queryTotalDayAccessList(request);
        List<MerchantStatDayVO> dataList = Lists.newArrayList();
        for (MerchantStatDayAccessRO ro : result.getData()) {
            MerchantStatDayVO merchantStatDayVO = new MerchantStatDayVO();
            merchantStatDayVO.setDateStr(DateUtils.date2Ymd(ro.getDataTime()));
            merchantStatDayVO.setTotalCount(ro.getSuccessCount());
            merchantStatDayVO.setDailyLimit(ro.getDailyLimit());
            BigDecimal totalCountDecimal = new BigDecimal(ro.getSuccessCount());
            BigDecimal dailyLimitDecimal = new BigDecimal(ro.getDailyLimit());
            if (BigDecimal.ZERO.compareTo(dailyLimitDecimal) != 0) {
                merchantStatDayVO.setDailyLimitRate(totalCountDecimal.divide(dailyLimitDecimal, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                merchantStatDayVO.setDailyLimitRate(BigDecimal.ZERO);
            }
            if (dayAccessROMap.get(DateUtils.date2Ymd(ro.getDataTime())) != null
                    && dayAccessROMap.get(DateUtils.date2Ymd(ro.getDataTime())).getDailyLimit() != 0) {
                BigDecimal totalDailyLimitDecimal = new BigDecimal(dayAccessROMap.get(DateUtils.date2Ymd(ro.getDataTime())).getDailyLimit());
                merchantStatDayVO.setUseOnTotalLimitRate(totalCountDecimal.divide(totalDailyLimitDecimal, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                merchantStatDayVO.setUseOnTotalLimitRate(BigDecimal.ZERO);
            }
            dataList.add(merchantStatDayVO);
        }
        int total = dataList.size();
        int start = request.getOffset();
        int end = request.getOffset() + request.getPageSize();
        if (end > total) {
            end = total;
        }
        dataList = dataList.subList(start, end);
        return Results.newSuccessPageResult(request, result.getTotalCount(), dataList);
    }

    private Map<String, MerchantStatDayAccessRO> queryTotalDayAccessList(StatRequest request) {
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setDataType(EBizType4Monitor.TOTAL.getMonitorCode());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }

        Map<String, MerchantStatDayAccessRO> resultMap = result.getData()
                .stream()
                .collect(Collectors.toMap(ro -> DateUtils.date2Ymd(ro.getDataTime()), ro -> ro, (key1, key2) -> key1));
        return resultMap;
    }


    @Override
    public Result<Map<String, Object>> queryWeekAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(DateUtils.getFirstDayOfWeek(request.getStartDate()));
        statRequest.setEndDate(DateUtils.getLastDayOfWeek(request.getEndDate()));
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        //<weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantStatDayAccessRO>> timeMap = Maps.newLinkedHashMap();
        result.getData().forEach(ro -> {
            String timeStr = DateUtils.getWeekStrOfYear(ro.getDataTime());
            List<MerchantStatDayAccessRO> timeValueList = timeMap.get(timeStr);
            if (CollectionUtils.isEmpty(timeValueList)) {
                timeValueList = Lists.newArrayList();
                timeValueList.add(ro);
                timeMap.put(timeStr, timeValueList);
            } else {
                timeValueList.add(ro);
            }
        });
        return wrapDataPageResult(request, timeMap);

    }


    @Override
    public Result<Map<String, Object>> queryMonthAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(DateUtils.getFirstDayOfMonth(request.getStartDate()));
        statRequest.setEndDate(DateUtils.getLastDayOfMonth(request.getEndDate()));
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }

        //<weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantStatDayAccessRO>> timeMap = Maps.newLinkedHashMap();
        result.getData().forEach(ro -> {
            String timeStr = DateUtils.date2SimpleYm(ro.getDataTime());
            List<MerchantStatDayAccessRO> timeValueList = timeMap.get(timeStr);
            if (CollectionUtils.isEmpty(timeValueList)) {
                timeValueList = Lists.newArrayList();
                timeValueList.add(ro);
                timeMap.put(timeStr, timeValueList);
            } else {
                timeValueList.add(ro);
            }
        });
        return wrapDataPageResult(request, timeMap);
    }

    @Override
    public Map<String, Object> queryAllAccessList(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryAllAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        List<MerchantStatAccessRO> roList = changeIntervalDataTime(result.getData(), request.getIntervalMins());

        //遍历获取时间节点list
        Set<Date> dataTimeSet = Sets.newHashSet();
        roList.forEach(ro -> {
            dataTimeSet.add(ro.getDataTime());
        });
        List<Date> dataTimeList = Lists.newArrayList(dataTimeSet);

        //<appId,<dataTime,totalCount>>
        Map<String, Map<Date, Integer>> dataMap = Maps.newHashMap();
        roList.forEach(ro -> {
            Map<Date, Integer> valueMap = dataMap.get(ro.getAppId());
            if (valueMap == null || valueMap.isEmpty()) {
                valueMap = Maps.newHashMap();
                valueMap.put(ro.getDataTime(), ro.getTotalCount());
                dataMap.put(ro.getAppId(), valueMap);
            } else {
                valueMap.put(ro.getDataTime(), ro.getTotalCount());
            }
        });
        //填充缺少的时间点的totalCount为0
        this.fillDataTime(dataMap, dataTimeList);
        //更换map的key为appName
        Map<String, Map<Date, Integer>> appNameMap = this.changeKey2AppName(dataMap);

        List<String> keysList = Lists.newArrayList(appNameMap.keySet()).stream().sorted((String::compareTo)).collect(Collectors.toList());
        keysList.add(0, "总任务量");
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.countTotalTask(appNameMap);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    @Override
    public Map<String, Object> queryAllAccessList4Pie(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryAllAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }
        //<appId,totalCount>
        Map<String, Integer> dataMap = Maps.newHashMap();
        for (MerchantStatAccessRO ro : result.getData()) {
            Integer total = dataMap.get(ro.getAppId());
            if (total == null) {
                total = ro.getTotalCount();
            } else {
                total = total + ro.getTotalCount();
            }
            dataMap.put(ro.getAppId(), total);
        }
        Map<String, Integer> appNameDataMap = this.changeKey2AppName4Pie(dataMap);
        List<Integer> totalCountList = Lists.newArrayList(appNameDataMap.values());
        int allTotalCount = totalCountList.stream().reduce(0, (sum, e) -> sum + e);//所有商户此段时间内的总任务量
        List<PieChartStatRateVO> valueList = Lists.newArrayList();
        for (Map.Entry<String, Integer> entry : appNameDataMap.entrySet()) {
            BigDecimal value = BigDecimal.valueOf(entry.getValue())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(allTotalCount), 2, BigDecimal.ROUND_HALF_UP);
            PieChartStatRateVO vo = new PieChartStatRateVO();
            vo.setName(entry.getKey());
            vo.setValue(value);
            valueList.add(vo);
        }
        List<String> keysList = Lists.newArrayList(appNameDataMap.keySet()).stream().sorted((String::compareTo)).collect(Collectors.toList());
        wrapMap.put("keys", keysList);
        wrapMap.put("values", valueList);
        return wrapMap;
    }

    @Override
    public Map<String, Object> queryAccessNumberList(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryAllAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        List<MerchantStatAccessRO> roList = changeIntervalDataTime(result.getData(), request.getIntervalMins());

        //<key,<data,keyCount>>
        Map<String, Map<Date, Integer>> dataMap = Maps.newLinkedHashMap();
        Map<Date, Integer> valueTotalMap = Maps.newHashMap();
        Map<Date, Integer> valueSuccessMap = Maps.newHashMap();
        Map<Date, Integer> valueFailMap = Maps.newHashMap();
        Map<Date, Integer> valueCancelMap = Maps.newHashMap();

        roList.forEach(ro -> {
            if (valueTotalMap.get(ro.getDataTime()) == null) {
                valueTotalMap.put(ro.getDataTime(), ro.getTotalCount());
            } else {
                Integer newValue = valueTotalMap.get(ro.getDataTime()) + ro.getTotalCount();
                valueTotalMap.put(ro.getDataTime(), newValue);
            }

            if (valueSuccessMap.get(ro.getDataTime()) == null) {
                valueSuccessMap.put(ro.getDataTime(), ro.getSuccessCount());
            } else {
                Integer newValue = valueSuccessMap.get(ro.getDataTime()) + ro.getSuccessCount();
                valueSuccessMap.put(ro.getDataTime(), newValue);
            }

            if (valueFailMap.get(ro.getDataTime()) == null) {
                valueFailMap.put(ro.getDataTime(), ro.getFailCount());
            } else {
                Integer newValue = valueFailMap.get(ro.getDataTime()) + ro.getFailCount();
                valueFailMap.put(ro.getDataTime(), newValue);
            }

            if (valueCancelMap.get(ro.getDataTime()) == null) {
                valueCancelMap.put(ro.getDataTime(), ro.getCancelCount());
            } else {
                Integer newValue = valueCancelMap.get(ro.getDataTime()) + ro.getCancelCount();
                valueCancelMap.put(ro.getDataTime(), newValue);
            }
        });

        dataMap.put("总数", valueTotalMap);
        dataMap.put("成功数", valueSuccessMap);
        dataMap.put("失败数", valueFailMap);
        dataMap.put("取消数", valueCancelMap);
        List<String> keysList = Lists.newArrayList(dataMap.keySet());
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.wrapNumberTaskChart(dataMap);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private List<MerchantStatAccessRO> changeIntervalDataTime(List<MerchantStatAccessRO> list, final Integer intervalMins) {
        Map<Date, List<MerchantStatAccessRO>> map = list.stream().collect(Collectors.groupingBy(ro -> DateUtils.getIntervalDateTime(ro.getDataTime(), intervalMins)));
        List<MerchantStatAccessRO> resultList = Lists.newArrayList();
        for (Map.Entry<Date, List<MerchantStatAccessRO>> entry : map.entrySet()) {
            if (CollectionUtils.isEmpty(entry.getValue())) {
                continue;
            }
            MerchantStatAccessRO ro = new MerchantStatAccessRO();
            BeanUtils.convert(entry.getValue().get(0), ro);
            ro.setDataTime(entry.getKey());
            List<MerchantStatAccessRO> entryList = entry.getValue();
            int totalCount = 0, successCount = 0, failCount = 0, cancelCount = 0;
            for (MerchantStatAccessRO data : entryList) {
                totalCount = totalCount + data.getTotalCount();
                successCount = successCount + data.getSuccessCount();
                failCount = failCount + data.getFailCount();
                cancelCount = cancelCount + data.getCancelCount();
            }
            ro.setTotalCount(totalCount);
            ro.setSuccessCount(successCount);
            ro.setFailCount(failCount);
            ro.setCancelCount(cancelCount);
            resultList.add(ro);
        }
        return resultList;
    }

    private Map<String, List<ChartStatVO>> wrapNumberTaskChart(Map<String, Map<Date, Integer>> dataMap) {
        Map<String, List<ChartStatVO>> valuesMap = Maps.newHashMap();
        for (Map.Entry<String, Map<Date, Integer>> dataEntry : dataMap.entrySet()) {
            List<ChartStatVO> voList = Lists.newArrayList();
            for (Map.Entry<Date, Integer> entry : dataEntry.getValue().entrySet()) {
                ChartStatVO vo = new ChartStatVO();
                vo.setDataTime(entry.getKey());
                vo.setDataValue(entry.getValue());
                voList.add(vo);
            }
            voList = voList.stream().sorted((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(voList)) {
                voList.remove(voList.size() - 1);
            }
            valuesMap.put(dataEntry.getKey(), voList);
        }
        return valuesMap;
    }

    @Override
    public Map<String, Object> queryAccessRateList(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryAllAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        List<MerchantStatAccessRO> roList = changeIntervalDataTime(result.getData(), request.getIntervalMins());

        //<key,<data,keyRate>>
        Map<String, Map<Date, BigDecimal>> dataMap = Maps.newLinkedHashMap();
        Map<Date, Integer> valueTotalMap = Maps.newHashMap();
        Map<Date, Integer> valueSuccessMap = Maps.newHashMap();
        Map<Date, Integer> valueFailMap = Maps.newHashMap();
        Map<Date, Integer> valueCancelMap = Maps.newHashMap();

        roList.forEach(ro -> {
            if (valueTotalMap.get(ro.getDataTime()) == null) {
                valueTotalMap.put(ro.getDataTime(), ro.getTotalCount());
            } else {
                Integer newValue = valueTotalMap.get(ro.getDataTime()) + ro.getTotalCount();
                valueTotalMap.put(ro.getDataTime(), newValue);
            }

            if (valueSuccessMap.get(ro.getDataTime()) == null) {
                valueSuccessMap.put(ro.getDataTime(), ro.getSuccessCount());
            } else {
                Integer newValue = valueSuccessMap.get(ro.getDataTime()) + ro.getSuccessCount();
                valueSuccessMap.put(ro.getDataTime(), newValue);
            }

            if (valueFailMap.get(ro.getDataTime()) == null) {
                valueFailMap.put(ro.getDataTime(), ro.getFailCount());
            } else {
                Integer newValue = valueFailMap.get(ro.getDataTime()) + ro.getFailCount();
                valueFailMap.put(ro.getDataTime(), newValue);
            }

            if (valueCancelMap.get(ro.getDataTime()) == null) {
                valueCancelMap.put(ro.getDataTime(), ro.getCancelCount());
            } else {
                Integer newValue = valueCancelMap.get(ro.getDataTime()) + ro.getCancelCount();
                valueCancelMap.put(ro.getDataTime(), newValue);
            }
        });

        dataMap = this.countRateTask(valueTotalMap, valueSuccessMap, valueFailMap, valueCancelMap);
        List<String> keysList = Lists.newArrayList(dataMap.keySet());
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatRateVO>> valuesMap = this.wrapRateTaskChart(dataMap);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    @Override
    public List<MerchantStatOverviewTimeVO> queryOverviewAccessList(StatRequest request) {
        baseCheck(request);
        Integer statType = request.getStatType();
        if (statType == null) {
            throw new IllegalArgumentException("请求参数statType不能为空！");
        }
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryAllDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            logger.info("result of merchantStatAccessFacade.queryDayAccessListNoPage() is empty : request={}, result={}", statRequest, JSON.toJSONString(result));
            return Lists.newArrayList();
        }
        List<Date> dateList = DateUtils.getDateLists(this.getStartDate(request), this.getEndDate(request));
        dateList = dateList.stream().sorted(Date::compareTo).collect(Collectors.toList());
        if (dateList.size() != 7) {
            throw new IllegalArgumentException("请求参数startDate,endDate非法!");
        }
        List<MerchantStatOverviewVO> overviewVOList = Lists.newArrayList();
        result.getData().forEach(ro -> {
            MerchantStatOverviewVO vo = new MerchantStatOverviewVO();
            vo.setAppId(ro.getAppId());
            vo.setDateStr(DateUtils.date2Md(ro.getDataTime()));
            vo.setDate(ro.getDataTime());
            vo.setTotalCount(ro.getTotalCount());
            if (statType == 1) {
                BigDecimal successRate = BigDecimal.valueOf(ro.getSuccessCount())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(successRate);
            } else if (statType == 2) {
                BigDecimal failRate = BigDecimal.valueOf(ro.getFailCount())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(failRate);
            } else {
                BigDecimal cancelRate = BigDecimal.valueOf(ro.getCancelCount())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(cancelRate);
            }
            overviewVOList.add(vo);
        });


        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        if (!EBizType4Monitor.TOTAL.getCode().equals(request.getBizType())) {
            appBizLicenseCriteria.createCriteria().andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);

        List<String> appIdList = appBizLicenseList.stream().map(AppBizLicense::getAppId).distinct().collect(Collectors.toList());
        List<List<String>> appIdPartList = Lists.partition(appIdList, 50);
        List<MerchantBase> merchantBaseList = Lists.newArrayList();
        for (List<String> appIdParts : appIdPartList) {
            MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
            merchantBaseCriteria.createCriteria().andAppIdIn(appIdParts);
            List<MerchantBase> merchantBasePartList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
            merchantBaseList.addAll(merchantBasePartList);
        }

        List<Long> merchantIdList = merchantBaseList.stream().map(MerchantBase::getId).collect(Collectors.toList());
        List<List<Long>> merchantIdPartList = Lists.partition(merchantIdList, 50);
        List<MerchantUser> merchantUserList = Lists.newArrayList();
        for (List<Long> merchantIdParts : merchantIdPartList) {
            MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
            merchantUserCriteria.createCriteria().andMerchantIdIn(merchantIdParts);
            List<MerchantUser> merchantUserPartList = merchantUserMapper.selectByExample(merchantUserCriteria);
            merchantUserList.addAll(merchantUserPartList);
        }
        //<merchantId,merchantUser>
        Map<Long, MerchantUser> merchantUserMap = merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, m -> m));

        Map<String, List<MerchantStatOverviewVO>> ovMap = overviewVOList.stream()
                .filter(ov -> StringUtils.isNotBlank(ov.getAppId()))
                .collect(Collectors.groupingBy(MerchantStatOverviewVO::getAppId));

        Map<String, Map<Date, MerchantStatOverviewVO>> resultMap = Maps.newHashMap();
        for (Map.Entry<String, List<MerchantStatOverviewVO>> entry : ovMap.entrySet()) {
            List<MerchantStatOverviewVO> list = entry.getValue();
            Map<Date, MerchantStatOverviewVO> tempMap = list.stream().collect(Collectors.toMap(MerchantStatOverviewVO::getDate, vo -> vo));
            resultMap.put(entry.getKey(), tempMap);
        }
        List<MerchantStatOverviewTimeVO> timeOverViewList = Lists.newArrayList();

        for (MerchantBase merchantBase : merchantBaseList) {
            String appId = merchantBase.getAppId();
            MerchantStatOverviewTimeVO timeVO = new MerchantStatOverviewTimeVO();
            timeVO.setAppId(appId);
            timeVO.setAppName(merchantBase.getAppName());
            timeVO.setAppCreateTime(merchantBase.getCreateTime());
            MerchantUser merchantUser = merchantUserMap.get(merchantBase.getId());
            if (merchantUser != null) {
                timeVO.setAppIsTest(merchantUser.getIsTest());
            } else {
                timeVO.setAppIsTest(true);
            }
            Map<Date, MerchantStatOverviewVO> entry = resultMap.get(appId);
            if (MapUtils.isEmpty(entry)) {
                timeVO.setTime1Val("0 | NA");
                timeVO.setTime2Val("0 | NA");
                timeVO.setTime3Val("0 | NA");
                timeVO.setTime4Val("0 | NA");
                timeVO.setTime5Val("0 | NA");
                timeVO.setTime6Val("0 | NA");
                timeVO.setTime7Val("0 | NA");
                timeVO.setNum(0);
            } else {
                MerchantStatOverviewVO vo1 = entry.get(dateList.get(0));
                timeVO.setTime1Val(vo1 == null ? "0 | NA" : new StringBuilder().append(vo1.getTotalCount()).append(" | ").append(vo1.getRate()).append("%").toString());
                MerchantStatOverviewVO vo2 = entry.get(dateList.get(1));
                timeVO.setTime2Val(vo2 == null ? "0 | NA" : new StringBuilder().append(vo2.getTotalCount()).append(" | ").append(vo2.getRate()).append("%").toString());

                MerchantStatOverviewVO vo3 = entry.get(dateList.get(2));
                timeVO.setTime3Val(vo3 == null ? "0 | NA" : new StringBuilder().append(vo3.getTotalCount()).append(" | ").append(vo3.getRate()).append("%").toString());

                MerchantStatOverviewVO vo4 = entry.get(dateList.get(3));
                timeVO.setTime4Val(vo4 == null ? "0 | NA" : new StringBuilder().append(vo4.getTotalCount()).append(" | ").append(vo4.getRate()).append("%").toString());

                MerchantStatOverviewVO vo5 = entry.get(dateList.get(4));
                timeVO.setTime5Val(vo5 == null ? "0 | NA" : new StringBuilder().append(vo5.getTotalCount()).append(" | ").append(vo5.getRate()).append("%").toString());

                MerchantStatOverviewVO vo6 = entry.get(dateList.get(5));
                timeVO.setTime6Val(vo6 == null ? "0 | NA" : new StringBuilder().append(vo6.getTotalCount()).append(" | ").append(vo6.getRate()).append("%").toString());
                MerchantStatOverviewVO vo7 = entry.get(dateList.get(6));
                timeVO.setTime7Val(vo7 == null ? "0 | NA" : new StringBuilder().append(vo7.getTotalCount()).append(" | ").append(vo7.getRate()).append("%").toString());
                timeVO.setNum(vo7 == null ? 0 : vo7.getTotalCount());
            }

            timeOverViewList.add(timeVO);
        }

        timeOverViewList = timeOverViewList
                .stream()
                .sorted((n1, n2) -> n2.getNum().compareTo(n1.getNum()))
                .collect(Collectors.toList());

        return timeOverViewList;
    }

    @Override
    public Result<Map<String, Object>> queryOverviewDetailAccessList(StatDayRequest request) {
        if (StringUtils.isBlank(request.getAppId()) || request.getDate() == null
                || request.getStatType() == null || request.getBizType() == null) {
            throw new BizException("appId,date,statType,bizType不能为空");
        }
        TaskCriteria taskCriteria = new TaskCriteria();
        TaskCriteria.Criteria criteria = taskCriteria.createCriteria().andAppIdEqualTo(request.getAppId());
        if (request.getStatType() == 2) {
            criteria.andStatusEqualTo((byte) 3);//失败的任务
        } else if (request.getStatType() == 3) {
            criteria.andStatusEqualTo((byte) 1);//取消的任务
        } else if (request.getStatType() == 1) {
            criteria.andStatusEqualTo((byte) 2);//成功的任务
        } else {
            throw new BizException("statType参数有误");
        }
        if (EBizType4Monitor.TOTAL.getCode().equals(request.getBizType())) {
            List<AppBizType> list = appBizTypeMapper.selectByExample(null);
            List<Byte> bizTypeList = list.stream().map(AppBizType::getBizType).collect(Collectors.toList());
            criteria.andBizTypeIn(bizTypeList);
        } else {
            criteria.andBizTypeEqualTo(request.getBizType());
        }
        if (StringUtils.isNotBlank(request.getWebsiteDetailName())) {
            if (request.getBizType() == 3) {//运营商
                List<Long> taskIdList = this.getTaskIdByTaskAttributeGroupName(request);
                if (CollectionUtils.isEmpty(taskIdList)) {
                    return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
                }
                criteria.andIdIn(taskIdList);
            } else if (request.getBizType() == -1) {//合计
                List<Long> taskIdList = this.getTaskIdByTaskAttributeGroupName(request);
                if (!CollectionUtils.isEmpty(taskIdList)) {
                    criteria.andIdIn(taskIdList);
                } else {
                    criteria.andWebSiteLike("%" + request.getWebsiteDetailName() + "%");
                }
            } else {//邮箱账单或电商或其他
                criteria.andWebSiteLike("%" + request.getWebsiteDetailName() + "%");
            }
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            criteria.andCreateTimeBetween(request.getStartTime(), request.getEndTime());
        } else if (request.getDate() != null) {
            criteria.andCreateTimeBetween(DateUtils.getTodayBeginDate(request.getDate()), DateUtils.getTomorrowBeginDate(request.getDate()));
        }
        long total = taskMapper.countByExample(taskCriteria);
        if (total <= 0) {
            return Results.newSuccessPageResult(request, total, Lists.newArrayList());
        }
        taskCriteria.setOffset(request.getOffset());
        taskCriteria.setLimit(request.getPageSize());
        taskCriteria.setOrderByClause("createTime desc");
        List<Task> taskList = taskMapper.selectPaginationByExample(taskCriteria);

        List<Long> taskIdList = taskList.stream().map(Task::getId).collect(Collectors.toList());

        TaskLogCriteria logCriteria = new TaskLogCriteria();
        logCriteria.createCriteria().andTaskIdIn(taskIdList);
        List<TaskLog> taskLogList = taskLogMapper.selectByExample(logCriteria);
        Map<Long, List<TaskLog>> taskLogsMap = taskLogList.stream().collect(Collectors.groupingBy(TaskLog::getTaskId));

        Map<Long, TaskAttribute> taskAttributeMap = this.getOperatorMapFromAttribute(taskList);

        List<TaskDetailVO> resultList = Lists.newArrayList();

        for (Task task : taskList) {
            TaskDetailVO vo = new TaskDetailVO();
            vo.setId(task.getId());
            vo.setAppId(task.getAppId());
            vo.setUniqueId(task.getUniqueId());
            vo.setBizType(task.getBizType());
            vo.setBizTypeName(EBizType4Monitor.getMainName(task.getBizType()));
            List<TaskLog> taskLogs = taskLogsMap.get(task.getId());
            if (!CollectionUtils.isEmpty(taskLogs)) {
                taskLogs = taskLogs.stream()
                        .sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
                        .collect(Collectors.toList());

                TaskLog taskLog = null;
                if (request.getStatType() == 3) {//取消,取消任务会在log表中插入一条取消环节为取消的日志记录,而这条记录没有实际意义.
                    for (int i = 0; i < taskLogs.size(); i++) {
                        taskLog = taskLogs.get(i);
                        if ("任务取消".equals(taskLog.getMsg())) {
                            taskLog = taskLogs.get(i + 1);
                            break;
                        }
                    }
                }
                if (request.getStatType() == 2) {//失败,某些任务中会有"回调通知成功"环节,此环节没有实际意义,需剔除.
                    List<String> taskErrorStepList = ETaskErrorStep.getTaskErrorStepList();
                    List<TaskLog> list = taskLogs.stream().filter(taskLog1 -> taskErrorStepList.contains(taskLog1.getMsg()))
                            .sorted((o1, o2) -> o2.getOccurTime().compareTo(o1.getOccurTime()))
                            .collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(list)) {
                        taskLog = list.get(0);
                    }
                }
                if (taskLog == null) {
                    logger.error("查询任务taskId={},状态status={}时,在task_log表中未查询到对应的状态日志记录", task.getId(), task.getStatus());
                }
                if (taskLog != null) {
                    vo.setMsg(taskLog.getMsg());
                    vo.setErrorMsg(taskLog.getErrorMsg());
                }
            } else {
                logger.error("查询任务taskId={},状态status={}时,在task_log表中未查询到对应的状态日志记录", task.getId(), task.getStatus());
            }
            vo.setOccurTime(task.getCreateTime());

            if (EBizType.OPERATOR.getCode().equals(task.getBizType())) {
                TaskAttribute taskAttribute = taskAttributeMap.get(task.getId());
                if (taskAttribute != null) {
                    vo.setWebsiteDetailName(taskAttribute.getValue());
                }
            } else {
                vo.setWebsiteDetailName(task.getWebSite());

            }
            resultList.add(vo);
        }
        return Results.newSuccessPageResult(request, total, resultList);
    }

    private List<Long> getTaskIdByTaskAttributeGroupName(StatDayRequest request) {
        TaskAttributeCriteria taskAttributeCriteria = new TaskAttributeCriteria();
        TaskAttributeCriteria.Criteria criteria = taskAttributeCriteria.createCriteria();
        criteria.andNameEqualTo(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute())
                .andValueLike("%" + request.getWebsiteDetailName() + "%");
        if (request.getStartTime() != null && request.getEndTime() != null) {
            criteria.andCreateTimeBetween(request.getStartTime(), request.getEndTime());
        } else if (request.getDate() != null) {
            criteria.andCreateTimeBetween(DateUtils.getTodayBeginDate(request.getDate()), DateUtils.getTomorrowBeginDate(request.getDate()));
        }
        taskAttributeCriteria.setOrderByClause("CreateTime desc");
        taskAttributeCriteria.setOffset(request.getOffset());
        taskAttributeCriteria.setLimit(request.getPageSize());
        List<TaskAttribute> list = taskAttributeMapper.selectPaginationByExample(taskAttributeCriteria);
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        List<Long> taskIdList = list.stream().map(TaskAttribute::getTaskId).distinct().collect(Collectors.toList());
        return taskIdList;
    }

    private Map<Long, TaskAttribute> getOperatorMapFromAttribute(List<Task> taskList) {
        List<Long> taskIdList = taskList.stream().map(Task::getId).collect(Collectors.toList());
        TaskAttributeCriteria criteria = new TaskAttributeCriteria();
        criteria.createCriteria().andTaskIdIn(taskIdList).andNameEqualTo(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());
        List<TaskAttribute> list = taskAttributeMapper.selectByExample(criteria);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<Long, TaskAttribute> map = list.stream().collect(Collectors.toMap(TaskAttribute::getTaskId, taskAttribute -> taskAttribute));
        return map;
    }

    @Override
    public Map<String, Object> queryTaskStepStatInfo(StatRequest request) {
        baseCheck(request);
        SaasErrorStepDayStatRequest statRequest = new SaasErrorStepDayStatRequest();
        statRequest.setStartDate(this.getDayStartDate(request));
        statRequest.setEndDate(this.getDayEndDate(request));
        statRequest.setDataType(EBizType4Monitor.getMonitorCode(request.getBizType()));
        MonitorResult<List<SaasErrorStepDayStatRO>> result = merchantStatAccessFacade.querySaasErrorStepDayStatListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.querySaasErrorDayStatListNoPage() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            logger.info("result of merchantStatAccessFacade.Results.newSuccessResult(() is empty : request={}, result={}", statRequest, JSON.toJSONString(result));
            return Maps.newHashMap();
        }
        List<SaasErrorStepDayStatRO> statROList = result.getData();
        Set<Date> dateSet = Sets.newHashSet();
        for (SaasErrorStepDayStatRO ro : statROList) {
            dateSet.add(ro.getDataTime());
        }

        List<SaasErrorStepDayStatDTO> statDTOList = Lists.newArrayList();
        for (SaasErrorStepDayStatRO ro : statROList) {
            SaasErrorStepDayStatDTO dto = new SaasErrorStepDayStatDTO();
            BeanUtils.convert(ro, dto);
            dto.setStepCode(ro.getErrorStepCode());
            dto.setStageCode(TaskStepEnum.getStageCodeByStepCode(ro.getErrorStepCode()));
            dto.setStageText(TaskStepEnum.getStageTextByStepCode(ro.getErrorStepCode()));
            statDTOList.add(dto);
        }

        List<Date> dateList = Lists.newArrayList(dateSet);
        //<dataTime,List<SaasErrorStepDayStatDTO>>
        Map<Date, List<SaasErrorStepDayStatDTO>> statDateMap = statDTOList.stream().collect(Collectors.groupingBy(SaasErrorStepDayStatDTO::getDataTime));
        //<dataTime,failTotalCount>
        Map<Date, Integer> failTotalCountMap = Maps.newHashMap();
        for (Date dataTime : dateList) {
            int failTotalCount = 0;
            List<SaasErrorStepDayStatDTO> dtoList = statDateMap.get(dataTime);
            if (!CollectionUtils.isEmpty(dtoList)) {
                for (SaasErrorStepDayStatDTO dto : dtoList) {
                    failTotalCount = failTotalCount + dto.getFailCount();
                }
                failTotalCountMap.put(dataTime, failTotalCount);
            }
            failTotalCountMap.put(dataTime, failTotalCount);

        }
        //<majorText,List<SaasErrorStepDayStatDTO>>
        Map<String, List<SaasErrorStepDayStatDTO>> statTextMap = statDTOList.stream().collect(Collectors.groupingBy(SaasErrorStepDayStatDTO::getStageText));
        List<String> keyList = Lists.newArrayList(statTextMap.keySet());

        Map<String, List<ChartStatDayRateVO>> rateMap = Maps.newHashMap();
        for (Map.Entry<String, List<SaasErrorStepDayStatDTO>> entry : statTextMap.entrySet()) {
            List<ChartStatRateVO> voList = Lists.newArrayList();
            String key = entry.getKey();
            Map<Date, List<SaasErrorStepDayStatDTO>> timeMap = entry.getValue().stream().collect(Collectors.groupingBy(SaasErrorStepDayStatDTO::getDataTime));
            for (Date dataTime : dateList) {
                ChartStatRateVO vo = new ChartStatRateVO();
                List<SaasErrorStepDayStatDTO> list = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(timeMap.get(dataTime))) {
                    list = timeMap.get(dataTime);
                }

                int totalCount = failTotalCountMap.get(dataTime);
                int rateCount = 0;
                for (SaasErrorStepDayStatDTO dto : list) {
                    rateCount = rateCount + dto.getFailCount();

                }
                BigDecimal rate = BigDecimal.valueOf(rateCount, 2)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(totalCount, 2), 2, BigDecimal.ROUND_HALF_UP);
                vo.setDataTime(dataTime);
                vo.setDataValue(rate);
                voList.add(vo);
            }
            voList = voList.stream().sorted((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime())).collect(Collectors.toList());
            List<ChartStatDayRateVO> newVoList = Lists.newArrayList();
            for (ChartStatRateVO vo : voList) {
                ChartStatDayRateVO newVo = new ChartStatDayRateVO();
                newVo.setDataTime(DateUtils.date2Ymd(vo.getDataTime()));
                newVo.setDataValue(vo.getDataValue());
                newVoList.add(newVo);
            }
            rateMap.put(key, newVoList);
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("keys", keyList);
        resultMap.put("values", rateMap);
        return resultMap;
    }

    private Map<String, List<ChartStatRateVO>> wrapRateTaskChart(Map<String, Map<Date, BigDecimal>> dataMap) {
        Map<String, List<ChartStatRateVO>> valuesMap = Maps.newHashMap();
        for (Map.Entry<String, Map<Date, BigDecimal>> dataEntry : dataMap.entrySet()) {
            List<ChartStatRateVO> voList = Lists.newArrayList();
            for (Map.Entry<Date, BigDecimal> entry : dataEntry.getValue().entrySet()) {
                ChartStatRateVO vo = new ChartStatRateVO();
                vo.setDataTime(entry.getKey());
                vo.setDataValue(entry.getValue());
                voList.add(vo);
            }
            voList = voList.stream().sorted((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(voList)) {
                voList.remove(voList.size() - 1);
            }
            valuesMap.put(dataEntry.getKey(), voList);
        }
        return valuesMap;
    }

    private Map<String, Map<Date, BigDecimal>> countRateTask(Map<Date, Integer> valueTotalMap,
                                                             Map<Date, Integer> valueSuccessMap,
                                                             Map<Date, Integer> valueFailMap,
                                                             Map<Date, Integer> valueCancelMap) {
        Map<String, Map<Date, BigDecimal>> valuesMap = Maps.newLinkedHashMap();
        Map<Date, BigDecimal> successRateMap = Maps.newHashMap();
        Map<Date, BigDecimal> failRateMap = Maps.newHashMap();
        Map<Date, BigDecimal> cancelRateMap = Maps.newHashMap();
        for (Map.Entry<Date, Integer> entry : valueTotalMap.entrySet()) {
            Integer total = entry.getValue();
            Integer success = valueSuccessMap.get(entry.getKey());
            Integer fail = valueFailMap.get(entry.getKey());
            Integer cancel = valueCancelMap.get(entry.getKey());
            BigDecimal successRate = BigDecimal.valueOf(success * 100).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal failRate = BigDecimal.valueOf(fail * 100).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal cancelRate = BigDecimal.valueOf(cancel * 100).divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
            successRateMap.put(entry.getKey(), successRate);
            failRateMap.put(entry.getKey(), failRate);
            cancelRateMap.put(entry.getKey(), cancelRate);
        }
        valuesMap.put("转化率", successRateMap);
        valuesMap.put("失败率", failRateMap);
        valuesMap.put("取消率", cancelRateMap);
        return valuesMap;
    }

    private Map<String, List<ChartStatVO>> countTotalTask(Map<String, Map<Date, Integer>> appNameMap) {
        Map<String, List<ChartStatVO>> valuesMap = Maps.newHashMap();
        //计算总任务量的map
        Map<Date, Integer> totalMap = Maps.newHashMap();
        for (Map.Entry<String, Map<Date, Integer>> entry : appNameMap.entrySet()) {
            List<ChartStatVO> voList = Lists.newArrayList();
            for (Map.Entry<Date, Integer> valueEntry : entry.getValue().entrySet()) {
                ChartStatVO vo = new ChartStatVO();
                vo.setDataTime(valueEntry.getKey());
                vo.setDataValue(valueEntry.getValue());
                voList.add(vo);
                if (totalMap.get(valueEntry.getKey()) == null) {
                    totalMap.put(valueEntry.getKey(), valueEntry.getValue());
                } else {
                    int i = totalMap.get(valueEntry.getKey());
                    totalMap.put(valueEntry.getKey(), i + valueEntry.getValue());
                }
            }
            voList = voList.stream().sorted(((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime()))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(voList)) {
                voList.remove(voList.size() - 1);
            }
            valuesMap.put(entry.getKey(), voList);
        }
        List<ChartStatVO> totalVOList = Lists.newArrayList();
        for (Map.Entry<Date, Integer> entry : totalMap.entrySet()) {
            ChartStatVO vo = new ChartStatVO();
            vo.setDataTime(entry.getKey());
            vo.setDataValue(entry.getValue());
            totalVOList.add(vo);
        }
        totalVOList = totalVOList.stream().sorted((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime())).collect(Collectors.toList());
        totalVOList.remove(totalVOList.size() - 1);
        valuesMap.put("总任务量", totalVOList);
        return valuesMap;
    }

    private void fillDataTime(Map<String, Map<Date, Integer>> dataMap, List<Date> dataTimeList) {
        for (Map.Entry<String, Map<Date, Integer>> entry : dataMap.entrySet()) {
            Map<Date, Integer> valueMap = entry.getValue();
            for (Date date : dataTimeList) {
                if (valueMap.get(date) == null) {
                    valueMap.put(date, 0);
                }
            }
        }

    }

    private Map<String, Map<Date, Integer>> changeKey2AppName(Map<String, Map<Date, Integer>> dataMap) {
        Map<String, Map<Date, Integer>> appNameMap = Maps.newHashMap();
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(Lists.newArrayList(dataMap.keySet()));
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList
                .stream()
                .collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        for (Map.Entry<String, Map<Date, Integer>> entry : dataMap.entrySet()) {
            MerchantBase merchantBase = merchantBaseMap.get(entry.getKey());
            if (merchantBase == null) {
                logger.error("系统任务量统计中,appId={}在MerchantBase表中未查询到相关记录", entry.getKey());
                continue;
            }
            appNameMap.put(merchantBase.getAppName(), entry.getValue());
        }
        return appNameMap;
    }

    private Map<String, Integer> changeKey2AppName4Pie(Map<String, Integer> dataMap) {
        Map<String, Integer> appNameMap = Maps.newHashMap();
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(Lists.newArrayList(dataMap.keySet()));
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList
                .stream()
                .collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            MerchantBase merchantBase = merchantBaseMap.get(entry.getKey());
            if (merchantBase == null) {
                logger.error("系统任务量统计中,appId={}在MerchantBase表中未查询到相关记录", entry.getKey());
                continue;
            }
            appNameMap.put(merchantBase.getAppName(), entry.getValue());
        }
        return appNameMap;
    }


    private Result<Map<String, Object>> wrapDataPageResult(StatRequest request, Map<String, List<MerchantStatDayAccessRO>> timeMap) {
        int totalCount = timeMap.keySet().size();
        List<MerchantStatVO> dataList = Lists.newArrayList();
        if (totalCount == 0) {
            return Results.newSuccessPageResult(request, totalCount, dataList);
        }
        for (Map.Entry<String, List<MerchantStatDayAccessRO>> entry : timeMap.entrySet()) {
            MerchantStatVO merchantStatVO = new MerchantStatVO();
            int useTotalCount = 0, dailyLimit = 0;
            List<MerchantStatDayAccessRO> list = entry.getValue();
            for (MerchantStatDayAccessRO ro : list) {
                useTotalCount = useTotalCount + ro.getSuccessCount();
                dailyLimit = dailyLimit + ro.getDailyLimit();
            }
            merchantStatVO.setDateStr(entry.getKey());
            merchantStatVO.setDailyLimit(dailyLimit);
            merchantStatVO.setTotalCount(useTotalCount);
            if (dailyLimit == 0) {
                merchantStatVO.setDailyLimitRate(BigDecimal.ZERO);
            } else {
                BigDecimal dailyLimitDecimal = new BigDecimal(dailyLimit);
                BigDecimal useTotalCountDecimal = new BigDecimal(useTotalCount);
                BigDecimal weekDailyLimitRate = useTotalCountDecimal.divide(dailyLimitDecimal, 2, BigDecimal.ROUND_HALF_UP);
                merchantStatVO.setDailyLimitRate(weekDailyLimitRate);
            }
            dataList.add(merchantStatVO);
        }
        int total = dataList.size();
        int start = request.getOffset();
        int end = request.getOffset() + request.getPageSize();
        if (end > total) {
            end = total;
        }
        dataList = dataList.subList(start, end);
        return Results.newSuccessPageResult(request, totalCount, dataList);
    }

    private void checkParam(StatRequest request) {
        baseCheck(request);
        if (StringUtils.isEmpty(request.getAppId())) {
            throw new IllegalArgumentException("请求参数appId不能为空");
        }
        if (request.getPageNumber() < 1) {
            throw new IllegalArgumentException("请求参数pageNumber非法！");
        }
        if (request.getPageSize() < 1) {
            throw new IllegalArgumentException("请求参数pageSize非法！");
        }
    }

    public void baseCheck(StatRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        Byte bizType = request.getBizType();
        if (bizType == null) {
            throw new IllegalArgumentException("请求参数bizType不能为空！");
        }
        if (bizType < -2 || bizType > 4) {
            throw new IllegalArgumentException("请求参数bizType非法！");
        }
        if (request.getDateType() == null || request.getDateType() < 0 || request.getDateType() > 4) {
            throw new IllegalArgumentException("请求参数dateType为空或非法!");
        }
        if (request.getDateType() == 0) {
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("请求参数startDate或endDate不能为空！");
            }
            if (request.getStartDate().after(request.getEndDate())) {
                throw new IllegalArgumentException("请求参数startDate不能晚于endDate！");
            }
        }
    }

    /**
     * 获取开始时间
     *
     * @param request
     * @return
     */
    protected Date getStartDate(StatRequest request) {
        // 0-自选日期，1-过去1天，2-过去3天，3-过去7天，4-过去30天;
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return request.getStartDate();
            case 1:
                return org.apache.commons.lang.time.DateUtils.addHours(new Date(), -24);
            case 2:
                return org.apache.commons.lang.time.DateUtils.addHours(new Date(), -24 * 3);
            case 3:
                return org.apache.commons.lang.time.DateUtils.addHours(new Date(), -24 * 7);
            case 4:
                return org.apache.commons.lang.time.DateUtils.addHours(new Date(), -24 * 30);
        }
        return null;
    }

    protected Date getDayStartDate(StatRequest request) {
        // 0-自选日期，1-过去1天，2-过去3天，3-过去7天，4-过去30天;
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return DateUtils.getTodayBeginDate(request.getStartDate());
            case 1:
                return org.apache.commons.lang.time.DateUtils.addDays(DateUtils.getTodayBeginDate(), -1);
            case 2:
                return org.apache.commons.lang.time.DateUtils.addDays(DateUtils.getTodayBeginDate(), -3);
            case 3:
                return org.apache.commons.lang.time.DateUtils.addDays(DateUtils.getTodayBeginDate(), -7);
            case 4:
                return org.apache.commons.lang.time.DateUtils.addDays(DateUtils.getTodayBeginDate(), -30);
        }
        return null;
    }

    /**
     * 获取结束时间
     *
     * @param request
     * @return
     */
    protected Date getEndDate(StatRequest request) {
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return org.apache.commons.lang.time.DateUtils.addSeconds(request.getEndDate(), 24 * 60 * 60 - 1);
        }
        return new Date();
    }

    protected Date getDayEndDate(StatRequest request) {
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return DateUtils.getTodayBeginDate(request.getEndDate());
        }
        return DateUtils.getTodayBeginDate(new Date());
    }

    static class Obj {
        public Integer num;
        public Boolean flag;

        public Obj(Integer num, Boolean flag) {
            this.num = num;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return num + "," + flag + ";";
        }
    }
}
