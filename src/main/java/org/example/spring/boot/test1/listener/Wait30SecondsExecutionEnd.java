package org.example.spring.boot.test1.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("Wait30SecondsExecutionEnd")
@Slf4j
public class Wait30SecondsExecutionEnd implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        if (log.isDebugEnabled()) log.debug("-----> notify: Enter - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());

        //TODO:
        //Do something.

        if (log.isDebugEnabled()) log.debug("-----> notify: Exit - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());
    }
}
