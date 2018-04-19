package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.grapserver.facade.enums.ETaskAttribute;
import com.treefinance.saas.management.console.biz.common.handler.CallbackSecureHandler;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.OssDataService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.dto.CallbackLicenseDTO;
import com.treefinance.saas.management.console.common.domain.request.OssDataRequest;
import com.treefinance.saas.management.console.common.domain.vo.OssCallbackDataVO;
import com.treefinance.saas.management.console.common.enumeration.EBizType;
import com.treefinance.saas.management.console.common.enumeration.ECallBackDataType;
import com.treefinance.saas.management.console.common.enumeration.ETaskStatus;
import com.treefinance.saas.management.console.common.exceptions.BizException;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
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
import com.treefinance.saas.merchant.center.facade.result.grapsever.AppCallbackResult;
import com.treefinance.saas.merchant.center.facade.service.AppCallbackConfigFacade;
import com.treefinance.saas.merchant.center.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.monitor.common.utils.RemoteDataDownloadUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

/**
 * Created by haojiahong on 2017/11/21.
 */
@Service
public class OssDataServiceImpl implements OssDataService {

    private static final Logger logger = LoggerFactory.getLogger(OssDataService.class);

    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;

    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskAttributeMapper taskAttributeMapper;
    @Autowired
    private TaskCallbackLogMapper taskCallbackLogMapper;

    @Autowired
    private AppCallbackConfigFacade appCallbackConfigFacade;
    @Autowired
    protected CallbackSecureHandler callbackSecureHandler;
    @Autowired
    private AppLicenseService appLicenseService;


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

            QueryMerchantByAppName queryMerchantByAppName = new QueryMerchantByAppName();
            queryMerchantByAppName.setAppName(request.getAppName());
            MerchantResult<List<MerchantBaseInfoResult>> listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseByAppName(queryMerchantByAppName);
            List<MerchantBase> list = DataConverterUtils.convert(listMerchantResult.getData(),MerchantBase.class);
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
        TaskCallbackLog log = taskCallbackLogMapper.selectByPrimaryKey(id);
        if (log == null) {
            logger.error("oss数据下载,id={}的callbackLog记录不存在", id);
            return Results.newFailedResult(CommonStateCode.DOWNLOAD_ERROR);
        }
        JSONObject requestParamJsonObj;
        try {
            requestParamJsonObj = JSONObject.parseObject(log.getRequestParam());
        } catch (Exception e) {
            logger.error("oss数据下载,id={}的callbackLog记录解析dataUrl异常,log={}", id, JSON.toJSONString(log));
            return Results.newFailedResult(CommonStateCode.DOWNLOAD_ERROR);
        }
        String dataUrl = requestParamJsonObj.getString("dataUrl");
        if (StringUtils.isBlank(dataUrl)) {
            logger.error("oss数据下载,id={}的记录requestParam有误,log={}", id, JSON.toJSONString(log));
            return Results.newFailedResult(CommonStateCode.DOWNLOAD_ERROR);
        }
        String ossData = this.getOssData(log, dataUrl);
        if (StringUtils.isBlank(ossData)) {
            logger.error("oss数据下载,id={}的记录从oss上下载并解密数据失败,log={}", id, JSON.toJSONString(log));
            return Results.newFailedResult(CommonStateCode.DOWNLOAD_ERROR);
        }

        JSONObject jsonObject = JSON.parseObject(ossData);
        ossData = JSON.toJSONString(jsonObject, WriteMapNullValue, PrettyFormat);
        OutputStream outputStream = null;
        try {
            byte[] data = ossData.getBytes();
            String fileName = "data.json";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Results.newSuccessResult("下载成功");
    }

    @Override
    public Object downloadOssDataCheck(Long id) {
        TaskCallbackLog log = taskCallbackLogMapper.selectByPrimaryKey(id);
        if (log == null) {
            logger.error("oss数据下载,id={}的callbackLog记录不存在", id);
            throw new BizException("下载失败");
        }
        JSONObject requestParamJsonObj;
        try {
            requestParamJsonObj = JSONObject.parseObject(log.getRequestParam());
        } catch (Exception e) {
            logger.error("oss数据下载,id={}的callbackLog记录解析dataUrl异常,log={}", id, JSON.toJSONString(log));
            throw new BizException("回调参数有误,解析异常");
        }
        String dataUrl = requestParamJsonObj.getString("dataUrl");
        if (StringUtils.isBlank(dataUrl)) {
            logger.error("oss数据下载,id={}的记录requestParam有误,log={}", id, JSON.toJSONString(log));
            throw new BizException("回调参数有误,不存在dataUrl");
        }
        String ossData = this.getOssData(log, dataUrl);
        if (StringUtils.isBlank(ossData)) {
            logger.error("oss数据下载,id={}的记录从oss上下载并解密数据失败,log={}", id, JSON.toJSONString(log));
            throw new BizException("oss数据下载解密异常");
        }
        return Results.newSuccessResult(true);
    }

