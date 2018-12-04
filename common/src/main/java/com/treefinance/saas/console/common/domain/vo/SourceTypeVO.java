package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2018/1/22.
 */
public class SourceTypeVO implements Serializable {
    private static final long serialVersionUID = -5429297673603422375L;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
