package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.GroupStatService;
import com.treefinance.saas.management.console.common.domain.vo.StatGroupVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.monitor.facade.domain.base.BaseRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27上午10:57
 */
@RestController
@RequestMapping("/saas/console/monitor/stat/group/")
public class GroupStatController {
    private static  final Logger logger = LoggerFactory.getLogger(GroupStatController.class);

    @Autowired
    GroupStatService groupStatService;


    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Result<List<StatGroupVO>> queryStatGroupByTemplateId(@RequestBody StatGroupVO statGroupVO) {
        if(statGroupVO.getTemplateId()==null)
        {
            logger.error("统计分组列表查询，传入的参数为空");
            throw new IllegalArgumentException("请求参数不合法");
        }
        logger.info("统计分组列表查询，传入的参数为{}",statGroupVO.toString());
        return groupStatService.queryStatGroup(statGroupVO);


    }


    @RequestMapping(value = "addorupdate", method = RequestMethod.POST)
    public Result<Boolean> addOrUpdateStatGroup(@RequestBody StatGroupVO statGroupVO) {
        if(statGroupVO.getGroupCode()==null||statGroupVO.getGroupExpression()==null||statGroupVO.getGroupIndex()==null||statGroupVO.getGroupName()==null||statGroupVO.getTemplateId()==null)
        {
            logger.error("新增或更新统计分组，传入参数为空");
            throw new IllegalArgumentException("请求参数不合法");
        }
        logger.info("新增或更新统计分组，传入参数为{}",statGroupVO.toString());
        return groupStatService.addOrUpdateStatGroup(statGroupVO);


    }

    @RequestMapping(value = "queryAllgroupIndex", method = RequestMethod.GET)
    public Result<Set<Integer>> queryAllgroupIndex(BaseRequest baseRequest) {

        logger.info("查询所有统计分组序号",baseRequest.toString());
        return groupStatService.queryAllgroupIndex();


    }
}
