package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.console.biz.enums.BizTypeEnum;
import com.treefinance.saas.console.biz.enums.ECallBackDataTypeEnum;
import com.treefinance.saas.console.biz.enums.ETaskBuryPointEnum;
import com.treefinance.saas.console.biz.service.TaskService;
import com.treefinance.saas.console.common.domain.dto.TaskCallbackLogDTO;
import com.treefinance.saas.console.common.domain.request.TaskRequest;
import com.treefinance.saas.console.common.domain.vo.TaskBuryPointLogVO;
import com.treefinance.saas.console.common.domain.vo.TaskNextDirectiveVO;
import com.treefinance.saas.console.common.domain.vo.TaskVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.dao.entity.AppCallbackConfig;
import com.treefinance.saas.console.dao.entity.MerchantBase;
import com.treefinance.saas.console.dao.entity.TaskAttribute;
import com.treefinance.saas.console.dao.entity.TaskBuryPointLog;
import com.treefinance.saas.console.dao.entity.TaskCallbackLog;
import com.treefinance.saas.console.dao.entity.TaskLog;
import com.treefinance.saas.console.dao.entity.TaskNextDirective;
import com.treefinance.saas.console.manager.TaskManager;
import com.treefinance.saas.console.manager.domain.TaskBO;
import com.treefinance.saas.console.manager.domain.TaskPagingResultSet;
import com.treefinance.saas.console.manager.param.TaskPagingQuery;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.QueryAppCallBackConfigByIdRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryMerchantByAppName;
import com.treefinance.saas.merchant.facade.request.grapserver.QueryMerchantByAppIdRequest;
import com.treefinance.saas.merchant.facade.result.console.AppCallbackConfigResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseInfoResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppCallbackConfigFacade;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.taskcenter.facade.request.TaskAttributeRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskBuryPointLogRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskLogRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskNextDirectiveRequest;
import com.treefinance.saas.taskcenter.facade.result.TaskAttributeRO;
import com.treefinance.saas.taskcenter.facade.result.TaskBuryPointLogRO;
import com.treefinance.saas.taskcenter.facade.result.TaskCallbackLogRO;
import com.treefinance.saas.taskcenter.facade.result.TaskLogRO;
import com.treefinance.saas.taskcenter.facade.result.TaskNextDirectiveRO;
import com.treefinance.saas.taskcenter.facade.result.common.TaskResult;
import com.treefinance.saas.taskcenter.facade.service.TaskAttributeFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskBuryPointLogFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskCallbackLogFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskLogFacade;
import com.treefinance.saas.taskcenter.facade.service.TaskNextDirectiveFacade;
import com.treefinance.toolkit.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/8/15.
 */
