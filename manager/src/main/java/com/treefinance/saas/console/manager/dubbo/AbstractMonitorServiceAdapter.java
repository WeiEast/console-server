package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.context.exception.RpcServiceException;
import com.treefinance.saas.console.share.adapter.AbstractDubboServiceAdapter;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Jerry
 * @date 2018/11/26 22:16
 */
public abstract class AbstractMonitorServiceAdapter extends AbstractDubboServiceAdapter {

    protected <T> void validateResponse(MonitorResult<T> result, RpcActionEnum action, Object... args) {
        super.validateResponse(result, action, args);

        String errorMsg = result.getErrorMsg();
        if (StringUtils.isNotEmpty(errorMsg)) {
            throw new RpcServiceException("[MONITOR] Error server! errorMsg: " + errorMsg + " - action: " + action + appendArgs(args));
        }
    }
}
