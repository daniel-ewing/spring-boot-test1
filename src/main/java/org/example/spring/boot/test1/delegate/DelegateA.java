package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("DelegateA")
@Slf4j
public class DelegateA implements JavaDelegate {
    private final RuntimeService runtimeService;

    public DelegateA(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> DelegateA.execute: Enter - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());

        waitUntilProcessHasCompleted(delegateExecution);

        if (log.isDebugEnabled()) log.debug("-----> DelegateA.execute: Exit - instance: {} - activity: {}", delegateExecution.getProcessInstanceId(), delegateExecution.getCurrentActivityId());
    }

    private void waitUntilProcessHasCompleted(DelegateExecution delegateExecution) {

        Map<String, Object> variables = delegateExecution.getVariables();
        int sleepMillis = Integer.parseInt(variables.get("sleepMillis").toString());
        int maxTries = Integer.parseInt(variables.get("maxTries").toString());
        String processInstanceId = variables.get("processInstanceId").toString();

        int numberOfTries = 0;
        boolean isActive;
        do {
            sleepMillis(sleepMillis); // wait 10 secs
            numberOfTries++;

            isActive = isInstanceStillRunning(processInstanceId);

            if (log.isDebugEnabled()) log.debug("-----> DelegateA.waitUntilProcessHasCompleted - numberOfTries: {} - isActive: {}", numberOfTries, isActive);
        } while (isActive && numberOfTries < maxTries); // maxTries comes from the application config
    }

    private boolean isInstanceStillRunning(String processInstanceId) {
        List<Execution> executionsOfProcessId = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId)
                .list();

        // If active executions were found, the instance is still running
        return (executionsOfProcessId != null && !executionsOfProcessId.isEmpty());
    }

    private void sleepMillis(int sleepMillis) {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
