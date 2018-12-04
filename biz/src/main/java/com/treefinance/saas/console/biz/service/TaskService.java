package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.TaskRequest;
import com.treefinance.saas.console.common.domain.vo.TaskBuryPointLogVO;
import com.treefinance.saas.console.common.domain.vo.TaskNextDirectiveVO;
import com.treefinance.saas.console.dao.entity.TaskLog;
import com.treefinance.saas.knife.result.SaasResult;

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
    SaasResult<Map<String, Object>> findByExample(TaskRequest request);

    /**
     * 根据taskId查询task_log信息
     *
     * @param taskId
     * @return
     */
    List<TaskLog> findByTaskId(Long taskId);


    /**
     * 根据taskId查询task_bury_point_log信息
     *
     * @param taskId
     * @return
     */
    List<TaskBuryPointLogVO> findBuryPointByTaskId(Long taskId);

    /**
     * 根据taskId查询task_next_directive信息
     * @param taskId
     * @return
     */
    List<TaskNextDirectiveVO> findtaskNextDirectiveByTaskId(Long taskId);
}
