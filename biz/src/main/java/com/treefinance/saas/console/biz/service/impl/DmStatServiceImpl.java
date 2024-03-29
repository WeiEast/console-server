package com.treefinance.saas.console.biz.service.impl;

import com.treefinance.saas.console.biz.service.DmStatService;
import com.treefinance.saas.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.console.common.domain.vo.DmStatDayVO;
import com.treefinance.saas.dataservice.monitor.dto.DmStatActualDTO;
import com.treefinance.saas.dataservice.monitor.dto.DmStatDayDTO;
import com.treefinance.saas.dataservice.monitor.statds.facade.DmStatDsFacade;
import com.treefinance.saas.dataservice.monitor.statds.request.StatDsRequest;
import com.treefinance.saas.dataservice.monitor.stattp.facade.DmStatTpFacade;
import com.treefinance.saas.dataservice.monitor.stattp.request.StatTpRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmStatServiceImpl implements DmStatService {

    @Autowired
    private DmStatDsFacade dmStatDsFacade;
    @Autowired
    private DmStatTpFacade dmStatTpFacade;


    @Override
    public SaasResult<DmStatActualVO> statDsActual(DmStatDsRequest request) {
        StatDsRequest statDsRequest = new StatDsRequest();
        BeanUtils.copyProperties(request, statDsRequest);
        DmStatActualDTO dto = dmStatDsFacade.statDsActual(statDsRequest);
        DmStatActualVO vo = new DmStatActualVO();
        BeanUtils.copyProperties(dto, vo);
        return Results.newSuccessResult(vo);
    }


    @Override
    public SaasResult<DmStatDayVO> statDsDay(DmStatDsRequest request) {
        StatDsRequest statDsRequest = new StatDsRequest();
        BeanUtils.copyProperties(request, statDsRequest);
        DmStatDayDTO dto = dmStatDsFacade.statDsDay(statDsRequest);
        DmStatDayVO vo = new DmStatDayVO();
        BeanUtils.copyProperties(dto, vo);
        return Results.newSuccessResult(vo);
    }

    @Override
    public SaasResult<DmStatActualVO> statTpActual(DmStatTpRequest request) {
        StatTpRequest statTpRequest = new StatTpRequest();
        BeanUtils.copyProperties(request, statTpRequest);
        DmStatActualDTO dto = dmStatTpFacade.statTpActual(statTpRequest);
        DmStatActualVO vo = new DmStatActualVO();
        BeanUtils.copyProperties(dto, vo);
        return Results.newSuccessResult(vo);
    }

    @Override
    public SaasResult<DmStatDayVO> statTpDay(DmStatTpRequest request) {
        StatTpRequest statTpRequest = new StatTpRequest();
        BeanUtils.copyProperties(request, statTpRequest);
        DmStatDayDTO dto = dmStatTpFacade.statTpDay(statTpRequest);
        DmStatDayVO vo = new DmStatDayVO();
        BeanUtils.copyProperties(dto, vo);
        return Results.newSuccessResult(vo);
    }
}
