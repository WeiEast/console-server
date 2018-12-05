package com.treefinance.saas.console.biz.service.impl;

import com.treefinance.saas.console.biz.service.StatItemService;
import com.treefinance.saas.console.common.domain.vo.StatItemVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.autostat.StatItemRO;
import com.treefinance.saas.monitor.facade.service.autostat.StatItemFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yh-treefinance on 2018/5/22.
 */
@Component("statItemService")
public class StatItemServiceImpl extends AbstractService implements StatItemService {
    @Autowired
    private StatItemFacade statItemFacade;

    @Override
    public SaasResult<List<StatItemVO>> queryByTemplateId(Long templateId) {
        MonitorResult<List<StatItemRO>> result = statItemFacade.queryByTemplateId(templateId);
        if (result == null) {
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        }
        if (StringUtils.isNotEmpty(result.getErrorMsg())) {
            return Results.newFailedResult(CommonStateCode.FAILURE, result.getErrorMsg());
        }
        List<StatItemVO> statItemVOS = this.convertList(result.getData(), StatItemVO.class);
        return Results.newSuccessResult(statItemVOS);
    }

    @Override
    public SaasResult<Long> saveStatItem(StatItemVO statItemVO) {
        StatItemRO statItemRO = new StatItemRO();
        this.copy(statItemVO, statItemRO);
        Long id = statItemRO.getId();
        if (id == null) {
            MonitorResult<Long> result = statItemFacade.addStatItem(statItemRO);
            if (result == null) {
                return Results.newFailedResult(CommonStateCode.FAILURE);
            }
            if (StringUtils.isNotEmpty(result.getErrorMsg())) {
                return Results.newFailedResult(CommonStateCode.FAILURE, result.getErrorMsg());
            }
            id = result.getData();
        } else {
            MonitorResult<Boolean> result = statItemFacade.updateStatItem(statItemRO);
            if (result == null) {
                return Results.newFailedResult(CommonStateCode.FAILURE);
            }
            if (StringUtils.isNotEmpty(result.getErrorMsg())) {
                return Results.newFailedResult(CommonStateCode.FAILURE, result.getErrorMsg());
            }
        }
        return Results.newSuccessResult(id);
    }
}
