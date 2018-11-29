package com.treefinance.saas.console.share.adapter;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.context.exception.RpcServiceException;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Jerry
 * @date 2018/11/23 19:15
 */
public abstract class AbstractDubboServiceAdapter extends AbstractDomainObjectAdapter {

    protected <Response> void validateResponse(Response result, RpcActionEnum action, Object... args) {
        if (result == null) {
            throw new RpcServiceException("Bad response! - action: " + action + appendArgs(args));
        }
    }

    protected String appendArgs(Object... args) {
        return ArrayUtils.isNotEmpty(args) ? ", args: " + JSON.toJSONString(args) : StringUtils.EMPTY;
    }

}
