package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.gateway.servicefacade.enums.BizTypeEnum;
import com.treefinance.saas.management.console.biz.service.TaskService;
import com.treefinance.saas.management.console.common.domain.dto.TaskCallbackLogDTO;
import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.domain.vo.TaskVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.MerchantBaseMapper;
import com.treefinance.saas.management.console.dao.mapper.TaskCallbackLogMapper;
import com.treefinance.saas.management.console.dao.mapper.TaskLogMapper;
import com.treefinance.saas.management.console.dao.mapper.TaskMapper;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.OperatorRO;
import com.treefinance.saas.monitor.facade.service.OperatorFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TaskMapper taskMapper;
    @Autowired
    private ISecurityCryptoService securityCryptoService;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private OperatorFacade operatorFacade;
    @Autowired
    private TaskLogMapper taskLogMapper;
    @Autowired
    private TaskCallbackLogMapper taskCallbackLogMapper;

    @Override
    public Result<Map<String, Object>> findByExample(TaskRequest taskRequest) {
        TaskCriteria taskCriteria = new TaskCriteria();
        taskCriteria.setOffset(taskRequest.getOffset());
        taskCriteria.setLimit(taskRequest.getPageSize());
        taskCriteria.setOrderByClause("lastUpdateTime desc");

        TaskCriteria.Criteria criteria = taskCriteria.createCriteria();
        if (taskRequest.getTaskId() != null) {
            criteria.andIdEqualTo(taskRequest.getTaskId());
        }
        if (StringUtils.isNotBlank(taskRequest.getUniqueId())) {
            criteria.andUniqueIdEqualTo(taskRequest.getUniqueId());
        }
        if (StringUtils.isNotBlank(taskRequest.getAccountNo())) {
            criteria.andAccountNoEqualTo(securityCryptoService.encrypt(taskRequest.getAccountNo(), EncryptionIntensityEnum.NORMAL));
        }
        if (StringUtils.isNotBlank(taskRequest.getAppName())) {
            List<String> appIdList = this.getAppIdsLikeAppName(taskRequest.getAppName());
            if (CollectionUtils.isNotEmpty(appIdList)) {
                criteria.andAppIdIn(appIdList);
            }
        }
        criteria.andCreateTimeGreaterThanOrEqualTo(taskRequest.getStartDate());
        // +23:59:59
        criteria.andCreateTimeLessThanOrEqualTo(DateUtils.addSeconds(taskRequest.getEndDate(), 24 * 60 * 60 - 1));
        Byte bizType = BizTypeEnum.valueOfType(BizTypeEnum.valueOf(taskRequest.getType()));
        criteria.andBizTypeEqualTo(bizType);
        List<Task> taskList = taskMapper.selectPaginationByExample(taskCriteria);
        long count = taskMapper.countByExample(taskCriteria);
        List<TaskVO> result = Lists.newArrayList();
        if (count <= 0) {
            return Results.newSuccessPageResult(taskRequest, count, result);
        }
        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = getMerchantBaseMap(taskList);
        //<website,OperatorRO>
        Map<String, OperatorRO> operatorROMap = Maps.newHashMap();
        if (BizTypeEnum.valueOfType(BizTypeEnum.OPERATOR).equals(bizType)) {
            operatorROMap = getOperatorMap(taskList);
        }
        //<taskId,TaskCallbackLog>
        Map<Long, TaskCallbackLogDTO> taskCallbackLogMap = getTaskCallbackLogMap(taskList);

        for (Task task : taskList) {
            TaskVO vo = new TaskVO();
            vo.setUniqueId(task.getUniqueId());
            vo.setId(task.getId());
            vo.setAccountNo(securityCryptoService.decrypt(task.getAccountNo(), EncryptionIntensityEnum.NORMAL));
            vo.setStatus(task.getStatus());
            vo.setCreateTime(task.getCreateTime());
            vo.setLastUpdateTime(task.getLastUpdateTime());
            MerchantBase merchantBase = merchantBaseMap.get(task.getAppId());
            if (merchantBase != null) {
                vo.setAppName(merchantBase.getAppName());
            }
            if (BizTypeEnum.valueOfType(BizTypeEnum.OPERATOR).equals(bizType)) {
                OperatorRO operatorRO = operatorROMap.get(task.getWebSite());
                if (operatorRO != null) {
                    vo.setOperatorName(operatorRO.getOperatorName());
                }
            }
            result.add(vo);
        }
        return Results.newSuccessPageResult(taskRequest, count, result);
    }

    private Map<Long, TaskCallbackLogDTO> getTaskCallbackLogMap(List<Task> taskList) {
        Map<Long, TaskCallbackLogDTO> result = Maps.newHashMap();
        List<Long> taskIdList = taskList.stream().map(Task::getId).collect(Collectors.toList());
        Map<Long, Task> taskMap = taskList.stream().collect(Collectors.toMap(Task::getId, t -> t));

        TaskCallbackLogCriteria taskCallbackLogCriteria = new TaskCallbackLogCriteria();
        taskCallbackLogCriteria.createCriteria().andTaskIdIn(taskIdList);
        List<TaskCallbackLog> taskCallbackLogList = taskCallbackLogMapper.selectByExample(taskCallbackLogCriteria);
        if (CollectionUtils.isEmpty(taskCallbackLogList)) {
            return result;
        }


        return null;
    }

    @Override
    public List<TaskLog> findByTaskId(Long taskId) {
        TaskLogCriteria taskLogCriteria = new TaskLogCriteria();
        taskLogCriteria.createCriteria().andTaskIdEqualTo(taskId);
        taskLogCriteria.setOrderByClause("OccurTime desc, Id desc");
        return taskLogMapper.selectByExample(taskLogCriteria);
    }

    private Map<String, OperatorRO> getOperatorMap(List<Task> list) {
        List<String> websiteList = list.stream().map(Task::getWebSite).distinct().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        MonitorResult<Map<String, OperatorRO>> resultMap = operatorFacade.queryOperatorByWebsites(websiteList);
        if (logger.isDebugEnabled()) {
            logger.debug("operatorFacade.queryOperatorByWebsites() :request={},result={}",
                    JSON.toJSONString(websiteList), JSON.toJSONString(resultMap));
        }
        if (resultMap == null || MapUtils.isEmpty(resultMap.getData())) {
            logger.info("result of queryOperatorByWebsites() is empty : request={}, result={}", JSON.toJSONString(websiteList), JSON.toJSONString(resultMap));
            return Maps.newHashMap();
        }
        Map<String, OperatorRO> map = resultMap.getData();
        return map;
    }

    private Map<String, MerchantBase> getMerchantBaseMap(List<Task> taskList) {
        List<String> appIdList = taskList.stream().map(Task::getAppId).collect(Collectors.toList());
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, m -> m));
        return merchantBaseMap;
    }

    private List<String> getAppIdsLikeAppName(String appName) {
        List<String> result = Lists.newArrayList();
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppNameLike("%" + appName + "%");
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        if (CollectionUtils.isEmpty(merchantBaseList)) {
            return result;
        }
        result = merchantBaseList.stream().map(MerchantBase::getAppId).collect(Collectors.toList());
        return result;
    }
}
