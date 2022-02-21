package com.dewing.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTaskCreate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        log.debug("-----> notify: Enter");

/*
//Do something, e.g.:
        ManagementService managementService = delegateExecution.getProcessEngineServices().getManagementService();
 */
        log.debug("-----> notify: Exit");
    }
}
