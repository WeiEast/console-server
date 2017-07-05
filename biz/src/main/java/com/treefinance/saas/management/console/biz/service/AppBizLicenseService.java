package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
public interface AppBizLicenseService {

    List<AppBizLicenseVO> selectBizLicenseByAppIdBizType(AppBizLicenseRequest request);

    Boolean updateAppBizLicense(AppBizLicenseVO request);
}
