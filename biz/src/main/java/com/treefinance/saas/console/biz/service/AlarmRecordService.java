package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.AlarmRecordRequest;
import com.treefinance.saas.console.common.domain.request.AlarmWorkOrderRequest;
import com.treefinance.saas.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.console.common.domain.request.UpdateWorkOrderRequest;
import com.treefinance.saas.console.common.domain.vo.WorkOrderLogVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;
import java.util.Map;

/**
 * @author chengtong
 * @date 18/5/31 16:06
 */
public interface AlarmRecordService {

    SaasResult<Map<String, Object>> queryAlarmRecord(AlarmRecordRequest request);

    SaasResult<List<WorkOrderLogVO>> queryWorkOrderLog(Long id);

    SaasResult queryAlarmRecordOrder(AlarmWorkOrderRequest alarmWorkOrderRequest);

    SaasResult queryAlarmListAndHandleMessage(AlarmRecordRequest request);

    SaasResult queryAlarmType();

    SaasResult updateWorkerOrderProcessor(UpdateWorkOrderRequest updateWorkOrderRequest);

    SaasResult updateWorkerOrderStatus(UpdateWorkOrderRequest updateWorkOrderRequest);

    SaasResult querySaasWorker();

    SaasResult querySaasWorkerPage(SaasWorkerRequest request);

    SaasResult queryStatList(AlarmRecordRequest request);

    SaasResult queryErrorRecords(DashboardRequest request);

}
