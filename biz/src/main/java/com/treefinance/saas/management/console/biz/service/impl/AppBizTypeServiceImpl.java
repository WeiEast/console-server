package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType4Monitor;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.entity.AppBizTypeCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/4.
 */
@Service
public class AppBizTypeServiceImpl implements AppBizTypeService {

    @Autowired
    private AppBizTypeMapper appBizTypeMapper;
    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;

    @Override
    public List<AppBizTypeVO> getBizList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(appBizTypeList)) {
            return appBizTypeVOList;
        }
        appBizTypeVOList = BeanUtils.convertList(appBizTypeList, AppBizTypeVO.class);
        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getBizListByAppId(String appId) {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();

        AppBizLicenseCriteria criteria = new AppBizLicenseCriteria();
        criteria.createCriteria().andAppIdEqualTo(appId).andIsValidEqualTo((byte) 1);
        List<AppBizLicense> appBizLicenseList = appBizLicenseMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(appBizLicenseList)) {
            return appBizTypeVOList;
        }

        List<Byte> bizTypeList = appBizLicenseList
                .stream()
                .map(AppBizLicense::getBizType)
                .collect(Collectors.toList());

        AppBizTypeCriteria bizTypeCriteria = new AppBizTypeCriteria();
        bizTypeCriteria.createCriteria().andBizTypeIn(bizTypeList);
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(bizTypeCriteria);
        Map<Byte, AppBizType> appBizTypeMap = appBizTypeList
                .stream()
                .collect(Collectors.toMap(AppBizType::getBizType, appBizType -> appBizType));

        for (AppBizLicense o : appBizLicenseList) {
            AppBizType appBizType = appBizTypeMap.get(o.getBizType());
            AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
            appBizTypeVO.setBizType(appBizType.getBizType());
            appBizTypeVO.setBizName(appBizType.getBizName());
            appBizTypeVOList.add(appBizTypeVO);
        }
        appBizTypeVOList = appBizTypeVOList.stream().sorted((o1, o2) -> o1.getBizType().compareTo(o2.getBizType())).collect(Collectors.toList());
        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getTaskBizTypeList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(appBizTypeList)) {
            return appBizTypeVOList;
        }
        //添加一个系统总任务量监控
        AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
        appBizTypeVO.setBizType(EBizType4Monitor.TOTAL.getCode());
        appBizTypeVO.setBizName("系统总任务量监控");
        appBizTypeVOList.add(appBizTypeVO);

        appBizTypeList.forEach(o -> {
            AppBizTypeVO vo = new AppBizTypeVO();
            vo.setBizType(o.getBizType());
            vo.setBizName(o.getBizName() + "任务量监控");
            appBizTypeVOList.add(vo);
        });


        return appBizTypeVOList;
    }

    @Override
    public List<AppBizTypeVO> getAccessTaskBizTypeList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(appBizTypeList)) {
            return appBizTypeVOList;
        }
        //添加一个系统总访问量监控
        AppBizTypeVO appBizTypeVO = new AppBizTypeVO();
        appBizTypeVO.setBizType(EBizType4Monitor.TOTAL.getCode());
        appBizTypeVO.setBizName("系统总访问量监控");
        appBizTypeVOList.add(appBizTypeVO);

        appBizTypeList.forEach(o -> {
            AppBizTypeVO vo = new AppBizTypeVO();
            vo.setBizType(o.getBizType());
            vo.setBizName(o.getBizName() + "访问量监控");
            appBizTypeVOList.add(vo);
        });


        return appBizTypeVOList;
    }
}
