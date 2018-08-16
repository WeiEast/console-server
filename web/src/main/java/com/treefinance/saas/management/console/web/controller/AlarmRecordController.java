package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmRecordService;
import com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest;
import com.treefinance.saas.management.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.management.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 预警记录相关的访问控制器
 *
 * @author chengtong
 * @date 18/5/30 10:22
 */
@RestController
@RequestMapping("/saas/console/alarm")
public class AlarmRecordController {


    @Autowired
    AlarmRecordService alarmRecordService;

    /**
     * 获取预警记录的列表
     *
     * @param alarmRecordRequest 请求参数
     * @see AlarmRecordRequest
     * @return 分页的数据列表
     */
    @RequestMapping(value = "/record/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryAlarmList(@RequestBody AlarmRecordRequest alarmRecordRequest) {
        return alarmRecordService.queryAlarmRecord(alarmRecordRequest);
    }

    /**
     * 获取预警记录的列表（增加记录开始时间 持续时间等返回结果）
     *
     * @param alarmRecordRequest 请求参数
     * @see AlarmRecordRequest
     * @return 分页的数据列表
     */
    @RequestMapping(value = "/record/handleMessageList", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryAlarmListAndHandleMessage(@RequestBody AlarmRecordRequest alarmRecordRequest) {
        return alarmRecordService.queryAlarmListAndHandleMessage(alarmRecordRequest);
    }

    /**
     * 工单列表
     *
     * @param alarmRecordRequest 查询参数
     * @return 工单数据列表
     */
    @RequestMapping(value = "/workOrder/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryWorkOrderList(@RequestBody AlarmWorkOrderRequest alarmRecordRequest) {

        return alarmRecordService.queryAlarmRecordOrder(alarmRecordRequest);
    }

    /**
     * 根据工单查询工单的历史记录
     *
     * @param id 工单的编号
     * @return 工单的对应的历史更改记录列表
     */
    @RequestMapping(value = "/workOrder/history/{id}", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryWorkOrderHistory(@PathVariable long id) {
        return alarmRecordService.queryWorkOrderLog(id);
    }

    /**
     * 工作人员列表
     *
     * @return 工作人员列表
     */
    @RequestMapping(value = "/saasWorker/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult querySaasWorkerList() {
        return alarmRecordService.querySaasWorker();
    }

    /**
     * 工单更新 处理人员
     *
     * @param request 更新工单处理人员 ;{@see UpdateWorkOrderRequest}
     */
    @RequestMapping(value = "/workOrder/update/processor", produces = "application/json", method = RequestMethod.POST)
    public SaasResult updateWorkOrderProcessor(@RequestBody UpdateWorkOrderRequest request) {

        if (StringUtils.isEmpty(request.getProcessorName()) || Objects.isNull(request.getId())) {
            return Results.newFailedResult(CommonStateCode.ILLEGAL_PARAMETER);
        }

        return alarmRecordService.updateWorkerOrderProcessor(request);
    }

    /**
     * 工单更新状态
     *
     * @param request 请求参数
     */
    @RequestMapping(value = "/workOrder/update/status", produces = "application/json", method = RequestMethod.POST)
    public SaasResult updateWorkOrderStatus(@RequestBody UpdateWorkOrderRequest request) {
        if (Objects.isNull(request.getStatus()) || Objects.isNull(request.getId())) {
            return Results.newFailedResult(CommonStateCode.ILLEGAL_PARAMETER);
        }

        return alarmRecordService.updateWorkerOrderStatus(request);
    }

    @RequestMapping(value = "/saas/worker/list/page", produces = "application/json", method = RequestMethod.POST)
    public SaasResult querySaasWorkerPage(@RequestBody SaasWorkerRequest request) {
        return alarmRecordService.querySaasWorkerPage(request);
    }

    @RequestMapping(value = "error/stat/list", produces = "application/json",method = RequestMethod.POST)
    public SaasResult queryStatList(@RequestBody AlarmRecordRequest request){
        return alarmRecordService.queryStatList(request);
    }

}
