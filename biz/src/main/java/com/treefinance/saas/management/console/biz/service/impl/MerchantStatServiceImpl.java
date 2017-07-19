package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.management.console.biz.service.MerchantStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatDayVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType4Monitor;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatDayAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatDayAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.MerchantStatAccessFacade;
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
        Map<String, MerchantStatDayAccessRO> dayAccessROMap = this.queryTotalDayAccessList(request);
        List<MerchantStatDayVO> dataList = Lists.newArrayList();
        result.getData().forEach(ro -> {
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
        });
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
        //遍历获取时间节点list
        Set<Date> dataTimeSet = Sets.newHashSet();
        result.getData().forEach(ro -> {
            dataTimeSet.add(ro.getDataTime());
        });
        List<Date> dataTimeList = Lists.newArrayList(dataTimeSet);

        //<appId,<dataTime,totalCount>>
        Map<String, Map<Date, Integer>> dataMap = Maps.newHashMap();
        result.getData().forEach(ro -> {
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
}
