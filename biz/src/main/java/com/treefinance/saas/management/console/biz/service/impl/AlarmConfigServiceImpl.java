package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.console.context.exception.BizException;
import com.treefinance.saas.console.share.adapter.AbstractServiceAdapter;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AlarmConfigService;
import com.treefinance.saas.management.console.common.domain.request.AlarmConfigRequest;
import com.treefinance.saas.management.console.common.domain.request.SaasWorkerRequest;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigExpressionTestVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConfigVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmConstantDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmInfoDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmMsgDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmNotifyDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmQueryDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmTriggerDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.AlarmVariableDetailVO;
import com.treefinance.saas.management.console.common.domain.vo.SaasWorkerVO;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AlarmBasicConfigurationDetailRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AlarmBasicConfigurationRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AlarmBasicConfigurationTestRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmConstantInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmMsgInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmNotifyInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmQueryInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmTriggerInfoRequest;
import com.treefinance.saas.monitor.facade.domain.request.autoalarm.AsAlarmVariableInfoRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.SaasWorkerRO;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmBasicConfigurationDetailRO;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmMsgRO;
import com.treefinance.saas.monitor.facade.domain.ro.autoalarm.AsAlarmRO;
import com.treefinance.saas.monitor.facade.service.autoalarm.AlarmBasicConfigurationFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengtong
 * @date 18/7/19 16:07
 */
@Service
public class AlarmConfigServiceImpl extends AbstractServiceAdapter implements AlarmConfigService {

    @Resource
    private AlarmBasicConfigurationFacade alarmBasicConfigurationFacade;

