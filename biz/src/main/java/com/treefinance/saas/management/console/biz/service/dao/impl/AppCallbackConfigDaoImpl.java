package com.treefinance.saas.management.console.biz.service.dao.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.management.console.biz.service.dao.AppCallbackConfigDao;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackBizVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCallbackConfigVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.dao.entity.AppCallbackBiz;
import com.treefinance.saas.management.console.dao.entity.AppCallbackBizCriteria;
import com.treefinance.saas.management.console.dao.entity.AppCallbackConfig;
import com.treefinance.saas.management.console.dao.entity.AppCallbackConfigBackupCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppCallbackBizMapper;
import com.treefinance.saas.management.console.dao.mapper.AppCallbackConfigBackupMapper;
import com.treefinance.saas.management.console.dao.mapper.AppCallbackConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/10/16.
 */
@Service
public class AppCallbackConfigDaoImpl implements AppCallbackConfigDao {

    private static final Logger logger = LoggerFactory.getLogger(AppCallbackConfigDao.class);

    @Autowired
    private AppCallbackConfigMapper appCallbackConfigMapper;
    @Autowired
    private AppLicenseService appLicenseService;
    @Autowired
    private AppCallbackBizMapper appCallbackBizMapper;
    @Autowired
    private AppCallbackConfigBackupMapper appCallbackConfigBackupMapper;

    @Override
    @Transactional
    public Integer addCallbackConfig(AppCallbackConfigVO appCallbackConfigVO) {
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
        appCallbackConfig.setDataType(appCallbackConfigVO.getDataTypeVO() == null ? 0 : appCallbackConfigVO.getDataTypeVO().getCode());
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
    public void updateCallbackConfig(AppCallbackConfigVO appCallbackConfigVO) {
        logger.info("更新回调配置信息 appCallbackConfigVO={}", JSON.toJSONString(appCallbackConfigVO));
        if (Optional.fromNullable(appCallbackConfigVO.getId()).or(0) <= 0) {
            throw new BizException("Id不能为空");
        }
        AppCallbackConfig appCallbackConfig = new AppCallbackConfig();
        BeanUtils.copyProperties(appCallbackConfigVO, appCallbackConfig);
        if (appCallbackConfigVO.getDataTypeVO() != null) {
            appCallbackConfig.setDataType(appCallbackConfigVO.getDataTypeVO().getCode());
        }
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

        AppCallbackConfigBackupCriteria backupCriteria = new AppCallbackConfigBackupCriteria();
        backupCriteria.createCriteria().andCallBackConfigIdEqualTo(id);
        appCallbackConfigBackupMapper.deleteByExample(backupCriteria);


    }
}
