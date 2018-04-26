package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.BasicDataVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.base.PageRequest;
import com.treefinance.saas.monitor.facade.domain.ro.BasicDataRO;
import com.treefinance.saas.monitor.facade.service.BasicDataFacade;

import java.util.List;
import java.util.Map;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/23下午5:12
 */
public interface BasicDataService {
    Result<Map<String, Object>> queryAllBasicData(PageRequest pageRequest);

    Result<Boolean> addBasciData(BasicDataVO basicDataVO);


    Result<Boolean> updateBasciData(BasicDataVO basicDataVO);


    Result<List<String>> querydataName(BaseRequest baseRequest);

    Result<String> getdataNameById(BasicDataVO basicDataVO);
}