    @Override
    public SaasResult queryAlarmConfigList(AlarmConfigRequest request) {

        AlarmBasicConfigurationRequest configurationRequest = new AlarmBasicConfigurationRequest();

        configurationRequest.setName(request.getName());
        configurationRequest.setRunEnv(request.getRunEnv());
        configurationRequest.setPageNumber(request.getPageNumber());
        configurationRequest.setPageSize(request.getPageSize());

        MonitorResult<List<AsAlarmRO>> result;
        try {
            logger.info("向monitor请求数据，request：{}", configurationRequest);
            result = alarmBasicConfigurationFacade.queryAlarmConfigurationList(configurationRequest);
            logger.info("从monitor获取数据，result：{}", result);
        } catch (Exception e) {
            logger.info("请求monitor失败", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        List<AlarmConfigVO> returnList = this.convert(result.getData(), AlarmConfigVO.class);

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
        this.copy(rpcData.getAsAlarmRO(), alarmInfoDetailVO);
        alarmConfigDetailVO.setAlarmInfo(alarmInfoDetailVO);

        List<AlarmConstantDetailVO> alarmConstantDetailVOList =
            this.convert(rpcData.getAsAlarmConstantROList(), AlarmConstantDetailVO.class);
        alarmConfigDetailVO.setAlarmConstantList(alarmConstantDetailVOList);

        List<AlarmQueryDetailVO> alarmQueryDetailVOList =
            this.convertList(rpcData.getAsAlarmQueryROList(), AlarmQueryDetailVO.class);
        alarmConfigDetailVO.setAlarmQueryList(alarmQueryDetailVOList);

        List<AlarmVariableDetailVO> alarmVariableDetailVOList =
            this.convertList(rpcData.getAsAlarmVariableROList(), AlarmVariableDetailVO.class);
        alarmConfigDetailVO.setAlarmVariableList(alarmVariableDetailVOList);

        List<AlarmNotifyDetailVO> alarmNotifyDetailVOList =
            this.convertList(rpcData.getAsAlarmNotifyROList(), AlarmNotifyDetailVO.class);
        alarmConfigDetailVO.setAlarmNotifyList(alarmNotifyDetailVOList);

        List<AsAlarmMsgRO> notifyMsgList = rpcData.getAsAlarmMsgROList().stream()
            .filter(asAlarmMsgRO -> Byte.valueOf("1").equals(asAlarmMsgRO.getMsgType())).collect(Collectors.toList());
        List<AlarmMsgDetailVO> alarmNotifyMsgROList = this.convertList(notifyMsgList, AlarmMsgDetailVO.class);
        alarmConfigDetailVO.setAlarmNotifyMsgList(alarmNotifyMsgROList);

        List<AsAlarmMsgRO> recoveryMsgList = rpcData.getAsAlarmMsgROList().stream()
            .filter(asAlarmMsgRO -> Byte.valueOf("2").equals(asAlarmMsgRO.getMsgType())).collect(Collectors.toList());
        List<AlarmMsgDetailVO> alarmRecoveryMsgROList = this.convertList(recoveryMsgList, AlarmMsgDetailVO.class);
        alarmConfigDetailVO.setAlarmRecoveryMsgList(alarmRecoveryMsgROList);

        List<AlarmTriggerDetailVO> alarmTriggerDetailVOList =
            this.convertList(rpcData.getAsAlarmTriggerROList(), AlarmTriggerDetailVO.class);
        alarmConfigDetailVO.setAlarmTriggerList(alarmTriggerDetailVOList);
        return Results.newSuccessResult(alarmConfigDetailVO);
    }

    @Override
    public SaasResult<Boolean> saveAlarmConfigDetail(AlarmConfigDetailVO alarmConfigDetailVO) {
        checkSaveAlarmConfigDetailRequest(alarmConfigDetailVO);

        AlarmBasicConfigurationDetailRequest rpcRequest = new AlarmBasicConfigurationDetailRequest();
        AsAlarmInfoRequest asAlarmInfoRequest = new AsAlarmInfoRequest();
        this.copy(alarmConfigDetailVO.getAlarmInfo(), asAlarmInfoRequest);
        rpcRequest.setAsAlarmInfoRequest(asAlarmInfoRequest);

        List<AsAlarmConstantInfoRequest> asAlarmConstantInfoRequestList =
            this.convertList(alarmConfigDetailVO.getAlarmConstantList(), AsAlarmConstantInfoRequest.class);
        rpcRequest.setAsAlarmConstantInfoRequestList(asAlarmConstantInfoRequestList);

        List<AsAlarmQueryInfoRequest> asAlarmQueryInfoRequestList =
            this.convertList(alarmConfigDetailVO.getAlarmQueryList(), AsAlarmQueryInfoRequest.class);
        rpcRequest.setAsAlarmQueryInfoRequestList(asAlarmQueryInfoRequestList);

        List<AsAlarmVariableInfoRequest> asAlarmVariableInfoRequestList =
            this.convertList(alarmConfigDetailVO.getAlarmVariableList(), AsAlarmVariableInfoRequest.class);
        rpcRequest.setAsAlarmVariableInfoRequestList(asAlarmVariableInfoRequestList);

        List<AsAlarmNotifyInfoRequest> asAlarmNotifyInfoRequestList =
            this.convertList(alarmConfigDetailVO.getAlarmNotifyList(), AsAlarmNotifyInfoRequest.class);
        rpcRequest.setAsAlarmNotifyInfoRequestList(asAlarmNotifyInfoRequestList);

        List<AsAlarmMsgInfoRequest> asAlarmMsgInfoRequestList = Lists.newArrayList();
        for (AlarmMsgDetailVO alarmMsgDetailVO : alarmConfigDetailVO.getAlarmNotifyMsgList()) {
            AsAlarmMsgInfoRequest asAlarmMsgInfoRequest = this.convert(alarmMsgDetailVO, AsAlarmMsgInfoRequest.class);
            if (asAlarmMsgInfoRequest != null) {
                asAlarmMsgInfoRequest.setMsgType((byte)1);
                asAlarmMsgInfoRequestList.add(asAlarmMsgInfoRequest);
            }
        }

        for (AlarmMsgDetailVO alarmMsgDetailVO : alarmConfigDetailVO.getAlarmRecoveryMsgList()) {
            AsAlarmMsgInfoRequest asAlarmMsgInfoRequest = this.convert(alarmMsgDetailVO, AsAlarmMsgInfoRequest.class);
            if (asAlarmMsgInfoRequest != null) {
                asAlarmMsgInfoRequest.setMsgType((byte)2);
                asAlarmMsgInfoRequestList.add(asAlarmMsgInfoRequest);
            }
        }
        rpcRequest.setAsAlarmMsgInfoRequestList(asAlarmMsgInfoRequestList);
        List<AsAlarmTriggerInfoRequest> asAlarmTriggerInfoRequestList =
            this.convertList(alarmConfigDetailVO.getAlarmTriggerList(), AsAlarmTriggerInfoRequest.class);
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

        List<AlarmMsgDetailVO> notifyMsgList = alarmConfigDetailVO.getAlarmNotifyMsgList();
        for (AlarmMsgDetailVO alarmMsg : notifyMsgList) {
            checkAlarmMsgDetailVO(alarmMsg);
        }

        List<AlarmMsgDetailVO> recoveryMsgList = alarmConfigDetailVO.getAlarmRecoveryMsgList();
        for (AlarmMsgDetailVO alarmMsg : recoveryMsgList) {
            checkAlarmMsgDetailVO(alarmMsg);
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

    private void checkAlarmMsgDetailVO(AlarmMsgDetailVO elem) {
        if (StringUtils.isBlank(elem.getTitleTemplate())) {
            throw new BizException("预警通知消息模板中,消息标题必填");
        }
        if (StringUtils.isBlank(elem.getBodyTemplate())) {
            throw new BizException("预警通知消息模板中,消息模板必填");
        }
        if (StringUtils.isBlank(elem.getNotifyChannel())) {
            throw new BizException("预警通知消息模板中,消息通道必填");
        }
        if (elem.getAnalysisType() == null) {
            throw new BizException("预警通知消息模板中,消息解析类型必填");
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

        AsAlarmInfoRequest alarmInfoRequest =
            this.convert(request.getAlarmInfo(), AsAlarmInfoRequest.class);
        rpcRequest.setAsAlarmInfoRequest(alarmInfoRequest);

        List<AsAlarmConstantInfoRequest> constantInfoRequestList =
            this.convert(request.getAlarmConstantList(), AsAlarmConstantInfoRequest.class);
        rpcRequest.setAsAlarmConstantInfoRequestList(constantInfoRequestList);

        List<AsAlarmQueryInfoRequest> queryInfoRequestList =
            this.convert(request.getAlarmQueryList(), AsAlarmQueryInfoRequest.class);
        rpcRequest.setAsAlarmQueryInfoRequestList(queryInfoRequestList);

        List<AsAlarmVariableInfoRequest> variableInfoRequestList =
            this.convert(request.getAlarmVariableList(), AsAlarmVariableInfoRequest.class);
        rpcRequest.setAsAlarmVariableInfoRequestList(variableInfoRequestList);

        List<AsAlarmNotifyInfoRequest> notifyInfoRequestList =
            this.convert(request.getAlarmNotifyList(), AsAlarmNotifyInfoRequest.class);
        rpcRequest.setAsAlarmNotifyInfoRequestList(notifyInfoRequestList);

        List<AsAlarmMsgInfoRequest> alarmNotifyMsgInfoRequestList =
            this.convert(request.getAlarmNotifyMsgList(), AsAlarmMsgInfoRequest.class);
        for (AsAlarmMsgInfoRequest asAlarmMsgInfoRequest : alarmNotifyMsgInfoRequestList) {
            asAlarmMsgInfoRequest.setMsgType((byte)1);
        }
        rpcRequest.setAsAlarmNotifyMsgInfoRequestList(alarmNotifyMsgInfoRequestList);

        List<AsAlarmMsgInfoRequest> alarmRecoveryMsgInfoRequestList =
            this.convert(request.getAlarmRecoveryMsgList(), AsAlarmMsgInfoRequest.class);
        for (AsAlarmMsgInfoRequest asAlarmMsgInfoRequest : alarmRecoveryMsgInfoRequestList) {
            asAlarmMsgInfoRequest.setMsgType((byte)2);
        }
        rpcRequest.setAsAlarmRecoveryMsgInfoRequestList(alarmRecoveryMsgInfoRequestList);

        List<AsAlarmTriggerInfoRequest> triggerInfoRequestList =
            this.convert(request.getAlarmTriggerList(), AsAlarmTriggerInfoRequest.class);
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
        MonitorResult<Map<String, String>> rpcResult =
            alarmBasicConfigurationFacade.getCronComputeValue(cronExpression);
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
        MonitorResult<List<SaasWorkerRO>> monitorResult =
            alarmBasicConfigurationFacade.queryWorkerNameByDate(saasWorkerRequest.getDate());
        if (monitorResult.getData().size() == 0) {
            logger.error("返回值班人员的数据为空{}", monitorResult.getErrorMsg());
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA, monitorResult.getErrorMsg());
        }
        List<SaasWorkerVO> list = this.convert(monitorResult.getData(), SaasWorkerVO.class);
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

    @Override
    public SaasResult<Boolean> copyAlarm(Long id) {

        MonitorResult<Boolean> result = alarmBasicConfigurationFacade.duplicateConfig(id);

        logger.info("copyAlarm从monitor获取的数据：{}", result);

        if (StringUtils.isNotEmpty(result.getErrorMsg())) {
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        return Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult<Boolean> deleteAlarmConfig(Long id) {

        MonitorResult<Boolean> result = alarmBasicConfigurationFacade.deleteById(id);

        logger.info("deleteAlarmConfig从monitor获取的数据：{}", result);

        if (result == null || StringUtils.isNotEmpty(result.getErrorMsg())) {
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        return Results.newSuccessResult(result.getData());
    }
}
