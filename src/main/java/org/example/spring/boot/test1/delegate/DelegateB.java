package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("DelegateB")
@Slf4j
public class DelegateB implements JavaDelegate {
    private final RuntimeService runtimeService;
    private final static String processKey1 = "processA";
    private final static String processKey2 = "processC";
    private final static String processKey3 = "processD";


    public DelegateB(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());

        Map<String, Object> variables = new HashMap<>();
        variables.put("processInstanceId", delegateExecution.getProcessInstanceId());
        variables.put("sleepMillis", 10000);
        variables.put("numberOfTries", 0);
        variables.put("isActive", true);
        variables.put("maxTries", 15);

        runtimeService.startProcessInstanceByKey(processKey1, variables);
        runtimeService.startProcessInstanceByKey(processKey2, variables);
        runtimeService.startProcessInstanceByKey(processKey3, variables);

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());
    }
}
