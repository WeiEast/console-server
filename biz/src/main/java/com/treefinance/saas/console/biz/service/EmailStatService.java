package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.EmailStatRequest;

/**
 * @author chengtong
 * @date 18/3/15 15:21
 */
public interface EmailStatService {


    /**
     * 查询邮件监控日表的列表
     *
     * */
    Object queryEmailMonitorDayAccessList(EmailStatRequest request);

    /**
     * 查询邮件监控日表的列表
     *
     * */
    Object queryEmailMonitorDayAccessListDetail(EmailStatRequest request);

    /**
     * 查询邮件监控日表的列表
     *
     * */
    Object queryEmailSupportList(EmailStatRequest request);

}
