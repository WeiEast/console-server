package com.treefinance.saas.management.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/5/31 17:35
 */
public class SaasWorkerVO extends BaseVO{

    private Long id;
    private String name;
    private String mobile;
    private String email;
    private String dutyCorn;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDutyCorn(String dutyCorn) {
        this.dutyCorn = dutyCorn;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDutyCorn() {
        return this.dutyCorn;
    }

}
