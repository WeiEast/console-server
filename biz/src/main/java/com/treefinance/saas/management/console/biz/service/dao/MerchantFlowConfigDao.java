package com.treefinance.saas.management.console.biz.service.dao;

import com.treefinance.saas.assistant.config.model.ConfigUpdateModel;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/10/16.
 */
public interface MerchantFlowConfigDao {

    List<ConfigUpdateModel> batchUpdate(List<MerchantFlowConfigVO> list);
}
