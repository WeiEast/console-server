package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;

/**
 * @author chengtong
 * @date 18/6/12 19:56
 */
public class SaasWorkerRequest extends PageRequest{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
