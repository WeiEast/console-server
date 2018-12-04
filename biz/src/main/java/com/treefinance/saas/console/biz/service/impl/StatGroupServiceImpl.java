package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.biz.service.StatGroupService;
import com.treefinance.saas.console.common.domain.vo.StatGroupVO;
import com.treefinance.saas.console.share.adapter.AbstractServiceAdapter;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.request.StatGroupRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.StatGroupRO;
import com.treefinance.saas.monitor.facade.service.autostat.StatGroupFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author guoguoyun
 * @date 2018/4/27上午11:09
 */
@Service
public class StatGroupServiceImpl extends AbstractServiceAdapter implements StatGroupService {

    private static final Logger logger = LoggerFactory.getLogger(StatGroupService.class);
    @Autowired
    private StatGroupFacade statGroupFacade;

    @Override
    public SaasResult<List<StatGroupVO>> queryStatGroup(StatGroupVO statGroupVO) {
        StatGroupRequest groupStatRequest = new StatGroupRequest();
        this.copy(statGroupVO, groupStatRequest);
        MonitorResult<List<StatGroupRO>> monitorResult = statGroupFacade.queryStatGroup(groupStatRequest);
        if (monitorResult.getData() == null) {
            logger.info("找不到相关数据");
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        }
        List<StatGroupVO> statGroupVOList = this.convertList(monitorResult.getData(), StatGroupVO.class);
        logger.info("查询的统计分组数据为{}", JSON.toJSONString(statGroupVOList));
        return Results.newSuccessResult(statGroupVOList);
    }

    @Override
    public SaasResult<Boolean> addOrUpdateStatGroup(StatGroupVO statGroupVO) {
        StatGroupRequest groupStatRequest = new StatGroupRequest();
        this.copyProperties(statGroupVO, groupStatRequest);
        MonitorResult<Boolean> monitorResult = statGroupFacade.addOrUpdateStatGroup(groupStatRequest);
        if (!monitorResult.getData()) {
            logger.info("新增或修改计分组数据失败");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());
    }

    @Override
    public SaasResult<Set<Integer>> queryAllgroupIndex() {
        BaseRequest baseRequest = new BaseRequest();
        MonitorResult<Set<Integer>> monitorResult = statGroupFacade.queryAllGroupIndex(baseRequest);
        if (monitorResult.getData() == null) {
            logger.info("找不到相关数据");
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        }
        logger.info("查询的统计分组序号数据为{}", JSON.toJSONString(monitorResult.getData()));
        return Results.newSuccessResult(monitorResult.getData());
    }
}
