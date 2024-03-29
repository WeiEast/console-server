package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
import com.treefinance.saas.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.console.biz.service.MerchantService;
import com.treefinance.saas.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.console.util.SystemUtils;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.facade.request.console.AddAppBizLicenseRequest;
import com.treefinance.saas.merchant.facade.request.console.AddMerchantBaseRequest;
import com.treefinance.saas.merchant.facade.request.console.GetMerchantByIdRequest;
import com.treefinance.saas.merchant.facade.request.console.MerchantStatusChangeRequest;
import com.treefinance.saas.merchant.facade.request.console.ResetKeyRequest;
import com.treefinance.saas.merchant.facade.request.console.ResetPwdRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateMerchantResult;
import com.treefinance.saas.merchant.facade.result.common.BaseResult;
import com.treefinance.saas.merchant.facade.result.console.AddMerchantResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseInfoResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantBizLicense;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantSimpleResult;
import com.treefinance.saas.merchant.facade.result.console.ResetPwdResult;
import com.treefinance.saas.merchant.facade.result.grapsever.AppLicenseResult;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 商户管理
 * Created by haojiahong on 2017/6/21.
 */
@Component
public class MerchantServiceImpl extends AbstractService implements MerchantService {

    @Resource
    private ISecurityCryptoService iSecurityCryptoService;
    @Autowired
    private DiamondConfig diamondConfig;
    @Autowired
    private VariableMessageNotifyService variableMessageNotifyService;
    @Resource
    private MerchantBaseInfoFacade merchantBaseInfoFacade;

    @Override
    public MerchantBaseVO getMerchantById(Long id) {
        GetMerchantByIdRequest request = new GetMerchantByIdRequest();
        request.setId(id);

        MerchantResult<MerchantBaseInfoResult> result = merchantBaseInfoFacade.getBaseInfoById(request);

        if (!result.isSuccess()) {
            throw new BizException("不存在的商户");
        }
        MerchantBaseInfoResult infoResult = result.getData();
        logger.info("商户中心返回数据：infoResult={}",JSON.toJSONString(infoResult));
        MerchantBaseVO baseVO = new MerchantBaseVO();

        appLicenseVOprocess(infoResult, baseVO);

        List<MerchantBizLicense> list = infoResult.getAppBizLicenseList();

        List<AppBizLicenseVO> appBizLicenseVOList = this.convert(list, AppBizLicenseVO.class);

        baseVO.setAppBizLicenseVOList(appBizLicenseVOList);

        logger.info("返回前端数据：{}",JSON.toJSONString(baseVO));
        return baseVO;
    }

    private void appLicenseVOprocess(MerchantBaseInfoResult infoResult, MerchantBaseVO baseVO) {
        this.copyProperties(infoResult, baseVO);

        AppLicenseResult appLicenseResult = infoResult.getAppLicenseVO();
        if(appLicenseResult == null){
            logger.info("appLicense 为空");
        }else {
            AppLicenseVO appLicenseVO = new AppLicenseVO();
            this.copyProperties(appLicenseResult, appLicenseVO);
            baseVO.setAppLicenseVO(appLicenseVO);
        }
    }


    @Override
    public SaasResult<Map<String, Object>> getMerchantList(PageRequest request) {
        List<MerchantBaseVO> merchantBaseVOList = Lists.newArrayList();

        com.treefinance.saas.merchant.facade.request.common.PageRequest pageRequest = new com.treefinance.saas.merchant.facade.request.common.PageRequest();

        pageRequest.setPageNum(request.getPageNumber());
        pageRequest.setPageSize(request.getPageSize());


        MerchantResult<List<MerchantBaseResult>> result = merchantBaseInfoFacade.queryMerchantBaseList(pageRequest);
        if (!result.isSuccess()) {
            logger.error("获取商户列表失败：错误信息：{}", result.getRetMsg());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        if (!CollectionUtils.isEmpty(result.getData())) {
            merchantBaseVOList = this.convertList(result.getData(), MerchantBaseVO.class);
        }

        return Results.newPageResult(request, result.getTotalCount(), merchantBaseVOList);
    }

    @Override
    public Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO) {
        logger.info("添加商户信息 merchantBaseVO={}", JSON.toJSONString(merchantBaseVO));
        if (StringUtils.isBlank(merchantBaseVO.getAppId())) {
            throw new BizException("appId不能为空!");
        }
        if (StringUtils.isBlank(merchantBaseVO.getAppName())) {
            throw new BizException("app名称不能为空!");
        }
        String pattern = "^" + diamondConfig.getAppIdEnvironmentPrefix() + "_" + "[0-9a-zA-Z]{16}";
        String appId = StringUtils.deleteWhitespace(merchantBaseVO.getAppId());
        boolean isMatch = Pattern.matches(pattern, appId);
        if (!isMatch) {
            throw new BizException("appId格式有误!需满足:" + diamondConfig.getAppIdEnvironmentPrefix() + "_16位数字字母字符串");
        }

        AddMerchantBaseRequest request = new AddMerchantBaseRequest();
        this.copyProperties(merchantBaseVO, request);
        List<AddAppBizLicenseRequest> licenseRequests = new ArrayList<>();
        for (AppBizLicenseVO vo : merchantBaseVO.getAppBizLicenseVOList()) {
            AddAppBizLicenseRequest addAppBizLicenseRequest = new AddAppBizLicenseRequest();
            this.copyProperties(vo, addAppBizLicenseRequest);
            licenseRequests.add(addAppBizLicenseRequest);
        }
        request.setAppBizLicenseVOList(licenseRequests);
        logger.info("更新信息：{}", JSON.toJSONString(request));
        MerchantResult<AddMerchantResult> result;
        try {
            result = merchantBaseInfoFacade.addMerchant(request);
            if (result.isSuccess()) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("merchantId", result.getData().getMerchantId());
                map.put("plainTextPassword", result.getData().getPlainTextPassword());
                return map;
            }
        } catch (Exception e) {
            logger.error("新增商户失败", e);
        }


