/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.treefinance.saas.management.console.biz;

import com.datatrees.toolkits.util.crypto.AES;
import com.datatrees.toolkits.util.crypto.RSA;
import com.datatrees.toolkits.util.crypto.key.SimpleKeyPair;
import com.treefinance.saas.management.console.common.domain.Result;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.AppLicense;
import com.treefinance.saas.management.console.dao.entity.AppLicenseCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppLicenseMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Jerry
 * @since 19:14 25/04/2017
 */
@Component
public class AppLicenseService {

    @Autowired
    private AppLicenseMapper appLicenseMapper;

    /**
     * 根据传入的appId查找对应的app许可
     *
     * @param appId 第三方的appId
     * @return {@link AppLicense}
     */
    @Cacheable(value = "DAY", key = "'saas_gateway_app_license:'+#appId")
    public AppLicenseDTO selectOneByAppId(String appId) {
        AppLicenseCriteria criteria = new AppLicenseCriteria();
        criteria.createCriteria().andAppIdEqualTo(appId);
        criteria.setOrderByClause("Id desc limit 1");

        List<AppLicense> licenseList = appLicenseMapper.selectByExample(criteria);

        return licenseList.isEmpty() ? null : BeanUtils.convert(licenseList.get(0), new AppLicenseDTO());
    }

    /**
     * app许可
     *
     * @return
     */
    public Result<String> generateAppLicense() {
        String appId = Helper.generateAppId();
        AppLicense license = Helper.generateLicense(appId);
        appLicenseMapper.insert(license);
        Result<String> result = new Result<>();
        result.setData(appId);
        return result;
    }

    /**
     * 依据给定的APPID，生成对应app许可
     *
     * @param appId
     * @return
     */
    public Result<Integer> generateAppLicenseByAppId(String appId) {
        // 验证是否已经含有license
        AppLicenseDTO appLicenseDTO = this.selectOneByAppId(appId);
        if (appLicenseDTO != null) {
            return new Result("授权许可已经存在！");
        }
        AppLicense license = Helper.generateLicense(appId);
        appLicenseMapper.insert(license);
        return new Result<>(license.getId());
    }


    /**
     * 辅助工具类
     */
    public static class Helper {
        /**
         * 生成APPID
         *
         * @return
         */
        public static String generateAppId() {
            return RandomStringUtils.randomAlphanumeric(16);
        }

        /**
         * 生成许可
         *
         * @return
         */
        public static AppLicense generateLicense(String appId) {
            Date now = new Date();
            AppLicense license = new AppLicense();
            license.setAppId(appId);
            license.setCreateTime(now);
            license.setLastUpdateTime(now);
            // sdk密钥
            SimpleKeyPair sdk = RSA.generateKey();
            license.setSdkPrivateKey(sdk.getPrivateKeyString());
            license.setSdkPublicKey(sdk.getPublicKeyString());
            // server密钥
            SimpleKeyPair server = RSA.generateKey();
            license.setServerPrivateKey(server.getPrivateKeyString());
            license.setServerPublicKey(server.getPublicKeyString());
            // 数据密钥
            String data = AES.generateKeyString();
            license.setDataSecretKey(data);
            return license;
        }
    }
}
