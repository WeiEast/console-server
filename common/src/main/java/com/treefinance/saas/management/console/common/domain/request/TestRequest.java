package com.treefinance.saas.management.console.common.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yh-treefinance on 2018/5/22.
 */
public class TestRequest implements Serializable {

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 基础数据json
     */
    private List<String> dataJsons;

    /**
     * 分组序号
     */
    private List<Integer> groupIndexs;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public List<String> getDataJsons() {
        return dataJsons;
    }

    public void setDataJsons(List<String> dataJsons) {
        this.dataJsons = dataJsons;
    }

    public List<Integer> getGroupIndexs() {
        return groupIndexs;
    }

    public void setGroupIndexs(List<Integer> groupIndexs) {
        this.groupIndexs = groupIndexs;
    }
}
