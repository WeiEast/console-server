package com.treefinance.saas.management.console.biz.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.AppLicenseService;
import com.treefinance.saas.management.console.biz.MerchantService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商户管理
 * Created by haojiahong on 2017/6/21.
 */
@Component
public class MerchantServiceImpl implements MerchantService {
    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private MerchantUserMapper merchantUserMapper;
    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private AppLicenseService appLicenseService;


    @Override
    public List<MerchantBaseVO> getMerchantList() {
        List<MerchantBaseVO> merchantBaseVOList = Lists.newArrayList();
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return merchantBaseVOList;
        }
        //<merchantId,MerchantBase>
        Map<Long, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getId, merchantBase -> merchantBase));
        //<appId,merchantBase>
        Map<String, MerchantBase> merchantBaseAppIdMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.createCriteria().andMerchantIdIn(Lists.newArrayList(merchantBaseMap.keySet()));
        List<MerchantUser> merchantUserList = merchantUserMapper.selectByExample(merchantUserCriteria);
        //<merchantId,merchantUser>
        Map<Long, MerchantUser> merchantUserMerchantIdMap = merchantUserList.stream().collect(Collectors.toMap(MerchantUser::getMerchantId, merchantUser -> merchantUser));

        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andAppIdIn(Lists.newArrayList(merchantBaseAppIdMap.keySet()));
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);

        //<appId,List<appBizLicense>>
        Map<String, List<AppBizLicense>> appBizLicenseAppIdMap = Maps.newHashMap();
        for (AppBizLicense appBizLicense : appBizLicenseList) {
            if (CollectionUtils.isEmpty(appBizLicenseAppIdMap.get(appBizLicense.getAppId()))) {
                List<AppBizLicense> list = Lists.newArrayList();
                list.add(appBizLicense);
                appBizLicenseAppIdMap.put(appBizLicense.getAppId(), list);
            } else {
                appBizLicenseAppIdMap.get(appBizLicense.getAppId()).add(appBizLicense);
            }
        }

        for (MerchantBase merchantBase : merchantBaseList) {
            MerchantBaseVO merchantBaseVO = new MerchantBaseVO();
            BeanUtils.copyProperties(merchantBase, merchantBaseVO);
            MerchantUser merchantUser = merchantUserMerchantIdMap.get(merchantBase.getId());
            if (merchantUser != null) {
                merchantBaseVO.setLoginName(merchantUser.getLoginName());
                merchantBaseVO.setPassword(merchantUser.getPassword());
            }
            List<AppBizLicense> licenseList = appBizLicenseAppIdMap.get(merchantBase.getAppId());
            if (!CollectionUtils.isEmpty(licenseList)) {
                List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();
                for (AppBizLicense appBizLicense : licenseList) {
                    AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
                    appBizLicenseVO.setBizType(appBizLicense.getBizType());
                    appBizLicenseVO.setBizName(EBizType.getName(appBizLicense.getBizType()));
                    appBizLicenseVOList.add(appBizLicenseVO);
                }
                merchantBaseVO.setAppBizLicenseVOList(appBizLicenseVOList);
            }
            merchantBaseVOList.add(merchantBaseVO);
        }
        return merchantBaseVOList;
    }

    @Override
    @Transactional
    public void addMerchant(MerchantBaseVO merchantBaseVO) {
        logger.info("添加商户信息 merchantBaseVO={}", JSON.toJSONString(merchantBaseVO));
        String appId = CommonUtils.generateAppId();
        merchantBaseVO.setAppId(appId);
        Long merchantId = insertMerchantBase(merchantBaseVO);
        insertMerchantUser(merchantBaseVO, merchantId);
        if (!CollectionUtils.isEmpty(merchantBaseVO.getAppBizLicenseVOList())) {
            insertAppBizLicense(merchantBaseVO, appId);
        }
        appLicenseService.generateAppLicense(appId);
    }


    private void insertMerchantUser(MerchantBaseVO merchantBaseVO, Long merchantId) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantId(merchantId);
        merchantUser.setLoginName(merchantBaseVO.getLoginName());
        merchantUser.setPassword(merchantBaseVO.getPassword());
        merchantUser.setIsActive(Boolean.TRUE);
        merchantUserMapper.insert(merchantUser);
    }

    private void insertAppBizLicense(MerchantBaseVO merchantBaseVO, String appId) {
        for (AppBizLicenseVO appBizLicenseVO : merchantBaseVO.getAppBizLicenseVOList()) {
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setAppId(appId);
            appBizLicense.setBizType(appBizLicenseVO.getBizType());
            appBizLicenseMapper.insert(appBizLicense);
        }
    }

    private Long insertMerchantBase(MerchantBaseVO merchantBaseVO) {
        MerchantBase merchantBase = new MerchantBase();
        merchantBase.setAppId(merchantBaseVO.getAppId());
        merchantBase.setAppName(merchantBaseVO.getAppName());
        merchantBase.setContactPerson(merchantBaseVO.getContactPerson());
        merchantBase.setContactMobile(merchantBaseVO.getContactMobile());
        merchantBase.setCompany(merchantBaseVO.getCompany());
        long merchantId = merchantBaseMapper.insert(merchantBase);
        return merchantId;
    }
}
