package com.treefinance.saas.console.common.domain.vo.common;

import java.io.Serializable;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/8
 */
public class SaasEnvVO implements Serializable {

    private static final long serialVersionUID = 9190384509477077446L;

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
