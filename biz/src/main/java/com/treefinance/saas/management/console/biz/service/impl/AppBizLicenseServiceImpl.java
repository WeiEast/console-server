package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppBizLicenseRequest;
import com.treefinance.saas.merchant.center.facade.request.console.UpdateAppBizLicenseRequest;
import com.treefinance.saas.merchant.center.facade.request.console.UpdateLicenseQuotaRequest;
import com.treefinance.saas.merchant.center.facade.request.console.UpdateLicenseTrafficRequest;
import com.treefinance.saas.merchant.center.facade.result.common.BaseResult;
import com.treefinance.saas.merchant.center.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppBizLicenseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
}
