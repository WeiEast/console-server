package com.treefinance.saas.management.console.dao.mapper;

import com.treefinance.saas.management.console.dao.entity.Task;
import com.treefinance.saas.management.console.dao.entity.TaskCriteria;

import java.util.List;

public interface TaskMapper {
    long countByExample(TaskCriteria example);

    List<Task> selectByExample(TaskCriteria example);

    List<Task> selectPaginationByExample(TaskCriteria example);

    Task selectByPrimaryKey(Long id);

}