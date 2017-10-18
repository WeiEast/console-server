package com.treefinance.saas.management.console.biz.service.dao.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.assistant.config.model.ConfigUpdateBuilder;
import com.treefinance.saas.assistant.config.model.ConfigUpdateModel;
import com.treefinance.saas.assistant.config.model.enums.ConfigType;
import com.treefinance.saas.management.console.biz.service.dao.MerchantFlowConfigDao;
import com.treefinance.saas.management.console.common.domain.vo.MerchantFlowConfigVO;
import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfig;
import com.treefinance.saas.management.console.dao.entity.MerchantFlowConfigCriteria;
import com.treefinance.saas.management.console.dao.mapper.MerchantFlowConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/10/16.
 */
@Service
public class MerchantFlowConfigDaoImpl implements MerchantFlowConfigDao {
    @Autowired
    private MerchantFlowConfigMapper merchantFlowConfigMapper;

    @Override
    @Transactional
    public List<ConfigUpdateModel> batchUpdate(List<MerchantFlowConfigVO> list) {
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
            //拼装消息待发送
            ConfigUpdateModel model = ConfigUpdateBuilder.newBuilder()
                    .configType(ConfigType.MERCHANT_OTHER)
                    .configDesc("更新商户流量分配")
                    .configId(appId).build();
            modelList.add(model);
        }
        return modelList;
    }
}
