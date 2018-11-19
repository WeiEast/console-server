package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppCallbackConfigService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackBizVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackDataTypeVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import com.treefinance.saas.merchant.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.facade.request.console.*;
import com.treefinance.saas.merchant.facade.result.common.BaseResult;
import com.treefinance.saas.merchant.facade.result.console.*;
import com.treefinance.saas.merchant.facade.service.AppBizTypeFacade;
import com.treefinance.saas.merchant.facade.service.AppCallbackConfigFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/21.
 */
@Service
public class AppCallbackConfigServiceImpl implements AppCallbackConfigService {

    private static final Logger logger = LoggerFactory.getLogger(AppCallbackConfigServiceImpl.class);

    @Resource
    private AppCallbackConfigFacade appCallbackConfigFacade;
    @Resource
    private AppBizTypeFacade appBizTypeFacade;
    @Autowired
    private VariableMessageNotifyService variableMessageNotifyService;

    @Override
    public SaasResult<Map<String, Object>> getList(PageRequest request) {
        com.treefinance.saas.merchant.facade.request.common.PageRequest pageRequest =
            new com.treefinance.saas.merchant.facade.request.common.PageRequest();

        pageRequest.setPageNum(request.getPageNumber());
        pageRequest.setPageSize(request.getPageSize());
        MerchantResult<List<AppCallbackConfigResult>> result =
            appCallbackConfigFacade.queryAppCallbackConfig(pageRequest);

        if (!result.isSuccess()) {
            logger.error("获取配置列表失败，错误信息：{}", result.getRetMsg());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AppCallbackConfigResult> callbackConfigResults = result.getData();

        List<AppCallbackConfigVO> returnList = Lists.newArrayList();

        for (AppCallbackConfigResult callbackConfigResult : callbackConfigResults) {
            AppCallbackConfigVO vo = new AppCallbackConfigVO();
            BeanUtils.copyProperties(callbackConfigResult, vo);

            completeBizTypes(vo, callbackConfigResult);
            completeDataType(vo, callbackConfigResult);

            returnList.add(vo);
        }
        logger.info("商户中心返回数据：{}", JSON.toJSONString(result));
        return Results.newPageResult(request, result.getTotalCount(), returnList);
    }

    @Override
    public AppCallbackConfigVO getAppCallbackConfigById(Integer id) {

        GetAppCallbackConfigRequest request = new GetAppCallbackConfigRequest();
        request.setId(id.longValue());

        MerchantResult<AppCallbackConfigResult> result = appCallbackConfigFacade.getAppCallbackConfigResult(request);

        if (!result.isSuccess()) {
            logger.error("获取回调配置记录失败，错误信息：{}", result.getRetMsg());
            return null;
        }

        AppCallbackConfigVO vo = new AppCallbackConfigVO();

        AppCallbackConfigResult callbackConfigResult = result.getData();

        BeanUtils.copyProperties(callbackConfigResult, vo);

        logger.info(JSON.toJSONString(vo));

        completeBizTypes(vo, callbackConfigResult);
        completeDataType(vo, callbackConfigResult);

        logger.info(JSON.toJSONString(vo));

        return vo;
    }

    /**
     * 从merchant-center过来的数据不全，补全部分数据
     */
    private void completeDataType(AppCallbackConfigVO vo, AppCallbackConfigResult callbackConfigResult) {
        AppCallbackDataTypeResult dataTypeResult = callbackConfigResult.getDataTypeVO();
        AppCallbackDataTypeVO dataTypeVO = new AppCallbackDataTypeVO();
        dataTypeVO.setText(ECallBackDataType.getText(dataTypeResult.getCode()));
        dataTypeVO.setCode(dataTypeResult.getCode());
        vo.setDataTypeVO(dataTypeVO);
    }

    private void completeBizTypes(AppCallbackConfigVO vo, AppCallbackConfigResult callbackConfigResult) {
        List<AppCallbackBizVO> bizTypes = new ArrayList<>();
        List<AppCallbackBizSimpleResult> simpleResults = callbackConfigResult.getBizTypes();
        for (AppCallbackBizSimpleResult simpleResult : simpleResults) {
            AppCallbackBizVO bizVO = new AppCallbackBizVO();
            bizVO.setBizType(simpleResult.getBizType());
            bizVO.setBizName(wrapBizTypeName(bizVO.getBizType()));
            bizTypes.add(bizVO);
        }
        vo.setBizTypes(bizTypes);
    }

    @Override
    public Integer add(AppCallbackConfigVO appCallbackConfigVO) {

        if (StringUtils.isBlank(appCallbackConfigVO.getAppId())) {
            throw new BizException("appId不能为空");
        }
        if (StringUtils.isBlank(appCallbackConfigVO.getUrl())) {
            throw new BizException("回调地址URL不能为空");
        }
        if (CollectionUtils.isEmpty(appCallbackConfigVO.getBizTypes())) {
            throw new BizException("业务类型不能为空");
        }

        AddAppCallbackConfigRequest request = new AddAppCallbackConfigRequest();
        BeanUtils.copyProperties(appCallbackConfigVO, request);

        List<AppCallbackBizVO> bizVOList = appCallbackConfigVO.getBizTypes();
        List<AddAppCallbackBizRequest> list = getAddAppCallbackBizRequests(bizVOList);
        request.setBizTypes(list);

        AppCallbackDataTypeVO dataTypeVO = appCallbackConfigVO.getDataTypeVO();
        AppCallbackDataTypeRequest dataType = getAppCallbackDataTypeRequest(dataTypeVO);
        request.setDataTypeVO(dataType);

        MerchantResult<AddAppCallbackConfigResult> result = appCallbackConfigFacade.addAppCallbackConfig(request);

        if (result.isSuccess()) {
            variableMessageNotifyService.sendVariableMessage("merchant-callback", "create",
                appCallbackConfigVO.getAppId());
            return result.getData().getConfigId();
        }

        return null;
    }

    @Override
    public void update(AppCallbackConfigVO appCallbackConfigVO) {
        logger.info("更新回调配置，{}", JSON.toJSONString(appCallbackConfigVO));
        UpdateCallbackConfigRequest request = new UpdateCallbackConfigRequest();

        BeanUtils.copyProperties(appCallbackConfigVO, request);

        List<AppCallbackBizVO> bizVOList = appCallbackConfigVO.getBizTypes();
        List<AddAppCallbackBizRequest> list = getAddAppCallbackBizRequests(bizVOList);
        request.setBizTypes(list);

        AppCallbackDataTypeVO dataTypeVO = appCallbackConfigVO.getDataTypeVO();

        AppCallbackDataTypeRequest dataType = getAppCallbackDataTypeRequest(dataTypeVO);
        request.setDataTypeVO(dataType);

        MerchantResult<BaseResult> result;
        try {
            result = appCallbackConfigFacade.updateAppCallbackConfig(request);

            if (!result.isSuccess()) {
                logger.error("更新回调配置信息失败，错误信息：{}", result);
                return;
            }
        } catch (RpcException e) {
            logger.error("调用商户中心失败", e);
            return;
        }

        variableMessageNotifyService.sendVariableMessage("merchant-callback", "update", appCallbackConfigVO.getAppId());
    }

    private AppCallbackDataTypeRequest getAppCallbackDataTypeRequest(AppCallbackDataTypeVO dataTypeVO) {
        AppCallbackDataTypeRequest dataType = new AppCallbackDataTypeRequest();
        if (dataTypeVO != null) {
            dataType.setCode(dataTypeVO.getCode());
            dataType.setText(dataTypeVO.getText());
        }
        return dataType;
    }

    private List<AddAppCallbackBizRequest> getAddAppCallbackBizRequests(List<AppCallbackBizVO> bizVOList) {
        List<AddAppCallbackBizRequest> list = new ArrayList<>();
        if (bizVOList != null && !bizVOList.isEmpty()) {
            for (AppCallbackBizVO vo : bizVOList) {
                AddAppCallbackBizRequest callbackBizRequest = new AddAppCallbackBizRequest();
                BeanUtils.copyProperties(vo, callbackBizRequest);
                list.add(callbackBizRequest);
            }
        }
        return list;
    }

    @Override
    public void deleteAppCallbackConfigById(Integer id) {

        AppCallbackConfigVO appCallbackConfigVO = getAppCallbackConfigById(id);
        if (appCallbackConfigVO == null) {
            logger.error("删除回调配置失败，该商户配置不存在");
            return;
        }
        DeleteAppCallbackConfigRequest request = new DeleteAppCallbackConfigRequest();
        request.setId(id.longValue());

        MerchantResult<BaseResult> result;
        try {
            result = appCallbackConfigFacade.deleteAppCallbackConfigResult(request);

            if (result.isSuccess()) {
                variableMessageNotifyService.sendVariableMessage("merchant-callback", "delete",
                    appCallbackConfigVO.getAppId());
            }
        } catch (RpcException e) {
            logger.error("调用商户中心 删除回调配置接口失败，错误信息：{}", e.getMessage());
        }
    }

    @Override
    public List<AppBizTypeVO> getCallbackBizList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        AppBizTypeVO allVO = new AppBizTypeVO();
        allVO.setBizType((byte)0);
        allVO.setBizName("全部");
        appBizTypeVOList.add(0, allVO);
        MerchantResult<List<AppBizTypeSimpleResult>> result = null;

        try {
            result = appBizTypeFacade.queryAppBizTypeSimple(new BaseRequest());
        } catch (RpcException e) {
            logger.error("获取回调配置业务类型列表失败：错误信息{}", e.getMessage());
            return appBizTypeVOList;
        }

        if (result.isSuccess()) {
            List<AppBizTypeSimpleResult> list = result.getData();
            for (AppBizTypeSimpleResult simpleResult : list) {
                AppBizTypeVO vo = new AppBizTypeVO();
                BeanUtils.copyProperties(simpleResult, vo);
                appBizTypeVOList.add(vo);
            }
        }
        // 在回调中需要增加一个全部类型

        return appBizTypeVOList;
    }

    @Override
    public Boolean testUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        boolean flag = urlValidator.isValid(url);
        if (flag) {
            try {
                HttpClientUtils.doOptions(url);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AppCallbackDataTypeVO> getCallbackDataTypeList() {
        List<AppCallbackDataTypeVO> voList = Lists.newArrayList();
        for (ECallBackDataType item : ECallBackDataType.values()) {
            AppCallbackDataTypeVO vo = new AppCallbackDataTypeVO();
            vo.setCode(item.getCode());
            vo.setText(item.getText());
            voList.add(vo);
        }
        return voList;
    }

    private String wrapBizTypeName(Byte bizType) {
        if (bizType == 0) {
            return "全部";
        } else {
            return EBizType.getName(bizType);
        }
    }

}
