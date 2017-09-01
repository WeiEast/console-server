package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.assistant.config.model.ConfigUpdateBuilder;
import com.treefinance.saas.assistant.config.model.enums.ConfigType;
import com.treefinance.saas.assistant.config.model.enums.UpdateType;
import com.treefinance.saas.assistant.config.plugin.ConfigUpdatePlugin;
import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.entity.AppBizTypeCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizLicenseServiceImpl implements AppBizLicenseService {

    private static final Logger logger = LoggerFactory.getLogger(AppBizLicenseServiceImpl.class);


    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private AppBizTypeMapper appBizTypeMapper;
    @Autowired
    private ConfigUpdatePlugin configUpdatePlugin;

    @Override
    public List<AppBizLicenseVO> selectBizLicenseByAppIdBizType(AppBizLicenseRequest request) {
        Assert.notNull(request.getAppId(), "appId不能为空!");
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        AppBizLicenseCriteria.Criteria criteria = appBizLicenseCriteria.createCriteria();
        criteria.andAppIdEqualTo(request.getAppId());
        if (request.getBizType() != null) {
            criteria.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        //<bizType,AppBizLicense>
        Map<Byte, AppBizLicense> appBizLicenseMap = appBizLicenseList
                .stream()
                .collect(Collectors.toMap(AppBizLicense::getBizType, appBizLicense -> appBizLicense, (key1, key2) -> key1));
        AppBizTypeCriteria appBizTypeCriteria = new AppBizTypeCriteria();
        AppBizTypeCriteria.Criteria criteria1 = appBizTypeCriteria.createCriteria();
        if (request.getBizType() != null) {
            criteria1.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(appBizTypeCriteria);
        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();
        for (AppBizType appBizType : appBizTypeList) {
            AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
            AppBizLicense appBizLicense = appBizLicenseMap.get(appBizType.getBizType());
            if (appBizLicense == null) {
                appBizLicenseVO.setBizType(appBizType.getBizType());
                appBizLicenseVO.setBizName(appBizType.getBizName());
                appBizLicenseVO.setAppId(request.getAppId());
                appBizLicenseVO.setIsShowLicense((byte) 0);
                appBizLicenseVO.setIsValid((byte) 0);
            } else {
                BeanUtils.convert(appBizLicense, appBizLicenseVO);
                appBizLicenseVO.setBizName(appBizType.getBizName());
            }
            appBizLicenseVOList.add(appBizLicenseVO);

        }
        return appBizLicenseVOList;
    }

    @Override
    public Boolean updateAppBizLicense(AppBizLicenseVO request) {
        Assert.notNull(request.getAppId(), "appId不能为空");
        Assert.notNull(request.getBizType(), "bizType不能为空");

        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andAppIdEqualTo(request.getAppId()).andBizTypeEqualTo(request.getBizType());

        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        if (CollectionUtils.isEmpty(appBizLicenseList)) {
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setId(UidGenerator.getId());
            appBizLicense.setAppId(request.getAppId());
            appBizLicense.setBizType(request.getBizType());
            appBizLicense.setIsShowLicense(request.getIsShowLicense() == null ? 0 : request.getIsShowLicense());
            appBizLicense.setIsValid(request.getIsValid() == null ? 0 : request.getIsValid());
            if (request.getIsValid() != null && request.getIsValid() == (byte) 1) {
                appBizLicense.setDailyLimit(100000);
            }
            if (request.getIsValid() != null && request.getIsValid() == (byte) 0) {
                appBizLicense.setDailyLimit(0);
            }
            appBizLicenseMapper.insertSelective(appBizLicense);

            // 发送配置变更消息
            configUpdatePlugin.sendMessage(ConfigUpdateBuilder.newBuilder()
                    .configType(ConfigType.MERCHANT_LICENSE)
                    .configDesc("更新商户授权")
                    .updateType(UpdateType.UPDATE)
                    .configId(appBizLicense.getAppId())
                    .configData(appBizLicense).build());
        } else {
            AppBizLicense srcAppBizLicense = appBizLicenseList.get(0);
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setId(srcAppBizLicense.getId());
            if (request.getIsShowLicense() != null) {
                appBizLicense.setIsShowLicense(request.getIsShowLicense());
            }
            if (request.getIsValid() != null) {
                appBizLicense.setIsValid(request.getIsValid());
                if (request.getIsValid() == (byte) 1) {
                    appBizLicense.setDailyLimit(100000);
                }
                if (request.getIsValid() == (byte) 0) {
                    appBizLicense.setDailyLimit(0);
                }
            }
            appBizLicenseMapper.updateByPrimaryKeySelective(appBizLicense);

            // 发送配置变更消息
            configUpdatePlugin.sendMessage(ConfigUpdateBuilder.newBuilder()
                    .configType(ConfigType.MERCHANT_LICENSE)
                    .configDesc("更新商户授权")
                    .updateType(UpdateType.UPDATE)
                    .configId(srcAppBizLicense.getAppId())
                    .configData(appBizLicense).build());
        }
        return Boolean.TRUE;
    }

    @Override
    public List<AppBizLicenseVO> selectQuotaByAppIdBizType(AppBizLicenseRequest request) {
        Assert.notNull(request.getAppId(), "appId不能为空!");
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        AppBizLicenseCriteria.Criteria criteria = appBizLicenseCriteria.createCriteria();
        criteria.andAppIdEqualTo(request.getAppId()).andIsValidEqualTo((byte) 1);
        if (request.getBizType() != null) {
            criteria.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        AppBizTypeCriteria appBizTypeCriteria = new AppBizTypeCriteria();
        AppBizTypeCriteria.Criteria criteria1 = appBizTypeCriteria.createCriteria();
        if (request.getBizType() != null) {
            criteria1.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(appBizTypeCriteria);
        Map<Byte, AppBizType> appBizTypeMap = appBizTypeList.stream().collect(Collectors.toMap(AppBizType::getBizType, appBizType1 -> appBizType1));
        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();
        for (AppBizLicense appBizLicense : appBizLicenseList) {
            AppBizType appBizType = appBizTypeMap.get(appBizLicense.getBizType());
            if (appBizType == null) {
                logger.info("appBizLicense中bizType={}的服务权限类型在app_biz_type中未配置", appBizLicense.getBizType());
                continue;
            }
            AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
            appBizLicenseVO.setBizType(appBizType.getBizType());
            appBizLicenseVO.setBizName(appBizType.getBizName());
            appBizLicenseVO.setAppId(request.getAppId());
            appBizLicenseVO.setDailyLimit(appBizLicense.getDailyLimit() == null ? 0 : appBizLicense.getDailyLimit());
            appBizLicenseVOList.add(appBizLicenseVO);
        }
        return appBizLicenseVOList;

    }


    @Override
    public Boolean updateQuota(AppBizLicenseVO request) {
        Assert.notNull(request.getAppId(), "appId不能为空");
        Assert.notNull(request.getBizType(), "bizType不能为空");
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria()
                .andAppIdEqualTo(request.getAppId())
                .andBizTypeEqualTo(request.getBizType())
                .andIsValidEqualTo((byte) 1);

        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        if (CollectionUtils.isEmpty(appBizLicenseList)) {
            logger.info("更新商户配额时,商户appId={}的服务权限bizType={}未开通", request.getAppId(), request.getBizType());
            throw new BizException("商户此服务权限未开通!");
        }
        AppBizLicense srcAppBizLicense = appBizLicenseList.get(0);
        AppBizLicense appBizLicense = new AppBizLicense();
        appBizLicense.setId(srcAppBizLicense.getId());
        appBizLicense.setDailyLimit(request.getDailyLimit());
        appBizLicenseMapper.updateByPrimaryKeySelective(appBizLicense);
        return Boolean.TRUE;
    }

    @Override
    public List<AppBizLicenseVO> selectTrafficByAppIdBizType(AppBizLicenseRequest request) {
        Assert.notNull(request.getAppId(), "appId不能为空!");
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        AppBizLicenseCriteria.Criteria criteria = appBizLicenseCriteria.createCriteria();
        criteria.andAppIdEqualTo(request.getAppId()).andIsValidEqualTo((byte) 1);
        if (request.getBizType() != null) {
            criteria.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        AppBizTypeCriteria appBizTypeCriteria = new AppBizTypeCriteria();
        AppBizTypeCriteria.Criteria criteria1 = appBizTypeCriteria.createCriteria();
        if (request.getBizType() != null) {
            criteria1.andBizTypeEqualTo(request.getBizType());
        }
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(appBizTypeCriteria);
        Map<Byte, AppBizType> appBizTypeMap = appBizTypeList.stream().collect(Collectors.toMap(AppBizType::getBizType, appBizType1 -> appBizType1));
        List<AppBizLicenseVO> appBizLicenseVOList = Lists.newArrayList();
        for (AppBizLicense appBizLicense : appBizLicenseList) {
            AppBizType appBizType = appBizTypeMap.get(appBizLicense.getBizType());
            if (appBizType == null) {
                logger.info("appBizLicense中bizType={}的服务权限类型在app_biz_type中未配置", appBizLicense.getBizType());
                continue;
            }
            AppBizLicenseVO appBizLicenseVO = new AppBizLicenseVO();
            appBizLicenseVO.setBizType(appBizType.getBizType());
            appBizLicenseVO.setBizName(appBizType.getBizName());
            appBizLicenseVO.setAppId(request.getAppId());
            appBizLicenseVO.setTrafficLimit(appBizLicense.getTrafficLimit());
            appBizLicenseVOList.add(appBizLicenseVO);
        }
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
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria()
                .andAppIdEqualTo(request.getAppId())
                .andBizTypeEqualTo(request.getBizType())
                .andIsValidEqualTo((byte) 1);

        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
        if (CollectionUtils.isEmpty(appBizLicenseList)) {
            logger.info("更新商户流量限制时,商户appId={}的服务权限bizType={}未开通", request.getAppId(), request.getBizType());
            throw new BizException("商户此服务权限未开通!");
        }
        AppBizLicense srcAppBizLicense = appBizLicenseList.get(0);
        AppBizLicense appBizLicense = new AppBizLicense();
        appBizLicense.setId(srcAppBizLicense.getId());
        appBizLicense.setTrafficLimit(request.getTrafficLimit());
        appBizLicenseMapper.updateByPrimaryKeySelective(appBizLicense);
        return Boolean.TRUE;
    }
}
