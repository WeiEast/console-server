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

import com.alibaba.fastjson.JSON;
import com.datatrees.toolkits.util.crypto.AES;
import com.datatrees.toolkits.util.crypto.RSA;
import com.datatrees.toolkits.util.crypto.key.SimpleKeyPair;
import com.treefinance.saas.management.console.common.domain.Result;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jerry
 * @since 19:14 25/04/2017
 */
@Component
public class AppLicenseService {

    private static final Logger logger = LoggerFactory.getLogger(AppLicenseService.class);

    private final static String APPID_SUFFIX = "saas_gateway_app_license:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 根据传入的appId查找对应的app许可
     *
     * @param appId 第三方的appId
     */
    @Cacheable(value = "DAY", key = "'saas_gateway_app_license:'+#appId")
    public AppLicenseDTO selectOneByAppId(String appId) {
        String key = APPID_SUFFIX + appId;
        AppLicenseDTO result = null;
        if (stringRedisTemplate.hasKey(key)) {
            result = JSON.parseObject(stringRedisTemplate.opsForValue().get(key), AppLicenseDTO.class);
        }
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
        AppLicenseDTO license = Helper.generateLicense(appId);
        String key = APPID_SUFFIX + appId;
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(license));
        logger.info("generateAppLicense : key={},license={}", key, JSON.toJSONString(license));
        return null;
    }


    /**
     * 辅助工具类
     */
    public static class Helper {
        /**
         * 生成许可
         *
         * @return
         */
        public static AppLicenseDTO generateLicense(String appId) {
            AppLicenseDTO license = new AppLicenseDTO();
            license.setAppId(appId);

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
