package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("SetVariables")
@Slf4j
public class SetVariables implements JavaDelegate {
    private RepositoryService repositoryService;

    public SetVariables(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String processKey = repositoryService.getProcessDefinition(delegateExecution.getProcessDefinitionId()).getKey();

        if (log.isDebugEnabled()) log.debug("-----> execute: Enter {} - {}", processKey, delegateExecution.getCurrentActivityId());

        List<String> ids = new ArrayList<>(Arrays.asList("1", "2", "3"));
        delegateExecution.setVariable("ids", ids);

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit {} - {}", processKey, delegateExecution.getCurrentActivityId());
    }
}
