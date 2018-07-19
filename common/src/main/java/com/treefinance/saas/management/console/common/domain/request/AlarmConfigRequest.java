package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;

/**
 * @author chengtong
 * @date 18/7/19 15:51
 */
public class AlarmConfigRequest extends PageRequest {

    private String name;

    private Byte runEnv;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(Byte runEnv) {
        this.runEnv = runEnv;
    }
}
