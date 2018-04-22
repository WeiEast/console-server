package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantSimpleVO;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/6/21.
 */
public interface MerchantService {

    MerchantBaseVO getMerchantById(Long id);

    SaasResult<Map<String, Object>> getMerchantList(PageRequest request);

    Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO);

    void updateMerchant(MerchantBaseVO merchantBaseVO);

    String resetPassWord(Long id);

    List<MerchantSimpleVO> getMerchantBaseList();

    void resetAppLicenseKey(Long id);

    String autoGenerateAppId();

    String generateCipherTextPassword(String str);

    Result<Boolean> toggleMerchant(String appId,Byte isActive);

}
