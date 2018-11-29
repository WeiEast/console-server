package com.treefinance.saas.console.manager;

import com.treefinance.saas.console.manager.domain.TaskLogBO;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/1 23:36
 */
public interface TaskLogManager {

    List<TaskLogBO> listTaskLogsInTaskIds(@Nonnull List<Long> taskIds);
}
