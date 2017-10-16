package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;

/**
 * 更新添加有事务操作,从而需要再抽象一层.
 * Created by haojiahong on 2017/10/16.
 */
public interface AppCallbackConfigExtService {

    Integer addCallbackConfig(AppCallbackConfigVO appCallbackConfigVO);

    void updateCallbackConfig(AppCallbackConfigVO appCallbackConfigVO);

    void deleteAppCallbackConfigById(Integer id);
}
