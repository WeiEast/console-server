package com.treefinance.saas.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.console.biz.service.RealTimeStatService;
import com.treefinance.saas.console.common.domain.request.StatRequest;
import com.treefinance.saas.console.common.domain.vo.ChartStatRateVO;
import com.treefinance.saas.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.grapserver.facade.enums.ETaskStatLink;
import com.treefinance.saas.monitor.facade.domain.request.BaseStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.RealTimeStatAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.RealTimeStatAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Good Luck Bro , No Bug !
 *
 * @author haojiahong
 * @date 2018/6/25
 */
@Service
public class RealTimeStatServiceImpl implements RealTimeStatService {

    @Autowired
    private RealTimeStatAccessFacade realTimeStatAccessFacade;

    /**
     * 数量
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> queryAccessNumberList(StatRequest request) {
        Map<String, Object> resultMap = Maps.newHashMap();
        BaseStatAccessRequest rpcRequest = new BaseStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setBizType(request.getBizType());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setStartTime(this.getStartDate(request));
        rpcRequest.setEndTime(this.getEndDate(request));
        rpcRequest.setIntervalMins(request.getIntervalMins());
        rpcRequest.setHiddenRecentPoint(request.getHiddenRecentPoint());
        MonitorResult<List<RealTimeStatAccessRO>> rpcResult = realTimeStatAccessFacade.queryRealTimeStatAccess(rpcRequest);
        List<RealTimeStatAccessRO> rpcList = rpcResult.getData();

        List<String> nameKeyList = this.getNameKeysList();
        List<String> statCodeKeyList = this.getStatCodeKeysList();
        Map<String, List<ChartStatVO>> valueMap = this.getValueMap(rpcList, statCodeKeyList);

        Map<String, List<ChartStatVO>> nameValueMap = this.changeValueMap(valueMap);
        resultMap.put("keys", nameKeyList);
        resultMap.put("values", nameValueMap);
        return resultMap;
    }

    /**
     * 成功率
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> queryAccessRateList(StatRequest request) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<String> nameKeyList = Lists.newArrayList();
        Map<String, List<ChartStatRateVO>> nameValueMap = Maps.newHashMap();

        BaseStatAccessRequest rpcRequest = new BaseStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setBizType(request.getBizType());
        rpcRequest.setSaasEnv(request.getSaasEnv());
        rpcRequest.setStartTime(this.getStartDate(request));
        rpcRequest.setEndTime(this.getEndDate(request));
        rpcRequest.setIntervalMins(request.getIntervalMins());
        rpcRequest.setHiddenRecentPoint(request.getHiddenRecentPoint());

        MonitorResult<List<RealTimeStatAccessRO>> rpcResult = realTimeStatAccessFacade.queryRealTimeStatAccess(rpcRequest);
        List<RealTimeStatAccessRO> rpcList = rpcResult.getData();
        nameKeyList.add("当前成功率");
        List<ChartStatRateVO> rateValueList = this.getRateValueList(rpcList);
        nameValueMap.put("当前成功率", rateValueList);

        MonitorResult<List<RealTimeStatAccessRO>> avgRpcResult = realTimeStatAccessFacade.queryAvgRealTimeStatAccess(rpcRequest);
        List<RealTimeStatAccessRO> avgRpcList = avgRpcResult.getData();
        nameKeyList.add("平均成功率(前7天)");
        List<ChartStatRateVO> avgRateValueList = this.getRateValueList(avgRpcList);
        nameValueMap.put("平均成功率(前7天)", avgRateValueList);

        resultMap.put("keys", nameKeyList);
        resultMap.put("values", nameValueMap);
        return resultMap;
    }

    private List<ChartStatRateVO> getRateValueList(List<RealTimeStatAccessRO> rpcList) {
        List<ChartStatRateVO> result = Lists.newArrayList();
        for (RealTimeStatAccessRO realTimeStatAccessRO : rpcList) {
            Date dataTime = realTimeStatAccessRO.getDataTime();
            Map<String, Integer> statDataMap = realTimeStatAccessRO.getStatDataMap();

            ChartStatRateVO chartStatRateVO = new ChartStatRateVO();
            chartStatRateVO.setDataTime(dataTime);

            Integer taskSuccessCount = statDataMap.get(ETaskStatLink.TASK_SUCCESS.getStatCode());
            Integer taskCreateCount = statDataMap.get(ETaskStatLink.TASK_CREATE.getStatCode());

            if (taskSuccessCount == null || taskSuccessCount == 0) {
                chartStatRateVO.setDataValue(BigDecimal.ZERO);
            } else if (taskCreateCount == null || taskCreateCount == 0) {
                double fakeTaskCreateCount = 0.01;
                BigDecimal value = BigDecimal.valueOf(taskSuccessCount * 100).divide(BigDecimal.valueOf(fakeTaskCreateCount), 2, BigDecimal.ROUND_HALF_UP);
                chartStatRateVO.setDataValue(value);
            } else {
                BigDecimal value = BigDecimal.valueOf(taskSuccessCount * 100).divide(BigDecimal.valueOf(taskCreateCount), 2, BigDecimal.ROUND_HALF_UP);
                chartStatRateVO.setDataValue(value);
            }
            result.add(chartStatRateVO);
        }
        result = result.stream().sorted(Comparator.comparing(ChartStatRateVO::getDataTime)).collect(Collectors.toList());
        return result;
    }

    private Map<String, List<ChartStatVO>> getValueMap(List<RealTimeStatAccessRO> rpcList, List<String> statCodeKeyList) {
        Map<String, List<ChartStatVO>> valueMap = Maps.newHashMap();
        for (RealTimeStatAccessRO realTimeStatAccessRO : rpcList) {
            Date dataTime = realTimeStatAccessRO.getDataTime();
            Map<String, Integer> statDataMap = realTimeStatAccessRO.getStatDataMap();

            for (String statCodeKey : statCodeKeyList) {
                ChartStatVO vo = new ChartStatVO();
                vo.setDataTime(dataTime);
                if (statDataMap.get(statCodeKey) == null) {
                    vo.setDataValue(0);
                } else {
                    vo.setDataValue(statDataMap.get(statCodeKey));
                }
                List<ChartStatVO> voList = valueMap.get(statCodeKey);
                if (CollectionUtils.isEmpty(voList)) {
                    voList = Lists.newArrayList();
                }
                voList.add(vo);
                valueMap.put(statCodeKey, voList);
            }
        }
        return valueMap;
    }

    private Map<String, List<ChartStatVO>> changeValueMap(Map<String, List<ChartStatVO>> valueMap) {
        Map<String, List<ChartStatVO>> result = Maps.newHashMap();
        for (Map.Entry<String, List<ChartStatVO>> entry : valueMap.entrySet()) {
            String stepCode = ETaskStatLink.getDescByStatCode(entry.getKey());
            if (StringUtils.isBlank(stepCode)) {
                continue;
            }
            List<ChartStatVO> list = entry.getValue().stream().sorted(Comparator.comparing(ChartStatVO::getDataTime)).collect(Collectors.toList());
            result.put(stepCode, list);
        }
        return result;
    }

    /**
     * 数量统计,获取统计环节描述
     *
     * @return
     */
    private List<String> getNameKeysList() {
        List<String> result = Lists.newArrayList();
        for (ETaskStatLink item : ETaskStatLink.values()) {
            if (StringUtils.equals(item.getSource(), "self-define")) {
                continue;
            }
            result.add(item.getDesc());
        }
        return result;
    }

    private List<String> getStatCodeKeysList() {
        return ETaskStatLink.getStatCodeListNotSelfDefine();
    }

    /**
     * 获取开始时间
     *
     * @param request
     * @return
     */
    protected Date getStartDate(StatRequest request) {
        // 0-自选日期，1-过去1小时，2-过去3小时，3-过去半天,4-过去一天,5-过去三天
        Integer dateType = request.getDateType();
        switch (dateType) {
            case 0:
                return request.getStartDate();
            case 1:
                return DateUtils.addHours(new Date(), -1);
            case 2:
                return DateUtils.addHours(new Date(), -3);
            case 3:
                return DateUtils.addHours(new Date(), -12);
            case 4:
                return DateUtils.addHours(new Date(), -24);
            case 5:
                return DateUtils.addHours(new Date(), -24 * 3);
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
                Date endDate = com.treefinance.saas.console.util.DateUtils.getTodayEndDate(request.getEndDate());
                if (endDate.compareTo(now) > 0) {
                    return now;
                }
                return endDate;
            default:
                return new Date();
        }

    }

}
