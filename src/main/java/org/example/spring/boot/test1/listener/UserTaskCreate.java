package org.example.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component("UserTaskCreate")
@Slf4j
public class UserTaskCreate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        if (log.isDebugEnabled()) log.debug("-----> notify: Enter - instance: {} - activity: {}", delegateTask.getExecution().getProcessInstanceId(), delegateTask.getExecution().getCurrentActivityId());

/*
//Do something, e.g.:
        ManagementService managementService = delegateExecution.getProcessEngineServices().getManagementService();
 */
        if (log.isDebugEnabled()) log.debug("-----> notify: Exit - instance: {} - activity: {}", delegateTask.getExecution().getProcessInstanceId(), delegateTask.getExecution().getCurrentActivityId());
    }
}
