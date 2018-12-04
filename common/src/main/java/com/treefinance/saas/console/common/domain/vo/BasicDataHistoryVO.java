package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yh-treefinance on 2018/5/2.
 */
public class BasicDataHistoryVO implements Serializable {
    /**
     * 数据历史ID
     */
    private Long id;

    /**
     * 基础数据ID
     */
    private Long basicDataId;

    /**
     * 基础数据编码
     */
    private String basicDataCode;

    /**
     * 数据唯一编码
     */
    private String dataId;

    /**
     * 数据时间
     */
    private Date dataTime;

    /**
     * 数据json
     */
    private String dataJson;

    /** 创建时间 */
    private Date createTime;

    /** 上次更新时间 */
    private Date lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBasicDataId() {
        return basicDataId;
    }

    public void setBasicDataId(Long basicDataId) {
        this.basicDataId = basicDataId;
    }

    public String getBasicDataCode() {
        return basicDataCode;
    }

    public void setBasicDataCode(String basicDataCode) {
        this.basicDataCode = basicDataCode;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
