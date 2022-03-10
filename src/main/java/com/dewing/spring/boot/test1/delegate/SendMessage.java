package com.dewing.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SendMessage implements JavaDelegate {
    private final String messageName = "message";
    private RuntimeService runtimeService;

    public SendMessage(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        Map<String, Object> vars = new HashMap<>();
        vars.put("variable1", "value1");
        vars.put("variable2", true);

        runtimeService.correlateMessage(messageName, delegateExecution.getProcessBusinessKey(), vars);

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }
}
