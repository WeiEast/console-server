package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.management.console.biz.service.CallbackMsgStatService;
import com.treefinance.saas.management.console.common.domain.request.CallbackMsgStatRequest;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.monitor.facade.domain.request.CallbackMsgStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.AsStatCallbackRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.AsStatDayCallbackRO;
import com.treefinance.saas.monitor.facade.service.stat.CallbackMsgStatAccessFacade;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Map<String, Integer> valueMap;
            if (MapUtils.isEmpty(map.get(data.getDataTime()))) {
                valueMap = Maps.newHashMap();
                valueMap.put(data.getCallbackMsg(), data.getCallbackCount());
            } else {
                valueMap = map.get(data.getDataTime());
                if (valueMap.get(data.getCallbackMsg()) == null) {
                    valueMap.put(data.getCallbackMsg(), data.getCallbackCount());
                } else {
                    Integer v = valueMap.get(data.getCallbackMsg()) + data.getCallbackCount();
                    valueMap.put(data.getCallbackMsg(), v);
                }
            }
            map.put(data.getDataTime(), valueMap);
        }
        for (Map.Entry<Date, Map<String, Integer>> entry : map.entrySet()) {
            Date key = entry.getKey();
            rowSet.stream().filter(row -> entry.getValue().get(row) == null).forEach(row -> {
                Map<String, Integer> map1 = map.get(key);
                map1.put(row, 0);
                map.put(key, map1);
            });
        }

        for (Date date : columns) {
            Map<String, Integer> valueMap = map.get(date);
            if (MapUtils.isEmpty(valueMap)) {
                continue;
            }
            List<Map.Entry<String, Integer>> list = Lists.newArrayList(valueMap.entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (Map.Entry<String, Integer> entry : list) {
                rows.add(entry.getKey());
            }
            break;
        }

        Map<String, Object> result = Maps.newHashMap();
        result.put("keys", columns.stream().map(DateUtils::date2Ymd).collect(Collectors.toList()));
        List<Object> valueList = Lists.newArrayList();
        for (String row : rows) {
            Map<String, Object> columnMap = Maps.newLinkedHashMap();
            for (Date column : columns) {
                int v = 0;
                if (!MapUtils.isEmpty(map.get(column)) && map.get(column).get(row) != null) {
                    v = map.get(column).get(row);
                }
                columnMap.put(DateUtils.date2Ymd(column), v);
            }
            columnMap.put("callbackMsg", row);
            valueList.add(columnMap);
        }
        result.put("values", valueList);

        return Results.newSuccessResult(result);
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
            Map<String, Integer> valueMap;
            if (MapUtils.isEmpty(map.get(data.getDataTime()))) {
                valueMap = Maps.newHashMap();
                valueMap.put(data.getCallbackMsg(), data.getCallbackCount());
            } else {
                valueMap = map.get(data.getDataTime());
                if (valueMap.get(data.getCallbackMsg()) == null) {
                    valueMap.put(data.getCallbackMsg(), data.getCallbackCount());
                } else {
                    Integer v = valueMap.get(data.getCallbackMsg()) + data.getCallbackCount();
                    valueMap.put(data.getCallbackMsg(), v);
                }
            }
            map.put(data.getDataTime(), valueMap);
        }
        for (Map.Entry<Date, Map<String, Integer>> entry : map.entrySet()) {
            Date key = entry.getKey();
            rowSet.stream().filter(row -> entry.getValue().get(row) == null).forEach(row -> {
                Map<String, Integer> map1 = map.get(key);
                map1.put(row, 0);
                map.put(key, map1);
            });
        }

        for (Date date : columns) {
            Map<String, Integer> valueMap = map.get(date);
            if (MapUtils.isEmpty(valueMap)) {
                continue;
            }
            List<Map.Entry<String, Integer>> list = Lists.newArrayList(valueMap.entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (Map.Entry<String, Integer> entry : list) {
                rows.add(entry.getKey());
            }
            break;
        }

        Map<String, Object> result = Maps.newHashMap();
        result.put("keys", columns.stream().map(DateUtils::date2SimpleHm).collect(Collectors.toList()));
        List<Object> valueList = Lists.newArrayList();
        for (String row : rows) {
            Map<String, Object> columnMap = Maps.newLinkedHashMap();
            for (Date column : columns) {
                int v = 0;
                if (!MapUtils.isEmpty(map.get(column)) && map.get(column).get(row) != null) {
                    v = map.get(column).get(row);
                }
                columnMap.put(DateUtils.date2SimpleHm(column), v);
            }
            columnMap.put("callbackMsg", row);
            valueList.add(columnMap);
        }
        result.put("values", valueList);

        return Results.newSuccessResult(result);
    }
}