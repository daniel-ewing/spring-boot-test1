package com.dewing.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserTaskExecutionStart implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.debug("-----> notify: Enter");

/*
//Do something, e.g.:
        ManagementService managementService = delegateExecution.getProcessEngineServices().getManagementService();
 */
        log.debug("-----> notify: Exit");
    }
}
