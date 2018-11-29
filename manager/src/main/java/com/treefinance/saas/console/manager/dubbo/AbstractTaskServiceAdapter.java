package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.context.exception.RpcServiceException;
import com.treefinance.saas.console.share.adapter.AbstractDubboServiceAdapter;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import com.treefinance.saas.taskcenter.facade.result.common.TaskResult;

/**
 * @author Jerry
 * @date 2018/11/26 22:16
 */
public abstract class AbstractTaskServiceAdapter extends AbstractDubboServiceAdapter {

    protected <T> void validateResponse(TaskResult<T> result, RpcActionEnum action, Object... args) {
        super.validateResponse(result, action, args);

        if (!result.isSuccess()) {
            throw new RpcServiceException("[TASK] Error server! code: " + result.getCode() + ", message: " + result.getMessage() + " - action:" + " " + action + appendArgs(args));
        }
    }
}
