package com.treefinance.saas.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chengtong
 * @date 18/6/12 19:56
 */
public class SaasWorkerRequest extends PageRequest{

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
