package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.treefinance.saas.console.biz.service.DataApiConfigService;
import com.treefinance.saas.console.common.domain.vo.DataTypeVO;
import com.treefinance.saas.dataservice.dataserver.dataapiconfig.facade.DataApiConfigFacade;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataApiConfigServiceImpl implements DataApiConfigService {

    @Autowired
    private DataApiConfigFacade dataApiConfigFacade;

    @Override
    public SaasResult<List<DataTypeVO>> getDsApiConfigType() {
        Map<String, String> resultMap = dataApiConfigFacade.getDataApiConfigType();
        List<DataTypeVO> voList = Lists.newArrayList();
        resultMap.forEach((k, v) -> {
            DataTypeVO vo = new DataTypeVO();
            vo.setMsg(v);
            vo.setType(k);
            voList.add(vo);
        });
        return Results.newSuccessResult(voList);
    }

    @Override
    public SaasResult<List<JSONObject>> getDsAppid(String dataApiNameType) {
        List<String> resultList = dataApiConfigFacade.getAppIdByNameAndType(dataApiNameType);
        List<JSONObject> jsonObjectList = resultList.stream()
                .map(r -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("appId", r);
                    return jsonObject;
                })
                .collect(Collectors.toList());
        return Results.newSuccessResult(jsonObjectList);
    }
}
