package com.treefinance.saas.management.console.dao.ecommerce.Impl;

import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.management.console.dao.ecommerce.EcommerceMonitorDao;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppBizLicenseByBizTypeRequest;
import com.treefinance.saas.merchant.center.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppBizLicenseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppBizLicenseFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/16下午8:33
 */
@Repository
public class EcommerceMonitorDaoImpl implements EcommerceMonitorDao {

    private final static Logger logger = LoggerFactory.getLogger(EcommerceMonitorDaoImpl.class);

    @Autowired
    AppBizLicenseMapper appBizLicenseMapper;
    @Autowired
    AppBizLicenseFacade appBizLicenseFacade;
    @Autowired
    MerchantBaseMapper merchantBaseMapper;
    @Autowired
    MerchantBaseInfoFacade merchantBaseInfoFacade;


    @Override
    public List<MerchantBase> queryAllEcommerceListByBizeType(Integer bizType) {
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andBizTypeEqualTo(bizType.byteValue());
        List<MerchantBase> merchantBaseListTotal = new ArrayList<>();

        QueryAppBizLicenseByBizTypeRequest queryAppBizLicenseByBizTypeRequest = new QueryAppBizLicenseByBizTypeRequest();
        queryAppBizLicenseByBizTypeRequest.setBizType(bizType.byteValue());
        MerchantResult<List<AppBizLicenseResult>> licenseByBizType = appBizLicenseFacade.queryAppBizLicenseByBizType(queryAppBizLicenseByBizTypeRequest);
        List<AppBizLicense> licenseList = DataConverterUtils.convert(licenseByBizType.getData(),AppBizLicense.class);

        for (AppBizLicense appBizLicense : licenseList) {

            List<String> appIds= new ArrayList<>();
            QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
            appIds.add(appBizLicense.getAppId());
            queryMerchantByAppIdRequest.setAppIds(appIds);
            MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
            List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(),MerchantBase.class);
            merchantBaseListTotal.addAll(merchantBaseList);
        }
        logger.info("电商列表数据查询返回结果：{}", merchantBaseListTotal.toString());

        return merchantBaseListTotal;


    }
}
