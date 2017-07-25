package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.Map;

/**
 * 商户回调配置
 * Created by haojiahong on 2017/7/21.
 */
public interface AppCallbackConfigService {

    /**
     * 查询app_callback_config
     *
     * @param request
     * @return
     */
    Result<Map<String, Object>> getList(PageRequest request);

    /**
     * 根据id查询app_callback_config
     *
     * @param id
     * @return
     */
    AppCallbackConfigVO getAppCallbackConfigById(Integer id);

    /**
     * 添加
     *
     * @param appCallbackConfigVO
     * @return
     */
    Integer add(AppCallbackConfigVO appCallbackConfigVO);

    /**
     * 更新
     *
     * @param appCallbackConfigVO
     */
    void update(AppCallbackConfigVO appCallbackConfigVO);

    /**
     * 删除
     *
     * @param id
     */
    void deleteAppCallbackConfigById(Integer id);
}