@Service
public class TaskServiceImpl extends AbstractService implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private TaskCallbackLogFacade taskCallbackLogFacade;
    @Autowired
    private TaskBuryPointLogFacade taskBuryPointLogFacade;
    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Autowired
    private TaskLogFacade taskLogFacade;
    @Autowired
    private AppCallbackConfigFacade appCallbackConfigFacade;
    @Autowired
    private TaskAttributeFacade taskAttributeFacade;
    @Autowired
    private TaskNextDirectiveFacade taskNextDirectiveFacade;

    @Override
    public SaasResult<Map<String, Object>> findByExample(TaskRequest taskRequest) {
        List<String> appIds = null;
        if (StringUtils.isNotBlank(taskRequest.getAppName())) {
            appIds = this.getAppIdsLikeAppName(taskRequest.getAppName());
            // fail fast
            if (CollectionUtils.isEmpty(appIds)) {
                return Results.newPageResult(taskRequest, 0, Collections.emptyList());
            }
        }

        Byte bizType = BizTypeEnum.valueOf(taskRequest.getType()).getCode();

        RemoteService remoteService = new RemoteService(taskRequest, appIds, bizType).invoke();
        if (remoteService.failure()) {
            return Results.newPageResult(taskRequest, 0, Collections.emptyList());
        }

        long count = remoteService.getCount();
        List<TaskBO> taskList = remoteService.getTaskList();

        // <appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = getMerchantBaseMap(taskList);
        // <website,OperatorRO>
        Map<Long, TaskAttribute> operatorMap;
        if (BizTypeEnum.OPERATOR.is(bizType)) {
            operatorMap = getOperatorMapFromAttribute(taskList);
        } else {
            operatorMap = Collections.emptyMap();
        }
        // <taskId,TaskCallbackLog>
        Map<Long, TaskCallbackLogDTO> taskCallbackLogMap = getTaskCallbackLogMap(taskList);

        List<TaskVO> result = Lists.newArrayList();

        for (TaskBO task : taskList) {
            TaskVO vo = new TaskVO();
            vo.setUniqueId(task.getUniqueId());
            vo.setId(task.getId());
            vo.setAccountNo(task.getAccountNo());
            vo.setWebSite(task.getWebSite());
            vo.setStatus(task.getStatus());
            vo.setCreateTime(task.getCreateTime());
            vo.setLastUpdateTime(task.getLastUpdateTime());
            MerchantBase merchantBase = merchantBaseMap.get(task.getAppId());
            if (merchantBase != null) {
                vo.setAppName(merchantBase.getAppName());
                vo.setAppId(merchantBase.getAppId());
            }
            if (!operatorMap.isEmpty()) {
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
                return Long.valueOf(expirationTime) > System.currentTimeMillis();
            }
        }
        return false;
    }

    private Map<Long, TaskAttribute> getOperatorMapFromAttribute(List<TaskBO> taskList) {
        List<Long> taskIdList = taskList.stream().map(TaskBO::getId).collect(Collectors.toList());
        List<TaskAttribute> list = getTaskAttributes(taskIdList);
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream().collect(Collectors.toMap(TaskAttribute::getTaskId, taskAttribute -> taskAttribute));
    }

    private List<TaskAttribute> getTaskAttributes(List<Long> taskIdList) {

        TaskAttributeRequest taskAttributeRequest = new TaskAttributeRequest();

        taskAttributeRequest.setTaskIds(taskIdList);
        taskAttributeRequest.setName(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());

        TaskResult<List<TaskAttributeRO>> taskResult;

        try {
            taskResult = taskAttributeFacade.queryTaskAttribute(taskAttributeRequest);
        } catch (Exception e) {
            logger.info("任务中心请求出错", e.getMessage());
            return Lists.newArrayList();
        }
        logger.info("任务中心请求返回数据", taskResult);
        if (!taskResult.isSuccess()) {
            logger.info("任务中心请求返回失败", taskResult);
            return Lists.newArrayList();
        }

        return this.convert(taskResult.getData(), TaskAttribute.class);
    }

    private Map<Long, TaskCallbackLogDTO> getTaskCallbackLogMap(List<TaskBO> taskList) {
        Map<Long, TaskCallbackLogDTO> result = Maps.newHashMap();
        List<Long> taskIdList = taskList.stream().map(TaskBO::getId).collect(Collectors.toList());
        Map<Long, TaskBO> taskMap = taskList.stream().collect(Collectors.toMap(TaskBO::getId, Function.identity()));

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
        List<AppCallbackConfig> appCallbackConfigList = this.convert(listMerchantResult.getData(), AppCallbackConfig.class);
        logger.info("数据转换：{}", JSON.toJSONString(appCallbackConfigList));
        // <configId,AppCallbackConfig>
        Map<Integer, AppCallbackConfig> appCallbackConfigMap = appCallbackConfigList.stream().collect(Collectors.toMap(AppCallbackConfig::getId, t -> t));

        for (TaskCallbackLog log : taskCallbackLogList) {
            if (log == null) {
                continue;
            }

            TaskBO task = taskMap.get(log.getTaskId());
            if (task == null) {
                logger.error("解密回调参数时,在任务回调记录表中未找到对应的任务信息 taskId={}", log.getTaskId());
                continue;
            }
            // 后端回调
            if (log.getType() == 1) {
                AppCallbackConfig appCallbackConfig = appCallbackConfigMap.get(log.getConfigId().intValue());
                if (appCallbackConfig == null) {
                    logger.error("解密回调参数时,任务回调记录表中未找到对应的商户回调配置信息 configId={}", log.getConfigId());
                    continue;
                }
                if (ECallBackDataTypeEnum.MAIN.getCode().equals(appCallbackConfig.getDataType())) {
                    Long taskId = task.getId();
                    TaskCallbackLogDTO logDTO = new TaskCallbackLogDTO();
                    this.copyProperties(log, logDTO);
                    logDTO.setPlainRequestParam(log.getRequestParam());
                    // 网关支持:一个任务,回调多方,这里现将日志打印出来
                    if (result.get(taskId) != null) {
                        logger.error("此taskId={},存在多个回调配置,TaskCallbackLogDTO={}", JSON.toJSONString(result.get(taskId)), logDTO);
                        continue;
                    }
                    result.put(taskId, logDTO);
                }
            }
            // 前端回调
            if (log.getType() == 2) {
                Long taskId = task.getId();
                TaskCallbackLogDTO logDTO = new TaskCallbackLogDTO();
                this.copyProperties(log, logDTO);
                logDTO.setPlainRequestParam(log.getRequestParam());
                result.put(taskId, logDTO);

            }

        }
        logger.info("解密回调参数时,得到的任务对应主流程回调数据,Result={}", JSON.toJSONString(result));
        return result;
    }

    private List<TaskCallbackLog> getTaskCallbackLogs(List<Long> taskIdList) {
        TaskResult<List<TaskCallbackLogRO>> taskResult = taskCallbackLogFacade.queryTaskCallbackLog(taskIdList);
        logger.info("任务中心请求返回数据：{}", JSON.toJSONString(taskResult));
        if (!taskResult.isSuccess()) {
            return new ArrayList<>();
        }
        return this.convert(taskResult.getData(), TaskCallbackLog.class);
    }

    @Override
    public List<TaskLog> findByTaskId(Long taskId) {

        TaskLogRequest rpcRequest = new TaskLogRequest();
        rpcRequest.setTaskIdList(Collections.singletonList(taskId));

        TaskResult<List<TaskLogRO>> taskResult;

        try {
            taskResult = taskLogFacade.queryTaskLogById(rpcRequest);
        } catch (Exception e) {
            logger.error("任务中心请求出错", e);
            return Lists.newArrayList();
        }
        logger.info("任务中心请求返回数据taskResult={}", JSON.toJSONString(taskResult));
        if (!taskResult.isSuccess()) {
            logger.error("任务中心请求返回失败taskResult={}", JSON.toJSONString(taskResult));
            return Lists.newArrayList();
        }

        return this.convert(taskResult.getData(), TaskLog.class);
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
            taskBuryPointLogVO.setCodeMessage(ETaskBuryPointEnum.getText(Integer.parseInt(taskBuryPointLog.getCode())));
            taskBuryPointLogVOList.add(taskBuryPointLogVO);
        }
        return taskBuryPointLogVOList;

    }

    private List<TaskBuryPointLog> getTaskBuryPointLogs(Long taskId) {

        TaskBuryPointLogRequest taskBuryPointLogRequest = new TaskBuryPointLogRequest();

        taskBuryPointLogRequest.setTaskId(taskId);

        TaskResult<List<TaskBuryPointLogRO>> taskResult;

        try {
            taskResult = taskBuryPointLogFacade.queryTaskBuryPointLogById(taskBuryPointLogRequest);
        } catch (Exception e) {
            logger.error("请求任务中心出错", e.getMessage());
            return Lists.newArrayList();
        }

        logger.info("从任务中心获取数据：{}", taskResult);
        if (!taskResult.isSuccess()) {
            logger.error("请求任务中心失败:{}", taskResult);
            return Lists.newArrayList();
        }

        return this.convert(taskResult.getData(), TaskBuryPointLog.class);
    }

    @Override
    public List<TaskNextDirectiveVO> findtaskNextDirectiveByTaskId(Long taskId) {
        List<TaskNextDirective> taskNextDirectiveList = getTaskNextDirectives(taskId);
        return this.convert(taskNextDirectiveList, TaskNextDirectiveVO.class);
    }

    private List<TaskNextDirective> getTaskNextDirectives(Long taskId) {

        TaskNextDirectiveRequest request = new TaskNextDirectiveRequest();
        request.setTaskId(taskId);

        TaskResult<List<TaskNextDirectiveRO>> taskResult;

        try {
            taskResult = taskNextDirectiveFacade.queryTaskNextDirectiveByTaskId(request);
        } catch (Exception e) {
            logger.error("请求任务中心出错", e.getMessage());
            return Lists.newArrayList();
        }

        logger.info("从任务中心获取数据：{}", taskResult);
        if (!taskResult.isSuccess()) {
            logger.error("请求任务中心失败:{}", taskResult);
            return Lists.newArrayList();
        }

        return this.convert(taskResult.getData(), TaskNextDirective.class);

    }

    private Map<String, MerchantBase> getMerchantBaseMap(List<TaskBO> taskList) {
        List<String> appIdList = taskList.stream().map(TaskBO::getAppId).distinct().collect(Collectors.toList());

        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(appIdList);
        MerchantResult<List<MerchantBaseResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = this.convert(listMerchantResult.getData(), MerchantBase.class);
        return merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, m -> m));
    }

    private List<String> getAppIdsLikeAppName(String appName) {
        QueryMerchantByAppName queryMerchantByAppName = new QueryMerchantByAppName();
        queryMerchantByAppName.setAppName(StringUtils.deleteWhitespace(appName));
        MerchantResult<List<MerchantBaseInfoResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseByAppName(queryMerchantByAppName);
        List<MerchantBase> merchantBaseList = this.convert(listMerchantResult.getData(), MerchantBase.class);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return Collections.emptyList();
        }
        return merchantBaseList.stream().map(MerchantBase::getAppId).collect(Collectors.toList());
    }

    private class RemoteService {
        private final TaskRequest request;
        private final List<String> appIds;
        private final Byte bizType;
        private List<TaskBO> taskList;
        private long count = 0;
        private boolean failure = false;

        RemoteService(TaskRequest request, List<String> appIds, Byte bizType) {
            this.request = request;
            this.appIds = appIds;
            this.bizType = bizType;
        }

        boolean failure() {
            return failure;
        }

        long getCount() {
            return count;
        }

        List<TaskBO> getTaskList() {
            return taskList;
        }

        RemoteService invoke() {
            Long taskId = request.getTaskId();
            if (taskId != null) {
                try {
                    TaskBO task = taskManager.getTaskById(taskId);
                    taskList = Collections.singletonList(task);
                    count = 1;
                } catch (Exception e) {
                    logger.warn("找不到任务[{}]", taskId, e);
                    failure = true;
                }
                return this;
            }

            TaskPagingQuery query = new TaskPagingQuery();
            if (StringUtils.isNotBlank(request.getUniqueId())) {
                query.setUniqueId(request.getUniqueId());
            }
            if (StringUtils.isNotBlank(request.getAccountNo())) {
                query.setAccountNo(request.getAccountNo());
            }
            if (CollectionUtils.isNotEmpty(appIds)) {
                query.setAppIds(appIds);
            }
            query.setBizTypes(Collections.singletonList(bizType));
            query.setStartDate(request.getStartDate());
            // +23:59:59
            query.setEndDate(DateUtils.getEndTimeOfDay(request.getEndDate()));
            query.setOrder("lastUpdateTime desc");
            query.setPageNum(request.getPageNumber());
            query.setPageSize(request.getPageSize());

            try {
                TaskPagingResultSet pagination = taskManager.queryPagingTasks(query);
                logger.info("请求任务中心返回数据：{}", JSON.toJSONString(pagination));
                count = pagination.getTotal();
                if (count > 0) {
                    taskList = pagination.getList();
                } else {
                    failure = true;
                }
            } catch (Exception e) {
                logger.error("请求Task远程服务，分页查询任务败", e);
                failure = true;
            }

            return this;
        }
    }
}
