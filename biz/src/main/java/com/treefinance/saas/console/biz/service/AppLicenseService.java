package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.result.grapsever.AppLicenseResult;
import com.treefinance.saas.merchant.facade.result.grapsever.CallbackLicenseResult;

import java.util.Map;

/**
 * @author Jerry
 * @date 2018/11/15 16:04
 */
public interface AppLicenseService {

    /**
     * 根据传入的appId查找对应的DataSecretKey.
     *
     * @param appId 第三方的appId
     */
    String getAppDataSecretKeyByAppId(String appId);

    /**
     * 根据传入的appId查找对应的app许可
     *
     * @param appId 第三方的appId
     */
    AppLicenseResult getAppLicenseByAppId(String appId);

    /**
     * 根据传入的<code>callbackConfigId</code>查找对应的回调DataSecretKey
     * 
     * @param callbackConfigId 回调配置ID
     */
    CallbackLicenseResult getCallbackLicenseByCallbackConfigId(Integer callbackConfigId);

    SaasResult<Map<String, Object>> getAppLicenseList(PageRequest request);

    String getCallbackDataSecretKeyByCallbackConfigId(Integer callbackConfigId);
}
