package com.treefinance.saas.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;

/**
 * Created by haojiahong on 2017/8/16.
 */
public class RedisRequest extends PageRequest {

    private String key;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
