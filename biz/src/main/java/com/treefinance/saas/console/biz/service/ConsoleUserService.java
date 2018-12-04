package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.dao.entity.ConsoleUser;

/**
 * Created by haojiahong on 2017/6/23.
 */
public interface ConsoleUserService {

    ConsoleUser getByLoginName(String loginName);

}
