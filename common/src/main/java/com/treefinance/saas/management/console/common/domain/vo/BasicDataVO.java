package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/24上午11:30
 */
public class BasicDataVO implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 基础数据编码
     */
    private String dataCode;

    /**
     * 基础数据名字
     */
    private String dataName;


    /**
     * 基础数据样例
     */
    private String dataJson;

    /**
     * 数据请求来源类型（0：mq;1:db
     */
    private Byte dataSource;


    /**
     * 数据来源配置
     */
    private String dataSourceConfigJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Byte getDataSource() {
        return dataSource;
    }

    public void setDataSource(Byte dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceConfigJson() {
        return dataSourceConfigJson;
    }

    @Override
    public String toString() {
        return "BasicDataVO{" +
                "id='" + id + '\'' +
                ", dataCode='" + dataCode + '\'' +
                ", dataName='" + dataName + '\'' +
                ", dataJson='" + dataJson + '\'' +
                ", dataSource=" + dataSource +
                ", dataSourceConfigJson='" + dataSourceConfigJson + '\'' +
                '}';
    }

    public void setDataSourceConfigJson(String dataSourceConfigJson) {
        this.dataSourceConfigJson = dataSourceConfigJson;


    }
}
