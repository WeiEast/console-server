package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.Map;

public interface DsDataApiRawResultSerivce {

    /**
     * 分页查询dsDataApiRawResult信息
     *
     * @param request
     * @return
     */
    SaasResult<Map<String, Object>> findPageByExample(DsDataApiRequest request);
}
