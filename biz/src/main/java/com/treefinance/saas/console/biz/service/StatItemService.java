package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.vo.StatItemVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;

/**
 * Created by yh-treefinance on 2018/5/22.
 */
public interface StatItemService {

    /**
     * 根据模板ID查询数据项
     *
     * @param templateId
     * @return
     */
    SaasResult<List<StatItemVO>> queryByTemplateId(Long templateId);

    /**
     * 数据项保存（新增、修改）
     *
     * @param statItemVO
     * @return
     */
    SaasResult<Long> saveStatItem(StatItemVO statItemVO);
}
