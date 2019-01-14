package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.b2b.saas.util.SaasDateUtils;
import com.treefinance.saas.console.biz.enums.EBizType4MonitorEnum;
import com.treefinance.saas.console.biz.enums.EBizTypeEnum;
import com.treefinance.saas.console.biz.enums.ESaasEnvEnum;
import com.treefinance.saas.console.biz.enums.ETaskErrorStepEnum;
import com.treefinance.saas.console.biz.enums.TaskStepEnum;
import com.treefinance.saas.console.biz.service.MerchantStatService;
import com.treefinance.saas.console.common.domain.dto.SaasErrorStepDayStatDTO;
import com.treefinance.saas.console.common.domain.request.StatDayRequest;
import com.treefinance.saas.console.common.domain.request.StatRequest;
import com.treefinance.saas.console.common.domain.vo.ChartStatDayRateVO;
import com.treefinance.saas.console.common.domain.vo.ChartStatRateVO;
import com.treefinance.saas.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.console.common.domain.vo.MerchantStatDayVO;
import com.treefinance.saas.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.console.common.domain.vo.MerchantStatOverviewVO;
import com.treefinance.saas.console.common.domain.vo.MerchantStatVO;
import com.treefinance.saas.console.common.domain.vo.PieChartStatRateVO;
import com.treefinance.saas.console.common.domain.vo.TaskDetailVO;
import com.treefinance.saas.console.context.Constants;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.dao.entity.MerchantBase;
import com.treefinance.saas.console.dao.entity.MerchantUser;
import com.treefinance.saas.console.manager.BizLicenseInfoManager;
import com.treefinance.saas.console.manager.BizTypeManager;
import com.treefinance.saas.console.manager.MerchantStatManager;
import com.treefinance.saas.console.manager.TaskLogManager;
import com.treefinance.saas.console.manager.TaskManager;
import com.treefinance.saas.console.manager.domain.CompositeTaskAttrBO;
import com.treefinance.saas.console.manager.domain.CompositeTaskAttrPagingResultSet;
import com.treefinance.saas.console.manager.domain.DailyErrorStepStatBO;
import com.treefinance.saas.console.manager.domain.MerchantAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatResultSet;
import com.treefinance.saas.console.manager.domain.TaskLogBO;
import com.treefinance.saas.console.manager.param.CompositeTaskAttrPagingQuery;
import com.treefinance.saas.console.manager.param.DailyErrorStepStatQuery;
import com.treefinance.saas.console.manager.param.MerchantAccessStatQuery;
import com.treefinance.saas.console.manager.param.MerchantDailyAccessStatQuery;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.QueryMerchantByMerchantIdRequest;
import com.treefinance.saas.merchant.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantUserResult;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.merchant.facade.service.MerchantUserFacade;
import com.treefinance.toolkit.util.DateUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/5.
 */
@Service
public class MerchantStatServiceImpl extends AbstractService implements MerchantStatService {

    @Resource
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Resource
    private MerchantUserFacade merchantUserFacade;
    @Resource
    private TaskManager taskManager;
    @Autowired
    private BizLicenseInfoManager bizLicenseInfoManager;
    @Autowired
    private BizTypeManager bizTypeManager;
    @Autowired
    private MerchantStatManager merchantStatManager;
    @Autowired
    private TaskLogManager taskLogManager;

