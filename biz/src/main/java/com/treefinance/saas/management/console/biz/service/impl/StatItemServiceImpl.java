package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.StatItemService;
import com.treefinance.saas.management.console.common.domain.vo.StatItemVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
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
public class StatItemServiceImpl implements StatItemService {
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
        List<StatItemVO> statItemVOS = BeanUtils.convertList(result.getData(), StatItemVO.class);
        return Results.newSuccessResult(statItemVOS);
    }

    @Override
    public SaasResult<Long> saveStatItem(StatItemVO statItemVO) {
        StatItemRO statItemRO = BeanUtils.convert(statItemVO, new StatItemRO());
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
