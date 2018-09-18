package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskLog;
import com.treefinance.saas.management.console.dao.entity.TaskLogCriteria;

import java.util.List;

public interface TaskLogMapper {

    List<TaskLog> selectByExample(TaskLogCriteria example);

}