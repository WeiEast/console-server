package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
public interface AppBizLicenseService {

    List<AppBizLicenseVO> selectBizLicenseByAppIdBizType(AppBizLicenseRequest request);
    /**
     * 只能更新一下属性：
     *  isShowLicense、isValid、template
     * */
    Boolean updateAppBizLicense(AppBizLicenseVO request);

    List<AppBizLicenseVO> selectQuotaByAppIdBizType(AppBizLicenseRequest request);

    Boolean updateQuota(AppBizLicenseVO request);

    List<AppBizLicenseVO> selectTrafficByAppIdBizType(AppBizLicenseRequest request);

    Boolean updateTraffic(AppBizLicenseVO request);
}
