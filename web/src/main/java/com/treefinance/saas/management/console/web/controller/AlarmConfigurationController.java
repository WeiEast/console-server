package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmConfigService;
import com.treefinance.saas.management.console.biz.service.AlarmExcuteLogService;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.request.AsAlarmRequest;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chengtong
 * @date 18/7/19 15:47
 */
@RestController
@RequestMapping("/saas/console/alarm/config")
public class AlarmConfigurationController {

    @Autowired
    AlarmConfigService alarmConfigService;

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

    @RequestMapping(value="/list",produces = "application/json",method = RequestMethod.POST)
    public SaasResult<List<AlarmConfigVO>> queryAlarmConfigList(AlarmConfigRequest request){
        if(request.getRunEnv() != null){
            if(request.getRunEnv().intValue() >2 ||request.getRunEnv().intValue() <0  ){
                throw new IllegalArgumentException("请求参数runEnv为空或非法!");
            }
        }
        return alarmConfigService.queryAlarmConfigList(request);
    }

}
