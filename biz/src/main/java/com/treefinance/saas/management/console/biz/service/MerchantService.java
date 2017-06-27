package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.Map;

/**
 * Created by haojiahong on 2017/6/21.
 */
public interface MerchantService {

    MerchantBaseVO getMerchantById(Long id);

    Result<Map<String, Object>> getMerchantList(PageRequest request);

    Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO);

    void updateMerchant(MerchantBaseVO merchantBaseVO);

    String resetPassWord(Long id);
}
