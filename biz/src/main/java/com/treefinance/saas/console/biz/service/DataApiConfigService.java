package com.treefinance.saas.console.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.treefinance.saas.console.common.domain.vo.DataTypeVO;
import com.treefinance.saas.knife.result.SaasResult;

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
