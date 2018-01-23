package com.treefinance.saas.management.console.biz.service;


import com.treefinance.saas.management.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.management.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.management.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.management.console.common.domain.vo.DmStatDayVO;
import com.treefinance.saas.management.console.common.result.Result;

public interface DmStatService {

    Result<DmStatActualVO> statDsActual(DmStatDsRequest request);

    Result<DmStatDayVO> statDsDay(DmStatDsRequest request);

    Result<DmStatActualVO> statTpActual(DmStatTpRequest request);

    Result<DmStatDayVO> statTpDay(DmStatTpRequest request);

}
