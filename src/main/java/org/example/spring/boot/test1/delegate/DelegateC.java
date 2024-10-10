package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("DelegateC")
@Slf4j
public class DelegateC implements JavaDelegate {
    private final RuntimeService runtimeService;
    private final static String processKey = "processA";


    public DelegateC(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> DelegateC.execute: Enter - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());

        waitUntilProcessHasCompleted(delegateExecution);

        if (log.isDebugEnabled()) log.debug("-----> DelegateC.execute: Exit - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());
    }

    private void waitUntilProcessHasCompleted(DelegateExecution delegateExecution) {

        Map<String, Object> variables = delegateExecution.getVariables();
        int sleepMillis = Integer.parseInt(variables.get("sleepMillis").toString());
        int numberOfTries = Integer.parseInt(variables.get("numberOfTries").toString());
        String processInstanceId = variables.get("processInstanceId").toString();

        sleepMillis(sleepMillis); // wait sleepMillis secs
        boolean isActive = isInstanceStillRunning(processInstanceId);
        numberOfTries++;

        variables.replace("isActive", isActive);
        variables.replace("numberOfTries", numberOfTries);
        delegateExecution.setVariables(variables);

        if (log.isDebugEnabled()) log.debug("-----> DelegateC.waitUntilProcessHasCompleted - numberOfTries: {} - isActive: {}", numberOfTries, isActive);
    }

    private boolean isInstanceStillRunning(String processInstanceId) {
        List<Execution> executionsOfProcessId = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId)
                .list();

        // If active executions were found, the instance is still running
        return (executionsOfProcessId != null && !executionsOfProcessId.isEmpty());
    }

    private void sleepMillis(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
