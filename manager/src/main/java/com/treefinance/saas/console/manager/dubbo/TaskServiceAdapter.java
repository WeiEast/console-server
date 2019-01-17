package com.treefinance.saas.console.manager.dubbo;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.context.component.RpcActionEnum;
import com.treefinance.saas.console.manager.TaskManager;
import com.treefinance.saas.console.manager.domain.CompositeTaskAttrBO;
import com.treefinance.saas.console.manager.domain.CompositeTaskAttrPagingResultSet;
import com.treefinance.saas.console.manager.domain.TaskBO;
import com.treefinance.saas.console.manager.domain.TaskPagingResultSet;
import com.treefinance.saas.console.manager.param.CompositeTaskAttrPagingQuery;
import com.treefinance.saas.console.manager.param.TaskPagingQuery;
import com.treefinance.saas.console.manager.param.TaskQuery;
import com.treefinance.saas.taskcenter.facade.request.CompositeTaskAttrPagingQueryRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskPagingQueryRequest;
import com.treefinance.saas.taskcenter.facade.request.TaskQueryRequest;
import com.treefinance.saas.taskcenter.facade.response.TaskResponse;
import com.treefinance.saas.taskcenter.facade.result.CompositeTaskAttrDTO;
import com.treefinance.saas.taskcenter.facade.result.PagingDataSet;
import com.treefinance.saas.taskcenter.facade.result.SimpleTaskDTO;
import com.treefinance.saas.taskcenter.facade.service.TaskFacade;
import com.treefinance.toolkit.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jerry
 * @date 2018/12/16 04:23
 */
@Service
public class TaskServiceAdapter extends AbstractTaskServiceAdapter implements TaskManager {

    private final TaskFacade taskFacade;

    @Autowired
    public TaskServiceAdapter(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    @Override
    public TaskBO getTaskById(@Nonnull Long id) {
        Preconditions.notNull("id", id);

        TaskResponse<SimpleTaskDTO> response = taskFacade.getTaskById(id);

        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        validateResult(response, RpcActionEnum.QUERY_TASK_BY_ID, params);

        return convert(response.getEntity(), TaskBO.class);
    }

    @Override
    public String getAppIdByTaskId(@Nonnull Long taskId) {
        Preconditions.notNull("taskId", taskId);

        TaskResponse<SimpleTaskDTO> response = taskFacade.getTaskById(taskId);

        Map<String, Object> params = new HashMap<>(1);
        params.put("id", taskId);
        validateResult(response, RpcActionEnum.QUERY_TASK_BY_ID, params);

        SimpleTaskDTO entity = response.getEntity();

        return entity.getAppId();
    }

    @Override
    public List<TaskBO> queryTasks(@Nonnull TaskQuery query) {
        Preconditions.notNull("query", query);

        TaskQueryRequest request = convertStrict(query, TaskQueryRequest.class);

        TaskResponse<List<SimpleTaskDTO>> response = taskFacade.queryTasks(request);

        validateResult(response, RpcActionEnum.QUERY_TASKS, request);

        return convert(response.getEntity(), TaskBO.class);
    }

    @Override
    public TaskPagingResultSet queryPagingTasks(@Nonnull TaskPagingQuery query) {
        Preconditions.notNull("query", query);

        TaskPagingQueryRequest request = convertStrict(query, TaskPagingQueryRequest.class);

        TaskResponse<PagingDataSet<SimpleTaskDTO>> response = taskFacade.queryPagingTasks(request);

        validateResult(response, RpcActionEnum.QUERY_PAGING_TASKS, request);

        PagingDataSet<SimpleTaskDTO> entity = response.getEntity();
        if (entity != null) {
            TaskPagingResultSet resultSet = new TaskPagingResultSet();
            resultSet.setTotal(entity.getTotal());
            resultSet.setList(convert(entity.getList(), TaskBO.class));
            return resultSet;
        }

        return null;
    }

    @Override
    public CompositeTaskAttrPagingResultSet queryPagingCompositeTaskAttrs(@Nonnull CompositeTaskAttrPagingQuery query) {
        Preconditions.notNull("query", query);
        
        CompositeTaskAttrPagingQueryRequest request = convertStrict(query, CompositeTaskAttrPagingQueryRequest.class);

        logger.info("请求task时的参数request={}", JSON.toJSONString(request));
        TaskResponse<PagingDataSet<CompositeTaskAttrDTO>> response = taskFacade.queryPagingCompositeTaskAttrs(request);

        validateResult(response, RpcActionEnum.QUERY_PAGING_TASKS, request);

        PagingDataSet<CompositeTaskAttrDTO> entity = response.getEntity();
        if (entity != null) {
            CompositeTaskAttrPagingResultSet resultSet = new CompositeTaskAttrPagingResultSet();
            resultSet.setTotal(entity.getTotal());
            resultSet.setList(convert(entity.getList(), CompositeTaskAttrBO.class));
            return resultSet;
        }

        return null;
    }
}
