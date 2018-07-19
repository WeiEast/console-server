package com.treefinance.saas.management.console.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.vo.DataTypeVO;

import java.util.List;

public interface DataApiConfigService {

    /**
     * 获取DataServer层distinct type的类型
     *
     * @return key: [dataApiName]-[type]  blacklist-1
     * val:desc    华道黑名单接口
     */

    SaasResult<List<DataTypeVO>> getDsApiConfigType();

    SaasResult<List<JSONObject>> getDsAppid(String dataApiNameType);
}
