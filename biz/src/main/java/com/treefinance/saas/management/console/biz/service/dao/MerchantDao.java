package com.treefinance.saas.management.console.biz.service.dao;

import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;

import java.util.Map;

/**
 * Created by haojiahong on 2017/10/16.
 */
public interface MerchantDao {

    /**
     * 添加商户基本信息以及相关信息
     *
     * @param merchantBaseVO
     */
    Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO);

    /**
     * 更新商户信息
     *
     * @param merchantBaseVO
     */
    void updateMerchant(MerchantBaseVO merchantBaseVO);
}
