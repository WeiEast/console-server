package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.dataservice.dataserver.tpapiconfig.facade.TpApiConfigFacade;
import com.treefinance.saas.management.console.biz.service.TpApiConfigService;
import com.treefinance.saas.management.console.common.domain.vo.DataTypeVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TpApiConfigServiceImpl implements TpApiConfigService{

    @Autowired
    private TpApiConfigFacade tpApiConfigFacade;

    @Override
    public Result<List<DataTypeVO>> getTpApiConfigType() {

        Map<String,String> resultMap = tpApiConfigFacade.getTpApiConfigType();
        List<DataTypeVO> voList = Lists.newArrayList();
        resultMap.forEach((k,v) -> {
            DataTypeVO vo = new DataTypeVO();
            vo.setType(k);
            vo.setMsg(v);
            voList.add(vo);
        });
        return Results.newSuccessResult(voList);
    }
}
