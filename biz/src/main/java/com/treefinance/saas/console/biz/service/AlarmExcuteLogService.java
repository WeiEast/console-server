package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.AsAlarmRequest;
import com.treefinance.saas.knife.result.SaasResult;

/**
 * @author:guoguoyun
 * @date:Created in 2018/7/19下午3:12
 */
public interface AlarmExcuteLogService {
    SaasResult queryAlarmLogList(AsAlarmRequest asAlarmRequest);
}
