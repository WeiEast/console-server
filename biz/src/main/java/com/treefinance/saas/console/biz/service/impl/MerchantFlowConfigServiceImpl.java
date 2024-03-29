package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.treefinance.commonservice.uid.UidService;
import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
import com.treefinance.saas.console.biz.enums.EServiceTagEnum;
import com.treefinance.saas.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.console.common.domain.vo.MerchantFlowAllotVO;
import com.treefinance.saas.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.console.common.domain.vo.MerchantFlowEnvQuotaVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.facade.request.common.PageRequest;
import com.treefinance.saas.merchant.facade.request.console.BatchUpdateFlowRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateMerchantFlowAllotRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateMerchantFlowAllotSubRequest;
import com.treefinance.saas.merchant.facade.request.console.UpdateMerchantFlowRequest;
import com.treefinance.saas.merchant.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantFlowAllotResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantSimpleResult;
import com.treefinance.saas.merchant.facade.result.gateway.MerchantFlowConfigResult;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.merchant.facade.service.MerchantFlowConfigFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/9/28.
 */
@Service
public class MerchantFlowConfigServiceImpl extends AbstractService implements MerchantFlowConfigService {

    @Resource
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Resource
    private MerchantFlowConfigFacade merchantFlowConfigFacade;
    @Autowired
    private VariableMessageNotifyService variableMessageNotifyService;
    @Autowired
    private UidService uidService;

    @Override
    public List<MerchantFlowConfigVO> getList() {
        List<MerchantFlowConfigVO> result = Lists.newArrayList();

        MerchantResult<List<MerchantFlowConfigResult>> rpcResult;
        try {
            rpcResult = merchantFlowConfigFacade.queryAllMerchantFlowConfig(new BaseRequest());
        } catch (RpcException e) {
            logger.error("获取merchantFlowConfig失败", e);
            return result;
        }
        if (!rpcResult.isSuccess()) {
            logger.error("获取merchantFlowConfig失败");
            return result;
        }

        List<MerchantFlowConfigResult> list = rpcResult.getData();

        List<String> appIds = list.stream().map(MerchantFlowConfigResult::getAppId).collect(Collectors.toList());

        MerchantResult<List<MerchantBaseResult>> merchantResult;
        try {
            QueryMerchantByAppIdRequest request = new QueryMerchantByAppIdRequest();
            request.setAppIds(appIds);
            merchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(request);
        } catch (RpcException e) {
            logger.error("获取merchantBase失败", e);
            return result;
        }
        if (!merchantResult.isSuccess()) {
            logger.error("获取merchantBase失败");
            return result;
        }

        List<MerchantBaseResult> baseList = merchantResult.getData();
        // <appId,appName>
        Map<String, String> appIdNameMap =
            baseList.stream().collect(Collectors.toMap(MerchantBaseResult::getAppId, MerchantBaseResult::getAppName));
        for (MerchantFlowConfigResult config : list) {
            MerchantFlowConfigVO vo = new MerchantFlowConfigVO();
            vo.setId(config.getId());
            String appName = appIdNameMap.get(config.getAppId());
            if (StringUtils.isNotBlank(appName)) {
                vo.setAppName(appName);
            }
            vo.setServiceTag(config.getServiceTag());
            vo.setServiceTagName(EServiceTagEnum.getDesc(config.getServiceTag()));
            result.add(vo);
        }
        logger.info(JSON.toJSONString(result));
        return result;
    }

