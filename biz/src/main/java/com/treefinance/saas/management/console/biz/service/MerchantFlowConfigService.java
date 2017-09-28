package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/9/28.
 */
public interface MerchantFlowConfigService {

    List<MerchantFlowConfigVO> getList();

    void batchUpdate(List<MerchantFlowConfigVO> list);

    void init();

}
