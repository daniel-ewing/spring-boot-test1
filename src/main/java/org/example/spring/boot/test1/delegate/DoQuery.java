package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DoQuery")
@Slf4j
public class DoQuery implements JavaDelegate {
    private TaskService taskService;

    DoQuery(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        List taskList = taskService.createTaskQuery().taskCandidateGroup("test.group").list();

        if (log.isDebugEnabled()) log.debug("-----> execute: taskList.size() - {}", taskList.size());
        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }
}
