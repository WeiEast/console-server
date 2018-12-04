package com.treefinance.saas.console.biz.service;


import com.treefinance.saas.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.console.common.domain.vo.DmStatDayVO;
import com.treefinance.saas.knife.result.SaasResult;

public interface DmStatService {

    SaasResult<DmStatActualVO> statDsActual(DmStatDsRequest request);

    SaasResult<DmStatDayVO> statDsDay(DmStatDsRequest request);

    SaasResult<DmStatActualVO> statTpActual(DmStatTpRequest request);

    SaasResult<DmStatDayVO> statTpDay(DmStatTpRequest request);

}
