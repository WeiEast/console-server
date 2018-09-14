package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmRecordService;
import com.treefinance.saas.management.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.management.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.management.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.management.console.common.domain.vo.*;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.monitor.facade.domain.request.*;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.*;
import com.treefinance.saas.monitor.facade.service.AlarmRecordFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author chengtong
 * @date 18/5/31 16:36
 */
@Service
public class AlarmRecordServiceImpl implements AlarmRecordService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AlarmRecordFacade alarmRecordFacade;


    @Override
    public SaasResult<Map<String, Object>> queryAlarmRecord(com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest request){

        AlarmRecordRequest recordRequest = new AlarmRecordRequest();
        recordRequest.setId(request.getId());
        recordRequest.setSummary(request.getSummary());
        recordRequest.setPageSize(request.getPageSize());
        recordRequest.setPageNumber(request.getPageNumber());
        recordRequest.setAlarmType(request.getAlarmType());
        recordRequest.setLevel(request.getLevel());
        recordRequest.setStatus(request.getStatus());

        recordRequest.setEndTime(DateUtils.strToDateOrNull(request.getEndTime(),"yyyy-MM-dd hh:mm:ss"));
        recordRequest.setStartTime(DateUtils.strToDateOrNull(request.getStartTime(),"yyyy-MM-dd hh:mm:ss"));

        MonitorResult<List<AlarmRecordRO>> result;
        try{
            result = alarmRecordFacade.queryAlarmRecord(recordRequest);
            logger.info("获取monitor返回数据：{}", JSON.toJSONString(result));
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AlarmRecordVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),AlarmRecordVO.class);

        return  Results.newPageResult(request,result.getTotalCount(),alarmRecordVOS);
    }


    @Override
    public SaasResult queryAlarmListAndHandleMessage(com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest request){
        AlarmRecordStatRequest recordRequest = new AlarmRecordStatRequest();

        recordRequest.setEndTime(DateUtils.strToDateOrNull(request.getEndTime(),"yyyy-MM-dd hh:mm:ss"));
        recordRequest.setStartTime(DateUtils.strToDateOrNull(request.getStartTime(),"yyyy-MM-dd hh:mm:ss"));
        recordRequest.setDateType(request.getDateType());
        recordRequest.setName(request.getName());

        MonitorResult<List<AlarmRecordRO>> result;
        try{
            result = alarmRecordFacade.queryAlarmListAndHandleMessage(recordRequest);
            logger.info("获取monitor返回结果：{}", JSON.toJSONString(result));
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AlarmRecordVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),AlarmRecordVO.class);

        return  Results.newSuccessResult(alarmRecordVOS);
    }

    @Override
    public SaasResult<List<WorkOrderLogVO>> queryWorkOrderLog(Long id){
        WorkOrderLogRequest recordRequest = new WorkOrderLogRequest();

        recordRequest.setOrderId(id);

        MonitorResult<List<WorkOrderLogRO>> result;
        try{
            result = alarmRecordFacade.queryWorkOrderLog(recordRequest);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<WorkOrderLogVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),WorkOrderLogVO.class);

        return  Results.newSuccessResult(alarmRecordVOS);
    }


    @Override
    public SaasResult queryAlarmRecordOrder(AlarmWorkOrderRequest alarmWorkOrderRequest){

        WorkOrderRequest request = new WorkOrderRequest();

        request.setDutyName(alarmWorkOrderRequest.getDutyName());
        request.setProcessorName(alarmWorkOrderRequest.getProcessorName());
        request.setEndTime(DateUtils.strToDate(alarmWorkOrderRequest.getEndTime(),"yyyy-MM-dd hh:mm:ss"));
        request.setStartTime(DateUtils.strToDate(alarmWorkOrderRequest.getStartTime(),"yyyy-MM-dd hh:mm:ss"));
        request.setId(alarmWorkOrderRequest.getId());
        request.setPageNumber(alarmWorkOrderRequest.getPageNumber());
        request.setPageSize(alarmWorkOrderRequest.getPageSize());

        MonitorResult<List<AlarmWorkOrderRO>> result;
        try{
            result = alarmRecordFacade.queryAlarmWorkerOrder(request);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AlarmWorkOrderVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),AlarmWorkOrderVO.class);

        return  Results.newPageResult(alarmWorkOrderRequest,result.getTotalCount(),alarmRecordVOS);

    }
    @Override
    public SaasResult updateWorkerOrderProcessor(com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest updateWorkOrderRequest){

        UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();

        request.setProcessorName(updateWorkOrderRequest.getProcessorName());
        request.setId(updateWorkOrderRequest.getId());
        if(StringUtils.isNotEmpty(updateWorkOrderRequest.getOpName())){
            request.setOpName(updateWorkOrderRequest.getOpName());
        }


        MonitorResult<Boolean> result;
        try{
            result = alarmRecordFacade.updateWorkerOrderProcessor(request);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        if(StringUtils.isNotEmpty(result.getErrorMsg())){
            return Results.newFailedResult(CommonStateCode.FAILURE,result.getErrorMsg());
        }
        return  Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult updateWorkerOrderStatus(com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest updateWorkOrderRequest){

        UpdateWorkOrderRequest request = new UpdateWorkOrderRequest();

        request.setStatus(updateWorkOrderRequest.getStatus());
        request.setRemark(updateWorkOrderRequest.getRemark());
        request.setId(updateWorkOrderRequest.getId());
        if(StringUtils.isNotEmpty(updateWorkOrderRequest.getOpName())){
            request.setOpName(updateWorkOrderRequest.getOpName());
        }

        MonitorResult<Boolean> result;
        try{
            result = alarmRecordFacade.updateWorkerOrderStatus(request);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        if(StringUtils.isNotEmpty(result.getErrorMsg())){
            return Results.newFailedResult(CommonStateCode.FAILURE,result.getErrorMsg());
        }
        return  Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult querySaasWorker(){
        MonitorResult<List<SaasWorkerRO>> result;
        try{
            result = alarmRecordFacade.querySaasWorker();
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        List<SaasWorkerVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),SaasWorkerVO.class);
        return  Results.newSuccessResult(alarmRecordVOS);
    }

    @Override
    public SaasResult querySaasWorkerPage(SaasWorkerRequest request) {

        com.treefinance.saas.monitor.facade.domain.request.SaasWorkerRequest saasWorkerRequest = new com.treefinance.saas.monitor.facade.domain.request.SaasWorkerRequest();

        saasWorkerRequest.setName(request.getName());
        saasWorkerRequest.setPageNumber(request.getPageNumber());
        saasWorkerRequest.setPageSize(request.getPageSize());


        MonitorResult<List<SaasWorkerRO>> result;
        try{
            result = alarmRecordFacade.querySaasWorkerPaginate(saasWorkerRequest);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        List<SaasWorkerVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),SaasWorkerVO.class);
        return  Results.newPageResult(request,result.getTotalCount(),alarmRecordVOS);
    }

    @Override
    public SaasResult queryStatList(com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest request) {

        AlarmRecordStatRequest recordRequest = new AlarmRecordStatRequest();

        recordRequest.setStartTime(DateUtils.strToDateOrNull(request.getStartTime(), "yyyy-MM-dd hh:mm:ss"));
        recordRequest.setEndTime(DateUtils.strToDateOrNull(request.getEndTime(), "yyyy-MM-dd hh:mm:ss"));
        recordRequest.setDateType(request.getDateType());
        recordRequest.setName(request.getName());

        MonitorResult<List<AlarmRecordStatisticRO>> result;
        try{
            result = alarmRecordFacade.queryAlarmStatistic(recordRequest);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AlarmRecordStatVO> alarmRecordVOS = DataConverterUtils.convert(result.getData(),AlarmRecordStatVO.class);
        return  Results.newSuccessResult(alarmRecordVOS);
    }

    @Override
    public SaasResult queryAlarmType() {
        MonitorResult<List<AlarmTypeListRO>> result;
        try{
            result = alarmRecordFacade.queryAlarmTypeList();
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return  Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult queryErrorRecords(DashboardRequest request) {
        AlarmRecordDashBoardRequest statRequest = new AlarmRecordDashBoardRequest();

        statRequest.setSaasEnv(request.getSaasEnv());
        statRequest.setBizType(request.getBizType());

        statRequest.setStartTime(DateUtils.strToDateOrNull(request.getStartDate(), "yyyy-MM-dd"));
        statRequest.setEndTime(DateUtils.strToDateOrNull(request.getStartDate(), "yyyy-MM-dd"));

        statRequest.setPageNumber(request.getPageNumber());
        statRequest.setPageSize(request.getPageSize());


        MonitorResult result;

        try{
            result = alarmRecordFacade.queryAlarmRecordInDashBoard(statRequest);
        }catch (Exception e){
            logger.error("请求monitor-server失败：{}",e.getMessage());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("monitor获取数据：{}",result);

        return Results.newPageResult(request,result.getTotalCount() , result.getData());


    }
}
