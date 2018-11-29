package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.console.util.DateUtils;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.CallbackMsgStatService;
import com.treefinance.saas.management.console.common.domain.request.CallbackMsgStatRequest;
import com.treefinance.saas.monitor.facade.domain.request.CallbackMsgStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.AsStatCallbackRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.AsStatDayCallbackRO;
import com.treefinance.saas.monitor.facade.service.stat.CallbackMsgStatAccessFacade;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/3/15
 */
@Service
public class CallbackMsgStatServiceImpl implements CallbackMsgStatService {

    @Autowired
    private CallbackMsgStatAccessFacade callbackMsgStatAccessFacade;

    @Override
    public Object queryCallbackMsgStatDayAccessList(CallbackMsgStatRequest request) {
        CallbackMsgStatAccessRequest rpcRequest = new CallbackMsgStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setDataType(request.getDataType());
        rpcRequest.setBizType(request.getBizType());
        rpcRequest.setStartTime(request.getStartDate());
        rpcRequest.setEndTime(request.getEndDate());
        MonitorResult<List<AsStatDayCallbackRO>> rpcResult = callbackMsgStatAccessFacade.queryCallbackMsgStatDayAccessList(rpcRequest);

        Set<String> rowSet = Sets.newHashSet();
        List<String> rows = Lists.newArrayList();
        List<Date> columns = DateUtils.getDayDateRegion(rpcRequest.getStartTime(), rpcRequest.getEndTime(), 1);
        Map<Date, Map<String, Integer>> map = Maps.newHashMap();
        for (AsStatDayCallbackRO data : rpcResult.getData()) {
            rowSet.add(data.getCallbackMsg());
            Map<String, Integer> valueMap =
                getStringIntegerMap(map, data.getDataTime(), data.getCallbackMsg(), data.getCallbackCount());
            map.put(data.getDataTime(), valueMap);
        }
        //改变map中数据
        changeMap(rowSet, map);

        Map<Date, Map<String, String>> resultMap = getDateMapMap(rowSet, map);

        setRows(rows, columns, map);

        Map<String, Object> result = Maps.newHashMap();
        result.put("keys", columns.stream().map(DateUtils::date2Ymd).collect(Collectors.toList()));
        List<Object> valueList = Lists.newArrayList();
        for (String row : rows) {
            Map<String, Object> columnMap = Maps.newLinkedHashMap();
            for (Date column : columns) {
                String v = "0 | 0.00%";
                if (StringUtils.equals(row, "任务总数")) {
                    v = 0 + "";
                }
                if (!MapUtils.isEmpty(resultMap.get(column)) && resultMap.get(column).get(row) != null) {
                    v = resultMap.get(column).get(row);
                }
                columnMap.put(DateUtils.date2Ymd(column), v);
            }
            columnMap.put("callbackMsg", row);
            valueList.add(columnMap);
        }
        result.put("values", valueList);

        return Results.newSuccessResult(result);
    }

    private Map<Date, Map<String, String>> getDateMapMap(Set<String> rowSet, Map<Date, Map<String, Integer>> map) {
        Map<Date, Map<String, String>> resultMap = Maps.newHashMap();
        for (Map.Entry<Date, Map<String, Integer>> entry : map.entrySet()) {
            Date key = entry.getKey();
            int total = entry.getValue().get("任务总数");
            Map<String, String> map1 = Maps.newHashMap();
            for (String row : rowSet) {
                if (StringUtils.equals(row, "任务总数")) {
                    map1.put("任务总数", total + "");
                    continue;
                }
                map1.put(row, count(map.get(key).get(row), total));
            }
            resultMap.put(key, map1);
        }
        return resultMap;
    }

