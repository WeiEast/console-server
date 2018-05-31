package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmRecordService;
import com.treefinance.saas.management.console.common.domain.request.AlarmRecordRequest;
import com.treefinance.saas.management.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.management.console.common.domain.request.UpdateWorkOrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param alarmRecordRequest {summary: 摘要信息 alarmType：预警类型 processorName dutyName }
     * @return 分页的数据列表
     */
    @RequestMapping(value = "/record/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryAlarmList(AlarmRecordRequest alarmRecordRequest) {

        return alarmRecordService.queryAlarmRecord(alarmRecordRequest);
    }

    /**
     * 工单列表
     *
     * @param alarmRecordRequest 查询参数
     * @return 工单数据列表
     * @see AlarmWorkOrderRequest
     */
    @RequestMapping(value = "/workOrder/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryWorkOrderList(AlarmWorkOrderRequest alarmRecordRequest) {


        return alarmRecordService.queryAlarmRecordOrder(alarmRecordRequest);

    }

    /**
     * 根据工单查询工单的历史记录
     *
     * @param id 工单的编号
     * @return 工单的对应的历史更改记录列表
     */
    @RequestMapping(value = "/workOrder/history/{id}", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryWorkOrderHistory(@PathVariable Long id) {
        return alarmRecordService.queryWorkOrderLog(id);
    }

    /**
     * 工作人员列表
     *
     * @return 工作人员列表
     * @see
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
    public SaasResult updateWorkOrderProcessor(UpdateWorkOrderRequest request) {

        if(StringUtils.isEmpty(request.getProcessorName())|| Objects.isNull(request.getId())){
            return Results.newFailedResult(CommonStateCode.ILLEGAL_PARAMETER);
        }

        return alarmRecordService.updateWorkerOrderProcessor(request);
    }


    /**
     * 工单更新状态
     */
    @RequestMapping(value = "/workOrder/update/status", produces = "application/json", method = RequestMethod.POST)
    public SaasResult updateWorkOrderStatus(UpdateWorkOrderRequest request) {
        if(Objects.isNull(request.getStatus())|| Objects.isNull(request.getId())){
            return Results.newFailedResult(CommonStateCode.ILLEGAL_PARAMETER);
        }

        return alarmRecordService.updateWorkerOrderStatus(request);
    }

}
