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
import com.treefinance.saas.management.console.biz.common.handler.CallbackSecureHandler;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.TaskService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.CallbackLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.TaskCallbackLogDTO;
import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.domain.vo.TaskVO;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.CommonUtils;
import com.treefinance.saas.management.console.dao.entity.*;
import com.treefinance.saas.management.console.dao.mapper.*;
import com.treefinance.saas.monitor.common.utils.AESSecureUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
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
    private TaskLogMapper taskLogMapper;
    @Autowired
    private TaskCallbackLogMapper taskCallbackLogMapper;
    @Autowired
    private AppCallbackConfigMapper appCallbackConfigMapper;
    @Autowired
    private AppLicenseService appLicenseService;
    @Autowired
    private CallbackSecureHandler callbackSecureHandler;
    @Autowired
    private TaskAttributeMapper taskAttributeMapper;

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
            } else {//根据appName未查询到appId,则直接返回空集合
                return Results.newSuccessPageResult(taskRequest, 0, Lists.newArrayList());
            }
        }
        criteria.andCreateTimeGreaterThanOrEqualTo(taskRequest.getStartDate());
        // +23:59:59
        criteria.andCreateTimeLessThanOrEqualTo(DateUtils.addSeconds(taskRequest.getEndDate(), 24 * 60 * 60 - 1));
        Byte bizType = BizTypeEnum.valueOfType(BizTypeEnum.valueOf(taskRequest.getType()));
        criteria.andBizTypeEqualTo(bizType);
        long count = taskMapper.countByExample(taskCriteria);
        List<TaskVO> result = Lists.newArrayList();
        if (count <= 0) {
            return Results.newSuccessPageResult(taskRequest, count, result);
        }
        List<Task> taskList = taskMapper.selectPaginationByExample(taskCriteria);
        //<appId,MerchantBase>
        Map<String, MerchantBase> merchantBaseMap = getMerchantBaseMap(taskList);
        //<website,OperatorRO>
        Map<Long, TaskAttribute> operatorMap = Maps.newHashMap();
        if (BizTypeEnum.valueOfType(BizTypeEnum.OPERATOR).equals(bizType)) {
            operatorMap = getOperatorMapFromAttribute(taskList);
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
        return Results.newSuccessPageResult(taskRequest, count, result);
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
            if (StringUtils.isNotBlank(dataUrl)) {
                String expires = CommonUtils.getUrlQueryString(dataUrl, "Expires");
                if (StringUtils.isNotBlank(expires)) {
                    if (Integer.valueOf(expires) * 1000 > System.currentTimeMillis()) {
                        return true;
                    }
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

        TaskCallbackLogCriteria taskCallbackLogCriteria = new TaskCallbackLogCriteria();
        taskCallbackLogCriteria.createCriteria().andTaskIdIn(taskIdList);
        List<TaskCallbackLog> taskCallbackLogList = taskCallbackLogMapper.selectByExample(taskCallbackLogCriteria);
        if (CollectionUtils.isEmpty(taskCallbackLogList)) {
            return result;
        }
        List<Integer> configIdList = taskCallbackLogList.stream().map(t -> t.getConfigId().intValue()).collect(Collectors.toList());
        AppCallbackConfigCriteria appCallbackConfigCriteria = new AppCallbackConfigCriteria();
        appCallbackConfigCriteria.createCriteria().andIdIn(configIdList);
        List<AppCallbackConfig> appCallbackConfigList = appCallbackConfigMapper.selectByExample(appCallbackConfigCriteria);
        //<configId,AppCallbackConfig>
        Map<Integer, AppCallbackConfig> appCallbackConfigMap = appCallbackConfigList.stream().collect(Collectors.toMap(AppCallbackConfig::getId, t -> t));

        for (TaskCallbackLog log : taskCallbackLogList) {
            Task task = taskMap.get(log.getTaskId());
            if (task == null) {
                logger.error("解密回调参数时,在任务回调记录表中未找到对应的任务信息 taskId={}", log.getTaskId());
                continue;
            }
            AppCallbackConfig appCallbackConfig = appCallbackConfigMap.get(log.getConfigId().intValue());
            if (appCallbackConfig == null) {
                logger.error("解密回调参数时,任务回调记录表中未找到对应的商户回调配置信息 configId={}", log.getConfigId());
                continue;
            }
            if (ECallBackDataType.SHIPPING_ADDRESS.getCode().equals(appCallbackConfig.getDataType())) {
                logger.info("收货地址的回调不显示");
                continue;
            }
            Long taskId = task.getId();
//            String plainParams = getPlainParamsCallbackLog(task, appCallbackConfig, log);
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
        return result;
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

    private Map<String, MerchantBase> getMerchantBaseMap(List<Task> taskList) {
        List<String> appIdList = taskList.stream().map(Task::getAppId).distinct().collect(Collectors.toList());
        MerchantBaseCriteria merchantBaseCriteria = new MerchantBaseCriteria();
        merchantBaseCriteria.createCriteria().andAppIdIn(appIdList);
        List<MerchantBase> merchantBaseList = merchantBaseMapper.selectByExample(merchantBaseCriteria);
        Map<String, MerchantBase> merchantBaseMap = merchantBaseList.stream().collect(Collectors.toMap(MerchantBase::getAppId, m -> m));
        return merchantBaseMap;
    }

    private List<String> getAppIdsLikeAppName(String appName) {
        appName = StringUtils.deleteWhitespace(appName);
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
