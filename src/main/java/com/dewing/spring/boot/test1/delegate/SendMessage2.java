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
public class SendMessage2 implements JavaDelegate {
    private final String messageName = "message-2";
    private RuntimeService runtimeService;

    public SendMessage2(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        Map<String, Object> vars = new HashMap<>();
        vars.put("variable0", "AfterEventGateway");
        vars.put("variable1", "value1");
        vars.put("fileId", "other/stuff");

        runtimeService.correlateMessage(messageName, delegateExecution.getProcessBusinessKey(), vars);

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }
}
