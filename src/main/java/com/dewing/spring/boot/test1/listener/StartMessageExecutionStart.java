package com.dewing.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StartMessageExecutionStart implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        if (log.isDebugEnabled()) log.debug("-----> notify: Enter");

        Map<String, Object> vars = delegateExecution.getVariables();
        Map<String, Object> localVars = new HashMap<>();

        // Copy message variables from vars to localVars.
        // The variable names must be predetermined.
        localVars.put("messageVar1", vars.get("messageVar1"));
        localVars.put("messageVar2", vars.get("messageVar2"));
        delegateExecution.setVariablesLocal(localVars);

        if (log.isDebugEnabled()) log.debug("-----> notify: Exit");
    }
}
