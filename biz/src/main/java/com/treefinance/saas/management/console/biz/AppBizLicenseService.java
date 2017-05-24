package com.treefinance.saas.management.console.biz;
import com.treefinance.saas.management.console.dao.entity.AppBizLicense;
import com.treefinance.saas.management.console.dao.entity.AppBizLicenseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizLicenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by luoyihua on 2017/5/10.
 */
@Component
public class AppBizLicenseService {

    @Autowired
    private AppBizLicenseMapper appBizLicenseMapper;

    public List<AppBizLicense> getByLicenseId(Integer licenseId) {
        AppBizLicenseCriteria appBizLicenseCriteria = new AppBizLicenseCriteria();
        appBizLicenseCriteria.createCriteria().andLicenseIdEqualTo(licenseId);
        return appBizLicenseMapper.selectByExample(appBizLicenseCriteria);
    }
}
