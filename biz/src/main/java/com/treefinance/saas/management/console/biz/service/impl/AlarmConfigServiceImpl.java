package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmConfigService;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.management.console.common.domain.vo.*;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.*;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.SaasWorkerRO;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmBasicConfigurationDetailRO;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmRO;
import com.treefinance.saas.monitor.facade.service.autoalarm.AlarmBasicConfigurationFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

        return Results.newPageResult(request, result.getTotalCount(), returnList);
    }

    @Override
    public SaasResult<AlarmConfigDetailVO> queryAlarmConfigDetailById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id为空或非法!");
        }
        MonitorResult<AsAlarmBasicConfigurationDetailRO> rpcResult;
        try {
            rpcResult = alarmBasicConfigurationFacade.queryAlarmConfigurationDetailById(id);
        } catch (Exception e) {
            throw new BizException("调用saas-monitor异常", e);
        }
        AlarmConfigDetailVO alarmConfigDetailVO = new AlarmConfigDetailVO();
        AsAlarmBasicConfigurationDetailRO rpcData = rpcResult.getData();
        if (rpcData == null) {
            throw new BizException("saas-monitor中未查询到id=" + id + "的预警配置");
        }
        AlarmInfoDetailVO alarmInfoDetailVO = new AlarmInfoDetailVO();
        BeanUtils.convert(rpcData.getAsAlarmRO(), alarmInfoDetailVO);
        alarmConfigDetailVO.setAlarmInfo(alarmInfoDetailVO);

        List<AlarmConstantDetailVO> alarmConstantDetailVOList
                = BeanUtils.convertList(rpcData.getAsAlarmConstantROList(), AlarmConstantDetailVO.class);
        alarmConfigDetailVO.setAlarmConstantList(alarmConstantDetailVOList);

        List<AlarmQueryDetailVO> alarmQueryDetailVOList
                = BeanUtils.convertList(rpcData.getAsAlarmQueryROList(), AlarmQueryDetailVO.class);
        alarmConfigDetailVO.setAlarmQueryList(alarmQueryDetailVOList);

        List<AlarmVariableDetailVO> alarmVariableDetailVOList
                = BeanUtils.convertList(rpcData.getAsAlarmVariableROList(), AlarmVariableDetailVO.class);
        alarmConfigDetailVO.setAlarmVariableList(alarmVariableDetailVOList);

        List<AlarmNotifyDetailVO> alarmNotifyDetailVOList
                = BeanUtils.convertList(rpcData.getAsAlarmNotifyROList(), AlarmNotifyDetailVO.class);
        alarmConfigDetailVO.setAlarmNotifyList(alarmNotifyDetailVOList);

        AlarmMsgDetailVO alarmMsgDetailVO = new AlarmMsgDetailVO();
        BeanUtils.convert(rpcData.getAsAlarmMsgRO(), alarmMsgDetailVO);
        alarmConfigDetailVO.setAlarmMsg(alarmMsgDetailVO);

        List<AlarmTriggerDetailVO> alarmTriggerDetailVOList
                = BeanUtils.convertList(rpcData.getAsAlarmTriggerROList(), AlarmTriggerDetailVO.class);
        alarmConfigDetailVO.setAlarmTriggerList(alarmTriggerDetailVOList);
        return Results.newSuccessResult(alarmConfigDetailVO);
    }

    @Override
    public SaasResult<Boolean> saveAlarmConfigDetail(AlarmConfigDetailVO alarmConfigDetailVO) {
        checkSaveAlarmConfigDetailRequest(alarmConfigDetailVO);

        AlarmBasicConfigurationDetailRequest rpcRequest = new AlarmBasicConfigurationDetailRequest();
        AsAlarmInfoRequest asAlarmInfoRequest = new AsAlarmInfoRequest();
        BeanUtils.convert(alarmConfigDetailVO.getAlarmInfo(), asAlarmInfoRequest);
        rpcRequest.setAsAlarmInfoRequest(asAlarmInfoRequest);

        List<AsAlarmConstantInfoRequest> asAlarmConstantInfoRequestList
                = BeanUtils.convertList(alarmConfigDetailVO.getAlarmConstantList(), AsAlarmConstantInfoRequest.class);
        rpcRequest.setAsAlarmConstantInfoRequestList(asAlarmConstantInfoRequestList);

        List<AsAlarmQueryInfoRequest> asAlarmQueryInfoRequestList
                = BeanUtils.convertList(alarmConfigDetailVO.getAlarmQueryList(), AsAlarmQueryInfoRequest.class);
        rpcRequest.setAsAlarmQueryInfoRequestList(asAlarmQueryInfoRequestList);

        List<AsAlarmVariableInfoRequest> asAlarmVariableInfoRequestList
                = BeanUtils.convertList(alarmConfigDetailVO.getAlarmVariableList(), AsAlarmVariableInfoRequest.class);
        rpcRequest.setAsAlarmVariableInfoRequestList(asAlarmVariableInfoRequestList);

        List<AsAlarmNotifyInfoRequest> asAlarmNotifyInfoRequestList
                = BeanUtils.convertList(alarmConfigDetailVO.getAlarmNotifyList(), AsAlarmNotifyInfoRequest.class);
        rpcRequest.setAsAlarmNotifyInfoRequestList(asAlarmNotifyInfoRequestList);

        AsAlarmMsgInfoRequest asAlarmMsgInfoRequest = new AsAlarmMsgInfoRequest();
        BeanUtils.convert(alarmConfigDetailVO.getAlarmMsg(), asAlarmMsgInfoRequest);
        rpcRequest.setAsAlarmMsgInfoRequest(asAlarmMsgInfoRequest);

        List<AsAlarmTriggerInfoRequest> asAlarmTriggerInfoRequestList
                = BeanUtils.convertList(alarmConfigDetailVO.getAlarmTriggerList(), AsAlarmTriggerInfoRequest.class);
        rpcRequest.setAsAlarmTriggerInfoRequestList(asAlarmTriggerInfoRequestList);

        MonitorResult<Void> rpcResult;
        try {
            rpcResult = alarmBasicConfigurationFacade.addOrUpdate(rpcRequest);
        } catch (Exception e) {
            throw new BizException("调用saas-monitor异常", e);
        }

        if (StringUtils.isNotBlank(rpcResult.getErrorMsg())) {
            logger.info("调用saas-monitor异常,error={}", rpcResult.getErrorMsg());
            throw new BizException(rpcResult.getErrorMsg());
        }
        return Results.newSuccessResult(Boolean.TRUE);
    }

    private void checkSaveAlarmConfigDetailRequest(AlarmConfigDetailVO alarmConfigDetailVO) {
        AlarmInfoDetailVO alarmInfo = alarmConfigDetailVO.getAlarmInfo();
        if (alarmInfo == null) {
            throw new BizException("预警基本信息必填");
        }
        if (alarmInfo.getRunEnv() == null) {
            throw new BizException("预警基本信息中,执行环境必填");
        }
        if (StringUtils.isBlank(alarmInfo.getName())) {
            throw new BizException("预警基本信息中,预警名称必填");
        }
        if (StringUtils.isBlank(alarmInfo.getAlarmSwitch())) {
            throw new BizException("预警基本信息中,预警开关必填");
        }
        if (StringUtils.isBlank(alarmInfo.getRunInterval())) {
            throw new BizException("预警基本信息中,预警执行时间必填");
        }

        List<AlarmConstantDetailVO> alarmConstantList = alarmConfigDetailVO.getAlarmConstantList();
        if (CollectionUtils.isNotEmpty(alarmConstantList)) {
            for (AlarmConstantDetailVO alarmConstant : alarmConstantList) {
                if (StringUtils.isBlank(alarmConstant.getName())) {
                    throw new BizException("预警常量中,常量名称必填");
                }
                if (StringUtils.isBlank(alarmConstant.getCode())) {
                    throw new BizException("预警常量中,常量编码必填");
                }
                if (StringUtils.isBlank(alarmConstant.getValue())) {
                    throw new BizException("预警常量中,常量值必填");
                }

            }
        }
        List<AlarmQueryDetailVO> alarmQueryList = alarmConfigDetailVO.getAlarmQueryList();
        if (!CollectionUtils.isEmpty(alarmQueryList)) {
            for (AlarmQueryDetailVO alarmQuery : alarmQueryList) {
                if (StringUtils.isBlank(alarmQuery.getQuerySql())) {
                    throw new BizException("预警数据查询中,查询语句必填");
                }
                if (StringUtils.isBlank(alarmQuery.getResultCode())) {
                    throw new BizException("预警数据查询中,查询结果编码必填");
                }
            }
        }

        List<AlarmVariableDetailVO> alarmVariableList = alarmConfigDetailVO.getAlarmVariableList();
        if (CollectionUtils.isNotEmpty(alarmVariableList)) {
            for (AlarmVariableDetailVO alarmVariable : alarmVariableList) {
                if (StringUtils.isBlank(alarmVariable.getName())) {
                    throw new BizException("预警变量中,变量名称必填");
                }
                if (StringUtils.isBlank(alarmVariable.getCode())) {
                    throw new BizException("预警变量中,变量编码必填");
                }
                if (StringUtils.isBlank(alarmVariable.getValue())) {
                    throw new BizException("预警变量中,变量值必填");
                }
            }
        }

        AlarmMsgDetailVO alarmMsg = alarmConfigDetailVO.getAlarmMsg();
        if (alarmMsg == null) {
            throw new BizException("预警消息模板必填");
        }
        if (StringUtils.isBlank(alarmMsg.getTitleTemplate())) {
            throw new BizException("预警消息模板中,消息标题必填");
        }
        if (StringUtils.isBlank(alarmMsg.getBodyTemplate())) {
            throw new BizException("预警消息模板中,消息模板必填");
        }

        List<AlarmTriggerDetailVO> alarmTriggerList = alarmConfigDetailVO.getAlarmTriggerList();
        if (CollectionUtils.isNotEmpty(alarmTriggerList)) {
            for (AlarmTriggerDetailVO alarmTrigger : alarmTriggerList) {
                if (StringUtils.isBlank(alarmTrigger.getName())) {
                    throw new BizException("预警触发条件中,名称必填");
                }
                if (alarmTrigger.getStatus() == null) {
                    throw new BizException("预警触发条件中,状态必填");
                }
            }
        }

    }

    @Override
    public SaasResult<Object> testAlarmConfigDetail(AlarmConfigExpressionTestVO request) {
        if (request.getExpressionType() == null) {
            throw new BizException("expressionType不能为空");
        }
        Byte type = request.getExpressionType();
        if (type >= 1 && type <= 3) {
            if (StringUtils.isBlank(request.getExpressionCode())) {
                throw new BizException("expressionCode不能为空");
            }
        }

        AlarmBasicConfigurationTestRequest rpcRequest = new AlarmBasicConfigurationTestRequest();

        rpcRequest.setTestCode(request.getExpressionCode());
        rpcRequest.setTestType(request.getExpressionType());

        AsAlarmInfoRequest alarmInfoRequest = DataConverterUtils.convert(request.getAlarmInfo(), AsAlarmInfoRequest.class);
        rpcRequest.setAsAlarmInfoRequest(alarmInfoRequest);

        List<AsAlarmConstantInfoRequest> constantInfoRequestList
                = DataConverterUtils.convert(request.getAlarmConstantList(), AsAlarmConstantInfoRequest.class);
        rpcRequest.setAsAlarmConstantInfoRequestList(constantInfoRequestList);

        List<AsAlarmQueryInfoRequest> queryInfoRequestList
                = DataConverterUtils.convert(request.getAlarmQueryList(), AsAlarmQueryInfoRequest.class);
        rpcRequest.setAsAlarmQueryInfoRequestList(queryInfoRequestList);

        List<AsAlarmVariableInfoRequest> variableInfoRequestList
                = DataConverterUtils.convert(request.getAlarmVariableList(), AsAlarmVariableInfoRequest.class);
        rpcRequest.setAsAlarmVariableInfoRequestList(variableInfoRequestList);

        List<AsAlarmNotifyInfoRequest> notifyInfoRequestList
                = DataConverterUtils.convert(request.getAlarmNotifyList(), AsAlarmNotifyInfoRequest.class);
        rpcRequest.setAsAlarmNotifyInfoRequestList(notifyInfoRequestList);

        AsAlarmMsgInfoRequest alarmMsgInfoRequest
                = DataConverterUtils.convert(request.getAlarmMsg(), AsAlarmMsgInfoRequest.class);
        rpcRequest.setAsAlarmMsgInfoRequest(alarmMsgInfoRequest);

        List<AsAlarmTriggerInfoRequest> triggerInfoRequestList
                = DataConverterUtils.convert(request.getAlarmTriggerList(), AsAlarmTriggerInfoRequest.class);
        rpcRequest.setAsAlarmTriggerInfoRequestList(triggerInfoRequestList);

        MonitorResult<Object> rpcResult = alarmBasicConfigurationFacade.testAlarmConfiguration(rpcRequest);
        if (StringUtils.isNotBlank(rpcResult.getErrorMsg())) {
            logger.error("调用saas-monitor异常,error={}", rpcResult.getErrorMsg());
            throw new BizException(rpcResult.getErrorMsg());
        }
        return Results.newSuccessResult(rpcResult.getData());
    }

    @Override
    public SaasResult<Map<String, String>> cronCompute(String cronExpression) {
        if (StringUtils.isBlank(cronExpression)) {
            throw new BizException("cron表达式不能为空");
        }
        MonitorResult<Map<String, String>> rpcResult = alarmBasicConfigurationFacade.getCronComputeValue(cronExpression);
        if (StringUtils.isNotBlank(rpcResult.getErrorMsg())) {
            logger.info("调用saas-monitor异常,error={}", rpcResult.getErrorMsg());
            throw new BizException(rpcResult.getErrorMsg());
        }
        return Results.newSuccessResult(rpcResult.getData());
    }


    @Override
    public SaasResult<List<SaasWorkerVO>> queryWorkerByDate(SaasWorkerRequest saasWorkerRequest) {
        if (saasWorkerRequest.getDate() == null) {
            throw new BizException("值班日期不能为空");
        }
        MonitorResult<List<SaasWorkerRO>> monitorResult = alarmBasicConfigurationFacade.queryWorkerNameByDate(saasWorkerRequest.getDate());
        if (monitorResult.getData() == null) {
            logger.error("返回值班人员的数据为空{}", monitorResult.getErrorMsg());
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA, monitorResult.getErrorMsg());
        }
        List<SaasWorkerVO> list = DataConverterUtils.convert(monitorResult.getData(), SaasWorkerVO.class);
        return Results.newSuccessResult(list);
    }

    @Override
    public SaasResult<Boolean> updateAlarmSwitch(Long id) {
        if (id == null) {
            throw new BizException("预警信息ID不能为空");
        }
        MonitorResult<Object> monitorResult = alarmBasicConfigurationFacade.updateAlarmSwitch(id);
        if (StringUtils.isNoneBlank(monitorResult.getErrorMsg())) {
            logger.info("调用saas-monitor异常,error={}", monitorResult.getErrorMsg());
            throw new BizException(monitorResult.getErrorMsg());
        }
        return Results.newSuccessResult(true);

    }


}