package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.context.exception.IllegalBusinessDataException;
import com.treefinance.saas.console.manager.BizTypeManager;
import com.treefinance.saas.console.manager.LicenseManager;
import com.treefinance.saas.console.manager.domain.BizTypeBO;
import com.treefinance.saas.console.manager.domain.IdentifiedBizTypeBO;
import com.treefinance.saas.console.manager.domain.LicenseBO;
import com.treefinance.saas.console.share.adapter.AbstractServiceAdapter;
import com.treefinance.saas.management.console.biz.domain.BizTypeInfo;
import com.treefinance.saas.management.console.biz.domain.IdentifiedBizType;
import com.treefinance.saas.management.console.biz.domain.MonitoringBizType;
import com.treefinance.saas.management.console.biz.enums.MonitorTypeEnum;
import com.treefinance.saas.management.console.biz.service.AppBizTypeService;
import com.treefinance.toolkit.util.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author haojiahong
 * @date 2017/7/4.
 */
@Service
public class AppBizTypeServiceImpl extends AbstractServiceAdapter implements AppBizTypeService {

    private static final Logger logger = LoggerFactory.getLogger(AppBizTypeServiceImpl.class);

    private final BizTypeManager bizTypeManager;
    private final LicenseManager licenseManager;

    @Autowired
    public AppBizTypeServiceImpl(BizTypeManager bizTypeManager, LicenseManager licenseManager) {
        this.bizTypeManager = bizTypeManager;
        this.licenseManager = licenseManager;
    }

    @Override
    public List<IdentifiedBizType> listIdentifiedBizTypes() {
        List<IdentifiedBizTypeBO> list = bizTypeManager.listIdentifiedBizTypes();

        List<IdentifiedBizType> result = this.convert(list, IdentifiedBizType.class);

        logger.info("获取appBizType列表，result={}", result);

        return result;
    }


    @Override
    public List<BizTypeInfo> listBizTypeInfosByAppId(@Nonnull String appId) {
        List<LicenseBO> licenses = licenseManager.listValidAppLicensesByAppId(appId);
        if (CollectionUtils.isEmpty(licenses)) {
            logger.info("根据appId获取appBizLicense返回data结果为空");
            return Collections.emptyList();
        }

        List<Byte> bizTypeValues = transform(licenses, LicenseBO::getBizType, true);

        List<BizTypeBO> bizTypes = bizTypeManager.listBizTypesInValues(bizTypeValues);
        if (CollectionUtils.isEmpty(bizTypes)) {
            throw new IllegalBusinessDataException("Broken data set! The biz-type storage was damaged!");
        }

        Map<Byte, BizTypeBO> bizTypeBOMap = transformToMap(bizTypes, BizTypeBO::getBizType, Function.identity());

        List<BizTypeInfo> result = new ArrayList<>();
        for (LicenseBO o : licenses) {
            BizTypeBO type = bizTypeBOMap.get(o.getBizType());
            BizTypeInfo appBizTypeVO = new BizTypeInfo();
            appBizTypeVO.setBizType(type.getBizType());
            appBizTypeVO.setBizName(type.getBizName());
            appBizTypeVO.setBizCode(type.getBizCode());
            result.add(appBizTypeVO);
        }

        result = result.stream().sorted(Comparator.comparing(BizTypeInfo::getBizType)).collect(Collectors.toList());

        logger.info("根据bizType获取appBizType，result={}", JSON.toJSONString(result));

        return result;
    }

    @Override
    public List<MonitoringBizType> listMonitoringBizTypes(@Nonnull MonitorTypeEnum type) {
        Preconditions.notNull("monitorType", type);
        List<IdentifiedBizTypeBO> list = bizTypeManager.listIdentifiedBizTypes();

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<MonitoringBizType> result = new ArrayList<>(list.size() + 1);

        // 补充一个总的监控选项
        MonitoringBizType bizType = new MonitoringBizType();
        bizType.setName(type.getTotalDesc());
        bizType.setBizType((byte)0);
        result.add(bizType);

        list.forEach(o -> {
            MonitoringBizType item = new MonitoringBizType();
            item.setName(o.getBizName() + type.getDesc());
            item.setBizType(o.getBizType());
            result.add(item);
        });
        logger.info("{}监控列表，result={}", type.getName(), JSON.toJSONString(result));

        return result;
    }

}
