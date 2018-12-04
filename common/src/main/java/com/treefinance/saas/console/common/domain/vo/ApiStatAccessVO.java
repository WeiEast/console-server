package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/10.
 */
public class ApiStatAccessVO implements Serializable {
    private static final long serialVersionUID = 2222324336110605727L;

    private String key;
    private Map<String, List<ChartStatVO>> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, List<ChartStatVO>> getValue() {
        return value;
    }

    public void setValue(Map<String, List<ChartStatVO>> value) {
        this.value = value;
    }
}
