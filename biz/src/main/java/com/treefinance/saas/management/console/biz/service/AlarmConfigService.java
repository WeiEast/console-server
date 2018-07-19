package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigVO;

import java.util.List;

/**
 * @author chengtong
 * @date 18/7/19 16:06
 */
public interface AlarmConfigService {

    SaasResult<List<AlarmConfigVO>> queryAlarmConfigList(AlarmConfigRequest request);

}
