package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.manager.LicenseManager;
import com.treefinance.saas.console.manager.domain.LicenseBO;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizLicenseByAppIdRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizLicenseByBizTypeRequest;
import com.treefinance.saas.merchant.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppBizLicenseFacade;
import com.treefinance.toolkit.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/11/23 19:15
 */
@Service
public class LicenseServiceAdapter extends AbstractMerchantServiceAdapter implements LicenseManager {
    private final AppBizLicenseFacade bizLicenseFacade;

    @Autowired
    public LicenseServiceAdapter(AppBizLicenseFacade bizLicenseFacade) {
        this.bizLicenseFacade = bizLicenseFacade;
    }

    @Override
    public List<LicenseBO> listAppLicenses() {
        MerchantResult<List<AppBizLicenseResult>> result = queryAppLicenses();

        return convert(result.getData(), LicenseBO.class);
    }

    private MerchantResult<List<AppBizLicenseResult>> queryAppLicenses() {
        QueryAppBizLicenseByBizTypeRequest request = new QueryAppBizLicenseByBizTypeRequest();

        MerchantResult<List<AppBizLicenseResult>> result = bizLicenseFacade.queryAllAppBizLicense(request);

        validateResponse(result, RpcActionEnum.QUERY_APP_LICENSES);

        return result;
    }

    @Override
    public List<LicenseBO> listAppLicensesByBizType(@Nonnull Byte bizType) {
        MerchantResult<List<AppBizLicenseResult>> result = queryAppLicensesByBizType(bizType);

        return convert(result.getData(), LicenseBO.class);
    }

    private MerchantResult<List<AppBizLicenseResult>> queryAppLicensesByBizType(@Nonnull Byte bizType) {
        Preconditions.notNull("bizType", bizType);

        QueryAppBizLicenseByBizTypeRequest request = new QueryAppBizLicenseByBizTypeRequest();
        request.setBizType(bizType);

        MerchantResult<List<AppBizLicenseResult>> result = bizLicenseFacade.queryAppBizLicenseByBizType(request);

        validateResponse(result, RpcActionEnum.QUERY_APP_LICENSES_BY_BIZ_TYPE, request);

        return result;
    }

    @Override
    public List<String> listAppIds() {
        MerchantResult<List<AppBizLicenseResult>> result = queryAppLicenses();

        return transformAppIds(result);
    }

    @Override
    public List<String> listAppIdsByBizType(@Nonnull Byte bizType) {
        MerchantResult<List<AppBizLicenseResult>> result = queryAppLicensesByBizType(bizType);

        return transformAppIds(result);
    }

    private List<String> transformAppIds(MerchantResult<List<AppBizLicenseResult>> result) {
        return transform(result.getData(), AppBizLicenseResult::getAppId, true);
    }

    @Override
    public List<LicenseBO> listValidAppLicensesByAppId(@Nonnull String appId) {
        Preconditions.notBlank("appId", appId);

        QueryAppBizLicenseByAppIdRequest request = new QueryAppBizLicenseByAppIdRequest();
        request.setAppId(appId);
        request.setIsValid((byte)1);

        MerchantResult<List<AppBizLicenseResult>> result = bizLicenseFacade.queryAppBizLicense(request);

        validateResponse(result, RpcActionEnum.QUERY_VALID_APP_LICENSES_BY_APP_ID, request);

        return convert(result.getData(), LicenseBO.class);
    }
}
