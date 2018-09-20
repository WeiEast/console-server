package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datatrees.toolkits.util.Base64Codec;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.gateway.servicefacade.enums.BizTypeEnum;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.common.handler.CallbackSecureHandler;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.TaskService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.CallbackLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.TaskCallbackLogDTO;
import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.domain.vo.TaskBuryPointLogVO;
import com.treefinance.saas.management.console.common.domain.vo.TaskNextDirectiveVO;
import com.treefinance.saas.management.console.common.domain.vo.TaskVO;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.enumeration.ETaskBuryPoint;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.*;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppCallBackConfigByIdRequest;
import com.treefinance.saas.merchant.center.facade.request.console.QueryMerchantByAppName;
import com.treefinance.saas.merchant.center.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppCallbackConfigResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantBaseInfoResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppCallbackConfigFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.monitor.common.utils.AESSecureUtils;
import com.treefinance.saas.taskcenter.facade.result.TaskCallbackLogRO;
import com.treefinance.saas.taskcenter.facade.result.TaskRO;
import com.treefinance.saas.taskcenter.facade.result.common.TaskPagingResult;
import com.treefinance.saas.taskcenter.facade.result.common.TaskResult;
import com.treefinance.saas.taskcenter.facade.service.TaskCallbackLogFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/8/15.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskFacade taskFacade;
    @Autowired
    private TaskCallbackLogFacade taskCallbackLogFacade;
    @Autowired
    private TaskBuryPointLogMapper taskBuryPointLogMapper;
    @Autowired
    private ISecurityCryptoService securityCryptoService;

    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Autowired
    private TaskCallbackLogMapper taskCallbackLogMapper;
    @Autowired
    private AppCallbackConfigFacade appCallbackConfigFacade;
    @Autowired
    private AppLicenseService appLicenseService;
    @Autowired
    private CallbackSecureHandler callbackSecureHandler;
    @Autowired
    private TaskAttributeMapper taskAttributeMapper;
    @Autowired
    private TaskNextDirectiveMapper taskNextDirectiveMapper;

    @Override
    public SaasResult<Map<String, Object>> findByExample(TaskRequest taskRequest) {
        List<String> appIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(taskRequest.getAppName())) {
            appIdList = this.getAppIdsLikeAppName(taskRequest.getAppName());
            //fail fast
            if (CollectionUtils.isEmpty(appIdList)) {
                return Results.newPageResult(taskRequest, 0, Lists.newArrayList());
            }
        }
        Byte bizType = BizTypeEnum.valueOfType(BizTypeEnum.valueOf(taskRequest.getType()));

        RemoteService remoteService = new RemoteService(taskRequest, appIdList, bizType).invoke();
        if (remoteService.is()){
            return Results.newPageResult(taskRequest, remoteService.getCount(), new ArrayList<>());
        }
        long count = remoteService.getCount();
        List<Task> taskList = remoteService.getTaskList();


        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = getMerchantBaseMap(taskList);
        //<website,OperatorRO>
        Map<Long, TaskAttribute> operatorMap = Maps.newHashMap();
        if (BizTypeEnum.valueOfType(BizTypeEnum.OPERATOR).equals(bizType)) {
            operatorMap = getOperatorMapFromAttribute(taskList);
        }
        //<taskId,TaskCallbackLog>
        Map<Long, TaskCallbackLogDTO> taskCallbackLogMap = getTaskCallbackLogMap(taskList);


        List<TaskVO> result = Lists.newArrayList();

        for (Task task : taskList) {
            TaskVO vo = new TaskVO();
            vo.setUniqueId(task.getUniqueId());
            vo.setId(task.getId());
            vo.setAccountNo(securityCryptoService.decrypt(task.getAccountNo(), EncryptionIntensityEnum.NORMAL));
            vo.setWebSite(task.getWebSite());
            vo.setStatus(task.getStatus());
            vo.setCreateTime(task.getCreateTime());
            vo.setLastUpdateTime(task.getLastUpdateTime());
            MerchantBase merchantBase = merchantBaseMap.get(task.getAppId());
            if (merchantBase != null) {
                vo.setAppName(merchantBase.getAppName());
                vo.setAppId(merchantBase.getAppId());
            }
            if (BizTypeEnum.valueOfType(BizTypeEnum.OPERATOR).equals(bizType)) {
                TaskAttribute taskAttribute = operatorMap.get(task.getId());
                if (taskAttribute != null) {
                    vo.setOperatorName(taskAttribute.getValue());
                }
            }
            TaskCallbackLogDTO taskCallbackLogDTO = taskCallbackLogMap.get(task.getId());
            vo.setCanDownload(false);
            if (taskCallbackLogDTO != null) {
                vo.setCallbackRequest(taskCallbackLogDTO.getPlainRequestParam());
                vo.setCanDownload(canDownload(taskCallbackLogDTO.getPlainRequestParam()));
                vo.setCallbackResponse(taskCallbackLogDTO.getResponseData());
                vo.setCallbackLogId(taskCallbackLogDTO.getId());
            }
            result.add(vo);
        }
        return Results.newPageResult(taskRequest, count, result);
    }

    private Boolean canDownload(String requestParam) {
        if (StringUtils.isNotBlank(requestParam)) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(requestParam);
            } catch (Exception e) {
                logger.error("json转换异常", e);
                return false;
            }
            String dataUrl = jsonObject.getString("dataUrl");
            String expirationTime = jsonObject.getString("expirationTime");
            if (StringUtils.isNotBlank(dataUrl) && StringUtils.isNotBlank(expirationTime)) {
                if (Long.valueOf(expirationTime) > System.currentTimeMillis()) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<Long, TaskAttribute> getOperatorMapFromAttribute(List<Task> taskList) {
        List<Long> taskIdList = taskList.stream().map(Task::getId).collect(Collectors.toList());
        TaskAttributeCriteria criteria = new TaskAttributeCriteria();
        criteria.createCriteria().andTaskIdIn(taskIdList).andNameEqualTo(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());
        List<TaskAttribute> list = taskAttributeMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<Long, TaskAttribute> map = list.stream().collect(Collectors.toMap(TaskAttribute::getTaskId, taskAttribute -> taskAttribute));
        return map;
    }

    private Map<Long, TaskCallbackLogDTO> getTaskCallbackLogMap(List<Task> taskList) {
        Map<Long, TaskCallbackLogDTO> result = Maps.newHashMap();
        List<Long> taskIdList = taskList.stream().map(Task::getId).collect(Collectors.toList());
        Map<Long, Task> taskMap = taskList.stream().collect(Collectors.toMap(Task::getId, t -> t));

        List<TaskCallbackLog> taskCallbackLogList = getTaskCallbackLogs(taskIdList);
        logger.info("taskCallbackLog:{}", JSON.toJSONString(taskCallbackLogList));
        if (CollectionUtils.isEmpty(taskCallbackLogList)) {
            return result;
        }
        List<Integer> configIdList = taskCallbackLogList.stream().map(t -> t.getConfigId().intValue()).distinct().collect(Collectors.toList());
        QueryAppCallBackConfigByIdRequest queryAppCallBackConfigByIdRequest = new QueryAppCallBackConfigByIdRequest();
        queryAppCallBackConfigByIdRequest.setId(configIdList);
        MerchantResult<List<AppCallbackConfigResult>> listMerchantResult = appCallbackConfigFacade.queryAppCallBackConfigById(queryAppCallBackConfigByIdRequest);
        logger.info("商户中心返回数据:{}", JSON.toJSONString(listMerchantResult));
        List<AppCallbackConfig> appCallbackConfigList = DataConverterUtils.convert(listMerchantResult.getData(), AppCallbackConfig.class);
        logger.info("数据转换：{}", JSON.toJSONString(appCallbackConfigList));
        //<configId,AppCallbackConfig>
        Map<Integer, AppCallbackConfig> appCallbackConfigMap = appCallbackConfigList.stream().collect(Collectors.toMap(AppCallbackConfig::getId, t -> t));

        for (TaskCallbackLog log : taskCallbackLogList) {
            Task task = taskMap.get(log.getTaskId());
            if (task == null) {
                logger.error("解密回调参数时,在任务回调记录表中未找到对应的任务信息 taskId={}", log.getTaskId());
                continue;
            }
            //后端回调
            if (log.getType() == 1) {
                AppCallbackConfig appCallbackConfig = appCallbackConfigMap.get(log.getConfigId().intValue());
                if (appCallbackConfig == null) {
                    logger.error("解密回调参数时,任务回调记录表中未找到对应的商户回调配置信息 configId={}", log.getConfigId());
                    continue;
                }
                if (ECallBackDataType.MAIN.getCode().equals(appCallbackConfig.getDataType())) {
                    Long taskId = task.getId();
                    TaskCallbackLogDTO logDTO = new TaskCallbackLogDTO();
                    BeanUtils.convert(log, logDTO);
                    logDTO.setPlainRequestParam(log.getRequestParam());
                    //网关支持:一个任务,回调多方,这里现将日志打印出来
                    if (result.get(taskId) != null) {
                        logger.error("此taskId={},存在多个回调配置,TaskCallbackLogDTO={},otherTaskCallbackLogDTO={}", JSON.toJSONString(result.get(taskId)), logDTO);
                        continue;
                    }
                    result.put(taskId, logDTO);
                }
            }
            //前端回调
            if (log.getType() == 2) {
                Long taskId = task.getId();
                TaskCallbackLogDTO logDTO = new TaskCallbackLogDTO();
                BeanUtils.convert(log, logDTO);
                logDTO.setPlainRequestParam(log.getRequestParam());
                result.put(taskId, logDTO);

            }


        }
        logger.info("解密回调参数时,得到的任务对应主流程回调数据,Result={}", JSON.toJSONString(result));
        return result;
    }

    private List<TaskCallbackLog> getTaskCallbackLogs(List<Long> taskIdList) {


        TaskResult<List<TaskCallbackLogRO>> taskResult = taskCallbackLogFacade.queryTaskCallbackLog(taskIdList);

        if(!taskResult.isSuccess()){
            return new ArrayList<>();
        }

        return DataConverterUtils.convert(taskResult.getData(), TaskCallbackLog.class);

    }

    private String getPlainParamsCallbackLog(Task task, AppCallbackConfig appCallbackConfig, TaskCallbackLog log) {
        String appId = task.getAppId();
        Byte isNewKey = appCallbackConfig.getIsNewKey();
        String aesDataKey = "";
        AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(appId);
        if (Byte.valueOf("0").equals(isNewKey)) {
            if (appLicenseDTO == null) {
                logger.error("解密回调参数时,未找到对应的appLicenseDTO appId={}", appId);
                return null;
            }
            aesDataKey = appLicenseDTO.getDataSecretKey();
        } else if (Byte.valueOf("1").equals(isNewKey)) {
            CallbackLicenseDTO callbackLicenseDTO = appLicenseService.selectCallbackLicenseById(log.getConfigId().intValue());
            if (callbackLicenseDTO == null) {
                logger.error("解密回调参数时,未找到对应的callbackLicenseDTO configId={}", log.getConfigId());
                return null;
            }
            aesDataKey = callbackLicenseDTO.getDataSecretKey();
        }
        String plainParams;
        byte version = appCallbackConfig.getVersion();
        if (version > 0) {
            // 默认使用AES方式
            plainParams = decryptByAES(log.getRequestParam(), aesDataKey);
        } else {
            plainParams = decryptByRSA(log.getRequestParam(), appLicenseDTO);
        }
        return plainParams;
    }

    private String decryptByAES(String data, String dataKey) {
        try {
            byte[] newData = Base64Codec.decode(data);
            String decryData = AESSecureUtils.decrypt(dataKey, newData);
            return decryData;
        } catch (Exception e) {
            throw new BizException("decryptByAES exception", e);
        }
    }

    private String decryptByRSA(String data, AppLicenseDTO appLicense) {
        String params;
        String rsaPublicKey = appLicense.getServerPublicKey();
        // 兼容老版本，使用RSA
        try {
            params = callbackSecureHandler.decrypt(data, rsaPublicKey);
            params = URLEncoder.encode(params, "utf-8");
        } catch (Exception e) {
            throw new BizException("decryptByRSA exception", e);
        }
        return params;
    }

    @Override
    public List<TaskLog> findByTaskId(Long taskId) {
        TaskLogCriteria taskLogCriteria = new TaskLogCriteria();
        taskLogCriteria.createCriteria().andTaskIdEqualTo(taskId);
        taskLogCriteria.setOrderByClause("OccurTime desc, Id desc");
        return taskLogMapper.selectByExample(taskLogCriteria);
    }


    @Override
    public List<TaskBuryPointLogVO> findBuryPointByTaskId(Long taskId) {
        List<TaskBuryPointLog> taskBuryPointLogList = getTaskBuryPointLogs(taskId);

        List<TaskBuryPointLogVO> taskBuryPointLogVOList = new ArrayList<>();
        for (TaskBuryPointLog taskBuryPointLog : taskBuryPointLogList) {
            TaskBuryPointLogVO taskBuryPointLogVO = new TaskBuryPointLogVO();
            taskBuryPointLogVO.setTaskId(taskBuryPointLog.getTaskId());
            taskBuryPointLogVO.setAppId(taskBuryPointLog.getAppId());
            taskBuryPointLogVO.setCode(taskBuryPointLog.getCode());
            taskBuryPointLogVO.setCreateTime(taskBuryPointLog.getCreateTime());
            taskBuryPointLogVO.setLastUpdateTime(taskBuryPointLog.getLastUpdateTime());
            taskBuryPointLogVO.setCodeMessage(ETaskBuryPoint.getText(Integer.parseInt(taskBuryPointLog.getCode())));
            taskBuryPointLogVOList.add(taskBuryPointLogVO);
        }
        return taskBuryPointLogVOList;

    }

    private List<TaskBuryPointLog> getTaskBuryPointLogs(Long taskId) {
        TaskBuryPointLogCriteria taskBuryPointLogCriteria = new TaskBuryPointLogCriteria();
        taskBuryPointLogCriteria.createCriteria().andTaskIdEqualTo(taskId);
        taskBuryPointLogCriteria.setOrderByClause("createTime desc, Id desc");
        return taskBuryPointLogMapper.selectByExample(taskBuryPointLogCriteria);
    }

    @Override
    public List<TaskNextDirectiveVO> findtaskNextDirectiveByTaskId(Long taskId) {
        List<TaskNextDirective> taskNextDirectiveList = getTaskNextDirectives(taskId);
        return DataConverterUtils.convert(taskNextDirectiveList, TaskNextDirectiveVO.class);
    }

    private List<TaskNextDirective> getTaskNextDirectives(Long taskId) {
        TaskNextDirectiveCriteria taskNextDirectiveCriteria = new TaskNextDirectiveCriteria();
        taskNextDirectiveCriteria.createCriteria().andTaskIdEqualTo(taskId);
        taskNextDirectiveCriteria.setOrderByClause("createTime desc, Id desc");
        return taskNextDirectiveMapper.selectByExample(taskNextDirectiveCriteria);
    }

    private Map<String, MerchantBase> getMerchantBaseMap(List<Task> taskList) {
        List<String> appIdList = taskList.stream().map(Task::getAppId).distinct().collect(Collectors.toList());

        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(appIdList);
        MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantBase.class);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, m -> m));
        return merchantBaseMap;
    }

    private List<String> getAppIdsLikeAppName(String appName) {
        appName = StringUtils.deleteWhitespace(appName);
        List<String> result = Lists.newArrayList();
        QueryMerchantByAppName queryMerchantByAppName = new QueryMerchantByAppName();
        queryMerchantByAppName.setAppName(appName);
        MerchantResult<List<MerchantBaseInfoResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseByAppName(queryMerchantByAppName);
        List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(), MerchantBase.class);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return result;
        }
        result = merchantBaseList.stream().map(MerchantBase::getAppId).collect(Collectors.toList());
        return result;
    }

    private class RemoteService {
        private boolean myResult;
        private TaskRequest taskRequest;
        private List<String> appIdList;
        private Byte bizType;
        private long count;
        private List<Task> taskList;

        public RemoteService(TaskRequest taskRequest, List<String> appIdList, Byte bizType) {
            this.taskRequest = taskRequest;
            this.appIdList = appIdList;
            this.bizType = bizType;
        }

        boolean is() {
            return myResult;
        }

        public long getCount() {
            return count;
        }

        public List<Task> getTaskList() {
            return taskList;
        }

        public RemoteService invoke() {

            com.treefinance.saas.taskcenter.facade.request.TaskRequest rpcRequest = new com.treefinance.saas.taskcenter.facade.request.TaskRequest();




            rpcRequest.setPageNumber(taskRequest.getPageNumber());
            rpcRequest.setPageSize(taskRequest.getPageSize());

            if (taskRequest.getTaskId() != null) {
                rpcRequest.setId(taskRequest.getTaskId());
            }
            if (StringUtils.isNotBlank(taskRequest.getUniqueId())) {
                rpcRequest.setUniqueId(taskRequest.getUniqueId());
            }
            if (StringUtils.isNotBlank(taskRequest.getAccountNo())) {
                rpcRequest.setAccountNo(securityCryptoService.encrypt(taskRequest.getAccountNo(), EncryptionIntensityEnum
                        .NORMAL));
            }
            if (StringUtils.isNotBlank(taskRequest.getAppName())) {
                rpcRequest.setAppIdList(appIdList);
            }

            rpcRequest.setStartDate(taskRequest.getStartDate());
            // +23:59:59
            Date endDate = DateUtils.addSeconds(taskRequest.getEndDate(), 24 * 60 * 60 - 1);
            rpcRequest.setEndDate(endDate);
            rpcRequest.setBizType(bizType);

            TaskPagingResult<TaskRO> result = taskFacade.queryTaskListPage(rpcRequest);


            logger.info("请求任务中心返回数据：{}", JSON.toJSONString(result));

            if(!result.isSuccess()){
                logger.info("请求任务中心返回失败结果:{}", result.getMessage());
                myResult = true;
                return this;
            }

            count = result.getTotal();
            if (count <= 0) {
                myResult = true;
                return this;
            }
            List<TaskRO> taskROS = result.getList();
            taskList = DataConverterUtils.convert(taskROS, Task.class);
            myResult = false;
            return this;
        }
    }
}