    @Override
    public SaasResult<Map<String, Object>> queryDayAccessList(StatRequest request) {
        checkParam(request);
        MerchantDailyAccessStatQuery query = buildMerchantDailyAccessStatPaginationQuery(request);

        MerchantDailyAccessStatResultSet result = merchantStatManager.queryDailyAccessStatisticsResultSet(query);
        List<MerchantDailyAccessStatBO> list = result.getData();
        if (CollectionUtils.isEmpty(list)) {
            logger.error("merchantStatAccessFacade.queryDayAccessList()为空 : query={},result={}", JSON.toJSONString(query), JSON.toJSONString(result));
            return Results.newPageResult(request, 0, Lists.newArrayList());
        }

        Map<String, MerchantDailyAccessStatBO> dayAccessROMap = this.queryTotalDayAccessList(request);
        List<MerchantStatDayVO> dataList = Lists.newArrayList();
        for (MerchantDailyAccessStatBO ro : list) {
            MerchantStatDayVO merchantStatDayVO = new MerchantStatDayVO();
            merchantStatDayVO.setDateStr(DateUtils.formatDate(ro.getDataTime()));
            merchantStatDayVO.setTotalCount(ro.getSuccessCount());
            merchantStatDayVO.setDailyLimit(ro.getDailyLimit());
            BigDecimal totalCountDecimal = new BigDecimal(ro.getSuccessCount());
            BigDecimal dailyLimitDecimal = new BigDecimal(ro.getDailyLimit());
            if (BigDecimal.ZERO.compareTo(dailyLimitDecimal) != 0) {
                merchantStatDayVO.setDailyLimitRate(totalCountDecimal.divide(dailyLimitDecimal, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                merchantStatDayVO.setDailyLimitRate(BigDecimal.ZERO);
            }

            MerchantDailyAccessStatBO statBO = dayAccessROMap.get(DateUtils.formatDate(ro.getDataTime()));

            if (statBO != null && statBO.getDailyLimit() != 0) {
                BigDecimal totalDailyLimitDecimal = new BigDecimal(statBO.getDailyLimit());
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

        return Results.newPageResult(request, result.getTotal(), dataList);
    }

    @Nonnull
    private MerchantDailyAccessStatQuery buildMerchantDailyAccessStatPaginationQuery(StatRequest request) {
        MerchantDailyAccessStatQuery query = buildMerchantDailyAccessStatQuery(request);
        query.setPageNumber(request.getPageNumber());
        query.setPageSize(request.getPageSize());
        return query;
    }

    @Nonnull
    private MerchantDailyAccessStatQuery buildMerchantDailyAccessStatQuery(StatRequest request) {
        MerchantDailyAccessStatQuery statRequest = new MerchantDailyAccessStatQuery();
        statRequest.setAppId(request.getAppId());
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(ESaasEnvEnum.ALL.getCode());
        return statRequest;
    }

    private Map<String, MerchantDailyAccessStatBO> queryTotalDayAccessList(StatRequest request) {
        MerchantDailyAccessStatQuery statRequest = buildMerchantDailyAccessStatQuery(request);

        List<MerchantDailyAccessStatBO> list = merchantStatManager.queryDailyAccessStatisticsRecords(statRequest);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        return list.stream().collect(Collectors.toMap(ro -> DateUtils.formatDate(ro.getDataTime()), ro -> ro, (key1, key2) -> key1));
    }

    @Override
    public SaasResult<Map<String, Object>> queryWeekAccessList(StatRequest request) {
        checkParam(request);
        MerchantDailyAccessStatQuery query = new MerchantDailyAccessStatQuery();
        query.setAppId(request.getAppId());
        query.setStartDate(DateUtils.getFirstDayOfWeek(request.getStartDate()));
        query.setEndDate(DateUtils.getLastDayOfWeek(request.getEndDate()));
        query.setDataType(request.getBizType());
        query.setSaasEnv(ESaasEnvEnum.ALL.getCode());

        List<MerchantDailyAccessStatBO> list = merchantStatManager.queryDailyAccessStatisticsRecords(query);
        // <weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantDailyAccessStatBO>> timeMap;
        if (CollectionUtils.isNotEmpty(list)) {
            timeMap = new HashMap<>();
            list.forEach(bo -> {
                String timeStr = SaasDateUtils.getWeekStrOfYear(bo.getDataTime());

                List<MerchantDailyAccessStatBO> timeValueList = timeMap.computeIfAbsent(timeStr, s -> new ArrayList<>());

                timeValueList.add(bo);
            });
        } else {
            timeMap = Collections.emptyMap();
        }

        return wrapDataPageResult(request, timeMap);
    }

    @Override
    public SaasResult<Map<String, Object>> queryMonthAccessList(StatRequest request) {
        checkParam(request);
        MerchantDailyAccessStatQuery query = new MerchantDailyAccessStatQuery();
        query.setAppId(request.getAppId());
        query.setStartDate(DateUtils.getFirstDayOfMonth(request.getStartDate()));
        query.setEndDate(DateUtils.getLastDayOfMonth(request.getEndDate()));
        query.setDataType(request.getBizType());
        query.setSaasEnv(ESaasEnvEnum.ALL.getCode());

        List<MerchantDailyAccessStatBO> list = merchantStatManager.queryDailyAccessStatisticsRecords(query);
        // <weekTimeStr,List<MerchantStatDayAccessRO>>
        Map<String, List<MerchantDailyAccessStatBO>> timeMap;
        if (CollectionUtils.isNotEmpty(list)) {
            timeMap = new HashMap<>();
            list.forEach(ro -> {
                String timeStr = DateUtils.format(ro.getDataTime(), Constants.YEAR_MONTH_PATTERN);

                List<MerchantDailyAccessStatBO> timeValueList = timeMap.computeIfAbsent(timeStr, s -> new ArrayList<>());

                timeValueList.add(ro);
            });
        } else {
            timeMap = Collections.emptyMap();
        }

        return wrapDataPageResult(request, timeMap);
    }

    @Override
    public Map<String, Object> queryAllAccessList(StatRequest request) {
        baseCheck(request);

        MerchantAccessStatQuery statRequest = new MerchantAccessStatQuery();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setIntervalMinutes(request.getIntervalMins());
        statRequest.setSaasEnv(ESaasEnvEnum.ALL.getCode());

        List<MerchantAccessStatBO> list = merchantStatManager.queryAccessStatisticsRecords(statRequest);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        List<MerchantAccessStatBO> roList = changeIntervalDataTime(list, request.getIntervalMins());

        // 遍历获取时间节点list
        Set<Date> dataTimeSet = Sets.newHashSet();
        roList.forEach(ro -> dataTimeSet.add(ro.getDataTime()));
        List<Date> dataTimeList = Lists.newArrayList(dataTimeSet);

        // <appId,<dataTime,totalCount>>
        Map<String, Map<Date, Integer>> dataMap = Maps.newHashMap();
        roList.forEach(ro -> {
            Map<Date, Integer> valueMap = dataMap.computeIfAbsent(ro.getAppId(), k -> new HashMap<>());
            valueMap.put(ro.getDataTime(), ro.getTotalCount());
        });
        // 填充缺少的时间点的totalCount为0
        this.fillDataTime(dataMap, dataTimeList);
        // 更换map的key为appName
        Map<String, Map<Date, Integer>> appNameMap = this.changeKey2AppName(dataMap);

        List<String> keysList = Lists.newArrayList(appNameMap.keySet()).stream().sorted((String::compareTo)).collect(Collectors.toList());
        keysList.add(0, "总任务量");
        Map<String, Object> wrapMap = Maps.newHashMap();
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.countTotalTask(appNameMap);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    @Override
    public Map<String, Object> queryAllAccessList4Pie(StatRequest request) {
        baseCheck(request);
        Map<String, Object> wrapMap = Maps.newHashMap();

        MerchantAccessStatQuery statRequest = new MerchantAccessStatQuery();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(ESaasEnvEnum.ALL.getCode());

        List<MerchantAccessStatBO> list = merchantStatManager.queryAccessStatisticsRecords(statRequest);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        // <appId,totalCount>
        Map<String, Integer> dataMap = Maps.newHashMap();
        for (MerchantAccessStatBO ro : list) {
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
        MerchantAccessStatQuery statRequest = new MerchantAccessStatQuery();
        statRequest.setAppId(Constants.VIRTUAL_TOTAL_STAT_APPID);
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setIntervalMinutes(request.getIntervalMins());
        statRequest.setSaasEnv(request.getSaasEnv());
        statRequest.setSuccess(Boolean.TRUE);

        List<MerchantAccessStatBO> list = merchantStatManager.queryAccessStatisticsRecords(statRequest);

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<String, Object> wrapMap = Maps.newHashMap();

        List<String> keysList = Lists.newArrayList("总数", "成功数", "失败数", "取消数");
        wrapMap.put("keys", keysList);
        Map<String, List<ChartStatVO>> valuesMap = this.getNumberTaskChart(list);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private Map<String, List<ChartStatVO>> getNumberTaskChart(List<MerchantAccessStatBO> data) {
        Map<String, List<ChartStatVO>> result = Maps.newHashMap();
        List<ChartStatVO> totalList = Lists.newArrayList();
        List<ChartStatVO> successList = Lists.newArrayList();
        List<ChartStatVO> failList = Lists.newArrayList();
        List<ChartStatVO> cancelList = Lists.newArrayList();

        for (MerchantAccessStatBO ro : data) {
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

    private List<MerchantAccessStatBO> changeIntervalDataTime(List<MerchantAccessStatBO> list, final Integer intervalMins) {
        List<MerchantAccessStatBO> resultList = Lists.newArrayList();
        Map<String, List<MerchantAccessStatBO>> appIdROMap = list.stream().collect(Collectors.groupingBy(MerchantAccessStatBO::getAppId));
        for (Map.Entry<String, List<MerchantAccessStatBO>> appIdROEntry : appIdROMap.entrySet()) {
            if (CollectionUtils.isEmpty(appIdROEntry.getValue())) {
                continue;
            }
            Map<Date, List<MerchantAccessStatBO>> map = appIdROEntry.getValue().stream()
                .collect(Collectors.groupingBy(ro -> SaasDateUtils.getLaterIntervalDateTime(ro.getDataTime(), intervalMins)));
            for (Map.Entry<Date, List<MerchantAccessStatBO>> entry : map.entrySet()) {
                List<MerchantAccessStatBO> value = entry.getValue();
                if (CollectionUtils.isEmpty(value)) {
                    continue;
                }

                MerchantAccessStatBO ro = new MerchantAccessStatBO();

                this.copy(value.get(0), ro);

                ro.setDataTime(entry.getKey());
                int totalCount = 0, successCount = 0, failCount = 0, cancelCount = 0;
                for (MerchantAccessStatBO data : value) {
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

    @Override
    public Map<String, Object> queryAccessRateList(StatRequest request) {
        MerchantAccessStatQuery statRequest = new MerchantAccessStatQuery();
        statRequest.setDataType(request.getBizType());
        statRequest.setStartDate(this.getStartDate(request));
        statRequest.setEndDate(this.getEndDate(request));
        statRequest.setSaasEnv(request.getSaasEnv());
        statRequest.setIntervalMinutes(request.getIntervalMins());
        statRequest.setAppId(Constants.VIRTUAL_TOTAL_STAT_APPID);
        statRequest.setSuccess(Boolean.TRUE);

        List<MerchantAccessStatBO> list = merchantStatManager.queryAccessStatisticsRecords(statRequest);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<String, Object> wrapMap = Maps.newHashMap();
        wrapMap.put("keys", Lists.newArrayList("转化率", "失败率", "取消率"));
        Map<String, List<ChartStatRateVO>> valuesMap = this.getRateTaskChart(list);
        wrapMap.put("values", valuesMap);
        return wrapMap;
    }

    private Map<String, List<ChartStatRateVO>> getRateTaskChart(List<MerchantAccessStatBO> data) {
        Map<String, List<ChartStatRateVO>> result = Maps.newHashMap();
        List<ChartStatRateVO> successList = Lists.newArrayList();
        List<ChartStatRateVO> failList = Lists.newArrayList();
        List<ChartStatRateVO> cancelList = Lists.newArrayList();

        for (MerchantAccessStatBO ro : data) {
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
        // 从monitor-server 获取数据
        MerchantDailyAccessStatQuery query = new MerchantDailyAccessStatQuery();
        query.setDataType(request.getBizType());
        query.setStartDate(this.getStartDate(request));
        query.setEndDate(this.getEndDate(request));
        query.setSaasEnv(request.getSaasEnv());

        List<MerchantDailyAccessStatBO> list = merchantStatManager.queryDailyAccessStatisticsRecords(query);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("result of merchantStatAccessFacade.queryDayAccessListNoPage() is empty : request={}, " + "result={}", query, JSON.toJSONString(list));
            return Collections.emptyList();
        }
        // 将数据计算比率
        Integer statType = request.getStatType();
        List<MerchantStatOverviewVO> overviewVOList = getMerchantStatOverviewVOS(statType, list);

        // 获取所有的appBizLicense 数据
        List<String> appIds;
        if (request.getBizType() != null && request.getBizType() != 0) {
            appIds = bizLicenseInfoManager.listAppIdsByBizType(request.getBizType());
        } else {
            appIds = bizLicenseInfoManager.listAppIds();
        }

        // 获取所有的appBizLicense的appId数据
        List<MerchantBase> merchantBaseList = getMerchantBasesByAppId(appIds);

        //
        List<MerchantUser> merchantUserList = getMerchantUsersById(merchantBaseList);

        // <merchantId,merchantUser>
        List<Long> ids = merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, merchantUser -> 1, Integer::sum)).entrySet().stream()
            .filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
        logger.info("重复的merchantUser数据{}", JSON.toJSONString(ids));

        Map<Long, MerchantUser> merchantUserMap = merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, m -> m));

        Map<String, List<MerchantStatOverviewVO>> ovMap =
            overviewVOList.stream().filter(ov -> StringUtils.isNotBlank(ov.getAppId())).collect(Collectors.groupingBy(MerchantStatOverviewVO::getAppId));

        Map<String, Map<String, MerchantStatOverviewVO>> resultMap = Maps.newHashMap();
        for (Map.Entry<String, List<MerchantStatOverviewVO>> entry : ovMap.entrySet()) {
            Map<String, MerchantStatOverviewVO> tempMap = entry.getValue().stream().collect(Collectors.toMap(vo -> DateUtils.formatDate(vo.getDate()), vo -> vo));
            resultMap.put(entry.getKey(), tempMap);
        }
        List<MerchantStatOverviewTimeVO> timeOverViewList = Lists.newArrayList();

        // 获取时间的列表
        List<String> dateList = getDateStrings(request);
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
                MerchantStatOverviewVO vo = null;
                for (int i = 0; i < 7; i++) {
                    vo = entry.get(dateList.get(i));
                    String val = vo == null ? "0 | NA" : String.valueOf(vo.getTotalCount()) + " | " + vo.getRate() + "%";
                    setTimeVal(timeVO, val, i);
                }

                timeVO.setNum(vo == null ? 0 : vo.getTotalCount());
            }

            timeOverViewList.add(timeVO);
        }

        timeOverViewList = timeOverViewList.stream().sorted((n1, n2) -> n2.getNum().compareTo(n1.getNum())).collect(Collectors.toList());

        return timeOverViewList;
    }

    private void setTimeVal(MerchantStatOverviewTimeVO timeVO, String val, int i) {
        switch (i) {
            case 0:
                timeVO.setTime1Val(val);
                break;
            case 1:
                timeVO.setTime2Val(val);
                break;
            case 2:
                timeVO.setTime3Val(val);
                break;
            case 3:
                timeVO.setTime4Val(val);
                break;
            case 4:
                timeVO.setTime5Val(val);
                break;
            case 5:
                timeVO.setTime6Val(val);
                break;
            case 6:
                timeVO.setTime7Val(val);
                break;
            default:
                break;
        }
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

            List<MerchantUser> merchantUserPartList = this.convert(listMerchantResult.getData(), MerchantUser.class);
            merchantUserList.addAll(merchantUserPartList);
        }

        logger.info("merchantUser列表数据：{}", JSON.toJSONString(merchantUserList));

        return merchantUserList;
    }

    private List<MerchantBase> getMerchantBasesByAppId(List<String> appIdList) {
        logger.info("通过appId获取merchantBase：{}", JSON.toJSONString(appIdList));

        List<List<String>> appIdPartList = Lists.partition(appIdList, 50);
        List<MerchantBase> merchantBaseList = Lists.newArrayList();
        for (List<String> appIdParts : appIdPartList) {
            QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
            queryMerchantByAppIdRequest.setAppIds(appIdParts);
            MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
            List<MerchantBase> merchantBasePartList = this.convert(listMerchantResult.getData(), MerchantBase.class);
            merchantBaseList.addAll(merchantBasePartList);
        }
        return merchantBaseList;
    }


    private List<MerchantStatOverviewVO> getMerchantStatOverviewVOS(Integer statType, List<MerchantDailyAccessStatBO> result) {
        List<MerchantStatOverviewVO> overviewVOList = Lists.newArrayList();
        result.forEach(ro -> {
            MerchantStatOverviewVO vo = new MerchantStatOverviewVO();
            vo.setAppId(ro.getAppId());
            vo.setDateStr(DateUtils.format(ro.getDataTime(), Constants.MONTH_DAY_PATTERN));
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
        List<String> dateList = DateUtils.listDateStringsDuringDays(this.getStartDate(request), this.getEndDate(request));
        if (dateList.size() != 7) {
            throw new IllegalArgumentException("请求参数startDate,endDate非法!");
        }
        return dateList;
    }


    @Override
    public SaasResult<Map<String, Object>> queryOverviewDetailAccessList(StatDayRequest request) {

        TaskCenterRemoteService taskCenterRemoteService = new TaskCenterRemoteService(request).invoke();
        if (taskCenterRemoteService.failure()) {
            return Results.newPageResult(request, 0, Collections.emptyList());
        }
        long total = taskCenterRemoteService.getTotal();
        List<CompositeTaskAttrBO> taskList = taskCenterRemoteService.getTaskAttrs();

        List<Long> taskIdList = taskList.stream().map(CompositeTaskAttrBO::getId).collect(Collectors.toList());

        List<TaskLogBO> taskLogList = taskLogManager.listTaskLogsInTaskIds(taskIdList);
        Map<Long, List<TaskLogBO>> taskLogsMap = taskLogList.stream().collect(Collectors.groupingBy(TaskLogBO::getTaskId));

        List<TaskDetailVO> resultList = Lists.newArrayList();
        Map<Byte, String> bizTypeMap = bizTypeManager.getBizTypeNameMapping();

        for (CompositeTaskAttrBO task : taskList) {
            TaskDetailVO vo = new TaskDetailVO();
            vo.setId(task.getId());
            vo.setAppId(task.getAppId());
            vo.setUniqueId(task.getUniqueId());
            vo.setBizType(task.getBizType());
            vo.setBizTypeName(bizTypeMap.get(task.getBizType()));
            List<TaskLogBO> taskLogs = taskLogsMap.get(task.getId());
            if (!CollectionUtils.isEmpty(taskLogs)) {
                taskLogs = taskLogs.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toList());

                TaskLogBO taskLog = null;
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
                    List<String> taskErrorStepList = ETaskErrorStepEnum.getTaskErrorStepList();
                    List<TaskLogBO> list = taskLogs.stream().filter(taskLog1 -> taskErrorStepList.contains(taskLog1.getMsg()))
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

            if (EBizTypeEnum.OPERATOR.getCode().equals(task.getBizType())) {
                vo.setWebsiteDetailName(task.getAttrValue());
            } else {
                vo.setWebsiteDetailName(task.getWebSite());

            }
            resultList.add(vo);
        }
        return Results.newPageResult(request, total, resultList);
    }

    @Override
    public Map<String, Object> queryTaskStepStatInfo(StatRequest request) {
        baseCheck(request);

        DailyErrorStepStatQuery query = new DailyErrorStepStatQuery();
        query.setDataType(EBizType4MonitorEnum.getMonitorCode(request.getBizType()));
        query.setStartDate(this.getDayStartDate(request));
        query.setEndDate(this.getDayEndDate(request));

        List<DailyErrorStepStatBO> statROList = merchantStatManager.queryDailyErrorStepStatisticsRecords(query);

        if (CollectionUtils.isEmpty(statROList)) {
            logger.info("result of merchantStatAccessFacade.Results.newSuccessResult(() is empty : request={}, result={}", query, JSON.toJSONString(statROList));
            return Collections.emptyMap();
        }

        Set<Date> dateSet = Sets.newHashSet();
        List<SaasErrorStepDayStatDTO> statDTOList = Lists.newArrayList();
        for (DailyErrorStepStatBO ro : statROList) {
            if (ro != null) {
                dateSet.add(ro.getDataTime());

                SaasErrorStepDayStatDTO dto = new SaasErrorStepDayStatDTO();
                this.copyProperties(ro, dto);
                dto.setStepCode(ro.getErrorStepCode());
                dto.setStageCode(TaskStepEnum.getStageCodeByStepCode(ro.getErrorStepCode()));
                dto.setStageText(TaskStepEnum.getStageTextByStepCode(ro.getErrorStepCode()));
                statDTOList.add(dto);
            }
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
            voList = voList.stream().sorted(Comparator.comparing(ChartStatRateVO::getDataTime)).collect(Collectors.toList());
            List<ChartStatDayRateVO> newVoList = Lists.newArrayList();
            for (ChartStatRateVO vo : voList) {
                ChartStatDayRateVO newVo = new ChartStatDayRateVO();
                newVo.setDataTime(DateUtils.formatDate(vo.getDataTime()));
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
        List<MerchantBase> merchantBaseList = this.convert(listMerchantResult.getData(), MerchantBase.class);

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
        List<MerchantBase> merchantBaseList = this.convert(listMerchantResult.getData(), MerchantBase.class);

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

    private SaasResult<Map<String, Object>> wrapDataPageResult(StatRequest request, Map<String, List<MerchantDailyAccessStatBO>> timeMap) {
        int totalCount = timeMap.keySet().size();
        List<MerchantStatVO> dataList = Lists.newArrayList();
        if (totalCount == 0) {
            return Results.newPageResult(request, totalCount, dataList);
        }
        for (Map.Entry<String, List<MerchantDailyAccessStatBO>> entry : timeMap.entrySet()) {
            MerchantStatVO merchantStatVO = new MerchantStatVO();
            int useTotalCount = 0, dailyLimit = 0;
            List<MerchantDailyAccessStatBO> list = entry.getValue();
            for (MerchantDailyAccessStatBO ro : list) {
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

    private void baseCheck(StatRequest request) throws IllegalArgumentException {
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
                return DateUtils.getTimeOfTodayBefore(1);
            case 2:
                return DateUtils.getTimeOfTodayBefore(3);
            case 3:
                return DateUtils.getTimeOfTodayBefore(7);
            case 4:
                return DateUtils.getTimeOfTodayBefore(30);
            default:
                return null;

        }
    }

    private Date getDayStartDate(StatRequest request) {
        // 0-自选日期，1-过去1天，2-过去3天，3-过去7天，4-过去30天;
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return DateUtils.getStartTimeOfDay(request.getStartDate());
            case 1:
                return DateUtils.getStartTimeOfYesterday();
            case 2:
                return DateUtils.getStartTimeOfTodayBefore(3);
            case 3:
                return DateUtils.getStartTimeOfTodayBefore(7);
            case 4:
                return DateUtils.getStartTimeOfTodayBefore(30);
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
                Date endDate = DateUtils.getEndTimeOfDay(request.getEndDate());
                return endDate.after(now) ? now : endDate;
            default:
                return new Date();
        }

    }

    private Date getDayEndDate(StatRequest request) {
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return DateUtils.getStartTimeOfDay(request.getEndDate());
            default:
                return DateUtils.getStartTimeOfToday();
        }

    }

    private class TaskCenterRemoteService {
        private final StatDayRequest request;
        private List<CompositeTaskAttrBO> taskAttrs;
        private long total;
        private boolean failure = false;

        public TaskCenterRemoteService(StatDayRequest request) {
            this.request = request;
        }

        boolean failure() {
            return failure;
        }

        public long getTotal() {
            return total;
        }

        public List<CompositeTaskAttrBO> getTaskAttrs() {
            return taskAttrs;
        }

        public TaskCenterRemoteService invoke() {
            CompositeTaskAttrPagingQuery query = new CompositeTaskAttrPagingQuery();

            query.setAppId(request.getAppId());
            query.setSaasEnv(request.getSaasEnv());
            if (request.getStatType() == 2) {
                // 失败的任务
                query.setStatus((byte)3);
            } else if (request.getStatType() == 3) {
                // 取消的任务
                query.setStatus((byte)1);
            } else if (request.getStatType() == 1) {
                // 成功的任务
                query.setStatus((byte)2);
            } else {
                throw new IllegalArgumentException("statType参数有误");
            }

            if (request.getBizType() == 0) {
                List<Byte> bizTypeList = bizTypeManager.listBizTypeValues();

                query.setBizTypes(bizTypeList);
            } else {
                query.setBizTypes(Collections.singletonList(request.getBizType()));
            }
            query.setAttrName(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());
            String websiteDetailName = request.getWebsiteDetailName();
            if (StringUtils.isNotBlank(websiteDetailName)) {
                query.setWebsite(websiteDetailName);
                query.setAttrValue(websiteDetailName);
            }

            if (request.getStartTime() != null && request.getEndTime() != null) {
                query.setStartDate(request.getStartTime());
                query.setEndDate(request.getEndTime());
            } else if (request.getDate() != null) {
                query.setStartDate(DateUtils.getStartTimeOfDay(request.getDate()));
                query.setEndDate(DateUtils.getEndTimeOfDay(request.getDate()));
            }
            query.setPageNum(request.getPageNumber());
            query.setPageSize(request.getPageSize());
            query.setOrder("createTime desc");

            try {
                CompositeTaskAttrPagingResultSet pagination = taskManager.queryPagingCompositeTaskAttrs(query);
                total = pagination.getTotal();
                if (total > 0) {
                    taskAttrs = pagination.getList();
                } else {
                    failure = true;
                }
            } catch (Exception e) {
                logger.error("请求任务远程服务，复合查询任务及属性信息失败", e);
                failure = true;
            }

            return this;
        }
    }
}
