package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmExcuteLogService;
import com.treefinance.saas.management.console.common.domain.request.AsAlarmRequest;
import com.treefinance.saas.monitor.facade.domain.request.AlarmExcuteLogRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.AlarmExecuteLogRO;
import com.treefinance.saas.monitor.facade.service.autoalarm.AlarmBasicConfigurationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author:guoguoyun
 * @date:Created in 2018/7/19下午3:12
 */
@Service
public class AlarmExcuteLogServiceImpl implements AlarmExcuteLogService {
    private  static  final Logger logger = LoggerFactory.getLogger(AlarmExcuteLogService.class);


    @Autowired
    AlarmBasicConfigurationFacade alarmBasicConfigurationFacade;


    @Override
    public SaasResult queryAlarmLogList(AsAlarmRequest asAlarmRequest) {
        if(asAlarmRequest.getId()==0)
        {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK,"预警配置ID不能为空");
        }
        AlarmExcuteLogRequest alarmExcuteLogRequest = new AlarmExcuteLogRequest();
        alarmExcuteLogRequest.setId(asAlarmRequest.getId());
        alarmExcuteLogRequest.setStartDate(asAlarmRequest.getStartDate());
        alarmExcuteLogRequest.setEndDate(asAlarmRequest.getEndDate());
        MonitorResult<List<AlarmExecuteLogRO>> monitorResult = alarmBasicConfigurationFacade.queryAlaramExecuteLogList(alarmExcuteLogRequest);
        if(monitorResult.getData()==null){
            logger.error("取的数据为空{}",monitorResult.getErrorMsg());
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA,monitorResult.getErrorMsg());
        }
        return Results.newPageResult(asAlarmRequest,monitorResult.getTotalCount(),monitorResult.getData());
    }
}
