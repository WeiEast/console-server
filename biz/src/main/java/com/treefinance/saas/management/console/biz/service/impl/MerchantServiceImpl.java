package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.MerchantService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantBaseVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantUserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
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
    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;
    @Autowired
    private DiamondConfig diamondConfig;


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
            String text;
            try {
                text = iSecurityCryptoService.decrypt(merchantUser.getPassword(), EncryptionIntensityEnum.NORMAL);
            } catch (Exception e) {
                logger.error("merchantId={}的密文解析有误", id, e);
                text = "密码失效,请重置!";
            }
            merchantBaseVO.setPassword(text);
            merchantBaseVO.setIsTest(merchantUser.getIsTest());
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
        criteria.setOrderByClause("CreateTime desc");
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectPaginationByExample(criteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return Results.newSuccessPageResult(request, total, merchantBaseList);
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
        appBizLicenseCriteria.createCriteria().andAppIdIn(Lists.newArrayList(merchantBaseAppIdMap.keySet())).andIsValidEqualTo((byte) 1);
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
                String text;
                try {
                    text = iSecurityCryptoService.decrypt(merchantUser.getPassword(), EncryptionIntensityEnum.NORMAL);
                } catch (Exception e) {
                    logger.error("merchantId={}的密文解析有误", merchantBase.getId(), e);
                    text = "密码失效,请重置!";
                }
                merchantBaseVO.setPassword(text);
                merchantBaseVO.setIsTest(merchantUser.getIsTest());
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
                appBizLicenseVOList = appBizLicenseVOList.stream().sorted((o1, o2) -> o1.getBizType().compareTo(o2.getBizType())).collect(Collectors.toList());
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
        if (StringUtils.isBlank(merchantBaseVO.getAppId())) {
            throw new BizException("appId不能为空!");
        }
        if (StringUtils.isBlank(merchantBaseVO.getAppName())) {
            throw new BizException("app名称不能为空!");
        }

        String pattern = "^" + diamondConfig.getAppIdEnvironmentPrefix() + "_" + "[0-9a-zA-Z]{16}";
        String appId = StringUtils.deleteWhitespace(merchantBaseVO.getAppId());
//        boolean hasPrefix = appId.startsWith(diamondConfig.getAppIdEnvironmentPrefix() + "_");
        boolean isMatch = Pattern.matches(pattern, appId);
        if (!isMatch) {
            throw new BizException("appId格式有误!需满足:" + diamondConfig.getAppIdEnvironmentPrefix() + "_16位数字字母字符串");
        }
        checkAppNameUnique(merchantBaseVO);
        checkAppIdUnique(merchantBaseVO);
        if (StringUtils.isBlank(merchantBaseVO.getAppId())) {
            appId = CommonUtils.generateAppId();
            merchantBaseVO.setAppId(appId);
        }
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
    @Transactional
    public void updateMerchant(MerchantBaseVO merchantBaseVO) {
        logger.info("更新商户信息 merchantBaseVO={}", JSON.toJSONString(merchantBaseVO));
        if (StringUtils.isBlank(merchantBaseVO.getAppName())) {
            throw new BizException("app名称不能为空!");
        }
        Assert.notNull(merchantBaseVO.getId(), "id不能为空");
        MerchantBaseCriteria criteria = new MerchantBaseCriteria();
        criteria.createCriteria().andIdEqualTo(merchantBaseVO.getId());
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            logger.info("更新商户基本信息传入merchantId={}非法!", merchantBaseVO.getId());
            throw new BizException("id非法!");
        }
        if (!merchantBaseList.get(0).getAppName().equals(merchantBaseVO.getAppName())) {
            checkAppNameUnique(merchantBaseVO);
        }
        MerchantBase merchantBase = new MerchantBase();
        BeanUtils.copyProperties(merchantBaseVO, merchantBase);
        merchantBaseMapper.updateByPrimaryKeySelective(merchantBase);

        MerchantUserCriteria userCriteria = new MerchantUserCriteria();
        userCriteria.createCriteria().andMerchantIdEqualTo(merchantBase.getId());
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setIsTest(merchantBaseVO.getIsTest());
        merchantUserMapper.updateByExampleSelective(merchantUser, userCriteria);

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
        logger.info("重置商户id={}密码 newPwd={}", id, newPwd);
        merchantUser.setPassword(iSecurityCryptoService.encrypt(newPwd, EncryptionIntensityEnum.NORMAL));
        merchantUserMapper.updateByPrimaryKeySelective(merchantUser);
        return newPwd;
    }

    @Override
    public List<MerchantSimpleVO> getMerchantBaseList() {
        List<MerchantSimpleVO> merchantSimpleVOList = Lists.newArrayList();
        MerchantBaseCriteria criteria = new MerchantBaseCriteria();
        criteria.setOrderByClause("convert(AppName using gbk) asc");
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return merchantSimpleVOList;
        }
        merchantSimpleVOList = BeanUtils.convertList(merchantBaseList, MerchantSimpleVO.class);
        return merchantSimpleVOList;
    }

    @Override
    public void resetAppLicenseKey(Long id) {
        MerchantBase merchantBase = merchantBaseMapper.selectByPrimaryKey(id);
        if (merchantBase == null) {
            logger.error("根据merchantId{}未查询到相关商户信息", id);
            return;
        }
        String appId = merchantBase.getAppId();
        appLicenseService.removeAppLicenseByAppId(appId);
        appLicenseService.generateAppLicenseByAppId(appId);
    }

    @Override
    public String autoGenerateAppId() {
        String prefix = diamondConfig.getAppIdEnvironmentPrefix();
        String appId = prefix + "_" + CommonUtils.generateAppId();
        return appId;
    }

    @Override
    public String generateCipherTextPassword(String str) {
        String text = iSecurityCryptoService.encrypt(str, EncryptionIntensityEnum.NORMAL);
        return text;
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

    private void checkAppNameUnique(MerchantBaseVO merchantBaseVO) {
        MerchantBaseCriteria criteria = new MerchantBaseCriteria();
        criteria.createCriteria().andAppNameEqualTo(merchantBaseVO.getAppName());
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(criteria);
        if (merchantBaseList.size() > 0) {
            throw new BizException("app名称重复");
        }
    }

    private void checkAppIdUnique(MerchantBaseVO merchantBaseVO) {
        MerchantBaseCriteria criteria1 = new MerchantBaseCriteria();
        criteria1.createCriteria().andAppIdEqualTo(merchantBaseVO.getAppId());
        List<MerchantBase> merchantBaseList1 = merchantBaseMapper.selectByExample(criteria1);
        if (merchantBaseList1.size() > 0) {
            throw new BizException("appId重复");
        }
    }

}
