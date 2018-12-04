package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.biz.domain.BizTypeInfo;
import com.treefinance.saas.console.biz.domain.IdentifiedBizType;
import com.treefinance.saas.console.biz.domain.MonitoringBizType;
import com.treefinance.saas.console.biz.enums.MonitorTypeEnum;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author haojiahong
 * @date 2017/7/4.
 */
public interface AppBizTypeService {

    List<IdentifiedBizType> listIdentifiedBizTypes();

    List<BizTypeInfo> listBizTypeInfosByAppId(@Nonnull String appId);

    List<MonitoringBizType> listMonitoringBizTypes(@Nonnull MonitorTypeEnum type);

}
