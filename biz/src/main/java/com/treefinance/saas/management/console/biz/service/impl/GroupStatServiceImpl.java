package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.management.console.biz.service.GroupStatService;
import com.treefinance.saas.management.console.common.domain.vo.StatGroupVO;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import com.treefinance.saas.monitor.facade.domain.request.GroupStatRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.StatGroupRO;
import com.treefinance.saas.monitor.facade.service.stat.GroupStatFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27上午11:09
 */
@Service
public class GroupStatServiceImpl implements GroupStatService{

    private static  final Logger logger = LoggerFactory.getLogger(GroupStatService.class);
    @Autowired
    GroupStatFacade groupStatFacade;

    @Override
    public Result<List<StatGroupVO>> queryStatGroup(StatGroupVO statGroupVO ) {
        GroupStatRequest groupStatRequest =  new GroupStatRequest();
        groupStatRequest.setTemplateId(statGroupVO.getTemplateId());
        MonitorResult<List<StatGroupRO>> monitorResult =  groupStatFacade.queryStatGroup(groupStatRequest);
        if(monitorResult.getData()==null)
        {
            logger.info("找不到相关数据");
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        }
        List<StatGroupVO> statGroupVOList = BeanUtils.convertList(monitorResult.getData(),StatGroupVO.class);
       logger.info("查询的统计分组数据为{}",statGroupVOList.toString());
       return Results.newSuccessResult(statGroupVOList);
    }

    @Override
   public Result<Boolean> addOrUpdateStatGroup(StatGroupVO statGroupVO) {
        GroupStatRequest groupStatRequest =  new GroupStatRequest();
        BeanUtils.copyProperties(statGroupVO,groupStatRequest);
        MonitorResult<Boolean> monitorResult =  groupStatFacade.addOrUpdateStatGroup(groupStatRequest);
        if(monitorResult.getData()==false)
        {
            logger.info("新增或修改统计分组数据失败");
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(monitorResult.getData());


    }

    @Override
    public Result<Set<Integer>> queryAllgroupIndex() {
        BaseRequest baseRequest =  new BaseRequest();
        MonitorResult<Set<Integer>> monitorResult =  groupStatFacade.queryAllGroupIndex(baseRequest);
        if(monitorResult.getData()==null)
        {
            logger.info("找不到相关数据");
            return Results.newFailedResult(CommonStateCode.NO_RELATED_DATA);
        }
        logger.info("查询的统计分组序号数据为{}",monitorResult.getData().toString());
        return Results.newSuccessResult(monitorResult.getData());

    }
}
