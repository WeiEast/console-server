package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmConfigService;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigVO;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AlarmBasicConfigurationRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmRO;
import com.treefinance.saas.monitor.facade.service.autoalarm.AlarmBasicConfigurationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengtong
 * @date 18/7/19 16:07
 */
@Service
public class AlarmConfigServiceImpl implements AlarmConfigService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlarmBasicConfigurationFacade alarmBasicConfigurationFacade;

    @Override
    public SaasResult queryAlarmConfigList(AlarmConfigRequest request) {

        AlarmBasicConfigurationRequest configurationRequest = new AlarmBasicConfigurationRequest();

        configurationRequest.setName(request.getName());
        configurationRequest.setRunEnv(request.getRunEnv());
        configurationRequest.setPageNumber(request.getPageNumber());
        configurationRequest.setPageSize(request.getPageSize());

        MonitorResult<List<AsAlarmRO>> result = null;
        try {
            logger.info("向monitor请求数据，request：{}", configurationRequest);
            result = alarmBasicConfigurationFacade.queryAlarmConfigurationList(configurationRequest);
            logger.info("从monitor获取数据，result：{}", result);
        } catch (Exception e) {
            logger.info("请求monitor失败", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AsAlarmRO> list = result.getData();

        List<AlarmConfigVO> returnList = DataConverterUtils.convert(list, AlarmConfigVO.class);

        return Results.newPageResult(request, returnList.size(), returnList);
    }
}
