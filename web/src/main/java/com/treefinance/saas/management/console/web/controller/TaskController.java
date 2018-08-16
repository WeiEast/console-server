package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.gateway.servicefacade.enums.BizTypeEnum;
import com.treefinance.saas.gateway.servicefacade.model.DirectiveDTO;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.TaskService;
import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.domain.vo.TaskBuryPointLogVO;
import com.treefinance.saas.management.console.common.domain.vo.TaskLogVO;
import com.treefinance.saas.management.console.common.domain.vo.TaskNextDirectiveVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.entity.TaskBuryPointLog;
import com.treefinance.saas.management.console.dao.entity.TaskLog;
import com.treefinance.saas.management.console.dao.entity.TaskNextDirective;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haojiahong on 2017/8/15.
 */
@RestController
@RequestMapping("/saas/console/")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/tasks", method = {RequestMethod.GET})
    public Object taskList(TaskRequest taskRequest) {
        if (taskRequest == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (StringUtils.isEmpty(taskRequest.getUniqueId()) && StringUtils.isEmpty(taskRequest.getAccountNo())
                && taskRequest.getTaskId() == null && StringUtils.isBlank(taskRequest.getAppName())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "uniqueId、accountNo、taskId和appName不能同时为空");
        }
        if (StringUtils.isBlank(taskRequest.getType())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "type不能为空");
        }
        BizTypeEnum typeEnum = BizTypeEnum.valueOf(taskRequest.getType());
        if (typeEnum == null) {
            return Results.newFailedResult(CommonStateCode.ILLEGAL_PARAMETER, "type参数有误");
        }
        if (taskRequest.getStartDate() == null || taskRequest.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        return taskService.findByExample(taskRequest);
    }

    @RequestMapping(value = "/task/log/{taskId}", method = {RequestMethod.GET})
    public Object task(@PathVariable Long taskId) {
        List<TaskLog> taskLogList = taskService.findByTaskId(taskId);
        return Results.newSuccessResult(BeanUtils.convertList(taskLogList, TaskLogVO.class));
    }


    @RequestMapping(value = "/task/burypoint/log/{taskId}", method = {RequestMethod.GET})
    public Object taskBuryPoint(@PathVariable Long taskId) {
        List<TaskBuryPointLogVO> taskBuryPointLogs = taskService.findBuryPointByTaskId(taskId);
        return Results.newSuccessResult(taskBuryPointLogs);
    }
    @RequestMapping(value = "/task/nextdirective/{taskId}", method = {RequestMethod.GET})
    public Object taskNextDirective(@PathVariable Long taskId) {
        List<TaskNextDirectiveVO> taskNextDirectives = taskService.findtaskNextDirectiveByTaskId(taskId);
        return Results.newSuccessResult(taskNextDirectives);
    }
}
