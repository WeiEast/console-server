package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.management.console.biz.service.ConsoleUserService;
import com.treefinance.saas.management.console.dao.entity.ConsoleUser;
import com.treefinance.saas.management.console.dao.entity.ConsoleUserCriteria;
import com.treefinance.saas.management.console.dao.mapper.ConsoleUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by haojiahong on 2017/6/23.
 */
@Service
public class ConsoleUserServiceImpl implements ConsoleUserService {

    @Autowired
    private ConsoleUserMapper consoleUserMapper;

    @Override
    public ConsoleUser getByLoginName(String loginName) {
        ConsoleUserCriteria criteria = new ConsoleUserCriteria();
        criteria.createCriteria().andLoginNameEqualTo(loginName);
        List<ConsoleUser> consoleUserList = consoleUserMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(consoleUserList)) {
            return null;
        }
        return consoleUserList.get(0);
    }
}
