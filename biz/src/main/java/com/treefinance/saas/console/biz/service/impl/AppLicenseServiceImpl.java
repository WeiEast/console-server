/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.biz.service.AppLicenseService;
import com.treefinance.saas.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.exception.RpcServiceException;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.grapserver.GetAppLicenseRequest;
import com.treefinance.saas.merchant.facade.request.grapserver.GetCallbackLicenseRequest;
import com.treefinance.saas.merchant.facade.result.console.AppLicenseVOResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.result.grapsever.AppLicenseResult;
import com.treefinance.saas.merchant.facade.result.grapsever.CallbackLicenseResult;
import com.treefinance.saas.merchant.facade.service.AppLicenseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * @author Jerry
 * @date 19:14 25/04/2017
 */
@Component
public class AppLicenseServiceImpl extends AbstractService implements AppLicenseService {

    private static final Logger logger = LoggerFactory.getLogger(AppLicenseServiceImpl.class);

    @Resource
    private AppLicenseFacade appLicenseFacade;

    @Override
    public String getAppDataSecretKeyByAppId(String appId) {
        AppLicenseResult license = getAppLicenseByAppId(appId);

        return license.getDataSecretKey();
    }

    @Override
    public AppLicenseResult getAppLicenseByAppId(String appId) {
        logger.info("根据appId={}查询秘钥key", appId);
        GetAppLicenseRequest request = new GetAppLicenseRequest();
        request.setAppId(appId);
        MerchantResult<AppLicenseResult> result = appLicenseFacade.getAppLicense(request);
        if (!result.isSuccess()) {
            throw new RpcServiceException("请求商户[appId:" + appId + "]的app-license失败：" + result.getRetMsg());
        }

        logger.info("根据appId={}查询秘钥key结果为result={}", appId, JSON.toJSONString(result.getData()));

        return result.getData();
    }

    @Override
    public String getCallbackDataSecretKeyByCallbackConfigId(Integer callbackConfigId) {
        CallbackLicenseResult license = getCallbackLicenseByCallbackConfigId(callbackConfigId);

        return license.getDataSecretKey();
    }

    @Override
    public CallbackLicenseResult getCallbackLicenseByCallbackConfigId(Integer callbackConfigId) {
        GetCallbackLicenseRequest request = new GetCallbackLicenseRequest();
        request.setCallbackId(callbackConfigId);
        MerchantResult<CallbackLicenseResult> result = appLicenseFacade.getCallbackLicense(request);
        if (!result.isSuccess()) {
            throw new RpcServiceException("请求回调[callbackConfigId: " + callbackConfigId + "]的license失败：" + result.getRetMsg());
        }

        logger.info("根据Id={}查询回调的license结果为result={}", callbackConfigId, JSON.toJSONString(result.getData()));

        return result.getData();
    }

    @Override
    public SaasResult<Map<String, Object>> getAppLicenseList(PageRequest request) {
        com.treefinance.saas.merchant.facade.request.common.PageRequest pageRequest = new com.treefinance.saas.merchant.facade.request.common.PageRequest();

        pageRequest.setPageNum(request.getPageNumber());
        pageRequest.setPageSize(request.getPageSize());

        MerchantResult<List<AppLicenseVOResult>> result = appLicenseFacade.queryAppLicenseVo(pageRequest);
        if (!result.isSuccess()) {
            throw new RpcServiceException("请求商户中心的app-license列表失败：" + result.getRetMsg());
        }

        logger.info("商户中心返回数据：{}", result.getData());
        List<AppLicenseVO> appLicenseVOList = this.convert(result.getData(), AppLicenseVO.class);

        return Results.newPageResult(request, result.getTotalCount(), appLicenseVOList);
    }

}
