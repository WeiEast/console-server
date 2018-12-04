package com.treefinance.saas.console.common.domain.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 18/6/1 10:25
 */
public class BaseVO implements Serializable {

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
