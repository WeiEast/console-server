package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.dao.entity.ConsoleUser;

/**
 * Created by haojiahong on 2017/6/23.
 */
public interface ConsoleUserService {

    ConsoleUser getByLoginName(String loginName);

}
