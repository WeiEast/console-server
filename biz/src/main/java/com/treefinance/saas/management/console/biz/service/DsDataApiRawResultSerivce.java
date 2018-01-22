package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.Map;

public interface DsDataApiRawResultSerivce {

    /**
     * 分页查询dsDataApiRawResult信息
     *
     * @param request
     * @return
     */
    Result<Map<String, Object>> findPageByExample(DsDataApiRequest request);
}
