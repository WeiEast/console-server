package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DmStatActualVO implements Serializable{
    private static final long serialVersionUID = 5085252509315791878L;

    private List<String> keys;

    private Map<String,List<StatPointActualVO>> values;


    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Map<String, List<StatPointActualVO>> getValues() {
        return values;
    }

    public void setValues(Map<String, List<StatPointActualVO>> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "DmStatActualVO{" +
                "keys=" + keys +
                ", values=" + values +
                '}';
    }
}
