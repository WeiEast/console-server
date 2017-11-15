package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.OperatorStatService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.AllOperatorStatDayAccessVO;
import com.treefinance.saas.management.console.common.domain.vo.OperatorStatDayAccessDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.OperatorStatDayAccessVO;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.monitor.facade.domain.request.OperatorStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorAllStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.operator.OperatorStatDayAccessRO;
import com.treefinance.saas.monitor.facade.service.stat.OperatorStatAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/11/1.
 */
@Service
public class OperatorStatServiceImpl implements OperatorStatService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorStatService.class);

    @Autowired
    private OperatorStatAccessFacade operatorStatAccessFacade;

    @Override
    public Object queryAllOperatorStatDayAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        MonitorResult<List<OperatorAllStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryAllOperatorStatDayAccessListWithPage(rpcRequest);
        logger.info("查询所有运营商日监控统计数据(分页),rpcRequest={},rpcResult={}", JSON.toJSONString(rpcRequest), JSON.toJSONString(rpcResult));
        List<AllOperatorStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, result);
        }
        result = BeanUtils.convertList(rpcResult.getData(), AllOperatorStatDayAccessVO.class);
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryOperatorStatDayAccessList(OperatorStatRequest request) {
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setDataDate(request.getDataDate());
        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());
        rpcRequest.setGroupName(request.getGroupName());
        MonitorResult<List<OperatorStatDayAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatDayAccessListWithPage(rpcRequest);
        logger.info("查询各个运营商日监控统计数据(分页),rpcRequest={},rpcResult={}", JSON.toJSONString(rpcRequest), JSON.toJSONString(rpcResult));
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
        MonitorResult<List<OperatorStatDayAccessRO>> rpcDayResult = operatorStatAccessFacade.queryOneOperatorStatDayAccessListWithPage(rpcDayRequest);
        logger.info("查询某一个运营商日监控统计数据(分页),rpcDayRequest={},rpcResult={}", JSON.toJSONString(rpcDayRequest), JSON.toJSONString(rpcDayResult));
        if (CollectionUtils.isEmpty(rpcDayResult.getData())) {
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        OperatorStatAccessRequest rpcRequest = new OperatorStatAccessRequest();
        rpcRequest.setGroupCode(request.getGroupCode());
        rpcRequest.setStartDate(request.getStartDate());
        rpcRequest.setEndDate(request.getEndDate());
        MonitorResult<List<OperatorStatAccessRO>> rpcResult = operatorStatAccessFacade.queryOperatorStatAccessList(rpcRequest);
        logger.info("查询某一个运营商小时监控统计数据,rpcRequest={},rpcResult={}", JSON.toJSONString(rpcRequest), JSON.toJSONString(rpcResult));
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
                detailList.stream().sorted((o1, o2) -> o2.getDataTime().compareTo(o1.getDataTime())).collect(Collectors.toList());
            }
            vo.setDataTimeStr(dateStr);
            vo.setChildren(detailList);
            result.add(vo);
        }
        return Results.newSuccessPageResult(request, rpcDayResult.getTotalCount(), result);
    }
}
