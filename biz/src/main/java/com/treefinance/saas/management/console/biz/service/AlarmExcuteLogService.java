package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.AsAlarmRequest; /**
 * @author:guoguoyun
 * @date:Created in 2018/7/19下午3:12
 */
public interface AlarmExcuteLogService {
    SaasResult queryAlarmLogList(AsAlarmRequest asAlarmRequest);
}
