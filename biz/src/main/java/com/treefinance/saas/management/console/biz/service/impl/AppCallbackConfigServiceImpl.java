package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.assistant.config.model.ConfigUpdateBuilder;
import com.treefinance.saas.assistant.config.model.enums.ConfigType;
import com.treefinance.saas.assistant.config.model.enums.UpdateType;
import com.treefinance.saas.assistant.config.plugin.ConfigUpdatePlugin;
import com.treefinance.saas.management.console.biz.service.dao.AppCallbackConfigDao;
import com.treefinance.saas.management.console.biz.service.AppCallbackConfigService;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.CallbackLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppBizTypeVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackBizVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackDataTypeVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.*;
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
    @Autowired
    private ConfigUpdatePlugin configUpdatePlugin;
    @Autowired
    private AppCallbackConfigDao appCallbackConfigDao;
    @Autowired
    private AppCallbackConfigBackupMapper appCallbackConfigBackupMapper;
    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;


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
            relaVOs = relaVOs.stream().sorted((o1, o2) -> o1.getBizType().compareTo(o2.getBizType())).collect(Collectors.toList());
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
            AppCallbackDataTypeVO dataTypeVO = new AppCallbackDataTypeVO();
            dataTypeVO.setCode(config.getDataType());
            dataTypeVO.setText(ECallBackDataType.getText(config.getDataType()));
            vo.setDataTypeVO(dataTypeVO);
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
        AppCallbackDataTypeVO dataTypeVO = new AppCallbackDataTypeVO();
        dataTypeVO.setCode(config.getDataType());
        dataTypeVO.setText(ECallBackDataType.getText(config.getDataType()));
        vo.setDataTypeVO(dataTypeVO);
        return vo;
    }

    @Override
    public Integer add(AppCallbackConfigVO appCallbackConfigVO) {
        Integer configId = appCallbackConfigDao.addCallbackConfig(appCallbackConfigVO);
        // 发送配置变更消息
        configUpdatePlugin.sendMessage(ConfigUpdateBuilder.newBuilder()
                .configType(ConfigType.MERCHANT_CALLBACK)
                .configDesc("新增回调配置")
                .updateType(UpdateType.CREATE)
                .configId(appCallbackConfigVO.getAppId())
                .configData(appCallbackConfigVO).build());
        return configId;
    }

    @Override
    public void update(AppCallbackConfigVO appCallbackConfigVO) {
        appCallbackConfigDao.updateCallbackConfig(appCallbackConfigVO);
        AppCallbackConfigVO _appCallbackConfigVO = getAppCallbackConfigById(appCallbackConfigVO.getId());

        if (_appCallbackConfigVO != null) {
            configUpdatePlugin.sendMessage(ConfigUpdateBuilder.newBuilder()
                    .configType(ConfigType.MERCHANT_CALLBACK)
                    .configDesc("更新回调配置")
                    .configId(_appCallbackConfigVO.getAppId())
                    .configData(appCallbackConfigVO).build());
        }
    }

    @Override
    public void deleteAppCallbackConfigById(Integer id) {

        AppCallbackConfigVO appCallbackConfigVO = getAppCallbackConfigById(id);
        appCallbackConfigDao.deleteAppCallbackConfigById(id);
        // 发送配置变更消息
        if (appCallbackConfigVO != null) {
            configUpdatePlugin.sendMessage(ConfigUpdateBuilder.newBuilder()
                    .configType(ConfigType.MERCHANT_CALLBACK)
                    .configDesc("删除回调配置")
                    .updateType(UpdateType.DELETE)
                    .configId(appCallbackConfigVO.getAppId())
                    .configData(appCallbackConfigVO).build());
        }
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

    @Override
    public List<AppCallbackDataTypeVO> getCallbackDataTypeList() {
        List<AppCallbackDataTypeVO> voList = Lists.newArrayList();
        for (ECallBackDataType item : ECallBackDataType.values()) {
            AppCallbackDataTypeVO vo = new AppCallbackDataTypeVO();
            vo.setCode(item.getCode());
            vo.setText(item.getText());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    @Transactional
    public void initHistorySecretKey() {
        AppCallbackConfigCriteria configCriteria = new AppCallbackConfigCriteria();
        configCriteria.createCriteria().andIsNewKeyEqualTo((byte) 1);
        List<AppCallbackConfig> configList = appCallbackConfigMapper.selectByExample(configCriteria);
        for (AppCallbackConfig config : configList) {
            CallbackLicenseDTO callbackLicenseDTO = appLicenseService.selectCallbackLicenseById(config.getId());
            if (callbackLicenseDTO == null) {
                logger.info("初始化商户历史回调密钥时,appId={},configId={}的回调配置在redis中未查询到回调密钥.", config.getAppId(), config.getId());
            } else {

                AppCallbackConfigBackupCriteria backupCriteria = new AppCallbackConfigBackupCriteria();
                backupCriteria.createCriteria().andCallBackConfigIdEqualTo(config.getId());
                List<AppCallbackConfigBackup> list = appCallbackConfigBackupMapper.selectByExample(backupCriteria);
                if (!CollectionUtils.isEmpty(list)) {
                    logger.info("初始化商户历史回调密钥时,appId={},configId={}的回调配置在backup备份表中已存在,不再初始化.", config.getAppId(), config.getId());
                    continue;
                }
                AppCallbackConfigBackup backup = new AppCallbackConfigBackup();
                backup.setId(UidGenerator.getId());
                backup.setCreateTime(config.getCreateTime());
                backup.setCallBackConfigId(config.getId());
                backup.setDataSecretKey(iSecurityCryptoService.encrypt(callbackLicenseDTO.getDataSecretKey(), EncryptionIntensityEnum.NORMAL));
                appCallbackConfigBackupMapper.insertSelective(backup);
            }

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
