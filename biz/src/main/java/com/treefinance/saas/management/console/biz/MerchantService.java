package com.treefinance.saas.management.console.biz;

import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/6/21.
 */
public interface MerchantService {

    MerchantBaseVO getMerchantById(Long id);

    List<MerchantBaseVO> getMerchantList();

    Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO);

    void updateMerchant(MerchantBaseVO merchantBaseVO, Long id);
}
