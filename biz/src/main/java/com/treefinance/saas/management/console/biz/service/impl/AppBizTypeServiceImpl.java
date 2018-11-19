package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.merchant.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizLicenseByAppIdRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizTypeRequest;
import com.treefinance.saas.merchant.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.facade.result.console.AppBizTypeResult;
import com.treefinance.saas.merchant.facade.result.console.AppBizTypeSimpleResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppBizLicenseFacade;
import com.treefinance.saas.merchant.facade.service.AppBizTypeFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizTypeServiceImpl implements AppBizTypeService {

    private static final Logger logger = LoggerFactory.getLogger(AppBizTypeServiceImpl.class);

    @Resource
    private AppBizTypeFacade appBizTypeFacade;
    @Resource
    private AppBizLicenseFacade appBizLicenseFacade;


    @Override
    public List<AppBizTypeVO> getBizList() {
        List<AppBizTypeVO> appBizTypeVOList;

        List<AppBizTypeSimpleResult> list = getAppBizTypeList();

        appBizTypeVOList = BeanUtils.convertList(list, AppBizTypeVO.class);

        logger.info(JSON.toJSONString(appBizTypeVOList));
        return appBizTypeVOList;
    }

    private List<AppBizTypeSimpleResult> getAppBizTypeList() {
        MerchantResult<List<AppBizTypeSimpleResult>> result;

        try {
            result = appBizTypeFacade.queryAppBizTypeSimple(new BaseRequest());
        } catch (RpcException e) {
            logger.error("获取appBizType列表失败，错误信息：{}", e.getMessage());
            return new ArrayList<>();
        }

        return result.getData();
    }


    @Override
    public Map<Byte, String> getBizTypeNameMap() {
        Map<Byte, String> map = Maps.newHashMap();
        List<AppBizTypeSimpleResult> list = getAppBizTypeList();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        map = list.stream().collect(Collectors.toMap(AppBizTypeSimpleResult::getBizType, AppBizTypeSimpleResult::getBizName));
        logger.info(JSON.toJSONString(map));
        return map;
    }

    @Override
    public AppBizType getBizTypeByBizCodeIgnoreCase(String bizCode) {

        QueryAppBizTypeRequest queryAppBizTypeRequest = new QueryAppBizTypeRequest();

        queryAppBizTypeRequest.setBizCodes(Lists.newArrayList(bizCode, bizCode.toLowerCase()));

        MerchantResult<List<AppBizTypeResult>> result;
        try{
            result = appBizTypeFacade.queryAppBizType(queryAppBizTypeRequest);
        }catch (RpcException e){
            logger.error("获取appBizType列表失败，{}",e.getMessage());
            return null;
        }

        if(!result.isSuccess()){
            return null;
        }
        List<AppBizTypeResult> list = result.getData();
        AppBizType appBizType = new AppBizType();

        BeanUtils.copyProperties(list.get(0),appBizType);

        logger.info(JSON.toJSONString(appBizType));
        return appBizType;
    }

    @Override
    public List<AppBizTypeVO> getBizListByAppId(String appId) {

        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();


        QueryAppBizLicenseByAppIdRequest request = new QueryAppBizLicenseByAppIdRequest();
        request.setAppId(appId);
        request.setIsValid((byte)1);
        MerchantResult<List<AppBizLicenseResult>> result;
        try {
            result = appBizLicenseFacade.queryAppBizLicense(request);
        }catch (RpcException e){
            logger.error("根据appId获取appBizLicense失败,{}",e.getMessage());
            return appBizTypeVOList;
        }

        if(!result.isSuccess()){
            logger.error("根据appId获取appBizLicense失败");
            return appBizTypeVOList;
        }

        if (CollectionUtils.isEmpty(result.getData())) {
            return appBizTypeVOList;
        }

        List<AppBizLicenseResult> appBizLicenseList = result.getData();
        List<Byte> bizTypeList = appBizLicenseList
                .stream()
                .map(AppBizLicenseResult::getBizType)
                .collect(Collectors.toList());

        QueryAppBizTypeRequest queryAppBizTypeRequest = new QueryAppBizTypeRequest();

        queryAppBizTypeRequest.setBizTypes(bizTypeList);

        MerchantResult<List<AppBizTypeResult>> bizTypeResult;
        try{
            bizTypeResult = appBizTypeFacade.queryAppBizType(queryAppBizTypeRequest);
        }catch (RpcException e){
            logger.error("根据bizType获取appBizType列表失败，{}",e.getMessage());
            return appBizTypeVOList;
        }
        if(!bizTypeResult.isSuccess()){
            logger.error("根据bizType获取appBizType列表失败");
            return appBizTypeVOList;
        }

        List<AppBizTypeResult> appBizTypeResults = bizTypeResult.getData();

        Map<Byte, AppBizTypeResult> appBizTypeMap = appBizTypeResults
                .stream()
                .collect(Collectors.toMap(AppBizTypeResult::getBizType, appBizType -> appBizType));

        for (AppBizLicenseResult o : appBizLicenseList) {
            AppBizTypeResult appBizType = appBizTypeMap.get(o.getBizType());
            AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
            appBizTypeVO.setBizType(appBizType.getBizType());
            appBizTypeVO.setBizName(appBizType.getBizName());
            appBizTypeVOList.add(appBizTypeVO);
        }
        appBizTypeVOList = appBizTypeVOList.stream().sorted(Comparator.comparing(AppBizTypeVO::getBizType)).collect(Collectors.toList());

        logger.info(JSON.toJSONString(appBizTypeVOList));

        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getTaskBizTypeList() {

        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();

        List<AppBizTypeSimpleResult> list = getAppBizTypeList();

        if (CollectionUtils.isEmpty(list)) {
            return appBizTypeVOList;
        }
        //添加一个系统总任务量监控
        AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
        appBizTypeVO.setBizType((byte)0);
        appBizTypeVO.setBizName("系统总任务量监控");
        appBizTypeVOList.add(appBizTypeVO);

        list.forEach(o -> {
            AppBizTypeVO vo = new AppBizTypeVO();
            vo.setBizType(o.getBizType());
            vo.setBizName(o.getBizName() + "任务量监控");
            appBizTypeVOList.add(vo);
        });
        logger.info(JSON.toJSONString(appBizTypeVOList));

        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getAccessTaskBizTypeList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizTypeSimpleResult> list = getAppBizTypeList();
        if (CollectionUtils.isEmpty(list)) {
            return appBizTypeVOList;
        }
        //添加一个系统总访问量监控
        AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
        appBizTypeVO.setBizType((byte) 0);
        appBizTypeVO.setBizName("系统总访问量监控");
        appBizTypeVOList.add(appBizTypeVO);

        list.forEach(o -> {
            AppBizTypeVO vo = new AppBizTypeVO();
            vo.setBizType(o.getBizType());
            vo.setBizName(o.getBizName() + "访问量监控");
            appBizTypeVOList.add(vo);
        });

        logger.info(JSON.toJSONString(appBizTypeVOList));
        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getAccessTaskDetailBizTypeList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizTypeSimpleResult> list = getAppBizTypeList();
        if (CollectionUtils.isEmpty(list)) {
            return appBizTypeVOList;
        }
        //添加一个系统总访问量监控
        AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
        appBizTypeVO.setBizType((byte) 0);
        appBizTypeVO.setBizName("合计");
        appBizTypeVOList.add(appBizTypeVO);

        list.forEach(o -> {
            AppBizTypeVO vo = new AppBizTypeVO();
            vo.setBizType(o.getBizType());
            vo.setBizName(o.getBizName());
            appBizTypeVOList.add(vo);
        });

        logger.info(JSON.toJSONString(appBizTypeVOList));
        return appBizTypeVOList;
    }
}
