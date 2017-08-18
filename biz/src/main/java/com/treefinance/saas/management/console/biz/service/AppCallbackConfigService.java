package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.dao.entity.AppCallbackConfig;

import java.util.List;
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

    /**
     * 获取回调管理下的服务权限类型
     *
     * @return
     */
    List<AppBizTypeVO> getCallbackBizList();

    /**
     * 验证url是否能够通过
     *
     * @param url
     * @return
     */
    Boolean testUrl(String url);

}
