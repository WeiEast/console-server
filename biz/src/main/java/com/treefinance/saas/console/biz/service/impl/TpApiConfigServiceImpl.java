package com.treefinance.saas.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.console.biz.service.TpApiConfigService;
import com.treefinance.saas.console.common.domain.vo.DataTypeVO;
import com.treefinance.saas.dataservice.dataserver.tpapiconfig.facade.TpApiConfigFacade;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TpApiConfigServiceImpl implements TpApiConfigService {

    @Autowired
    private TpApiConfigFacade tpApiConfigFacade;

    @Override
    public SaasResult<List<DataTypeVO>> getTpApiConfigType() {

        Map<String, String> resultMap = tpApiConfigFacade.getTpApiConfigType();
        List<DataTypeVO> voList = Lists.newArrayList();
        resultMap.forEach((k, v) -> {
            DataTypeVO vo = new DataTypeVO();
            vo.setType(k);
            vo.setMsg(v);
            voList.add(vo);
        });
        return Results.newSuccessResult(voList);
    }
}
