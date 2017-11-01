package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.management.console.common.result.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haojiahong
 * @date 2017/11/1
 */
public class OperatorStatRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -2966509063662500226L;

    private String groupCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Date getDataDate() {
        return dataDate;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
