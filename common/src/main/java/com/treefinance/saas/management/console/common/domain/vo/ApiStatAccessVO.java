package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/7/10.
 */
public class ApiStatAccessVO implements Serializable {
    private static final long serialVersionUID = 2222324336110605727L;

    private Date dataTime;

    private Integer totalCount;

    private Integer avgResponseTime;

    private Integer http4xxCount;

    private Integer http2xxCount;

    private Integer http5xxCount;

    private Integer httpErrorCount;

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getAvgResponseTime() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(Integer avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public Integer getHttp4xxCount() {
        return http4xxCount;
    }

    public void setHttp4xxCount(Integer http4xxCount) {
        this.http4xxCount = http4xxCount;
    }

    public Integer getHttp2xxCount() {
        return http2xxCount;
    }

    public void setHttp2xxCount(Integer http2xxCount) {
        this.http2xxCount = http2xxCount;
    }

    public Integer getHttp5xxCount() {
        return http5xxCount;
    }

    public void setHttp5xxCount(Integer http5xxCount) {
        this.http5xxCount = http5xxCount;
    }

    public Integer getHttpErrorCount() {
        return httpErrorCount;
    }

    public void setHttpErrorCount(Integer httpErrorCount) {
        this.httpErrorCount = httpErrorCount;
    }
}