    @Override
    public Object queryCallbackMsgStatAccessList(CallbackMsgStatRequest request) {
        CallbackMsgStatAccessRequest rpcRequest = new CallbackMsgStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setDataType(request.getDataType());
        rpcRequest.setBizType(request.getBizType());
        rpcRequest.setStartTime(DateUtils.getIntervalDateTime(request.getStartTime(), request.getIntervalMins()));
        rpcRequest.setEndTime(DateUtils.getLaterIntervalDateTime(request.getEndTime(), request.getIntervalMins()));
        MonitorResult<List<AsStatCallbackRO>> rpcResult = callbackMsgStatAccessFacade.queryCallbackMsgStatAccessList(rpcRequest);

        Set<String> rowSet = Sets.newHashSet();
        List<String> rows = Lists.newArrayList();
        List<Date> columns = DateUtils.getIntervalDateRegion(rpcRequest.getStartTime(), rpcRequest.getEndTime(), request.getIntervalMins(), 1, 1);
        Map<Date, Map<String, Integer>> map = Maps.newHashMap();
        for (AsStatCallbackRO data : rpcResult.getData()) {
            rowSet.add(data.getCallbackMsg());
            data.setDataTime(DateUtils.getIntervalDateTime(data.getDataTime(), request.getIntervalMins()));
            Map<String, Integer> valueMap =
                getStringIntegerMap(map, data.getDataTime(), data.getCallbackMsg(), data.getCallbackCount());
            map.put(data.getDataTime(), valueMap);
        }
        changeMap(rowSet, map);

        Map<Date, Map<String, String>> resultMap = getDateMapMap(rowSet, map);

        setRows(rows, columns, map);

        Map<String, Object> result = Maps.newHashMap();
        result.put("keys", columns.stream().map(DateUtils::date2SimpleHm).collect(Collectors.toList()));
        List<Object> valueList = Lists.newArrayList();
        for (String row : rows) {
            Map<String, Object> columnMap = Maps.newLinkedHashMap();
            for (Date column : columns) {
                String v = "0 | 0.00%";
                if (StringUtils.equals(row, "任务总数")) {
                    v = 0 + "";
                }
                if (!MapUtils.isEmpty(resultMap.get(column)) && resultMap.get(column).get(row) != null) {
                    v = resultMap.get(column).get(row);
                }
                columnMap.put(DateUtils.date2SimpleHm(column), v);
            }
            columnMap.put("callbackMsg", row);
            valueList.add(columnMap);
        }
        result.put("values", valueList);

        return Results.newSuccessResult(result);
    }

    private void setRows(List<String> rows, List<Date> columns, Map<Date, Map<String, Integer>> map) {
        for (Date date : columns) {
            Map<String, Integer> valueMap = map.get(date);
            if (MapUtils.isEmpty(valueMap)) {
                continue;
            }
            List<Map.Entry<String, Integer>> list = Lists.newArrayList(valueMap.entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (Map.Entry<String, Integer> entry : list) {
                if (StringUtils.equals(entry.getKey(), "任务总数") || StringUtils.equals(entry.getKey(), "回调总数")
                    || StringUtils.equals(entry.getKey(), "回调成功")) {
                    continue;
                }
                rows.add(entry.getKey());
            }
            rows.add(0, "任务总数");
            rows.add(1, "回调总数");
            rows.add(2, "回调成功");
            break;
        }
    }

    private void changeMap(Set<String> rowSet, Map<Date, Map<String, Integer>> map) {
        for (Map.Entry<Date, Map<String, Integer>> entry : map.entrySet()) {
            Date key = entry.getKey();
            rowSet.stream().filter(row -> entry.getValue().get(row) == null).forEach(row -> {
                Map<String, Integer> map1 = map.get(key);
                map1.put(row, 0);
                map.put(key, map1);
            });
        }
    }

    private Map<String, Integer> getStringIntegerMap(Map<Date, Map<String, Integer>> map, Date dataTime,
        String callbackMsg, Integer callbackCount) {
        Map<String, Integer> valueMap;
        if (MapUtils.isEmpty(map.get(dataTime))) {
            valueMap = Maps.newHashMap();
            valueMap.put(callbackMsg, callbackCount);
        } else {
            valueMap = map.get(dataTime);
            if (valueMap.get(callbackMsg) == null) {
                valueMap.put(callbackMsg, callbackCount);
            } else {
                Integer v = valueMap.get(callbackMsg) + callbackCount;
                valueMap.put(callbackMsg, v);
            }
        }
        return valueMap;
    }

    private String count(int value, int total) {
        if (total == 0) {
            return "0 | 0.00%";
        }
        BigDecimal c = BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP);
        return value + " | " + c + "%";
    }
}
