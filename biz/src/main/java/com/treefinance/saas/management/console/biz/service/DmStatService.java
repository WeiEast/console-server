package com.treefinance.saas.management.console.biz.service;


import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.management.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.management.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.management.console.common.domain.vo.DmStatDayVO;

public interface DmStatService {

    SaasResult<DmStatActualVO> statDsActual(DmStatDsRequest request);

    SaasResult<DmStatDayVO> statDsDay(DmStatDsRequest request);

    SaasResult<DmStatActualVO> statTpActual(DmStatTpRequest request);

    SaasResult<DmStatDayVO> statTpDay(DmStatTpRequest request);

}
