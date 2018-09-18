package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskCallbackLog;
import com.treefinance.saas.management.console.dao.entity.TaskCallbackLogCriteria;

import java.util.List;

public interface TaskCallbackLogMapper {

    long countByExample(TaskCallbackLogCriteria example);

    List<TaskCallbackLog> selectByExample(TaskCallbackLogCriteria example);

    List<TaskCallbackLog> selectPaginationByExample(TaskCallbackLogCriteria example);

    TaskCallbackLog selectByPrimaryKey(Long id);

}