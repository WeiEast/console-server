package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.TaskRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.dao.entity.TaskLog;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/8/15.
 */
public interface TaskService {

    /**
     * 分页查询task信息
     *
     * @param request
     * @return
     */
    Result<Map<String, Object>> findByExample(TaskRequest request);

    /**
     * 根据taskId查询task_log信息
     *
     * @param taskId
     * @return
     */
    List<TaskLog> findByTaskId(Long taskId);
}
