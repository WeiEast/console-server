package com.treefinance.saas.management.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/5/31 17:35
 */
public class SaasWorkerVO extends BaseVO{

    /**
     * 工作人员编号
     * */
    private Long id;

    /**
     * 工作人员名字
     *
     * */
    private String name;

    /**
     * 联系手机
     * */
    private String mobile;

    /**
     * 邮箱地址
     * */
    private String email;

    /**
     * 工作值班日的corn表达式
     * */
    private String dutyCorn;

    /**
     * 下一个值班日
     * */
    private String nextOnDuty;

    /**
     * 上一个值班日
     * */
    private String preOnDuty;

    private String createTimeStr;

    private String lastUpdateTimeStr;

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

    public String getNextOnDuty() {
        return nextOnDuty;
    }

    public void setNextOnDuty(String nextOnDuty) {
        this.nextOnDuty = nextOnDuty;
    }

    public String getPreOnDuty() {
        return preOnDuty;
    }

    public void setPreOnDuty(String preOnDuty) {
        this.preOnDuty = preOnDuty;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getLastUpdateTimeStr() {
        return lastUpdateTimeStr;
    }

    public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
        this.lastUpdateTimeStr = lastUpdateTimeStr;
    }
}
