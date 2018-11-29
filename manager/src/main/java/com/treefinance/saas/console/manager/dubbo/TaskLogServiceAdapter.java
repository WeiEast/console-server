package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.manager.TaskLogManager;
import com.treefinance.saas.console.manager.domain.TaskLogBO;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import com.treefinance.saas.taskcenter.facade.request.TaskLogRequest;
import com.treefinance.saas.taskcenter.facade.result.TaskLogRO;
import com.treefinance.saas.taskcenter.facade.result.common.TaskResult;
import com.treefinance.saas.taskcenter.facade.service.TaskLogFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.Collections;
import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/1 23:37
 */
@Service
public class TaskLogServiceAdapter extends AbstractTaskServiceAdapter implements TaskLogManager {
    private final TaskLogFacade taskLogFacade;

    @Autowired
    public TaskLogServiceAdapter(TaskLogFacade taskLogFacade) {
        this.taskLogFacade = taskLogFacade;
    }

    @Override
    public List<TaskLogBO> listTaskLogsInTaskIds(@Nonnull List<Long> taskIds) {
        if (CollectionUtils.isEmpty(taskIds)) {
            return Collections.emptyList();
        }

        TaskLogRequest request = new TaskLogRequest();
        request.setTaskIdList(taskIds);

        TaskResult<List<TaskLogRO>> result = taskLogFacade.queryTaskLogById(request);

        validateResponse(result, RpcActionEnum.QUERY_TASK_LOG_ASSIGNED_TASK_IDS, request);

        return convert(result.getData(), TaskLogBO.class);
    }
}
