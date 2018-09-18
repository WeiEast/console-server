package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.TaskNextDirective;
import com.treefinance.saas.management.console.dao.entity.TaskNextDirectiveCriteria;

import java.util.List;

public interface TaskNextDirectiveMapper {

    /** used */
    List<TaskNextDirective> selectByExample(TaskNextDirectiveCriteria example);

}