        return null;
    }

    @Override
    public void updateMerchant(MerchantBaseVO merchantBaseVO) {
        logger.info("更新商户信息 merchantBaseVO={}", JSON.toJSONString(merchantBaseVO));
        if (StringUtils.isBlank(merchantBaseVO.getAppName())) {
            throw new BizException("app名称不能为空!");
        }
        Assert.notNull(merchantBaseVO.getId(), "id不能为空");

        AddMerchantBaseRequest request = new AddMerchantBaseRequest();
        this.copyProperties(merchantBaseVO, request);
        logger.info("更新商户信息，更新信息：request={}", JSON.toJSONString(request));

        MerchantResult<UpdateMerchantResult> result = merchantBaseInfoFacade.updateMerchant(request);

        if (result.isSuccess()) {
            variableMessageNotifyService.sendVariableMessage("merchant", "update", result.getData().getAppId());
        }
    }

    @Override
    public String resetPassWord(Long id) {

        String newPwd = SystemUtils.generatePassword();

        ResetPwdRequest resetPwdRequest = new ResetPwdRequest();
        resetPwdRequest.setId(id);
        resetPwdRequest.setNewPwd(newPwd);
        MerchantResult<ResetPwdResult> result = merchantBaseInfoFacade.resetPwd(resetPwdRequest);
        if (!result.isSuccess()) {
            logger.error("重置密码失败，错误信息{}", result.getRetMsg());
        }

        return result.getData().getPlainTextPwd();
    }

    @Override
    public List<MerchantSimpleVO> getMerchantBaseList() {

        List<MerchantSimpleVO> merchantSimpleVOList = Lists.newArrayList();

        MerchantResult<List<MerchantSimpleResult>> result = merchantBaseInfoFacade.querySimpleMerchantSimple(new
                BaseRequest());

        logger.info("获取简单列表 result={}",JSON.toJSONString(result));

        if (!result.isSuccess()) {
            logger.error("获取简单列表失败，错误信息：{}", result.getRetMsg());
            return merchantSimpleVOList;
        }

        merchantSimpleVOList = this.convertList(result.getData(), MerchantSimpleVO.class);
        return merchantSimpleVOList;
    }

    @Override
    public void resetAppLicenseKey(Long id) {
        ResetKeyRequest resetKeyRequest = new ResetKeyRequest();
        resetKeyRequest.setId(id);
        MerchantResult<BaseResult> merchantResult = merchantBaseInfoFacade.resetKey(resetKeyRequest);

        if(!merchantResult.isSuccess()){
            logger.error("重置Key失败，错误信息：{}",merchantResult.getRetMsg());
            throw new BizException("重置key失败，错误信息："+merchantResult.getRetMsg());
        }

    }

    @Override
    public String autoGenerateAppId() {
        String prefix = diamondConfig.getAppIdEnvironmentPrefix();
        return prefix + "_" + SystemUtils.generateAppId();
    }

    @Override
    public String generateCipherTextPassword(String str) {
        return iSecurityCryptoService.encrypt(str, EncryptionIntensityEnum.NORMAL);
    }

    @Override
    public SaasResult<Boolean> toggleMerchant(String appId, Byte isActive) {

        MerchantStatusChangeRequest request = new MerchantStatusChangeRequest();
        request.setOpType(new Integer(isActive));
        request.setAppId(appId);
        try{
            logger.info("{}，request：{}",isActive==0?"禁用商户":"启用商户",JSON.toJSONString(request));
            MerchantResult<BaseResult> rpcResult= merchantBaseInfoFacade.merchantActiveChange(request);
            logger.info("商户中心返回数据：{}",JSON.toJSONString(rpcResult));
            if(rpcResult.isSuccess()){
                return Results.newSuccessResult(true);
            }
            return Results.newFailedResult(false, CommonStateCode.FAILURE);
        }catch (RpcException e){
            logger.error("启用或禁用商户失败：{}",e.getMessage());
            return Results.newFailedResult(false, CommonStateCode.FAILURE);
        }

    }
}
