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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        String key = APPID_SUFFIX + "*";
        Set<String> keySet = stringRedisTemplate.keys(key);
        if (CollectionUtils.isEmpty(keySet)) {
            return Results.newSuccessPageResult(request, 0L, appLicenseVOList);
        }
        List<String> resultStrList = stringRedisTemplate.opsForValue().multiGet(keySet);
        if (CollectionUtils.isEmpty(resultStrList)) {
            return Results.newSuccessPageResult(request, 0L, appLicenseVOList);
        }
        List<AppLicenseDTO> appLicenseDTOList = Lists.newArrayList();
        for (String result : resultStrList) {
            AppLicenseDTO appLicenseDTO = JSON.parseObject(result, AppLicenseDTO.class);
            appLicenseDTOList.add(appLicenseDTO);
        }
        List<String> appIdList = appLicenseDTOList.stream().map(AppLicenseDTO::getAppId).collect(Collectors.toList());
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        for (AppLicenseDTO appLicenseDTO : appLicenseDTOList) {
            AppLicenseVO appLicenseVO = new AppLicenseVO();
            BeanUtils.copyProperties(appLicenseDTO, appLicenseVO);
            MerchantBase merchantBase = merchantBaseMap.get(appLicenseDTO.getAppId());
            if (merchantBase == null) {
                logger.info("key列表查询中,appId={}在merchant_base表中未找到对应商户信息", appLicenseDTO.getAppId());
                continue;
            }
            if (appLicenseVO.getCreateTime() == null) {
                appLicenseVO.setCreateTime(Long.valueOf(DateUtils.date2TimeStamp(merchantBase.getCreateTime())));
            }
            appLicenseVO.setAppName(merchantBase.getAppName());
            appLicenseVOList.add(appLicenseVO);
        }


        final long total = appLicenseVOList.size();
        int start = request.getOffset();
        int end = request.getOffset() + request.getPageSize();
        if (end > total) {
            end = (int) total;
        }
        Collections.sort(appLicenseVOList, (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        appLicenseVOList = appLicenseVOList.subList(start, end);
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
