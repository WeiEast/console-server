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

package com.treefinance.saas.management.console.biz.service;

import com.alibaba.fastjson.JSON;
import com.datatrees.toolkits.util.crypto.AES;
import com.datatrees.toolkits.util.crypto.RSA;
import com.datatrees.toolkits.util.crypto.key.SimpleKeyPair;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppLicenseVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;


    /**
     * 根据传入的appId查找对应的app许可
     *
     * @param appId 第三方的appId
     */
    public AppLicenseDTO selectOneByAppId(String appId) {
        logger.info("根据appId={}查询秘钥key", appId);
        String key = APPID_SUFFIX + appId;
        AppLicenseDTO result = null;
        if (stringRedisTemplate.hasKey(key)) {
            result = JSON.parseObject(stringRedisTemplate.opsForValue().get(key), AppLicenseDTO.class);
        }
        logger.info("根据appId={}查询秘钥key结果为result={}", appId, JSON.toJSONString(result));
        return result;
    }


    /**
     * 依据给定的APPID，生成对应app许可
     *
     * @param appId
     * @return
     */
    public Result generateAppLicenseByAppId(String appId) {
        // 验证是否已经含有license
        AppLicenseDTO appLicenseDTO = this.selectOneByAppId(appId);
        if (appLicenseDTO != null) {
            throw new BizException("授权许可已经存在！");
        }
        AppLicenseDTO license = Helper.generateLicense(appId);
        String key = APPID_SUFFIX + appId;
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(license));
        logger.info("generateAppLicense : key={},license={}", key, JSON.toJSONString(license));
        return null;
    }

    public Result removeAppLicenseByAppId(String appId) {
        logger.info("根据appId={}删除秘钥key", appId);
        String key = APPID_SUFFIX + appId;
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.opsForValue().getOperations().delete(key);
        }
        return null;
    }

    public Result<String> generateAppLicense() {
        String appId = CommonUtils.generateAppId();
        AppLicenseDTO license = Helper.generateLicense(appId);
        String key = APPID_SUFFIX + appId;
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(license));
        Result<String> result = new Result<>();
        result.setData(appId);
        logger.info("generateAppLicense : key={}, license={}", key, JSON.toJSONString(license));
        return result;
    }

    public Result<Map<String, Object>> getAppLicenseList(PageRequest request) {
        List<AppLicenseVO> appLicenseVOList = Lists.newArrayList();
        long total = merchantBaseMapper.countByExample(null);
        if (Optional.fromNullable(total).or(Long.valueOf(0)) <= 0) {
            return Results.newSuccessPageResult(request, total, appLicenseVOList);
        }
        MerchantBaseCriteria criteria = new MerchantBaseCriteria();
        criteria.setOffset(request.getOffset());
        criteria.setLimit(request.getPageSize());
        criteria.setOrderByClause("CreateTime desc");
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectPaginationByExample(criteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return Results.newSuccessPageResult(request, total, appLicenseVOList);
        }
        for (MerchantBase merchantBase : merchantBaseList) {
            String key = APPID_SUFFIX + merchantBase.getAppId();
            String result = stringRedisTemplate.opsForValue().get(key);
            AppLicenseVO appLicenseVO = new AppLicenseVO();
            if (result != null) {
                AppLicenseDTO appLicenseDTO = JSON.parseObject(result, AppLicenseDTO.class);
                appLicenseVO.setAppId(merchantBase.getAppId());
                appLicenseVO.setAppName(merchantBase.getAppName());
                appLicenseVO.setCreateTime(merchantBase.getCreateTime());
                BeanUtils.copyProperties(appLicenseDTO, appLicenseVO);
            } else {
                appLicenseVO.setAppId(merchantBase.getAppId());
                appLicenseVO.setAppName(merchantBase.getAppName());
                appLicenseVO.setCreateTime(merchantBase.getCreateTime());
                appLicenseVO.setSdkPublicKey("");
                appLicenseVO.setSdkPrivateKey("");
                appLicenseVO.setDataSecretKey("");
                appLicenseVO.setServerPrivateKey("");
                appLicenseVO.setServerPublicKey("");
            }
            appLicenseVOList.add(appLicenseVO);

        }
        return Results.newSuccessPageResult(request, total, appLicenseVOList);

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
            license.setCreateTime(System.currentTimeMillis() / 1000);
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
