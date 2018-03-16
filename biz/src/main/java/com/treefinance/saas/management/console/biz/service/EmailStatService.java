package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.EmailStatRequest;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;

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
