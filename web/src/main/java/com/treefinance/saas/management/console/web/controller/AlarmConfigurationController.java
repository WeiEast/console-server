package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmExcuteLogService;
import com.treefinance.saas.management.console.common.domain.request.AsAlarmRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengtong
 * @date 18/7/19 15:47
 */
@RestController
@RequestMapping("/saas/console/alarm/config")
public class AlarmConfigurationController {

    @Autowired
    AlarmExcuteLogService alarmExcuteLogService;
    /**
     * 获取预警执行日志的列表
     * @param asAlarmRequest
     * @return
     */
    @RequestMapping(value="/log/list",produces = "application/json",method = RequestMethod.POST)
    public SaasResult queryAlarmLogList(@RequestBody AsAlarmRequest asAlarmRequest) {
        return alarmExcuteLogService.queryAlarmLogList(asAlarmRequest);

    }

}
