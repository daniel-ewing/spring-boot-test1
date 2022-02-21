package com.dewing.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DelegateTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.info("-----> execute: Enter");
        
/*
//Do something, e.g.:
        ManagementService managementService = delegateExecution.getProcessEngineServices().getManagementService();
 */
        if (log.isDebugEnabled()) log.info("-----> execute: Exit");
    }
}
