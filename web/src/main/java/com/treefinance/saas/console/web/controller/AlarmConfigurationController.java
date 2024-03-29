package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.service.AlarmConfigService;
import com.treefinance.saas.console.biz.service.AlarmExcuteLogService;
import com.treefinance.saas.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.console.common.domain.request.AsAlarmRequest;
import com.treefinance.saas.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.console.common.domain.vo.AlarmConfigDetailVO;
import com.treefinance.saas.console.common.domain.vo.AlarmConfigExpressionTestVO;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预警配置管理
 * 
 * @author chengtong
 * @date 18/7/19 15:47
 */
@RestController
@RequestMapping("/saas/console/alarm/config")
public class AlarmConfigurationController {

    @Autowired
    private AlarmConfigService alarmConfigService;

    @Autowired
    private AlarmExcuteLogService alarmExcuteLogService;

    /**
     * 获取预警执行日志的列表
     *
     * @param asAlarmRequest
     * @return
     */
    @RequestMapping(value = "/log/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryAlarmLogList(@RequestBody AsAlarmRequest asAlarmRequest) {
        return alarmExcuteLogService.queryAlarmLogList(asAlarmRequest);

    }

    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.POST)
    public SaasResult queryAlarmConfigList(@RequestBody AlarmConfigRequest request) {
        if (request.getRunEnv() != null) {
            if (request.getRunEnv().intValue() > 2 || request.getRunEnv().intValue() < 0) {
                throw new IllegalArgumentException("请求参数runEnv为空或非法!");
            }
        }
        return alarmConfigService.queryAlarmConfigList(request);
    }

    @RequestMapping(value = "/get/{id}", produces = "application/json", method = RequestMethod.GET)
    public Object queryAlarmConfigDetailById(@PathVariable Long id) {
        return alarmConfigService.queryAlarmConfigDetailById(id);
    }

    @RequestMapping(value = "/save", produces = "application/json", method = RequestMethod.POST)
    public Object saveAlarmConfigDetail(@RequestBody AlarmConfigDetailVO alarmConfigDetailVO) {
        return alarmConfigService.saveAlarmConfigDetail(alarmConfigDetailVO);
    }

    @RequestMapping(value = "/test", produces = "application/json", method = RequestMethod.POST)
    public Object testAlarmConfigDetail(@RequestBody AlarmConfigExpressionTestVO alarmConfigExpressionTestVO) {
        return alarmConfigService.testAlarmConfigDetail(alarmConfigExpressionTestVO);
    }

    @RequestMapping(value = "/cron/compute", produces = "application/json")
    public Object cronCompute(String cron) {
        return alarmConfigService.cronCompute(cron);
    }

    /**
     * 根据日期返回值班人员信息
     *
     * @param saasWorkerRequest
     * @return
     */
    @RequestMapping(value = "/saasworker", produces = "application/json", method = RequestMethod.POST)
    public Object queryWorkerByDate(@RequestBody SaasWorkerRequest saasWorkerRequest) {
        return alarmConfigService.queryWorkerByDate(saasWorkerRequest);
    }

    /**
     * 更改预警开关
     *
     * @param id
     * @returnp
     */
    @RequestMapping(value = "/update/alarmswitch/{id}", produces = "application/json", method = RequestMethod.GET)
    public Object updateAlarmSwitch(@PathVariable Long id) {
        return alarmConfigService.updateAlarmSwitch(id);
    }

    @RequestMapping(value = "/copy", method = RequestMethod.GET)
    public Object copyAlarmConfig(Long id) {
        return alarmConfigService.copyAlarm(id);
    }

    /**
     * 删除预警配置
     *
     * @param id
     * @returnp
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteAlarmConfig(Long id) {
        return alarmConfigService.deleteAlarmConfig(id);
    }

}
