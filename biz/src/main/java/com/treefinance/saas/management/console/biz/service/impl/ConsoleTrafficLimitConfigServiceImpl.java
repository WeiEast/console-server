package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.ConsoleTrafficLimitConfigService;
import com.treefinance.saas.management.console.common.domain.vo.TrafficLimitConfigVO;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.dao.entity.AppBizType;
import com.treefinance.saas.management.console.dao.entity.ConsoleTrafficLimitConfig;
import com.treefinance.saas.management.console.dao.entity.ConsoleTrafficLimitConfigCriteria;
import com.treefinance.saas.management.console.dao.mapper.AppBizTypeMapper;
import com.treefinance.saas.management.console.dao.mapper.ConsoleTrafficLimitConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/7/25.
 */
@Service
public class ConsoleTrafficLimitConfigServiceImpl implements ConsoleTrafficLimitConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleTrafficLimitConfigServiceImpl.class);


    @Autowired
    private ConsoleTrafficLimitConfigMapper consoleTrafficLimitConfigMapper;
    @Autowired
    private AppBizTypeMapper appBizTypeMapper;

    @Override
    public List<TrafficLimitConfigVO> getList() {
        List<TrafficLimitConfigVO> resultList = Lists.newArrayList();
        List<ConsoleTrafficLimitConfig> configList = consoleTrafficLimitConfigMapper.selectByExample(null);
        if (CollectionUtils.isEmpty(configList)) {
            return resultList;
        }
        List<AppBizType> appBizTypeList = appBizTypeMapper.selectByExample(null);
        Map<Byte, AppBizType> bizTypeMap = appBizTypeList.stream().collect(Collectors.toMap(AppBizType::getBizType, appBizType -> appBizType));
        for (ConsoleTrafficLimitConfig config : configList) {
            TrafficLimitConfigVO vo = new TrafficLimitConfigVO();
            vo.setRate(config.getRate());
            vo.setBizType(config.getBizType());
            AppBizType type = bizTypeMap.get(config.getBizType());
            if (type == null) {
                logger.error("流量管理中bizType={}的服务权限类型在app_biz_type中不存在", config.getBizType());
                vo.setBizName("未定义");
            } else {
                vo.setBizName(type.getBizName());
            }
            resultList.add(vo);
        }
        return resultList;
    }

    @Override
    public void updateByBizType(TrafficLimitConfigVO trafficLimitConfigVO) {
        if (trafficLimitConfigVO.getBizType() == null) {
            throw new BizException("bizType不能为空!");
        }

        ConsoleTrafficLimitConfig consoleTrafficLimitConfig = new ConsoleTrafficLimitConfig();
        consoleTrafficLimitConfig.setRate(trafficLimitConfigVO.getRate());
        ConsoleTrafficLimitConfigCriteria configCriteria = new ConsoleTrafficLimitConfigCriteria();
        configCriteria.createCriteria().andBizTypeEqualTo(trafficLimitConfigVO.getBizType());
        consoleTrafficLimitConfigMapper.updateByExampleSelective(consoleTrafficLimitConfig, configCriteria);

    }
}
