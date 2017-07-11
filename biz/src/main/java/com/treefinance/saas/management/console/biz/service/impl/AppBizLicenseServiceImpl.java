package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.entity.AppBizTypeCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizLicenseServiceImpl implements AppBizLicenseService {

    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    private AppBizTypeMapper appBizTypeMapper;

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
        Map<Byte, AppBizLicense> appBizLicenseMap = appBizLicenseList.stream().collect(Collectors.toMap(AppBizLicense::getBizType, appBizLicense -> appBizLicense));
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
                appBizLicenseVO.setDailyLimit(0);
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
            appBizLicense.setDailyLimit(request.getDailyLimit() == null ? 0 : request.getDailyLimit());
            appBizLicenseMapper.insertSelective(appBizLicense);
        } else {
            AppBizLicense srcAppBizLicense = appBizLicenseList.get(0);
            AppBizLicense appBizLicense = new AppBizLicense();
            appBizLicense.setId(srcAppBizLicense.getId());
            if (request.getIsShowLicense() != null) {
                appBizLicense.setIsShowLicense(request.getIsShowLicense());
            }
            if (request.getIsValid() != null) {
                appBizLicense.setIsValid(request.getIsValid());
            }
            if (request.getDailyLimit() != null) {
                appBizLicense.setDailyLimit(request.getDailyLimit());
            }
            appBizLicenseMapper.updateByPrimaryKeySelective(appBizLicense);

        }
        return Boolean.TRUE;
    }
}