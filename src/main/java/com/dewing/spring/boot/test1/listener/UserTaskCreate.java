package com.dewing.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTaskCreate implements TaskListener {

    private TaskService taskService;

    public UserTaskCreate(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        if (log.isDebugEnabled()) log.debug("-----> notify: Enter - {}", delegateTask.getName());

        taskService.complete(delegateTask.getId());

        if (log.isDebugEnabled()) log.debug("-----> notify: Exit - {}", delegateTask.getName());
    }
}
