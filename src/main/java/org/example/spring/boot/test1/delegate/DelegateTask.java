package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("DelegateTask")
@Slf4j
public class DelegateTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        Map<String, Object> headers = (Map)delegateExecution.getVariable("headers");
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            if (log.isDebugEnabled()) log.debug("-----> execute: headerKey - {}, headerValue - {}", header.getKey(), header.getValue());
        }

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }
}
