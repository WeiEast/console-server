package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.vo.MerchantFunctionVO;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.MerchantFunctionRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantFunctionResult;

import java.util.List;
import java.util.Map;

/**
 * @author 张琰佳
 * @since 2:23 PM 2019/3/5
 */
public interface MerchantFunctionService {

   SaasResult<Integer> insert(MerchantFunctionRequest request);

   SaasResult<Integer> update(MerchantFunctionRequest request);

   MerchantFunctionVO getMerchantFunctionByAppId(MerchantFunctionRequest request);

   SaasResult<Map<String, Object>> queryMerchantFunctionList(PageRequest request);

   SaasResult<Integer> delete(Long id);

   SaasResult<MerchantFunctionResult> searchByAppId(Long id);



}
