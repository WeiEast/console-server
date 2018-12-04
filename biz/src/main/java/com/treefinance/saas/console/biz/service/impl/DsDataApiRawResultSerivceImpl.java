package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.treefinance.saas.console.biz.service.AppLicenseService;
import com.treefinance.saas.console.biz.service.DsDataApiRawResultSerivce;
import com.treefinance.saas.console.common.domain.request.DsDataApiRequest;
import com.treefinance.saas.console.common.domain.vo.DataApiRawResultVO;
import com.treefinance.saas.console.util.CallbackDataUtils;
import com.treefinance.saas.console.util.RemoteDataUtils;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.dto.DataApiRawResultDTO;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.facade.DataApiRawResultFacade;
import com.treefinance.saas.dataservice.dataserver.dataapirawresult.request.DataApiRawResultRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
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

    @Override
    public SaasResult<Map<String, Object>> findPageByExample(DsDataApiRequest request) {
        DataApiRawResultRequest dataApiRawResultRequest = new DataApiRawResultRequest();
        BeanUtils.copyProperties(request, dataApiRawResultRequest);
        List<DataApiRawResultDTO> dtoList = dataApiRawResultFacade.query(dataApiRawResultRequest);
        List<DataApiRawResultVO> voList = dtoList.stream().map(this::convert2VO).collect(Collectors.toList());
        return Results.newPageResult(request, dataApiRawResultFacade.count(dataApiRawResultRequest), voList);
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
        try {
            byte[] result = RemoteDataUtils.download(ossUrl, byte[].class);
            if (result != null) {
                String secretKey = appLicenseService.getAppDataSecretKeyByAppId(appId);
                return CallbackDataUtils.decryptByAES(result, secretKey);
            } else {
                logger.warn("oss下载数据失败,数据为空,ossUrl - {}", ossUrl);
            }
        } catch (Exception e) {
            logger.error("oss下载解密数据失败,ossUrl - {}", ossUrl, e);
        }
        return null;
    }

}
