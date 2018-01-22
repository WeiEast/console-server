package com.treefinance.saas.management.console.biz.service;


import com.treefinance.saas.management.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.management.console.common.domain.request.DmStatTpRequest;
import com.treefinance.saas.management.console.common.domain.vo.DmStatActualVO;
import com.treefinance.saas.management.console.common.domain.vo.DmStatDayVO;

public interface DmStatService {

    DmStatActualVO statDsActual(DmStatDsRequest request);

    DmStatDayVO statDsDay(DmStatDsRequest request);

    DmStatActualVO statTpActual(DmStatTpRequest request);

    DmStatDayVO statTpDay(DmStatTpRequest request);

}
