package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DmStatDayVO implements Serializable{
    private static final long serialVersionUID = 8542082619152871166L;

    private List<String> keys;

    private Map<String,List<StatPointDayVO>> values;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Map<String, List<StatPointDayVO>> getValues() {
        return values;
    }

    public void setValues(Map<String, List<StatPointDayVO>> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "DmStatDayVO{" +
                "keys=" + keys +
                ", values=" + values +
                '}';
    }
}
