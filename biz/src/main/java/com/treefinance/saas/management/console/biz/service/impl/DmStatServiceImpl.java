package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.dataservice.monitor.dto.DmStatDTO;
import com.treefinance.saas.dataservice.monitor.statds.facade.DmStatDsFacade;
import com.treefinance.saas.dataservice.monitor.statds.request.StatDsRequest;
import com.treefinance.saas.dataservice.monitor.stattp.facade.DmStatTpFacade;
import com.treefinance.saas.dataservice.monitor.stattp.request.StatTpRequest;
import com.treefinance.saas.management.console.biz.service.DmStatService;
import com.treefinance.saas.management.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.management.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.management.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.management.console.common.domain.vo.DmStatDayVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmStatServiceImpl implements DmStatService{

    @Autowired
    private DmStatDsFacade dmStatDsFacade;
    @Autowired
    private DmStatTpFacade dmStatTpFacade;


    @Override
    public Result<DmStatActualVO> statDsActual(DmStatDsRequest request) {
        StatDsRequest statDsRequest = new StatDsRequest();
        BeanUtils.copyProperties(request,statDsRequest);
        DmStatDTO dto = dmStatDsFacade.statDsActual(statDsRequest);
        DmStatActualVO vo = new DmStatActualVO();
        BeanUtils.copyProperties(dto,vo);
        return Results.newSuccessResult(vo);
    }


    @Override
    public Result<DmStatDayVO> statDsDay(DmStatDsRequest request) {
        StatDsRequest statDsRequest = new StatDsRequest();
        BeanUtils.copyProperties(request,statDsRequest);
        DmStatDTO dto = dmStatDsFacade.statDsDay(statDsRequest);
        DmStatDayVO vo = new DmStatDayVO();
        BeanUtils.copyProperties(dto,vo);
        return Results.newSuccessResult(vo);
    }

    @Override
    public Result<DmStatActualVO> statTpActual(DmStatTpRequest request) {
        StatTpRequest statTpRequest = new StatTpRequest();
        BeanUtils.copyProperties(request,statTpRequest);
        DmStatDTO dto = dmStatTpFacade.statTpActual(statTpRequest);
        DmStatActualVO vo = new DmStatActualVO();
        BeanUtils.copyProperties(dto,vo);
        return Results.newSuccessResult(vo);
    }

    @Override
    public Result<DmStatDayVO> statTpDay(DmStatTpRequest request) {
        StatTpRequest statTpRequest = new StatTpRequest();
        BeanUtils.copyProperties(request,statTpRequest);
        DmStatDTO dto = dmStatTpFacade.statTpDay(statTpRequest);
        DmStatDayVO vo = new DmStatDayVO();
        BeanUtils.copyProperties(dto,vo);
        return Results.newSuccessResult(vo);
    }
}
