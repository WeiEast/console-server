package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskAttribute;
import com.treefinance.saas.management.console.dao.entity.TaskAttributeCriteria;

import java.util.List;

public interface TaskAttributeMapper {

    List<TaskAttribute> selectByExample(TaskAttributeCriteria example);

}