    @Override
    public void init() {
        MerchantResult<List<MerchantSimpleResult>> result =
            merchantBaseInfoFacade.querySimpleMerchantSimple(new BaseRequest());
        if (!result.isSuccess()) {
            logger.error("init 失败");
            return;
        }

        List<MerchantSimpleResult> baseList = result.getData();
        if (CollectionUtils.isEmpty(baseList)) {
            logger.error("init 失败,返回数据：result={}", JSON.toJSONString(result));
            return;
        }
        MerchantResult<List<MerchantFlowConfigResult>> listMerchantResult =
            merchantFlowConfigFacade.queryAllMerchantFlowConfig(new BaseRequest());
        if (!listMerchantResult.isSuccess()) {
            logger.error("init 失败,返回数据：listMerchantResult={}", JSON.toJSONString(listMerchantResult));
            return;
        }
        List<MerchantFlowConfigResult> configList = listMerchantResult.getData();

        List<String> appIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(configList)) {
            appIdList = configList.stream().map(MerchantFlowConfigResult::getAppId).collect(Collectors.toList());
        }
        List<UpdateMerchantFlowRequest> list = Lists.newArrayList();
        for (MerchantSimpleResult merchantBase : baseList) {
            if (appIdList.contains(merchantBase.getAppId())) {
                continue;
            }
            UpdateMerchantFlowRequest config = new UpdateMerchantFlowRequest();
            config.setId(uidService.getId());
            config.setAppId(merchantBase.getAppId());
            config.setServiceTag(EServiceTagEnum.PRODUCT.getTag());
            list.add(config);
        }

        BatchUpdateFlowRequest request = new BatchUpdateFlowRequest();
        request.setRequests(list);
        try {
            MerchantResult insertResult = merchantFlowConfigFacade.batchInsert(request);
            if (insertResult.isSuccess()) {
                logger.info("init 成功");
            }
        } catch (RpcException e) {
            logger.error("批量插入失败", e);
        }

        logger.info("初始化商户流量分配配置,list={}", JSON.toJSONString(list));
    }

    @Override
    public SaasResult<Map<String, Object>>
        queryMerchantAllotVO(com.treefinance.saas.knife.request.PageRequest pageRequest) {
        PageRequest request = new PageRequest();
        request.setPageSize(pageRequest.getPageSize());
        request.setPageNum(pageRequest.getPageNumber());

        MerchantResult<List<MerchantFlowAllotResult>> result;
        try {
            result = merchantFlowConfigFacade.queryMerchantFlow(request);
        } catch (Exception e) {
            logger.error("获取列表失败", e);
            return Results.newFailedResult(CommonStateCode.FAILURE, e.getMessage());
        }

        if (!result.isSuccess()) {
            return Results.newFailedResult(CommonStateCode.FAILURE, result.getRetMsg());
        }

        List<MerchantFlowAllotResult> list = result.getData();

        List<MerchantFlowAllotVO> merchantFlowAllotVOS = this.convert(list, MerchantFlowAllotVO.class);

        return Results.newPageResult(pageRequest, result.getTotalCount(), merchantFlowAllotVOS);
    }

    @Override
    public SaasResult<Boolean> updateMerchantAllot(MerchantFlowAllotVO merchantFlowAllotVO) {

        UpdateMerchantFlowAllotRequest request = new UpdateMerchantFlowAllotRequest();

        request.setAppId(merchantFlowAllotVO.getAppId());
        List<MerchantFlowEnvQuotaVO> quotaVOList = merchantFlowAllotVO.getQuotaVOList();

        List<UpdateMerchantFlowAllotSubRequest> subRequests =
            this.convert(quotaVOList, UpdateMerchantFlowAllotSubRequest.class);

        request.setQuotas(subRequests);

        MerchantResult result;

        try {
            result = merchantFlowConfigFacade.updateMerchantFlow(request);
            logger.info("商户中心返回数据：{}", JSON.toJSONString(result));
        } catch (Exception e) {
            logger.error("更新商户流量配置失败", e);
            return Results.newFailedResult(CommonStateCode.FAILURE, e.getMessage());
        }

        if (!result.isSuccess()) {
            logger.error("更新商户流量配置失败：{}", result.getRetMsg());
            return Results.newFailedResult(CommonStateCode.FAILURE, result.getRetMsg());
        }

        variableMessageNotifyService.sendVariableMessage("merchant-flow", "update", merchantFlowAllotVO.getAppId());

        return Results.newSuccessResult(Boolean.TRUE);
    }
}
