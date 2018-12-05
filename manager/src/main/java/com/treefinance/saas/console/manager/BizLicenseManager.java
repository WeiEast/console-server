package com.treefinance.saas.console.manager;

import com.treefinance.saas.console.manager.domain.BizLicenseInfoBO;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/11/23 19:14
 */
public interface BizLicenseManager {

    /**
     * search all app's licenses
     *
     * @return list of {@link BizLicenseInfoBO}
     */
    List<BizLicenseInfoBO> listAppLicenses();

    /**
     * search all app's licenses that associated with the given <code>bizType</code>
     *
     * @param bizType the business type to search
     * @return list of {@link BizLicenseInfoBO}
     */
    List<BizLicenseInfoBO> listAppLicensesByBizType(@Nonnull Byte bizType);

    /**
     * search all app licenses and return its appIds.
     * 
     * @return list of appId
     */
    List<String> listAppIds();

    /**
     * search all app licenses that associated with the given <code>bizType</code> and return its appIds.
     *
     * @param bizType the business type to search
     * @return list of appId
     */
    List<String> listAppIdsByBizType(@Nonnull Byte bizType);

    List<BizLicenseInfoBO> listValidAppLicensesByAppId(@Nonnull String appId);
}
