package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.biz.service.MerchantStatService;
import com.treefinance.saas.management.console.common.domain.Constants;
import com.treefinance.saas.management.console.common.domain.dto.SaasErrorStepDayStatDTO;
import com.treefinance.saas.management.console.common.domain.request.StatDayRequest;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatDayRateVO;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatRateVO;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatDayVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatVO;
import com.treefinance.saas.management.console.common.domain.vo.PieChartStatRateVO;
import com.treefinance.saas.management.console.common.domain.vo.TaskDetailVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.EBizType4Monitor;
import com.treefinance.saas.management.console.common.enumeration.ESaasEnv;
import com.treefinance.saas.management.console.common.enumeration.ETaskErrorStep;
import com.treefinance.saas.management.console.common.enumeration.TaskStepEnum;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantUser;
import com.treefinance.saas.management.console.dao.entity.TaskAndTaskAttribute;
import com.treefinance.saas.management.console.dao.entity.TaskLog;
import com.treefinance.saas.merchant.center.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppBizLicenseByBizTypeRequest;
import com.treefinance.saas.merchant.center.facade.request.console.QueryMerchantByMerchantIdRequest;
import com.treefinance.saas.merchant.center.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.center.facade.result.console.AppBizTypeResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantUserResult;
import com.treefinance.saas.merchant.center.facade.service.AppBizLicenseFacade;
import com.treefinance.saas.merchant.center.facade.service.AppBizTypeFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantUserFacade;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatDayAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.SaasErrorStepDayStatRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.BaseStatRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.SaasErrorStepDayStatRO;
import com.treefinance.saas.monitor.facade.service.stat.MerchantStatAccessFacade;
import com.treefinance.saas.taskcenter.facade.request.TaskAndAttributeRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskLogRequest;
import com.treefinance.saas.taskcenter.facade.result.TaskAndAttributeRO;
import com.treefinance.saas.taskcenter.facade.result.TaskLogRO;
import com.treefinance.saas.taskcenter.facade.result.common.TaskPagingResult;
import com.treefinance.saas.taskcenter.facade.result.common.TaskResult;
import com.treefinance.saas.taskcenter.facade.service.TaskFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskLogFacade;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Resource
    private MerchantStatAccessFacade merchantStatAccessFacade;

    @Resource
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Resource
    private TaskLogFacade taskLogFacade;
    @Resource
    private MerchantUserFacade merchantUserFacade;
    @Resource
    private AppBizLicenseFacade appBizLicenseFacade;
    @Resource
    private AppBizTypeFacade appBizTypeFacade;
    @Resource
    private TaskFacade taskFacade;

    @Resource
    private AppBizTypeService appBizTypeService;

    @Override
    public SaasResult<Map<String, Object>> queryDayAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setPageNumber(request.getPageNumber());
        statRequest.setPageSize(request.getPageSize());
        statRequest.setDataType(request.getBizType());
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessList() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            logger.error("merchantStatAccessFacade.queryDayAccessList()为空 : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
            return Results.newPageResult(request, 0, Lists.newArrayList());
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
            if (dayAccessROMap.get(DateUtils.date2Ymd(ro.getDataTime())) != null && dayAccessROMap.get(DateUtils.date2Ymd(ro.getDataTime())).getDailyLimit() != 0) {
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
        return Results.newPageResult(request, result.getTotalCount(), dataList);
    }

    private Map<String, MerchantStatDayAccessRO> queryTotalDayAccessList(StatRequest request) {
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setDataType(request.getBizType());
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }

        Map<String, MerchantStatDayAccessRO> resultMap =
            result.getData().stream().collect(Collectors.toMap(ro -> DateUtils.date2Ymd(ro.getDataTime()), ro -> ro, (key1, key2) -> key1));
        return resultMap;
    }

    @Override
    public SaasResult<Map<String, Object>> queryWeekAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(DateUtils.getFirstDayOfWeek(request.getStartDate()));
        statRequest.setEndDate(DateUtils.getLastDayOfWeek(request.getEndDate()));
        statRequest.setDataType(request.getBizType());
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        // <weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantStatDayAccessRO>> timeMap = Maps.newLinkedHashMap();
        result.getData().forEach(ro -> {
            String timeStr = DateUtils.getWeekStrOfYear(ro.getDataTime());
            List<MerchantStatDayAccessRO> timeValueList = timeMap.get(timeStr);
            timeValueListAddElement(timeMap, ro, timeStr, timeValueList);
        });
        return wrapDataPageResult(request, timeMap);

    }

    private void timeValueListAddElement(Map<String, List<MerchantStatDayAccessRO>> timeMap, MerchantStatDayAccessRO ro, String timeStr,
        List<MerchantStatDayAccessRO> timeValueList) {
        if (CollectionUtils.isEmpty(timeValueList)) {
            timeValueList = Lists.newArrayList();
            timeValueList.add(ro);
            timeMap.put(timeStr, timeValueList);
        } else {
            timeValueList.add(ro);
        }
    }

    @Override
    public SaasResult<Map<String, Object>> queryMonthAccessList(StatRequest request) {
        checkParam(request);
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        statRequest.setAppId(request.getAppId());
        statRequest.setStartDate(DateUtils.getFirstDayOfMonth(request.getStartDate()));
        statRequest.setEndDate(DateUtils.getLastDayOfMonth(request.getEndDate()));
        statRequest.setDataType(request.getBizType());
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }

        // <weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantStatDayAccessRO>> timeMap = Maps.newLinkedHashMap();
        result.getData().forEach(ro -> {
            String timeStr = DateUtils.date2SimpleYm(ro.getDataTime());
            List<MerchantStatDayAccessRO> timeValueList = timeMap.get(timeStr);

            timeValueListAddElement(timeMap, ro, timeStr, timeValueList);
        });
        return wrapDataPageResult(request, timeMap);
    }

    @Override
    public Map<String, Object> queryAllAccessList(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setIntervalMins(request.getIntervalMins());
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);

        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        List<MerchantStatAccessRO> roList = changeIntervalDataTime(result.getData(), request.getIntervalMins());

        // 遍历获取时间节点list
        Set<Date> dataTimeSet = Sets.newHashSet();
        roList.forEach(ro -> {
            dataTimeSet.add(ro.getDataTime());
        });
        List<Date> dataTimeList = Lists.newArrayList(dataTimeSet);

        // <appId,<dataTime,totalCount>>
        Map<String, Map<Date, Integer>> dataMap = Maps.newHashMap();
        roList.forEach(ro -> {
            Map<Date, Integer> valueMap = dataMap.get(ro.getAppId());
            initMapAndPutData(dataMap, ro, valueMap);
        });
        // 填充缺少的时间点的totalCount为0
        this.fillDataTime(dataMap, dataTimeList);
        // 更换map的key为appName
        Map<String, Map<Date, Integer>> appNameMap = this.changeKey2AppName(dataMap);

        List<String> keysList = Lists.newArrayList(appNameMap.keySet()).stream().sorted((String::compareTo)).collect(Collectors.toList());
        keysList.add(0, "总任务量");
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.countTotalTask(appNameMap);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private void initMapAndPutData(Map<String, Map<Date, Integer>> dataMap, MerchantStatAccessRO ro, Map<Date, Integer> valueMap) {
        if (valueMap == null || valueMap.isEmpty()) {
            valueMap = Maps.newHashMap();
            valueMap.put(ro.getDataTime(), ro.getTotalCount());
            dataMap.put(ro.getAppId(), valueMap);
        } else {
            valueMap.put(ro.getDataTime(), ro.getTotalCount());
        }
    }

    @Override
    public Map<String, Object> queryAllAccessList4Pie(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(ESaasEnv.ALL.getCode());

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryAllAccessList() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }
        // <appId,totalCount>
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
        // 所有商户此段时间内的总任务量
        int allTotalCount = totalCountList.stream().reduce(0, (sum, e) -> sum + e);
        List<PieChartStatRateVO> valueList = Lists.newArrayList();
        for (Map.Entry<String, Integer> entry : appNameDataMap.entrySet()) {
            BigDecimal value = BigDecimal.valueOf(entry.getValue()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(allTotalCount), 2, BigDecimal.ROUND_HALF_UP);
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
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(request.getSaasEnv());
        statRequest.setIntervalMins(request.getIntervalMins());
        statRequest.setAppId(Constants.VIRTUAL_TOTAL_STAT_APPID);

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllSuccessAccessList(statRequest);

        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        List<String> keysList = Lists.newArrayList("总数", "成功数", "失败数", "取消数");
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.getNumberTaskChart(result.getData());
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private Map<String, List<ChartStatVO>> getNumberTaskChart(List<MerchantStatAccessRO> data) {
        Map<String, List<ChartStatVO>> result = Maps.newHashMap();
        List<ChartStatVO> totalList = Lists.newArrayList();
        List<ChartStatVO> successList = Lists.newArrayList();
        List<ChartStatVO> failList = Lists.newArrayList();
        List<ChartStatVO> cancelList = Lists.newArrayList();

        for (MerchantStatAccessRO ro : data) {
            Date dataTime = ro.getDataTime();
            ChartStatVO total = new ChartStatVO(dataTime, ro.getTotalCount());
            ChartStatVO success = new ChartStatVO(dataTime, ro.getSuccessCount());
            ChartStatVO fail = new ChartStatVO(dataTime, ro.getFailCount());
            ChartStatVO cancel = new ChartStatVO(dataTime, ro.getCancelCount());

            totalList.add(total);
            successList.add(success);
            failList.add(fail);
            cancelList.add(cancel);
        }
        result.put("总数", totalList);
        result.put("成功数", successList);
        result.put("失败数", failList);
        result.put("取消数", cancelList);
        return result;
    }

    private List<MerchantStatAccessRO> changeIntervalDataTime(List<MerchantStatAccessRO> list, final Integer intervalMins) {
        List<MerchantStatAccessRO> resultList = Lists.newArrayList();
        Map<String, List<MerchantStatAccessRO>> appIdROMap = list.stream().collect(Collectors.groupingBy(BaseStatRO::getAppId));
        for (Map.Entry<String, List<MerchantStatAccessRO>> appIdROEntry : appIdROMap.entrySet()) {
            if (CollectionUtils.isEmpty(appIdROEntry.getValue())) {
                continue;
            }
            Map<Date, List<MerchantStatAccessRO>> map =
                appIdROEntry.getValue().stream().collect(Collectors.groupingBy(ro -> DateUtils.getLaterIntervalDateTime(ro.getDataTime(), intervalMins)));
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
            voList = voList.stream().sorted(Comparator.comparing(ChartStatVO::getDataTime)).collect(Collectors.toList());
            valuesMap.put(dataEntry.getKey(), voList);
        }
        return valuesMap;
    }

    @Override
    public Map<String, Object> queryAccessRateList(StatRequest request) {
        Map<String, Object> wrapMap = Maps.newHashMap();
        MerchantStatAccessRequest statRequest = new MerchantStatAccessRequest();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(request.getSaasEnv());
        statRequest.setIntervalMins(request.getIntervalMins());
        statRequest.setAppId(Constants.VIRTUAL_TOTAL_STAT_APPID);

        MonitorResult<List<MerchantStatAccessRO>> result = merchantStatAccessFacade.queryAllSuccessAccessList(statRequest);
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }

        wrapMap.put("keys", Lists.newArrayList("转化率", "失败率", "取消率"));
        Map<String, List<ChartStatRateVO>> valuesMap = this.getRateTaskChart(result.getData());
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private Map<String, List<ChartStatRateVO>> getRateTaskChart(List<MerchantStatAccessRO> data) {
        Map<String, List<ChartStatRateVO>> result = Maps.newHashMap();
        List<ChartStatRateVO> successList = Lists.newArrayList();
        List<ChartStatRateVO> failList = Lists.newArrayList();
        List<ChartStatRateVO> cancelList = Lists.newArrayList();

        for (MerchantStatAccessRO ro : data) {
            Date dataTime = ro.getDataTime();
            ChartStatRateVO success = new ChartStatRateVO(dataTime, ro.getSuccessRate());
            ChartStatRateVO fail = new ChartStatRateVO(dataTime, ro.getFailRate());
            ChartStatRateVO cancel = new ChartStatRateVO(dataTime, ro.getCancelRate());

            successList.add(success);
            failList.add(fail);
            cancelList.add(cancel);
        }
        result.put("转化率", successList);
        result.put("失败率", failList);
        result.put("取消率", cancelList);
        return result;
    }

    @Override
    public List<MerchantStatOverviewTimeVO> queryOverviewAccessList(StatRequest request) {
        Integer statType = request.getStatType();

        // 获取时间的列表
        List<String> dateList = getDateStrings(request);

        // 从monitor-server 获取数据
        MerchantStatDayAccessRequest statRequest = new MerchantStatDayAccessRequest();
        MonitorResult<List<MerchantStatDayAccessRO>> result = getListMonitorResult(request, statRequest);
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            logger.info("result of merchantStatAccessFacade.queryDayAccessListNoPage() is empty : request={}, result={}", statRequest, JSON.toJSONString(result));
            return Lists.newArrayList();
        }
        // 将数据计算比率
        List<MerchantStatOverviewVO> overviewVOList = getMerchantStatOverviewVOS(statType, result.getData());
        // 获取所有的appBizLicense 数据
        List<AppBizLicense> appBizLicenseList = queryAllAppBizLicense(request);

        // 获取所有的appBizLicense的appId数据
        List<MerchantBase> merchantBaseList = getMerchantBasesByAppId(appBizLicenseList);

        //
        List<MerchantUser> merchantUserList = getMerchantUsersById(merchantBaseList);

        // <merchantId,merchantUser>
        List<Long> ids = findDuplicateElements(merchantUserList);
        logger.info("重复的merchantUser数据{}", JSON.toJSONString(ids));

        Map<Long, MerchantUser> merchantUserMap = getLongMerchantUserMap(merchantUserList);

        Map<String, List<MerchantStatOverviewVO>> ovMap =
            overviewVOList.stream().filter(ov -> StringUtils.isNotBlank(ov.getAppId())).collect(Collectors.groupingBy(MerchantStatOverviewVO::getAppId));

        Map<String, Map<String, MerchantStatOverviewVO>> resultMap = Maps.newHashMap();
        for (Map.Entry<String, List<MerchantStatOverviewVO>> entry : ovMap.entrySet()) {
            List<MerchantStatOverviewVO> list = entry.getValue();
            Map<String, MerchantStatOverviewVO> tempMap = list.stream().collect(Collectors.toMap(vo -> DateUtils.date2Ymd(vo.getDate()), vo -> vo));
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
            Map<String, MerchantStatOverviewVO> entry = resultMap.get(appId);
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

        timeOverViewList = timeOverViewList.stream().sorted((n1, n2) -> n2.getNum().compareTo(n1.getNum())).collect(Collectors.toList());

        return timeOverViewList;
    }

    private Map<Long, MerchantUser> getLongMerchantUserMap(List<MerchantUser> merchantUserList) {

        return merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, m -> m));
    }

    private List<Long> findDuplicateElements(List<MerchantUser> merchantUserList) {
        return merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, merchantUser -> 1, Integer::sum)).entrySet().stream()
            .filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private List<MerchantUser> getMerchantUsersById(List<MerchantBase> merchantBaseList) {

        List<Long> merchantIdList = merchantBaseList.stream().map(MerchantBase::getId).distinct().collect(Collectors.toList());
        logger.info("通过MerchantId获取merchantUser：{}", JSON.toJSONString(merchantIdList));
        List<List<Long>> merchantIdPartList = Lists.partition(merchantIdList, 50);
        List<MerchantUser> merchantUserList = Lists.newArrayList();
        for (List<Long> merchantIdParts : merchantIdPartList) {
            QueryMerchantByMerchantIdRequest queryMerchantByMerchantIdRequest = new QueryMerchantByMerchantIdRequest();
            queryMerchantByMerchantIdRequest.setMerchantId(merchantIdParts);

            MerchantResult<List<MerchantUserResult>> listMerchantResult;

            try {
                listMerchantResult = merchantUserFacade.queryMerchantUserByMerchantId(queryMerchantByMerchantIdRequest);
                logger.info("商户中心放回数据:{}", listMerchantResult);
            } catch (RpcException e) {
                logger.info("商户中心调用出错:{}", e.getMessage());
                return new ArrayList<>();
            }

            logger.info("商户中心返回数据：{}", JSON.toJSONString(listMerchantResult.getData()));

            List<MerchantUser> merchantUserPartList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantUser.class);
            merchantUserList.addAll(merchantUserPartList);
        }

        logger.info("merchantUser列表数据：{}", JSON.toJSONString(merchantUserList));

        return merchantUserList;
    }

    private List<MerchantBase> getMerchantBasesByAppId(List<AppBizLicense> appBizLicenseList) {
        List<String> appIdList = appBizLicenseList.stream().map(AppBizLicense::getAppId).distinct().collect(Collectors.toList());
        logger.info("通过appId获取merchantBase：{}", JSON.toJSONString(appIdList));

        List<List<String>> appIdPartList = Lists.partition(appIdList, 50);
        List<MerchantBase> merchantBaseList = Lists.newArrayList();
        for (List<String> appIdParts : appIdPartList) {
            QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
            queryMerchantByAppIdRequest.setAppIds(appIdParts);
            MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
            List<MerchantBase> merchantBasePartList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantBase.class);
            merchantBaseList.addAll(merchantBasePartList);
        }
        return merchantBaseList;
    }

    private List<AppBizLicense> queryAllAppBizLicense(StatRequest request) {
        MerchantResult<List<AppBizLicenseResult>> merchantResult;
        QueryAppBizLicenseByBizTypeRequest queryAppBizLicenseByBizTypeRequest = new QueryAppBizLicenseByBizTypeRequest();
        if (request.getBizType() != 0) {
            queryAppBizLicenseByBizTypeRequest.setBizType(request.getBizType());
            merchantResult = appBizLicenseFacade.queryAppBizLicenseByBizType(queryAppBizLicenseByBizTypeRequest);
        } else {
            merchantResult = appBizLicenseFacade.queryAllAppBizLicense(queryAppBizLicenseByBizTypeRequest);
        }

        return DataConverterUtils.convert(merchantResult.getData(), AppBizLicense.class);
    }

    private List<MerchantStatOverviewVO> getMerchantStatOverviewVOS(Integer statType, List<MerchantStatDayAccessRO> result) {
        List<MerchantStatOverviewVO> overviewVOList = Lists.newArrayList();
        result.forEach(ro -> {
            MerchantStatOverviewVO vo = new MerchantStatOverviewVO();
            vo.setAppId(ro.getAppId());
            vo.setDateStr(DateUtils.date2Md(ro.getDataTime()));
            vo.setDate(ro.getDataTime());
            vo.setTotalCount(ro.getTotalCount());
            if (statType == 1) {
                BigDecimal successRate =
                    BigDecimal.valueOf(ro.getSuccessCount()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(successRate);
            } else if (statType == 2) {
                BigDecimal failRate =
                    BigDecimal.valueOf(ro.getFailCount()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(failRate);
            } else {
                BigDecimal cancelRate =
                    BigDecimal.valueOf(ro.getCancelCount()).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(ro.getTotalCount()), 2, BigDecimal.ROUND_HALF_UP);
                vo.setRate(cancelRate);
            }
            overviewVOList.add(vo);
        });
        return overviewVOList;
    }

    private List<String> getDateStrings(StatRequest request) {
        List<String> dateList = DateUtils.getDayStrDateLists(this.getStartDate(request), this.getEndDate(request));
        if (dateList.size() != 7) {
            throw new IllegalArgumentException("请求参数startDate,endDate非法!");
        }
        return dateList;
    }

    private MonitorResult<List<MerchantStatDayAccessRO>> getListMonitorResult(StatRequest request, MerchantStatDayAccessRequest statRequest) {
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setDataType(request.getBizType());
        statRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryAllDayAccessListNoPage(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        return result;
    }

    @Override
    public SaasResult<Map<String, Object>> queryOverviewDetailAccessList(StatDayRequest request) {

        TaskCenterRemoteService taskCenterRemoteService = new TaskCenterRemoteService(request).invoke();
        if (taskCenterRemoteService.is()) {
            return Results.newPageResult(request, taskCenterRemoteService.getTotal(), Lists.newArrayList());
        }
        long total = taskCenterRemoteService.getTotal();
        List<TaskAndTaskAttribute> taskList = taskCenterRemoteService.getTaskList();

        List<Long> taskIdList = taskList.stream().map(TaskAndTaskAttribute::getId).collect(Collectors.toList());

        List<TaskLog> taskLogList = getTaskLogs(taskIdList);
        Map<Long, List<TaskLog>> taskLogsMap = taskLogList.stream().collect(Collectors.groupingBy(TaskLog::getTaskId));

        List<TaskDetailVO> resultList = Lists.newArrayList();
        Map<Byte, String> bizTypeMap = appBizTypeService.getBizTypeNameMap();

        for (TaskAndTaskAttribute task : taskList) {
            TaskDetailVO vo = new TaskDetailVO();
            vo.setId(task.getId());
            vo.setAppId(task.getAppId());
            vo.setUniqueId(task.getUniqueId());
            vo.setBizType(task.getBizType());
            vo.setBizTypeName(bizTypeMap.get(task.getBizType()));
            List<TaskLog> taskLogs = taskLogsMap.get(task.getId());
            if (!CollectionUtils.isEmpty(taskLogs)) {
                taskLogs = taskLogs.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toList());

                TaskLog taskLog = null;
                // 取消,取消任务会在log表中插入一条取消环节为取消的日志记录,而这条记录没有实际意义.
                if (request.getStatType() == 3) {
                    for (int i = 0; i < taskLogs.size(); i++) {
                        taskLog = taskLogs.get(i);
                        if ("任务取消".equals(taskLog.getMsg())) {
                            taskLog = taskLogs.get(i + 1);
                            break;
                        }
                    }
                }
                // 失败,某些任务中会有"回调通知成功"环节,此环节没有实际意义,需剔除.
                if (request.getStatType() == 2) {
                    List<String> taskErrorStepList = ETaskErrorStep.getTaskErrorStepList();
                    List<TaskLog> list = taskLogs.stream().filter(taskLog1 -> taskErrorStepList.contains(taskLog1.getMsg()))
                        .sorted((o1, o2) -> o2.getOccurTime().compareTo(o1.getOccurTime())).collect(Collectors.toList());
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
                vo.setWebsiteDetailName(task.getValue());
            } else {
                vo.setWebsiteDetailName(task.getWebSite());

            }
            resultList.add(vo);
        }
        return Results.newPageResult(request, total, resultList);
    }

    private List<TaskLog> getTaskLogs(List<Long> taskIdList) {
        TaskLogRequest request = new TaskLogRequest();
        request.setTaskIdList(taskIdList);

        TaskResult<List<TaskLogRO>> result = taskLogFacade.queryTaskLogById(request);

        if (result.isSuccess()) {
            return DataConverterUtils.convert(result.getData(), TaskLog.class);
        }

        return Lists.newArrayList();

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
            logger.debug("merchantStatAccessFacade.querySaasErrorDayStatListNoPage() : statRequest={},result={}", JSON.toJSONString(statRequest), JSON.toJSONString(result));
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
        // <dataTime,List<SaasErrorStepDayStatDTO>>
        Map<Date, List<SaasErrorStepDayStatDTO>> statDateMap = statDTOList.stream().collect(Collectors.groupingBy(SaasErrorStepDayStatDTO::getDataTime));
        // <dataTime,failTotalCount>
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
        // <majorText,List<SaasErrorStepDayStatDTO>>
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
                if (totalCount == 0) {
                    vo.setDataTime(dataTime);
                    vo.setDataValue(BigDecimal.valueOf(0, 2));
                    voList.add(vo);
                    continue;
                }
                int rateCount = 0;
                for (SaasErrorStepDayStatDTO dto : list) {
                    rateCount = rateCount + dto.getFailCount();

                }
                BigDecimal rate = BigDecimal.valueOf(rateCount, 2).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(totalCount, 2), 2, BigDecimal.ROUND_HALF_UP);
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
            voList = voList.stream().sorted(Comparator.comparing(ChartStatRateVO::getDataTime)).collect(Collectors.toList());
            valuesMap.put(dataEntry.getKey(), voList);
        }
        return valuesMap;
    }

    private Map<String, List<ChartStatVO>> countTotalTask(Map<String, Map<Date, Integer>> appNameMap) {
        Map<String, List<ChartStatVO>> valuesMap = Maps.newHashMap();
        // 计算总任务量的map
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
            voList = voList.stream().sorted((Comparator.comparing(ChartStatVO::getDataTime))).collect(Collectors.toList());
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
        totalVOList = totalVOList.stream().sorted(Comparator.comparing(ChartStatVO::getDataTime)).collect(Collectors.toList());
        if (totalVOList.size() > 0) {
            totalVOList.remove(totalVOList.size() - 1);
        }
        valuesMap.put("总任务量", totalVOList);
        return valuesMap;
    }

    private void fillDataTime(Map<String, Map<Date, Integer>> dataMap, List<Date> dataTimeList) {
        for (Map.Entry<String, Map<Date, Integer>> entry : dataMap.entrySet()) {
            Map<Date, Integer> valueMap = entry.getValue();
            for (Date date : dataTimeList) {
                valueMap.putIfAbsent(date, 0);
            }
        }

    }

    private Map<String, Map<Date, Integer>> changeKey2AppName(Map<String, Map<Date, Integer>> dataMap) {
        Map<String, Map<Date, Integer>> appNameMap = Maps.newHashMap();
        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(Lists.newArrayList(dataMap.keySet()));
        MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantBase.class);

        // <appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

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
        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(Lists.newArrayList(dataMap.keySet()));
        MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantBase.class);

        // <appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

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

    private SaasResult<Map<String, Object>> wrapDataPageResult(StatRequest request, Map<String, List<MerchantStatDayAccessRO>> timeMap) {
        int totalCount = timeMap.keySet().size();
        List<MerchantStatVO> dataList = Lists.newArrayList();
        if (totalCount == 0) {
            return Results.newPageResult(request, totalCount, dataList);
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
        return Results.newPageResult(request, totalCount, dataList);
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
            default:
                return null;

        }
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
            default:
                return null;
        }
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
                Date now = new Date();
                Date endDate = com.treefinance.saas.management.console.common.utils.DateUtils.getTodayEndDate(request.getEndDate());
                if (endDate.compareTo(now) > 0) {
                    return now;
                }
                return endDate;
            default:
                return new Date();
        }

    }

    protected Date getDayEndDate(StatRequest request) {
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return DateUtils.getTodayBeginDate(request.getEndDate());
            default:
                return DateUtils.getTodayBeginDate(new Date());
        }

    }

    private class TaskCenterRemoteService {
        private boolean myResult;
        private StatDayRequest request;
        private long total;
        private List<TaskAndTaskAttribute> taskList;

        public TaskCenterRemoteService(StatDayRequest request) {
            this.request = request;
        }

        boolean is() {
            return myResult;
        }

        public long getTotal() {
            return total;
        }

        public List<TaskAndTaskAttribute> getTaskList() {
            return taskList;
        }

        public TaskCenterRemoteService invoke() {
            TaskAndAttributeRequest rpcRequest = new TaskAndAttributeRequest();
            Map<String, Object> map = Maps.newHashMap();

            rpcRequest.setAppId(request.getAppId());
            rpcRequest.setSaasEnv(request.getSaasEnv());
            rpcRequest.setName(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());
            if (request.getStatType() == 2) {
                // 失败的任务
                rpcRequest.setStatus(3);
            } else if (request.getStatType() == 3) {
                // 取消的任务
                rpcRequest.setStatus(1);
            } else if (request.getStatType() == 1) {
                // 成功的任务
                rpcRequest.setStatus(2);
            } else {
                throw new IllegalArgumentException("statType参数有误");
            }

            if (request.getBizType() == 0) {
                MerchantResult<List<AppBizTypeResult>> merchantResult = appBizTypeFacade.queryAllAppBizType(new BaseRequest());
                List<AppBizType> list = DataConverterUtils.convert(merchantResult.getData(), AppBizType.class);
                List<Byte> bizTypeList = list.stream().map(AppBizType::getBizType).collect(Collectors.toList());
                rpcRequest.setBizTypeList(bizTypeList);
            } else {
                rpcRequest.setBizType(request.getBizType());
            }

            if (StringUtils.isNotBlank(request.getWebsiteDetailName())) {
                rpcRequest.setWebSite(request.getWebsiteDetailName());
                rpcRequest.setValue(request.getWebsiteDetailName());
            }

            if (request.getStartTime() != null && request.getEndTime() != null) {
                rpcRequest.setStartTime(request.getStartTime());
                rpcRequest.setEndTime(request.getEndTime());
            } else if (request.getDate() != null) {
                rpcRequest.setStartTime(DateUtils.getTodayBeginDate(request.getDate()));
                rpcRequest.setEndTime(DateUtils.getTomorrowBeginDate(request.getDate()));
            }
            rpcRequest.setStart(request.getOffset());
            rpcRequest.setLimit(request.getPageSize());
            rpcRequest.setOrderStr("createTime desc");

            TaskPagingResult<TaskAndAttributeRO> taskPagingResult;

            try {
                taskPagingResult = taskFacade.queryTaskAndTaskAttribute(rpcRequest);
            } catch (Exception e) {
                logger.error("请求任务中心出错", e.getMessage());
                myResult = true;
                return this;
            }

            logger.info("从任务中心获取数据：{}", taskPagingResult);
            if (!taskPagingResult.isSuccess()) {
                logger.info("请求任务中心失败:{}", taskPagingResult);
                myResult = true;
                return this;
            }

            total = taskPagingResult.getTotal();
            if (total <= 0) {
                myResult = true;
                return this;
            }

            List<TaskAndAttributeRO> list = taskPagingResult.getList();
            taskList = DataConverterUtils.convert(list, TaskAndTaskAttribute.class);
            myResult = false;
            return this;
        }
    }
}
