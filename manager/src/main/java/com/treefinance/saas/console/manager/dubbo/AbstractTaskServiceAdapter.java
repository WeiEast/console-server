package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.context.component.AbstractDubboServiceAdapter;
import com.treefinance.saas.console.context.component.RpcActionEnum;
import com.treefinance.saas.console.exception.RpcServiceException;
import com.treefinance.saas.taskcenter.facade.response.TaskResponse;
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

    protected <T> void validateResult(TaskResult<T> result, RpcActionEnum action, Object... args) {
        validateResponse(result, action, args);

        if (result.getData() == null) {
            throw new RpcServiceException("[TASK] Invalid response entity! - action:" + " " + action + appendArgs(args));
        }
    }

    /**
     * 只检查响应是否正常
     */
    protected <T> void validateResponse(TaskResponse<T> response, RpcActionEnum action, Object... args) {
        super.validateResponse(response, action, args);

        if (!response.isSuccess()) {
            throw new RpcServiceException(
                "[TASK] Error server! code: " + response.getCode() + ", message: " + response.getMessage() + " - action:" + " " + action + appendArgs(args));
        }
    }

    /**
     * 检查响应是否正常的基础上进一步检查响应的数据实体是否为空
     */
    protected <T> void validateResult(TaskResponse<T> response, RpcActionEnum action, Object... args) {
        validateResponse(response, action, args);

        if (response.getEntity() == null) {
            throw new RpcServiceException("[TASK] Invalid response entity! - action:" + " " + action + appendArgs(args));
        }
    }
}
