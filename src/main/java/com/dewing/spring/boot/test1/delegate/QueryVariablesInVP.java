package com.dewing.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class QueryVariablesInVP implements JavaDelegate {
    private RuntimeService runtimeService;

    public QueryVariablesInVP(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        List<String> instanceIds = (ArrayList)delegateExecution.getVariable("instanceIds");
        String[] instanceIdsArray = instanceIds.toArray(new String[0]);

        List<VariableInstance> variables = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(instanceIdsArray)
                .list();

        if (log.isDebugEnabled()) log.debug("-----> execute: variables.size() - {}", variables.size());

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }
}
