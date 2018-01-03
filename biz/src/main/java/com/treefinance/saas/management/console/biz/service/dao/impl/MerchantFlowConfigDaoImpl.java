package com.treefinance.saas.management.console.biz.service.dao.impl;

import com.treefinance.saas.assistant.variable.notify.server.VariableMessageNotifyService;
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
    @Autowired
    private VariableMessageNotifyService variableMessageNotifyService;

    @Override
    @Transactional
    public void batchUpdate(List<MerchantFlowConfigVO> list) {
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
            variableMessageNotifyService.sendVariableMessage("merchant-flow", "update", appId);
        }
    }
}
