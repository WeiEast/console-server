package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigVO;
import com.treefinance.saas.management.console.common.domain.vo.SaasWorkerVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chengtong
 * @date 18/7/19 16:06
 */
public interface AlarmConfigService {

    SaasResult<List<AlarmConfigVO>> queryAlarmConfigList(AlarmConfigRequest request);

    SaasResult<AlarmConfigDetailVO> queryAlarmConfigDetailById(Long id);

    SaasResult<Boolean> saveAlarmConfigDetail(AlarmConfigDetailVO alarmConfigDetailVO);

    SaasResult<Map<String, String>> cronCompute(String cronExpression);

    SaasResult<List<SaasWorkerVO>> queryWorkerByDate(SaasWorkerRequest saasWorkerRequest);
}
