package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.DsDataApiRequest;

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
