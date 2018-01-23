package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.dataservice.dataserver.dataapiconfig.facade.DataApiConfigFacade;
import com.treefinance.saas.management.console.biz.service.DataApiConfigService;
import com.treefinance.saas.management.console.common.domain.vo.DataTypeVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataApiConfigServiceImpl implements DataApiConfigService{

    @Autowired
    private DataApiConfigFacade dataApiConfigFacade;

    @Override
    public Result<List<DataTypeVO>> getDsApiConfigType() {
        Map<String,String> resultMap = dataApiConfigFacade.getDataApiConfigType();
        List<DataTypeVO> voList = Lists.newArrayList();
        resultMap.forEach((k,v) -> {
            DataTypeVO vo = new DataTypeVO();
            vo.setMsg(v);
            vo.setType(k);
            voList.add(vo);
        });
        return Results.newSuccessResult(voList);
    }
}
