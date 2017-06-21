package com.treefinance.saas.management.console.biz;

import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/6/21.
 */
public interface MerchantService {

    List<MerchantBaseVO> getMerchantList();

    void addMerchant(MerchantBaseVO merchantBaseVO);

}
