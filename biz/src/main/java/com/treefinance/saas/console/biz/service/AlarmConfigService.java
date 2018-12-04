package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.console.common.domain.vo.AlarmConfigDetailVO;
import com.treefinance.saas.console.common.domain.vo.AlarmConfigExpressionTestVO;
import com.treefinance.saas.console.common.domain.vo.SaasWorkerVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;
import java.util.Map;

/**
 * @author chengtong
 * @date 18/7/19 16:06
 */
public interface AlarmConfigService {

    SaasResult queryAlarmConfigList(AlarmConfigRequest request);

    SaasResult<AlarmConfigDetailVO> queryAlarmConfigDetailById(Long id);

    SaasResult<Boolean> saveAlarmConfigDetail(AlarmConfigDetailVO alarmConfigDetailVO);

    SaasResult<Map<String, String>> cronCompute(String cronExpression);

    SaasResult<List<SaasWorkerVO>> queryWorkerByDate(SaasWorkerRequest saasWorkerRequest);

    SaasResult<Boolean> updateAlarmSwitch(Long id);

    SaasResult<Object> testAlarmConfigDetail(AlarmConfigExpressionTestVO alarmConfigExpressionTestVO);

    SaasResult<Boolean> copyAlarm(Long id);

    SaasResult<Boolean> deleteAlarmConfig(Long id);

}