    private String getOssData(TaskCallbackLog log, String dataUrl) {
        String dataKey = "";
        //后端回调
        if (log.getType() == 1) {
            Long callbackConfigId = log.getConfigId();
            List<Integer> list = new ArrayList<>();
            list.add(callbackConfigId.intValue());
            QueryAppCallBackConfigByIdRequest queryAppCallBackConfigByIdRequest = new QueryAppCallBackConfigByIdRequest();
            queryAppCallBackConfigByIdRequest.setId(list);

            MerchantResult<List<AppCallbackConfigResult>> merchantResult = appCallbackConfigFacade.queryAppCallBackConfigById(queryAppCallBackConfigByIdRequest);
            List<AppCallbackConfig> appCallbackConfigList = DataConverterUtils.convert(merchantResult.getData(),AppCallbackConfig.class);
            AppCallbackConfig callbackConfig = appCallbackConfigList.get(0);
            if (callbackConfig == null) {
                logger.error("oss数据下载,log={}未查询到回调配置信息", JSON.toJSONString(log));
                return null;
            }
            if (callbackConfig.getIsNewKey() == 1) {
                CallbackLicenseDTO callbackLicenseDTO = appLicenseService.selectCallbackLicenseById(callbackConfig.getId());
                if (callbackLicenseDTO == null) {
                    logger.error("oss数据下载,log={}未查询到秘钥信息", JSON.toJSONString(log));
                    return null;
                }
                dataKey = callbackLicenseDTO.getDataSecretKey();
            } else {
                Long taskId = log.getTaskId();
                Task task = taskMapper.selectByPrimaryKey(taskId);
                if (task == null) {
                    logger.error("oss数据下载,log={}未查询到任务信息", JSON.toJSONString(log));
                    return null;
                }
                String appId = task.getAppId();
                AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(appId);
                if (appLicenseDTO == null) {
                    logger.error("oss数据下载,log={}未查询到秘钥信息", JSON.toJSONString(log));
                    return null;
                }
                dataKey = appLicenseDTO.getDataSecretKey();
            }
        }
        //前端回调
        if (log.getType() == 2) {
            Long taskId = log.getTaskId();
            Task task = taskMapper.selectByPrimaryKey(taskId);
            if (task == null) {
                logger.error("oss数据下载,log={}未查询到任务信息", JSON.toJSONString(log));
                return null;
            }
            String appId = task.getAppId();
            AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(appId);
            if (appLicenseDTO == null) {
                logger.error("oss数据下载,log={}未查询到秘钥信息", JSON.toJSONString(log));
                return null;
            }
            dataKey = appLicenseDTO.getDataSecretKey();
        }

        if (StringUtils.isBlank(dataKey)) {
            return null;
        }
        String data;
        try {
            byte[] result = RemoteDataDownloadUtils.download(dataUrl, byte[].class);
            if (result == null) {
                logger.info("oss数据下载,log={}从oss上下载数据失败,数据为空", JSON.toJSONString(log));
                return null;
            }
            data = callbackSecureHandler.decryptByAES(result, dataKey);
        } catch (Exception e) {
            logger.error("oss数据下载,log={}从oss上下载解密数据失败", JSON.toJSONString(log), e);
            return null;
        }
        return data;
    }

    private List<OssCallbackDataVO> wrapperOssCallbackData(List<TaskCallbackLog> taskCallbackLogList, List<Task> list, OssDataRequest request) {

        List<Long> taskIdList = list.stream().map(Task::getId).collect(Collectors.toList());
        Map<Long, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getId, task -> task));

        List<String> appIdList = list.stream().map(Task::getAppId).distinct().collect(Collectors.toList());


        QueryMerchantByAppIdRequest queryMerchantByAppIdRequest = new QueryMerchantByAppIdRequest();
        queryMerchantByAppIdRequest.setAppIds(appIdList);

        MerchantResult<List<MerchantBaseResult>>  listMerchantResult = merchantBaseInfoFacade.queryMerchantBaseListByAppId(queryMerchantByAppIdRequest);
        List<MerchantBase> merchantBaseList = DataConverterUtils.convert(listMerchantResult.getData(),MerchantBase.class);
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

        QueryAppCallBackConfigByIdRequest queryAppCallBackConfigByIdRequest = new QueryAppCallBackConfigByIdRequest();
        queryAppCallBackConfigByIdRequest.setId(configIdList);
        MerchantResult<List<AppCallbackConfigResult>>  merchantResult =appCallbackConfigFacade.queryAppCallBackConfigById(queryAppCallBackConfigByIdRequest);
        List<AppCallbackConfig> callbackConfigList = DataConverterUtils.convert(merchantResult.getData(),AppCallbackConfig.class);
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
            vo.setCanDownload(canDownload(log));
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
        dataList = dataList.stream().sorted((o1, o2) -> o2.getTaskStartTime().compareTo(o1.getTaskStartTime())).collect(Collectors.toList());
        return dataList;
    }

    private Boolean canDownload(TaskCallbackLog log) {
        if (StringUtils.isNotBlank(log.getRequestParam())) {
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(log.getRequestParam());
            } catch (Exception e) {
                logger.error("json转换异常", e);
                return false;
            }
            String dataUrl = jsonObject.getString("dataUrl");
            String expirationTime = jsonObject.get("expirationTime").toString();
            if (StringUtils.isNotBlank(dataUrl) && StringUtils.isNotBlank(expirationTime)) {
                if (Integer.valueOf(expirationTime) > System.currentTimeMillis()) {
                    return true;
                }
            }
        }

        return false;
    }

}
