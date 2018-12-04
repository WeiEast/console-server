package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.vo.MerchantFlowAllotVO;
import com.treefinance.saas.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/9/28.
 */
public interface MerchantFlowConfigService {

    List<MerchantFlowConfigVO> getList();


    void init();


    SaasResult<Map<String, Object>> queryMerchantAllotVO(com.treefinance.saas.knife.request.PageRequest
                                                                 pageRequest);

    SaasResult<Boolean> updateMerchantAllot(MerchantFlowAllotVO merchantFlowAllotVO);

}
