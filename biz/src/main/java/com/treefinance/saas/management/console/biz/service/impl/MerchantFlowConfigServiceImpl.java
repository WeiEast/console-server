package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.treefinance.commonservice.uid.UidGenerator;
import com.treefinance.saas.assistant.config.model.ConfigUpdateBuilder;
import com.treefinance.saas.assistant.config.model.ConfigUpdateModel;
import com.treefinance.saas.assistant.config.plugin.ConfigUpdatePlugin;
import com.treefinance.saas.management.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.management.console.common.enumeration.EServiceTag;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.management.console.dao.entity.MerchantBaseCriteria;
import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfig;
import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfigCriteria;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.MerchantFlowConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/9/28.
 */
@Service
public class MerchantFlowConfigServiceImpl implements MerchantFlowConfigService {

    private static Logger logger = LoggerFactory.getLogger(MerchantFlowConfigService.class);

    @Autowired
    private MerchantFlowConfigMapper merchantFlowConfigMapper;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private ConfigUpdatePlugin configUpdatePlugin;

    @Override
    public List<MerchantFlowConfigVO> getList() {
        List<MerchantFlowConfigVO> result = Lists.newArrayList();
        MerchantFlowConfigCriteria configCriteria = new MerchantFlowConfigCriteria();
        List<MerchantFlowConfig> list = merchantFlowConfigMapper.selectByExample(configCriteria);
        if (CollectionUtils.isEmpty(list)) {
            return result;
        }
        List<String> appIdList = list.stream().map(MerchantFlowConfig::getAppId).collect(Collectors.toList());
        MerchantBaseCriteria baseCriteria = new MerchantBaseCriteria();
        baseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> baseList = merchantBaseMapper.selectByExample(baseCriteria);
        //<appId,appName>
        Map<String, String> baseMap = baseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, MerchantBase::getAppName));
        for (MerchantFlowConfig config : list) {
            MerchantFlowConfigVO vo = new MerchantFlowConfigVO();
            vo.setId(config.getId());
            String appName = baseMap.get(config.getAppId());
            if (StringUtils.isNotBlank(appName)) {
                vo.setAppName(appName);
            }
            vo.setServiceTag(config.getServiceTag());
            vo.setServiceTagName(EServiceTag.getDesc(config.getServiceTag()));
            result.add(vo);
        }
        return result;
    }

    @Override
    @Transactional
    public void batchUpdate(List<MerchantFlowConfigVO> list) {
        List<ConfigUpdateModel> modelList = Lists.newArrayList();

        List<Long> idList = list.stream().map(MerchantFlowConfigVO::getId).collect(Collectors.toList());
        MerchantFlowConfigCriteria configCriteria = new MerchantFlowConfigCriteria();
        configCriteria.createCriteria().andIdIn(idList);
        List<MerchantFlowConfig> needUpdateList = merchantFlowConfigMapper.selectByExample(configCriteria);
        //<id,appId>
        Map<Long, String> map = needUpdateList.stream().collect(Collectors.toMap(MerchantFlowConfig::getId, MerchantFlowConfig::getAppId));

        for (MerchantFlowConfigVO vo : list) {
            //更新db
            MerchantFlowConfig config = new MerchantFlowConfig();
            config.setId(vo.getId());
            config.setServiceTag(vo.getServiceTag());
            merchantFlowConfigMapper.updateByPrimaryKeySelective(config);

            String appId = map.get(vo.getId());
            if (StringUtils.isBlank(appId)) {
                continue;
            }
            //发送消息 // TODO: 2017/9/28 haojiahong 配置有问题
            ConfigUpdateModel model = ConfigUpdateBuilder.newBuilder()
                    .configDesc("更新商户流量分配")
                    .configId(appId).build();
            modelList.add(model);
        }
        // 发送配置变更消息
//        configUpdatePlugin.sendMessageList(modelList);
        logger.info("发送更新商户流量分配配置消息,modelList={}", JSON.toJSONString(modelList));


    }

    @Override
    public void init() {
        MerchantBaseCriteria baseCriteria = new MerchantBaseCriteria();
        List<MerchantBase> baseList = merchantBaseMapper.selectByExample(baseCriteria);
        if (CollectionUtils.isEmpty(baseList)) {
            return;
        }
        MerchantFlowConfigCriteria configCriteria = new MerchantFlowConfigCriteria();
        List<MerchantFlowConfig> configList = merchantFlowConfigMapper.selectByExample(configCriteria);
        List<String> appIdList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(configList)) {
            appIdList = configList.stream().map(MerchantFlowConfig::getAppId).collect(Collectors.toList());
        }
        List<MerchantFlowConfig> list = Lists.newArrayList();
        for (MerchantBase merchantBase : baseList) {
            if (appIdList.contains(merchantBase.getAppId())) {
                continue;
            }
            MerchantFlowConfig config = new MerchantFlowConfig();
            config.setId(UidGenerator.getId());
            config.setAppId(merchantBase.getAppId());
            config.setServiceTag(EServiceTag.PRODUCT.getTag());
            config.setCreateTime(new Date());
            list.add(config);
        }
        merchantFlowConfigMapper.batchInsert(list);
        logger.info("初始化商户流量分配配置,list={}", JSON.toJSONString(list));

    }
}
