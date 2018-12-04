package com.treefinance.saas.console.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmMsgDetailVO implements Serializable {

    private static final long serialVersionUID = 659632901560653403L;

    private Long id;
    private String titleTemplate;
    private String bodyTemplate;
    private Byte analysisType;
    @JsonIgnore
    private String notifyChannel;
    private List<String> notifyChannelList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public Byte getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(Byte analysisType) {
        this.analysisType = analysisType;
    }

    public String getNotifyChannel() {
        if (notifyChannel == null) {
            if (CollectionUtils.isNotEmpty(notifyChannelList)) {
                notifyChannel = Joiner.on(",").join(notifyChannelList);
            }
        }
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel;
    }

    public List<String> getNotifyChannelList() {
        if (notifyChannelList == null) {
            if (StringUtils.isNotBlank(notifyChannel)) {
                notifyChannelList = Splitter.on(",").splitToList(notifyChannel);
            }
        }
        return notifyChannelList;
    }

    public void setNotifyChannelList(List<String> notifyChannelList) {
        this.notifyChannelList = notifyChannelList;
    }
}
