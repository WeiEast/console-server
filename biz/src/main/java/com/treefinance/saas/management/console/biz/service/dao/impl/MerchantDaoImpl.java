package com.treefinance.saas.management.console.biz.service.dao.impl;

import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.dao.MerchantDao;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.enumeration.EServiceTag;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantFlowConfigMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantUserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by haojiahong on 2017/10/16.
 */
@Service
public class MerchantDaoImpl implements MerchantDao {

    private static final Logger logger = LoggerFactory.getLogger(MerchantDao.class);

    @Autowired
    private MerchantFlowConfigMapper merchantFlowConfigMapper;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private MerchantUserMapper merchantUserMapper;
    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;
    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private AppLicenseService appLicenseService;

    @Override
    @Transactional
    public Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO) {
        String appId = merchantBaseVO.getAppId();
        //生成商户基本信息
        Long merchantId = insertMerchantBase(merchantBaseVO);
        //生成商户用户名密码
        String plainTextPassword = insertMerchantUser(merchantBaseVO, merchantId);
        //生成商户开通服务
        if (!CollectionUtils.isEmpty(merchantBaseVO.getAppBizLicenseVOList())) {
            insertAppBizLicense(merchantBaseVO, appId);
        }
        //生成相关秘钥key
        appLicenseService.generateAppLicenseByAppId(appId);
        //默认配置商户流量分配到生产环境
        this.insertMerchantFlowConfig(appId);
        Map<String, Object> map = Maps.newHashMap();
        map.put("merchantId", merchantId);
        map.put("plainTextPassword", plainTextPassword);
        return map;

    }

    @Override
    @Transactional
    public void updateMerchant(MerchantBaseVO merchantBaseVO) {
        MerchantBase merchantBase = new MerchantBase();
        BeanUtils.copyProperties(merchantBaseVO, merchantBase);
        merchantBaseMapper.updateByPrimaryKeySelective(merchantBase);

        MerchantUserCriteria userCriteria = new MerchantUserCriteria();
        userCriteria.createCriteria().andMerchantIdEqualTo(merchantBase.getId());
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setIsTest(merchantBaseVO.getIsTest());
        merchantUserMapper.updateByExampleSelective(merchantUser, userCriteria);
    }

    private void insertMerchantFlowConfig(String appId) {
        MerchantFlowConfig merchantFlowConfig = new MerchantFlowConfig();
        merchantFlowConfig.setId(UidGenerator.getId());
        merchantFlowConfig.setAppId(appId);
        merchantFlowConfig.setServiceTag(EServiceTag.PRODUCT.getTag());
        merchantFlowConfig.setCreateTime(new Date());
        merchantFlowConfigMapper.insert(merchantFlowConfig);
    }

    private Long insertMerchantBase(MerchantBaseVO merchantBaseVO) {
        MerchantBase merchantBase = new MerchantBase();
        merchantBase.setId(UidGenerator.getId());
        merchantBase.setAppId(merchantBaseVO.getAppId());
        merchantBase.setAppName(merchantBaseVO.getAppName());
        merchantBase.setContactPerson(merchantBaseVO.getContactPerson());
        merchantBase.setContactValue(merchantBaseVO.getContactValue());
        merchantBase.setCompany(merchantBaseVO.getCompany());
        merchantBase.setChName(merchantBaseVO.getChName());
        merchantBase.setBussiness(merchantBaseVO.getBussiness());
        merchantBase.setBussiness2(merchantBaseVO.getBussiness2());
        merchantBaseMapper.insertSelective(merchantBase);
        return merchantBase.getId();
    }

    private String insertMerchantUser(MerchantBaseVO merchantBaseVO, Long merchantId) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(UidGenerator.getId());
        merchantUser.setMerchantId(merchantId);
        String loginName = CommonUtils.generateLoginName(merchantBaseVO.getAppName());
        MerchantUserCriteria criteria = new MerchantUserCriteria();
        criteria.createCriteria().andLoginNameEqualTo(loginName);
        long count = merchantUserMapper.countByExample(criteria);
        if (count > 0) {
            logger.info("创建商户时,登录名loginName={}的商户已经存在");
            loginName = loginName + RandomStringUtils.randomNumeric(4);
        }
        merchantUser.setLoginName(loginName);
        String plainTextPassword = CommonUtils.generatePassword();
        merchantUser.setPassword(iSecurityCryptoService.encrypt(plainTextPassword, EncryptionIntensityEnum.NORMAL));
        merchantUser.setIsActive(Boolean.TRUE);
        merchantUser.setIsTest(merchantBaseVO.getIsTest());
        merchantUserMapper.insertSelective(merchantUser);
        return plainTextPassword;
    }

    private void insertAppBizLicense(MerchantBaseVO merchantBaseVO, String appId) {
        for (AppBizLicenseVO appBizLicenseVO : merchantBaseVO.getAppBizLicenseVOList()) {
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setId(UidGenerator.getId());
            appBizLicense.setAppId(appId);
            appBizLicense.setBizType(appBizLicenseVO.getBizType());
            appBizLicense.setIsValid((byte) 1);
            appBizLicense.setIsShowLicense((byte) 1);
            appBizLicense.setDailyLimit(100000);
            appBizLicenseMapper.insertSelective(appBizLicense);
        }
    }
}
