package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskBuryPointLog;
import com.treefinance.saas.management.console.dao.entity.TaskBuryPointLogCriteria;

import java.util.List;

public interface TaskBuryPointLogMapper {

    List<TaskBuryPointLog> selectByExample(TaskBuryPointLogCriteria example);

}