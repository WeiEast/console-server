package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.config.RawdataDomainConfig;
import com.treefinance.saas.management.console.common.domain.dto.HttpResponseResult;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCrawlerConfigParamVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import com.treefinance.saas.merchant.facade.request.common.PageRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizLicenseRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateAppBizLicenseRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateLicenseQuotaRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateLicenseTrafficRequest;
import com.treefinance.saas.merchant.facade.result.common.BaseResult;
import com.treefinance.saas.merchant.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantAppLicenseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppBizLicenseFacade;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizLicenseServiceImpl implements AppBizLicenseService {

    private static final Logger logger = LoggerFactory.getLogger(AppBizLicenseServiceImpl.class);

    @Autowired
    private VariableMessageNotifyService variableMessageNotifyService;
    @Resource
    private AppBizLicenseFacade appBizLicenseFacade;
    @Resource
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Autowired
    private DiamondConfig diamondConfig;

    @Override
    public List<AppBizLicenseVO> selectBizLicenseByAppIdBizType(AppBizLicenseRequest request) {
        Assert.notNull(request.getAppId(), "appId不能为空!");
        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();

        QueryAppBizLicenseRequest queryAppBizLicenseRequest = new QueryAppBizLicenseRequest();
        queryAppBizLicenseRequest.setAppId(request.getAppId());
        queryAppBizLicenseRequest.setBizType(request.getBizType());
        MerchantResult<List<AppBizLicenseResult>> result;

        try {
            result = appBizLicenseFacade.queryAppBizLicense(queryAppBizLicenseRequest);
        } catch (RpcException e) {
            logger.error("获取商户授权失败，错误信息：{}", e.getMessage());
            return appBizLicenseVOList;
        }

        if (result.isSuccess()) {
            List<AppBizLicenseResult> list = result.getData();

            appBizLicenseVOList = BeanUtils.convertList(list, AppBizLicenseVO.class);

        }

        return appBizLicenseVOList;

    }

    @Override
    public Boolean updateAppBizLicense(AppBizLicenseVO request) {
        Assert.notNull(request.getAppId(), "appId不能为空");
        Assert.notNull(request.getBizType(), "bizType不能为空");

        UpdateAppBizLicenseRequest updateAppBizLicenseRequest = new UpdateAppBizLicenseRequest();
        BeanUtils.copyProperties(request, updateAppBizLicenseRequest);
        MerchantResult<BaseResult> result;
        try {
            result = appBizLicenseFacade.updateAppBizLicense(updateAppBizLicenseRequest);
        } catch (RpcException e) {
            logger.error("更新appBizLicense失败，错误信息：{}", e.getMessage());
            return false;
        }

        if (result.isSuccess()) {
            // 发送配置变更消息
            variableMessageNotifyService.sendVariableMessage("merchant-license", "update", request.getAppId());
        }
        return result.isSuccess();
    }

    @Override
    public List<AppBizLicenseVO> selectQuotaByAppIdBizType(AppBizLicenseRequest request) {
        Assert.notNull(request.getAppId(), "appId不能为空!");

        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();

        MerchantResult<List<AppBizLicenseResult>> result = null;
        QueryAppBizLicenseRequest queryAppBizLicenseRequest = new QueryAppBizLicenseRequest();
        queryAppBizLicenseRequest.setAppId(request.getAppId());
        queryAppBizLicenseRequest.setBizType(request.getBizType());

        try {
            result = appBizLicenseFacade.queryAppBizLicenseQuota(queryAppBizLicenseRequest);
        } catch (RpcException e) {
            logger.error("获取商户额度列表失败，错误信息：{}", e.getMessage());
            return appBizLicenseVOList;
        }

        if (result.isSuccess()) {
            List<AppBizLicenseResult> list = result.getData();
            appBizLicenseVOList = BeanUtils.convertList(list, AppBizLicenseVO.class);
        }

        logger.info(JSON.toJSONString(appBizLicenseVOList));

        return appBizLicenseVOList;
    }

    @Override
    public Boolean updateQuota(AppBizLicenseVO request) {
        Assert.notNull(request.getAppId(), "appId不能为空");
        Assert.notNull(request.getBizType(), "bizType不能为空");
        Assert.notNull(request.getDailyLimit(), "dailyLimit不能为空");

        UpdateLicenseQuotaRequest updateLicenseQuotaRequest = new UpdateLicenseQuotaRequest();

        BeanUtils.copyProperties(request, updateLicenseQuotaRequest);

        MerchantResult<BaseResult> result;
        try {
            result = appBizLicenseFacade.updateAppBizLicenseQuota(updateLicenseQuotaRequest);
        } catch (RpcException e) {
            logger.error("更新商户每日限额失败，错误信息：{}", e.getMessage());
            return false;
        }
        return result.isSuccess();

    }

    @Override
    public List<AppBizLicenseVO> selectTrafficByAppIdBizType(AppBizLicenseRequest request) {

        Assert.notNull(request.getAppId(), "appId不能为空!");

        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();

        MerchantResult<List<AppBizLicenseResult>> result;
        QueryAppBizLicenseRequest queryAppBizLicenseRequest = new QueryAppBizLicenseRequest();
        queryAppBizLicenseRequest.setAppId(request.getAppId());
        queryAppBizLicenseRequest.setBizType(request.getBizType());

        try {
            result = appBizLicenseFacade.queryAppBizLicenseTraffic(queryAppBizLicenseRequest);
        } catch (RpcException e) {
            logger.error("获取商户额度列表失败，错误信息：{}", e.getMessage());
            return appBizLicenseVOList;
        }

        if (result.isSuccess()) {
            List<AppBizLicenseResult> list = result.getData();
            appBizLicenseVOList = BeanUtils.convertList(list, AppBizLicenseVO.class);
        }

        logger.info(JSON.toJSONString(appBizLicenseVOList));

        return appBizLicenseVOList;

    }

    @Override
    public Boolean updateTraffic(AppBizLicenseVO request) {
        Assert.notNull(request.getAppId(), "appId不能为空");
        Assert.notNull(request.getBizType(), "bizType不能为空");
        Assert.notNull(request.getTrafficLimit(), "trafficLimit不能为空");
        if (request.getTrafficLimit().compareTo(new BigDecimal(100)) > 0 || request.getTrafficLimit().compareTo(new BigDecimal(0)) < 0) {
            throw new BizException("流量百分比设置有误!");
        }
        UpdateLicenseTrafficRequest updateLicenseTrafficRequest = new UpdateLicenseTrafficRequest();

        BeanUtils.copyProperties(request, updateLicenseTrafficRequest);

        MerchantResult<BaseResult> result;
        try {
            result = appBizLicenseFacade.updateAppBizLicenseTraffic(updateLicenseTrafficRequest);
        } catch (RpcException e) {
            logger.error("更新商户每日限额失败，错误信息：{}", e.getMessage());
            return false;
        }
        return result.isSuccess();
    }

    @Override
    public SaasResult<Map<String, Object>> selectBizLicenseWithpaging(PageRequest request) {

        MerchantResult<List<MerchantAppLicenseResult>> merchantResult = merchantBaseInfoFacade.queryAllMerchantAppLicensePagination(request);
        if (StringUtils.isEmpty(merchantResult.getData())) {
            logger.error("分页查找商户户及商户相关信息失败，错误信息:{}", merchantResult.getRetMsg());
        }
        List<RawdataDomainConfig> list = diamondConfig.getRawDataDomainConfigList();
        Map<String, RawdataDomainConfig> appleMap = new HashMap<>();
        for (RawdataDomainConfig rawdataDomainConfig : list) {
            appleMap.put(rawdataDomainConfig.getSystemSymbol(), rawdataDomainConfig);
        }

        String url = appleMap.get("rawdatacentral").getDomian() + "app/crawler/getList";

        HttpResponseResult httpResponseResult = HttpClientUtils.doPostResult(url, merchantResult.getData());
        logger.info("调用http请求，传回的结果为{}，状态码为{}", httpResponseResult.getResponseBody(), httpResponseResult.getStatusCode());

        JSONObject jsonObject = JSON.parseObject(httpResponseResult.getResponseBody());
        JSONArray data = jsonObject.getJSONArray("data");
        List<AppCrawlerConfigParamVO> voList = data.toJavaList(AppCrawlerConfigParamVO.class);

        com.treefinance.saas.knife.request.PageRequest pageRequest = new com.treefinance.saas.knife.request.PageRequest();
        pageRequest.setPageNumber(request.getPageNum());
        pageRequest.setPageSize(request.getPageSize());
        return Results.newPageResult(pageRequest, merchantResult.getTotalCount(), voList);
    }
}
