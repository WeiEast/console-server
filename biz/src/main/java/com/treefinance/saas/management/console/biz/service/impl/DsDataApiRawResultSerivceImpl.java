package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.dto.DataApiRawResultDTO;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.facade.DataApiRawResultFacade;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.request.DataApiRawResultRequest;
import com.treefinance.saas.management.console.biz.common.handler.CallbackSecureHandler;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.biz.service.DsDataApiRawResultSerivce;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.management.console.common.domain.vo.DataApiRawResultVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.monitor.common.utils.RemoteDataDownloadUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DsDataApiRawResultSerivceImpl implements DsDataApiRawResultSerivce {
    private static final Logger logger = LoggerFactory.getLogger(DsDataApiRawResultSerivceImpl.class);

    @Autowired
    private DataApiRawResultFacade dataApiRawResultFacade;
    @Autowired
    private AppLicenseService appLicenseService;
    @Autowired
    protected CallbackSecureHandler callbackSecureHandler;


    @Override
    public Result<Map<String, Object>> findPageByExample(DsDataApiRequest request) {
        DataApiRawResultRequest dataApiRawResultRequest = new DataApiRawResultRequest();
        BeanUtils.copyProperties(request, dataApiRawResultRequest);
        List<DataApiRawResultDTO> dtoList = dataApiRawResultFacade.query(dataApiRawResultRequest);
        List<DataApiRawResultVO> voList = dtoList.stream().map(dto -> convert2VO(dto)).collect(Collectors.toList());
        return Results.newSuccessPageResult(request, dataApiRawResultFacade.count(dataApiRawResultRequest), voList);
    }


    private DataApiRawResultVO convert2VO(DataApiRawResultDTO dto) {
        DataApiRawResultVO vo = new DataApiRawResultVO();
        BeanUtils.copyProperties(dto, vo);
        String ossUrl = vo.getParamsStorePath();
        String ossjson = getOssJson(dto.getAppId(), ossUrl);
        if (StringUtils.isNotBlank(ossjson)) {
            JSONObject jsonObject = JSON.parseObject(ossjson);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();

            JsonElement requestJsonElement = jsonParser.parse(jsonObject.getString("requestParams"));
            vo.setRequesetJson(gson.toJson(requestJsonElement));

            JsonElement responseJsonElement = jsonParser.parse(jsonObject.getString("responseParams"));
            vo.setResponseJson(gson.toJson(responseJsonElement));

            vo.setErrorMsg(jsonObject.getString("detailErrorMsg"));
        }
        return vo;
    }

    private String getOssJson(String appId, String ossUrl) {
        AppLicenseDTO appLicenseDTO = appLicenseService.selectOneByAppId(appId);
        String secretKey = appLicenseDTO.getDataSecretKey();
        try {
            byte[] result = RemoteDataDownloadUtils.download(ossUrl, byte[].class);
            if (result == null) {
                logger.info("oss下载数据失败,数据为空,ossUrl - {}", ossUrl);
                return null;
            }
            String data = callbackSecureHandler.decryptByAES(result, secretKey);
            return data;
        } catch (Exception e) {
            logger.error("oss下载解密数据失败,ossUrl - {}", ossUrl, e);
            return null;
        }
    }


    private String jsonMerge(JSONObject jsonObject) {
        Map<String, Object> map = jsonObject;
        map.forEach((k, v) -> {
            if (v instanceof Long) {
                map.put(k, String.valueOf(v));
            }
            if (v instanceof JSONObject) {
                jsonMerge((JSONObject) v);
            }
            if (v instanceof JSONArray) {
                ((JSONArray) v).forEach(j -> jsonMerge((JSONObject) j));
            }
        });
        return JSON.toJSONString(map);
    }

}
