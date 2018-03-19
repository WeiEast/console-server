package com.treefinance.saas.management.console.dao.ecommerce.Impl;

import com.treefinance.saas.management.console.dao.ecommerce.EcommerceMonitorDao;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
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
    MerchantBaseMapper merchantBaseMapper;


    @Override
    public List<MerchantBase> queryAllEcommerceListByBizeType(Integer bizType) {
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andBizTypeEqualTo(bizType.byteValue());
        List<MerchantBase> merchantBaseListTotal = new ArrayList<>();

        List<AppBizLicense> licenseList = appBizLicenseMapper.selectByExample(appBizLicenseCriteria);

        for (AppBizLicense appBizLicense : licenseList) {
            MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
            merchantBaseCriteria.createCriteria().andAppIdEqualTo(appBizLicense.getAppId());
            List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
            merchantBaseListTotal.addAll(merchantBaseList);
        }
        logger.info("电商列表数据查询返回结果：{}", merchantBaseListTotal.toString());

        return merchantBaseListTotal;


    }
}
