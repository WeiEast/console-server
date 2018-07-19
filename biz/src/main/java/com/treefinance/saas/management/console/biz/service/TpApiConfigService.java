package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.vo.DataTypeVO;

import java.util.List;

public interface TpApiConfigService {

    /**
     * 获取TP层distinct type的类型
     *
     * @return key:TpApiName   blk_hd
     * val:desc        华道黑名单接口
     */
    SaasResult<List<DataTypeVO>> getTpApiConfigType();
}
