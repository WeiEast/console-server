package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.management.console.biz.service.OssDataService;
import com.treefinance.saas.management.console.common.domain.request.OssDataRequest;
import com.treefinance.saas.management.console.common.domain.vo.OssCallbackDataVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.enumeration.ETaskStatus;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/11/21.
 */
@Service
public class OssDataServiceImpl implements OssDataService {

    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;
    @Autowired
    private MerchantBaseMapper merchantBaseMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskAttributeMapper taskAttributeMapper;
    @Autowired
    private TaskCallbackLogMapper taskCallbackLogMapper;
    @Autowired
    private AppCallbackConfigMapper appCallbackConfigMapper;


    @Override
    public Object getOssCallbackDataList(OssDataRequest request) {
        TaskCriteria taskCriteria = new TaskCriteria();
        TaskCriteria.Criteria innerTaskCriteria = taskCriteria.createCriteria();
        if (request.getType() != null) {
            innerTaskCriteria.andBizTypeEqualTo(request.getType());
        }
        if (StringUtils.isNotBlank(request.getUniqueId())) {
            innerTaskCriteria.andUniqueIdEqualTo(request.getUniqueId());
        }
        if (StringUtils.isNotBlank(request.getAccountNo())) {
            innerTaskCriteria.andAccountNoEqualTo(iSecurityCryptoService.encrypt(request.getAccountNo(), EncryptionIntensityEnum.NORMAL));
        }
        if (request.getTaskId() != null) {
            innerTaskCriteria.andIdEqualTo(request.getTaskId());
        }
        if (StringUtils.isNotBlank(request.getAppName())) {
            MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
            merchantBaseCriteria.createCriteria().andAppNameLike("%" + request.getAppName() + "%");
            List<MerchantBase> list = merchantBaseMapper.selectByExample(merchantBaseCriteria);
            List<String> appIdList = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(list)) {
                appIdList = list.stream().map(MerchantBase::getAppId).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(appIdList)) {
                innerTaskCriteria.andAppIdIn(appIdList);
            }
        }
        List<Task> list = taskMapper.selectByExample(taskCriteria);
        if (CollectionUtils.isEmpty(list)) {
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        List<Long> taskIdList = list.stream().map(Task::getId).collect(Collectors.toList());
        TaskCallbackLogCriteria taskCallbackLogCriteria = new TaskCallbackLogCriteria();
        taskCallbackLogCriteria.setOffset(request.getOffset());
        taskCallbackLogCriteria.setLimit(request.getPageSize());
        taskCallbackLogCriteria.createCriteria().andTaskIdIn(taskIdList);
        long count = taskCallbackLogMapper.countByExample(taskCallbackLogCriteria);
        if (count <= 0) {
            return Results.newSuccessPageResult(request, 0, Lists.newArrayList());
        }
        List<TaskCallbackLog> taskCallbackLogList = taskCallbackLogMapper.selectPaginationByExample(taskCallbackLogCriteria);
        List<OssCallbackDataVO> dataList = wrapperOssCallbackData(taskCallbackLogList, list, request);
        return Results.newSuccessPageResult(request, count, dataList);
    }

    @Override
    public Object downloadOssData(Long id, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private List<OssCallbackDataVO> wrapperOssCallbackData(List<TaskCallbackLog> taskCallbackLogList, List<Task> list, OssDataRequest request) {

        List<Long> taskIdList = list.stream().map(Task::getId).collect(Collectors.toList());
        Map<Long, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getId, task -> task));

        List<String> appIdList = list.stream().map(Task::getAppId).distinct().collect(Collectors.toList());
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, merchantBase -> merchantBase));

        //运营商需展示运营商名称
        Map<Long, TaskAttribute> taskAttributeMap = Maps.newHashMap();
        if (request.getType() != null && EBizType.OPERATOR.getCode().equals(request.getType())) {
            TaskAttributeCriteria taskAttributeCriteria = new TaskAttributeCriteria();
            taskAttributeCriteria.createCriteria().andTaskIdIn(taskIdList).andNameEqualTo(ETaskAttribute.OPERATOR_GROUP_NAME.getAttribute());
            List<TaskAttribute> taskAttributeList = taskAttributeMapper.selectByExample(taskAttributeCriteria);
            taskAttributeMap = taskAttributeList.stream().collect(Collectors.toMap(TaskAttribute::getTaskId, taskAttribute -> taskAttribute));
        }

        List<Long> callbackConfigIdList = taskCallbackLogList.stream().map(TaskCallbackLog::getConfigId).distinct().collect(Collectors.toList());
        List<Integer> configIdList = Lists.newArrayList();
        configIdList.addAll(callbackConfigIdList.stream().map(Long::intValue).collect(Collectors.toList()));
        AppCallbackConfigCriteria appCallbackConfigCriteria = new AppCallbackConfigCriteria();
        appCallbackConfigCriteria.createCriteria().andIdIn(configIdList);
        List<AppCallbackConfig> callbackConfigList = appCallbackConfigMapper.selectByExample(appCallbackConfigCriteria);
        Map<Integer, AppCallbackConfig> callbackConfigMap = callbackConfigList.stream().collect(Collectors.toMap(AppCallbackConfig::getId, appCallbackConfig -> appCallbackConfig));


        List<OssCallbackDataVO> dataList = Lists.newArrayList();
        for (TaskCallbackLog log : taskCallbackLogList) {
            OssCallbackDataVO vo = new OssCallbackDataVO();
            Long taskId = log.getTaskId();
            Task task = taskMap.get(taskId);
            vo.setId(log.getId());
            vo.setTaskId(taskId);
            vo.setCallbackRequestParam(log.getRequestParam());
            vo.setCallbackResponseData(log.getResponseData());
            if (task != null) {
                vo.setUniqueId(task.getUniqueId());
                if (StringUtils.isNotBlank(task.getAccountNo())) {
                    vo.setAccountNo(iSecurityCryptoService.decrypt(task.getAccountNo(), EncryptionIntensityEnum.NORMAL));
                }
                vo.setTaskStartTime(task.getCreateTime());
                vo.setTaskStatusName(ETaskStatus.getNameByStatus(task.getStatus()));
                if (!ETaskStatus.RUNNING.getStatus().equals(task.getStatus())) {
                    vo.setTaskEndTime(task.getLastUpdateTime());
                }
                if (StringUtils.isNotBlank(task.getAppId())) {
                    MerchantBase merchantBase = merchantBaseMap.get(task.getAppId());
                    if (merchantBase != null) {
                        vo.setAppName(merchantBase.getAppName());
                    }
                }
            }
            if (request.getType() != null && EBizType.OPERATOR.getCode().equals(request.getType()) && MapUtils.isNotEmpty(taskAttributeMap)) {
                TaskAttribute taskAttribute = taskAttributeMap.get(taskId);
                vo.setGroupName(taskAttribute.getValue());
            }
            AppCallbackConfig appCallbackConfig = callbackConfigMap.get(log.getConfigId().intValue());
            if (appCallbackConfig != null) {
                vo.setCallbackTypeName(ECallBackDataType.getText(appCallbackConfig.getDataType()));
            }
            dataList.add(vo);
        }
        return dataList;
    }
}
