package com.treefinance.saas.console.manager;

import com.treefinance.saas.console.manager.domain.CompositeTaskAttrPagingResultSet;
import com.treefinance.saas.console.manager.domain.TaskBO;
import com.treefinance.saas.console.manager.domain.TaskPagingResultSet;
import com.treefinance.saas.console.manager.param.CompositeTaskAttrPagingQuery;
import com.treefinance.saas.console.manager.param.TaskPagingQuery;
import com.treefinance.saas.console.manager.param.TaskQuery;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/16 04:22
 */
public interface TaskManager {

    /**
     * 根据任务ID获取任务
     *
     * @param id 任务ID
     * @return 任务{@link TaskBO}
     */
    TaskBO getTaskById(@Nonnull Long id);

    /**
     * 根据任务ID查询任务所属的商户ID
     * 
     * @param taskId 任务ID
     * @return 商户ID
     */
    String getAppIdByTaskId(@Nonnull Long taskId);

    /**
     * 根据查询条件查询任务列表
     *
     * @param query 查询条件
     * @return 任务列表
     */
    List<TaskBO> queryTasks(@Nonnull TaskQuery query);

    /**
     * 分页查询任务数据
     *
     * @param query 分页查询条件
     * @return 任务分页数据
     */
    TaskPagingResultSet queryPagingTasks(@Nonnull TaskPagingQuery query);

    /**
     * 分页查询复合任务信息，包含任务属性
     *
     * @param query 分页查询条件
     * @return 复合任务的分页数据
     */
    CompositeTaskAttrPagingResultSet queryPagingCompositeTaskAttrs(@Nonnull CompositeTaskAttrPagingQuery query);
}
