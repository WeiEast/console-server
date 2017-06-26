package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.MerchantService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantUserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
    public MerchantBaseVO getMerchantById(Long id) {
        MerchantBase merchantBase = merchantBaseMapper.selectByPrimaryKey(id);
        if (merchantBase == null) {
            return null;
        }
        MerchantBaseVO merchantBaseVO = new MerchantBaseVO();
        Long merchantId = merchantBase.getId();
        String appId = merchantBase.getAppId();
        BeanUtils.copyProperties(merchantBase, merchantBaseVO);

        MerchantUserCriteria merchantUserCriteria = new MerchantUserCriteria();
        merchantUserCriteria.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantUser> merchantUserList = merchantUserMapper.selectByExample(merchantUserCriteria);
        if (!CollectionUtils.isEmpty(merchantUserList)) {
            MerchantUser merchantUser = merchantUserList.get(0);
            merchantBaseVO.setLoginName(merchantUser.getLoginName());
        }

        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andAppIdEqualTo(appId);
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        if (!CollectionUtils.isEmpty(appBizLicenseList)) {
            List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();
            for (AppBizLicense appBizLicense : appBizLicenseList) {
                AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
                appBizLicenseVO.setBizType(appBizLicense.getBizType());
                appBizLicenseVO.setBizName(EBizType.getName(appBizLicense.getBizType()));
                appBizLicenseVOList.add(appBizLicenseVO);
            }
            merchantBaseVO.setAppBizLicenseVOList(appBizLicenseVOList);

        }

        AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(appId);
        if (appLicenseDTO != null) {
            AppLicenseVO appLicenseVO = new AppLicenseVO();
            BeanUtils.copyProperties(appLicenseDTO, appLicenseVO);
            merchantBaseVO.setAppLicenseVO(appLicenseVO);
        }
        return merchantBaseVO;
    }

    @Override
    public Result<Map<String, Object>> getMerchantList(PageRequest request) {
        List<MerchantBaseVO> merchantBaseVOList = Lists.newArrayList();

        long total = merchantBaseMapper.countByExample(null);
        if (Optional.fromNullable(total).or(Long.valueOf(0)) <= 0) {
            return Results.newSuccessPageResult(request, total, merchantBaseVOList);
        }
        MerchantBaseCriteria criteria = new MerchantBaseCriteria();
        criteria.setOffset(request.getOffset());
        criteria.setLimit(request.getPageSize());
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectPaginationByExample(criteria);
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
        return Results.newSuccessPageResult(request, total, merchantBaseVOList);
    }

    @Override
    @Transactional
    public Map<String, Object> addMerchant(MerchantBaseVO merchantBaseVO) {
        logger.info("添加商户信息 merchantBaseVO={}", JSON.toJSONString(merchantBaseVO));
        Assert.notNull(merchantBaseVO.getAppName(), "app名称不能为空!");
        String appId = CommonUtils.generateAppId();
        merchantBaseVO.setAppId(appId);
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
        Map<String, Object> map = Maps.newHashMap();
        map.put("merchantId", merchantId);
        map.put("plainTextPassword", plainTextPassword);
        return map;
    }

    @Override
    public void updateMerchant(MerchantBaseVO merchantBaseVO, Long id) {
        Assert.notNull(merchantBaseVO.getAppName(), "app名称不能为空!");
        MerchantBase merchantBase = new MerchantBase();
        BeanUtils.copyProperties(merchantBaseVO, merchantBase);
        merchantBase.setId(id);
        merchantBaseMapper.updateByPrimaryKeySelective(merchantBase);

    }

    @Override
    public String resetPassWord(Long id) {
        MerchantUserCriteria criteria = new MerchantUserCriteria();
        criteria.createCriteria().andMerchantIdEqualTo(id);
        List<MerchantUser> merchantUserList = merchantUserMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(merchantUserList)) {
            return null;
        }
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(merchantUserList.get(0).getId());
        String newPwd = CommonUtils.generatePassword();
        merchantUser.setPassword(DigestUtils.md5Hex(newPwd));
        merchantUserMapper.updateByPrimaryKeySelective(merchantUser);
        return newPwd;
    }

    private String insertMerchantUser(MerchantBaseVO merchantBaseVO, Long merchantId) {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setMerchantId(merchantId);
        merchantUser.setLoginName(CommonUtils.generateLoginName(merchantBaseVO.getAppName()));
        String plainTextPassword = CommonUtils.generatePassword();
        merchantUser.setPassword(DigestUtils.md5Hex(plainTextPassword));
        merchantUser.setIsActive(Boolean.TRUE);
        merchantUserMapper.insertSelective(merchantUser);
        return plainTextPassword;
    }

    private void insertAppBizLicense(MerchantBaseVO merchantBaseVO, String appId) {
        for (AppBizLicenseVO appBizLicenseVO : merchantBaseVO.getAppBizLicenseVOList()) {
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setAppId(appId);
            appBizLicense.setBizType(appBizLicenseVO.getBizType());
            appBizLicenseMapper.insertSelective(appBizLicense);
        }
    }

    private Long insertMerchantBase(MerchantBaseVO merchantBaseVO) {
        MerchantBase merchantBase = new MerchantBase();
        merchantBase.setAppId(merchantBaseVO.getAppId());
        merchantBase.setAppName(merchantBaseVO.getAppName());
        merchantBase.setContactPerson(merchantBaseVO.getContactPerson());
        merchantBase.setContactMobile(merchantBaseVO.getContactMobile());
        merchantBase.setCompany(merchantBaseVO.getCompany());
        merchantBaseMapper.insertSelective(merchantBase);
        return merchantBase.getId();
    }
}
