package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.context.component.AbstractDubboServiceAdapter;
import com.treefinance.saas.console.exception.RpcServiceException;
import com.treefinance.saas.console.context.component.RpcActionEnum;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;

/**
 * @author Jerry
 * @date 2018/11/26 22:16
 */
public abstract class AbstractMerchantServiceAdapter extends AbstractDubboServiceAdapter {

    protected <T> void validateResponse(MerchantResult<T> result, RpcActionEnum action, Object... args) {
        super.validateResponse(result, action, args);

        if (!result.isSuccess()) {
            throw new RpcServiceException("[MERCHANT] Error server! errorMsg: " + result.getRetMsg() + " - action: " + action + appendArgs(args));
        }
    }
}
