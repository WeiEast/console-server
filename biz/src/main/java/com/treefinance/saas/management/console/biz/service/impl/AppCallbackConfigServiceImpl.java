package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.management.console.biz.service.AppCallbackConfigService;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.CallbackLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackBizVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import com.treefinance.saas.management.console.dao.mapper.AppCallbackBizMapper;
import com.treefinance.saas.management.console.dao.mapper.AppCallbackConfigMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/21.
 */
@Service
public class AppCallbackConfigServiceImpl implements AppCallbackConfigService {

    private static final Logger logger = LoggerFactory.getLogger(AppCallbackConfigServiceImpl.class);


    @Autowired
    private AppCallbackConfigMapper appCallbackConfigMapper;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private AppLicenseService appLicenseService;
    @Autowired
    private AppCallbackBizMapper appCallbackBizMapper;
    @Autowired
    private AppBizTypeMapper appBizTypeMapper;


    @Override
    public Result<Map<String, Object>> getList(PageRequest request) {
        List<AppCallbackConfigVO> resultList = Lists.newArrayList();
        long total = appCallbackConfigMapper.countByExample(null);
        if (total <= 0) {
            return Results.newSuccessPageResult(request, 0, resultList);
        }
        AppCallbackConfigCriteria configCriteria = new AppCallbackConfigCriteria();
        configCriteria.setOrderByClause("CreateTime desc");
        configCriteria.setOffset(request.getOffset());
        configCriteria.setLimit(request.getPageSize());
        List<AppCallbackConfig> configList = appCallbackConfigMapper.selectPaginationByExample(configCriteria);
        if (CollectionUtils.isEmpty(configList)) {
            return Results.newSuccessPageResult(request, total, resultList);
        }
        List<String> appIdList = configList.stream().map(AppCallbackConfig::getAppId).distinct().collect(Collectors.toList());
        MerchantBaseCriteria baseCriteria = new MerchantBaseCriteria();
        baseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(baseCriteria);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        List<Integer> callbackIdList = configList.stream().map(AppCallbackConfig::getId).collect(Collectors.toList());
        AppCallbackBizCriteria relaCriteria = new AppCallbackBizCriteria();
        relaCriteria.createCriteria().andCallbackIdIn(callbackIdList);
        List<AppCallbackBiz> relaList = appCallbackBizMapper.selectByExample(relaCriteria);
        Map<Integer, List<AppCallbackBiz>> relaMap = relaList.stream().collect(Collectors.groupingBy(AppCallbackBiz::getCallbackId));

        configList.forEach(config -> {
            AppCallbackConfigVO vo = new AppCallbackConfigVO();
            BeanUtils.copyProperties(config, vo);
            MerchantBase base = merchantBaseMap.get(config.getAppId());
            if (base == null) {
                logger.error("app_callback_config表中appId={}的商户在merchant_base中未找到对应记录", config.getAppId());
            } else {
                vo.setAppName(base.getAppName());
            }
            vo.setNotifyModelName(wrapNotifyModelName(config.getNotifyModel()));

            List<AppCallbackBiz> relas = relaMap.get(config.getId());
            List<AppCallbackBizVO> relaVOs = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(relas)) {
                for (AppCallbackBiz rela : relas) {
                    AppCallbackBizVO relaVO = new AppCallbackBizVO();
                    relaVO.setBizType(rela.getBizType());
                    relaVO.setBizName(wrapBizTypeName(rela.getBizType()));
                    relaVOs.add(relaVO);
                }

            }
            relaVOs.stream().sorted((o1, o2) -> o1.getBizType().compareTo(o2.getBizType())).collect(Collectors.toList());
            vo.setBizTypes(relaVOs);
            if (config.getIsNewKey() == 0) {
                AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(config.getAppId());
                if (appLicenseDTO == null) {
                    logger.error("appId={}未查询到对应的appLicenseKey", config.getAppId());
                } else {
                    vo.setDataSecretKey(appLicenseDTO.getDataSecretKey());
                }
            } else {
                CallbackLicenseDTO callbackLicenseDTO = appLicenseService.selectCallbackLicenseById(config.getId());
                if (callbackLicenseDTO == null) {
                    logger.error("Id={}未查询到对应的call_back配置的dataSecretKey", config.getId());
                } else {
                    vo.setDataSecretKey(callbackLicenseDTO.getDataSecretKey());
                }
            }

            resultList.add(vo);

        });
        return Results.newSuccessPageResult(request, total, resultList);
    }

    @Override
    public AppCallbackConfigVO getAppCallbackConfigById(Integer id) {
        AppCallbackConfig config = appCallbackConfigMapper.selectByPrimaryKey(id);
        if (config == null) {
            return null;
        }
        MerchantBaseCriteria baseCriteria = new MerchantBaseCriteria();
        baseCriteria.createCriteria().andAppIdEqualTo(config.getAppId());
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(baseCriteria);

        AppCallbackBizCriteria relaCriteria = new AppCallbackBizCriteria();
        relaCriteria.createCriteria().andCallbackIdEqualTo(config.getId());
        List<AppCallbackBiz> relaList = appCallbackBizMapper.selectByExample(relaCriteria);

        AppCallbackConfigVO vo = new AppCallbackConfigVO();
        BeanUtils.copyProperties(config, vo);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            logger.error("app_callback_config表中appId={}的商户在merchant_base中未找到对应记录", config.getAppId());
        } else {
            vo.setAppName(merchantBaseList.get(0).getAppName());
        }
        List<AppCallbackBizVO> relaVOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(relaList)) {
            for (AppCallbackBiz rela : relaList) {
                AppCallbackBizVO relaVO = new AppCallbackBizVO();
                relaVO.setBizType(rela.getBizType());
                relaVO.setBizName(wrapBizTypeName(rela.getBizType()));
                relaVOList.add(relaVO);
            }
        }
        vo.setBizTypes(relaVOList);
        vo.setNotifyModelName(wrapNotifyModelName(config.getNotifyModel()));
        if (config.getIsNewKey() == 0) {
            AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(config.getAppId());
            if (appLicenseDTO == null) {
                logger.error("appId={}未查询到对应的appLicenseKey", config.getAppId());
            } else {
                vo.setDataSecretKey(appLicenseDTO.getDataSecretKey());
            }
        } else {
            CallbackLicenseDTO callbackLicenseDTO = appLicenseService.selectCallbackLicenseById(config.getId());
            if (callbackLicenseDTO == null) {
                logger.error("Id={}未查询到对应的call_back配置的dataSecretKey", config.getId());
            } else {
                vo.setDataSecretKey(callbackLicenseDTO.getDataSecretKey());
            }
        }
        return vo;
    }

    @Override
    @Transactional
    public Integer add(AppCallbackConfigVO appCallbackConfigVO) {
        logger.info("添加回调配置信息 appCallbackConfigVO={}", JSON.toJSONString(appCallbackConfigVO));
        if (StringUtils.isBlank(appCallbackConfigVO.getAppId())) {
            throw new BizException("appId不能为空");
        }
        if (StringUtils.isBlank(appCallbackConfigVO.getUrl())) {
            throw new BizException("回调地址URL不能为空");
        }
        if (CollectionUtils.isEmpty(appCallbackConfigVO.getBizTypes())) {
            throw new BizException("业务类型不能为空");
        }

        AppCallbackConfig appCallbackConfig = new AppCallbackConfig();
        BeanUtils.copyProperties(appCallbackConfigVO, appCallbackConfig);
        appCallbackConfig.setVersion((byte) 1);
        appCallbackConfigMapper.insertSelective(appCallbackConfig);
        if (Optional.fromNullable(appCallbackConfigVO.getIsNewKey()).or((byte) 0) == 1) {
            appLicenseService.generateCallbackLicense(appCallbackConfig.getId());
        }
        List<Byte> bizTypeList = appCallbackConfigVO.getBizTypes().stream().map(AppCallbackBizVO::getBizType).collect(Collectors.toList());
        if (bizTypeList.contains((byte) 0)) {
            AppCallbackBiz rela = new AppCallbackBiz();
            rela.setId(UidGenerator.getId());
            rela.setCallbackId(appCallbackConfig.getId());
            rela.setBizType((byte) 0);
            appCallbackBizMapper.insertSelective(rela);
        } else {
            for (Byte bizType : bizTypeList) {
                AppCallbackBiz rela = new AppCallbackBiz();
                rela.setId(UidGenerator.getId());
                rela.setCallbackId(appCallbackConfig.getId());
                rela.setBizType(bizType);
                appCallbackBizMapper.insertSelective(rela);
            }
        }
        return appCallbackConfig.getId();
    }

    @Override
    @Transactional
    public void update(AppCallbackConfigVO appCallbackConfigVO) {
        logger.info("更新回调配置信息 appCallbackConfigVO={}", JSON.toJSONString(appCallbackConfigVO));
        if (Optional.fromNullable(appCallbackConfigVO.getId()).or(0) <= 0) {
            throw new BizException("Id不能为空");
        }
        AppCallbackConfig appCallbackConfig = new AppCallbackConfig();
        BeanUtils.copyProperties(appCallbackConfigVO, appCallbackConfig);
        appCallbackConfigMapper.updateByPrimaryKeySelective(appCallbackConfig);
        byte isNewKey = Optional.fromNullable(appCallbackConfig.getIsNewKey()).or((byte) 0);
        if (isNewKey == 1) {
            appLicenseService.generateCallbackLicense(appCallbackConfig.getId());
        } else if (isNewKey == 0) {
            appLicenseService.removeCallbackLicenseById(appCallbackConfig.getId());
        }
        if (!CollectionUtils.isEmpty(appCallbackConfigVO.getBizTypes())) {
            AppCallbackBizCriteria relaCriteria = new AppCallbackBizCriteria();
            relaCriteria.createCriteria().andCallbackIdEqualTo(appCallbackConfigVO.getId());
            appCallbackBizMapper.deleteByExample(relaCriteria);
            List<Byte> bizTypeList = appCallbackConfigVO.getBizTypes().stream().map(AppCallbackBizVO::getBizType).collect(Collectors.toList());
            if (bizTypeList.contains((byte) 0)) {
                AppCallbackBiz rela = new AppCallbackBiz();
                rela.setId(UidGenerator.getId());
                rela.setCallbackId(appCallbackConfig.getId());
                rela.setBizType((byte) 0);
                appCallbackBizMapper.insertSelective(rela);
            } else {
                for (Byte bizType : bizTypeList) {
                    AppCallbackBiz rela = new AppCallbackBiz();
                    rela.setId(UidGenerator.getId());
                    rela.setCallbackId(appCallbackConfig.getId());
                    rela.setBizType(bizType);
                    appCallbackBizMapper.insertSelective(rela);
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteAppCallbackConfigById(Integer id) {
        appCallbackConfigMapper.deleteByPrimaryKey(id);
        appLicenseService.removeCallbackLicenseById(id);
        AppCallbackBizCriteria relaCriteria = new AppCallbackBizCriteria();
        relaCriteria.createCriteria().andCallbackIdEqualTo(id);
        appCallbackBizMapper.deleteByExample(relaCriteria);
    }

    @Override
    public List<AppBizTypeVO> getCallbackBizList() {
        List<AppBizTypeVO> appBizTypeVOList = Lists.newArrayList();
        AppBizTypeCriteria criteria = new AppBizTypeCriteria();
        criteria.setOrderByClause("bizType asc");
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(appBizTypeList)) {
            return appBizTypeVOList;
        }
        appBizTypeVOList = com.treefinance.saas.management.console.common.utils.BeanUtils.convertList(appBizTypeList, AppBizTypeVO.class);

        //在回调中需要增加一个全部类型
        AppBizTypeVO allVO = new AppBizTypeVO();
        allVO.setBizType((byte) 0);
        allVO.setBizName("全部");
        appBizTypeVOList.add(0, allVO);
        return appBizTypeVOList;
    }

    @Override
    public Boolean testUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        boolean flag = urlValidator.isValid(url);
        if (flag) {
            try {
                HttpClientUtils.doOptions(url);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private String wrapNotifyModelName(Byte notifyModel) {
        if (notifyModel == 0) {
            return "URL";
        }
        if (notifyModel == 1) {
            return "数据";
        }
        return "未定义";
    }

    private String wrapBizTypeName(Byte bizType) {
        if (bizType == 0) {
            return "全部";
        } else {
            return EBizType.getName(bizType);
        }
    }

}
