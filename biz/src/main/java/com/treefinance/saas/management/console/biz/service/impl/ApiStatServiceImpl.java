package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.treefinance.saas.management.console.biz.service.ApiStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ApiStatAccessVO;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.management.console.common.domain.vo.PieChartStatVO;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.monitor.facade.domain.request.ApiStatBaseRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.api.ApiBaseStatRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.api.ApiStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.api.ApiStatDayAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.ApiStatAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/10.
 */
@Service
public class ApiStatServiceImpl implements ApiStatService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApiStatAccessFacade apiStatAccessFacade;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;


    @Override
    public Map<String, Object> queryAllAccessList(StatRequest request) {
        baseCheck(request);
        ApiStatBaseRequest statRequest = new ApiStatBaseRequest();
        statRequest.setStartDate(request.getStartDate());
        statRequest.setEndDate(request.getEndDate());

        MonitorResult<List<ApiBaseStatRO>> result = apiStatAccessFacade.queryTotalAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("apiStatAccessFacade.queryTotalAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        Map<String, Object> wrapMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }
        Map<String, List<ChartStatVO>> resultMap = Maps.newLinkedHashMap();
        result.getData().forEach(ro -> {
            List<ChartStatVO> totalList = resultMap.get("总访问量");
            List<ChartStatVO> http2xxList = resultMap.get("2xx量");
            List<ChartStatVO> http4xxList = resultMap.get("4xx量");
            List<ChartStatVO> http5xxList = resultMap.get("5xx量");

            ChartStatVO totalVO = new ChartStatVO();
            totalVO.setDataTime(ro.getDataTime());
            totalVO.setDataValue(ro.getTotalCount());

            ChartStatVO http2xxVO = new ChartStatVO();
            http2xxVO.setDataTime(ro.getDataTime());
            http2xxVO.setDataValue(ro.getHttp2xxCount());

            ChartStatVO http4xxVO = new ChartStatVO();
            http4xxVO.setDataTime(ro.getDataTime());
            http4xxVO.setDataValue(ro.getHttp4xxCount());

            ChartStatVO http5xxVO = new ChartStatVO();
            http5xxVO.setDataTime(ro.getDataTime());
            http5xxVO.setDataValue(ro.getHttp5xxCount());


            if (CollectionUtils.isEmpty(totalList)) {
                totalList = Lists.newArrayList();
                totalList.add(totalVO);
                resultMap.put("总访问量", totalList);
            } else {
                totalList.add(totalVO);
            }

            if (CollectionUtils.isEmpty(http2xxList)) {
                http2xxList = Lists.newArrayList();
                http2xxList.add(http2xxVO);
                resultMap.put("2xx量", http2xxList);
            } else {
                http2xxList.add(http2xxVO);
            }

            if (CollectionUtils.isEmpty(http4xxList)) {
                http4xxList = Lists.newArrayList();
                http4xxList.add(http4xxVO);
                resultMap.put("4xx量", http4xxList);
            } else {
                http4xxList.add(http4xxVO);
            }

            if (CollectionUtils.isEmpty(http5xxList)) {
                http5xxList = Lists.newArrayList();
                http5xxList.add(http5xxVO);
                resultMap.put("5xx量", http5xxList);
            } else {
                http5xxList.add(http5xxVO);
            }

        });
        List<String> keysList = Lists.newArrayList("总访问量", "2xx量", "4xx量", "5xx量");
        wrapMap.put("keys", keysList);
        wrapMap.put("values", resultMap);
        return wrapMap;
    }

    @Override
    public Map<String, Object> queryDayAccessList(StatRequest request) {
        baseCheck(request);
        ApiStatBaseRequest statRequest = new ApiStatBaseRequest();
        statRequest.setStartDate(request.getStartDate());
        statRequest.setEndDate(request.getEndDate());

        MonitorResult<List<ApiStatDayAccessRO>> result = apiStatAccessFacade.queryDayAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("apiStatAccessFacade.queryDayAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        Map<String, Object> warpMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(result.getData())) {
            return warpMap;
        }
        //遍历获取所有的时间节点
        Set<Date> dataTimeSet = Sets.newHashSet();
        result.getData().forEach(ro -> {
            dataTimeSet.add(ro.getDataTime());
        });
        List<Date> dataTimeList = Lists.newArrayList(dataTimeSet);

        //<appId,<dataTime,totalCount>>
        Map<String, Map<Date, Integer>> map = Maps.newHashMap();
        result.getData().forEach(ro -> {
            Map<Date, Integer> countMap = map.get(ro.getAppId());
            if (countMap == null || countMap.isEmpty()) {
                countMap = Maps.newHashMap();
                countMap.put(ro.getDataTime(), ro.getTotalCount());
                map.put(ro.getAppId(), countMap);
            } else {
                countMap.put(ro.getDataTime(), ro.getTotalCount());
            }
        });
        //填充未查到时间的数据
        this.fillDataTimeMap(map, dataTimeList);
        Map<String, Map<Date, Integer>> appNameMap = this.changeKey2AppName(map);

        List<String> keysList = Lists.newArrayList(appNameMap.keySet()).stream().sorted((String::compareTo)).collect(Collectors.toList());
        keysList.add(0, "总访问量");
        warpMap.put("keys", keysList);
        //计算总访问量
        Map<String, List<ChartStatVO>> valuesMap = this.countTotalTask(appNameMap);
        warpMap.put("values", valuesMap);
        return warpMap;
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
        valuesMap.put("总访问量", totalVOList);
        return valuesMap;
    }

    @Override
    public Map<String, Object> queryStatAccessList(StatRequest request, Integer type) {
        baseCheck(request);
        ApiStatBaseRequest statRequest = new ApiStatBaseRequest();
        statRequest.setStartDate(request.getStartDate());
        statRequest.setEndDate(request.getEndDate());

        MonitorResult<List<ApiStatAccessRO>> result = apiStatAccessFacade.queryStatAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("apiStatAccessFacade.queryStatAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        Map<String, Object> wrapMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(result.getData())) {
            return wrapMap;
        }
        Map<String, List<ChartStatVO>> resultMap = Maps.newHashMap();
        result.getData().forEach(ro -> {
            ChartStatVO vo = new ChartStatVO();
            vo.setDataTime(ro.getDataTime());
            if (type == 1) {
                vo.setDataValue(ro.getTotalCount());
            }
            if (type == 2) {
                vo.setDataValue(ro.getAvgResponseTime());
            }
            if (type == 3) {
                vo.setDataValue(ro.getHttp4xxCount() + ro.getHttp5xxCount());
            }
            List<ChartStatVO> list = resultMap.get(ro.getApiUrl());
            if (CollectionUtils.isEmpty(list)) {
                list = Lists.newArrayList();
                list.add(vo);
                resultMap.put(ro.getApiUrl(), list);
            } else {
                list.add(vo);
            }
        });

        //list按时间由小到大排序
        for (Map.Entry<String, List<ChartStatVO>> entry : resultMap.entrySet()) {
            List<ChartStatVO> voList = entry.getValue().stream().sorted((o1, o2) -> o1.getDataTime().compareTo(o2.getDataTime())).collect(Collectors.toList());
            resultMap.put(entry.getKey(), voList);
        }
        List<String> keysList = Lists.newArrayList(resultMap.keySet()).stream().sorted(String::compareTo).collect(Collectors.toList());
        wrapMap.put("keys", keysList);
        wrapMap.put("values", resultMap);
        return wrapMap;
    }

    @Override
    public List<ApiStatAccessVO> queryStatAccess(StatRequest request) {
        baseCheck(request);
        ApiStatBaseRequest statRequest = new ApiStatBaseRequest();
        statRequest.setStartDate(request.getStartDate());
        statRequest.setEndDate(request.getEndDate());

        MonitorResult<List<ApiStatAccessRO>> result = apiStatAccessFacade.queryStatAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("apiStatAccessFacade.queryStatAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }

        List<ApiStatAccessVO> resultList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(result.getData())) {
            return resultList;
        }
        //<apiUrl,List>
        Map<String, List<ApiStatAccessRO>> roMap = Maps.newHashMap();
        for (ApiStatAccessRO ro : result.getData()) {
            List<ApiStatAccessRO> roList = roMap.get(ro.getApiUrl());
            if (CollectionUtils.isEmpty(roList)) {
                roList = Lists.newArrayList();
                roList.add(ro);
                roMap.put(ro.getApiUrl(), roList);
            } else {
                roList.add(ro);
            }
        }
        for (Map.Entry<String, List<ApiStatAccessRO>> entry : roMap.entrySet()) {
            ApiStatAccessVO vo = new ApiStatAccessVO();
            vo.setKey(entry.getKey());
            Map<String, List<ChartStatVO>> voMap = Maps.newHashMap();
            List<ChartStatVO> totalList = Lists.newArrayList();
            List<ChartStatVO> responseTimeList = Lists.newArrayList();
            List<ChartStatVO> errorList = Lists.newArrayList();

            for (ApiStatAccessRO ro : entry.getValue()) {

                ChartStatVO totalVO = new ChartStatVO();
                totalVO.setDataTime(ro.getDataTime());
                totalVO.setDataValue(ro.getTotalCount());

                ChartStatVO responseTimeVO = new ChartStatVO();
                responseTimeVO.setDataTime(ro.getDataTime());
                responseTimeVO.setDataValue(ro.getAvgResponseTime());

                ChartStatVO errorVO = new ChartStatVO();
                errorVO.setDataTime(ro.getDataTime());
                errorVO.setDataValue(ro.getHttp4xxCount() + ro.getHttp5xxCount());

                totalList.add(totalVO);
                responseTimeList.add(responseTimeVO);
                errorList.add(errorVO);
            }
            voMap.put("总访问量", totalList);
            voMap.put("平均响应时间", responseTimeList);
            voMap.put("请求错误", errorList);
            vo.setValue(voMap);
            resultList.add(vo);
        }

        return resultList;
    }

    @Override
    public Map<String, Object> queryStatAccessRank(Date date) {
        ApiStatBaseRequest statRequest = new ApiStatBaseRequest();
        if (date == null) {
            statRequest.setStartDate(DateUtils.getTodayBeginDate());
            statRequest.setEndDate(DateUtils.getTomorrowBeginDate());
        } else {
            statRequest.setStartDate(DateUtils.getTodayBeginDate(date));
            statRequest.setEndDate(DateUtils.getTomorrowBeginDate(date));
        }

        MonitorResult<List<ApiStatAccessRO>> result = apiStatAccessFacade.queryStatAccessList(statRequest);
        if (logger.isDebugEnabled()) {
            logger.debug("apiStatAccessFacade.queryStatAccessList() : statRequest={},result={}",
                    JSON.toJSONString(statRequest), JSON.toJSONString(result));
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(result.getData())) {
            return resultMap;
        }
        Map<String, PieChartStatVO> dataMap = Maps.newHashMap();
        result.getData().forEach(ro -> {
            PieChartStatVO data = dataMap.get(ro.getApiUrl());
            if (data == null) {
                PieChartStatVO vo = new PieChartStatVO();
                vo.setName(ro.getApiUrl());
                vo.setValue(ro.getTotalCount());
                dataMap.put(ro.getApiUrl(), vo);
            } else {
                data.setValue(data.getValue() + ro.getTotalCount());
                dataMap.put(ro.getApiUrl(), data);
            }
        });

        resultMap.put("keys", Lists.newArrayList(dataMap.keySet()));
        resultMap.put("values", Lists.newArrayList(dataMap.values()));
        return resultMap;
    }

    private void fillDataTimeMap(Map<String, Map<Date, Integer>> map, List<Date> dateList) {
        for (Map.Entry<String, Map<Date, Integer>> entry : map.entrySet()) {
            Map<Date, Integer> valueMap = entry.getValue();
            for (Date date : dateList) {
                if (valueMap.get(date) == null) {
                    valueMap.put(date, 0);
                }
            }
        }
    }

    private Map<String, Map<Date, Integer>> changeKey2AppName(Map<String, Map<Date, Integer>> map) {
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(Lists.newArrayList(map.keySet()));
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList
                .stream()
                .collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));
        Map<String, Map<Date, Integer>> appNameMap = Maps.newHashMap();
        for (Map.Entry<String, Map<Date, Integer>> entry : map.entrySet()) {
            MerchantBase merchantBase = merchantBaseMap.get(entry.getKey());
            if (merchantBase == null) {
                logger.error("在接口系统日访问量统计中,appId={}在MerchantBase表中未查询到相关记录", entry.getKey());
                continue;
            }
            appNameMap.put(merchantBase.getAppName(), entry.getValue());
        }
        return appNameMap;
    }


    public void baseCheck(StatRequest request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("请求参数startDate或endDate不能为空！");
        }
        if (request.getStartDate().after(request.getEndDate())) {
            throw new IllegalArgumentException("请求参数startDate不能晚于endDate！");
        }
    }
}
