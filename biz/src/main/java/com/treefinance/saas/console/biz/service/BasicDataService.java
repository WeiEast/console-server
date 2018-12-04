package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.vo.BasicDataVO;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.request.autostat.BasicDataHistoryRequest;

import java.util.List;
import java.util.Map;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/23下午5:12
 */
public interface BasicDataService {
    SaasResult<Map<String, Object>> queryAllBasicData(PageRequest pageRequest);

    SaasResult<Boolean> addBasciData(BasicDataVO basicDataVO);


    SaasResult<Boolean> updateBasciData(BasicDataVO basicDataVO);


    SaasResult<List<String>> querydataName(BaseRequest baseRequest);

    SaasResult<String> getdataNameById(BasicDataVO basicDataVO);

    /**
     * 查询历史
     *
     * @param request
     * @return
     */
    SaasResult<Map<String, Object>> queryHistory(BasicDataHistoryRequest request);
}
