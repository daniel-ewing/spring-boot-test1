package com.dewing.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CreateInstancesOfVP implements JavaDelegate {
    private RuntimeService runtimeService;

    public CreateInstancesOfVP(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityId());

        int instancesToCreate = Integer.parseInt(delegateExecution.getVariable("instancesToCreate").toString());
        String processKey = delegateExecution.getVariable("processKey").toString();
        Map<String, Object> variables = setVariables();

        List<String> instanceIds = new ArrayList<>();
        List<String> quotedInstanceIds = new ArrayList<>();
        for (int pi = 1; pi <= instancesToCreate; pi++) {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, processKey + " bk " + pi, variables);
            instanceIds.add(processInstance.getProcessInstanceId());
            quotedInstanceIds.add("\"" + processInstance.getProcessInstanceId() + "\"");
            if ((pi % 1000) == 0) {
                if (log.isDebugEnabled()) log.debug("-----> execute created: {} process instances", pi);
            }
        }
        delegateExecution.setVariable("instanceIds", instanceIds);
        delegateExecution.setVariable("quotedInstanceIds", quotedInstanceIds);


        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityId());
    }

    private Map<String, Object> setVariables() {
        Map<String, Object> variables = new HashMap<>();

        variables.put("variable01", "value01");
        variables.put("variable02", 2);
        variables.put("variable03", 3.33);
        variables.put("variable04", 444444l);
        variables.put("variable05", true);
        variables.put("variable06", "value06");
        variables.put("variable07", 7);
        variables.put("variable08", 8.88);
        variables.put("variable09", 999999l);
        variables.put("variable10", false);
        variables.put("variable11", "value11");
        variables.put("variable12", 12);
        variables.put("variable13", 13.13);
        variables.put("variable14", 141414l);
        variables.put("variable15", true);
        variables.put("variable16", "value16");
        variables.put("variable17", 17);
        variables.put("variable18", 18.18);
        variables.put("variable19", 191919l);
        variables.put("variable20", false);
        variables.put("variable21", "value21");
        variables.put("variable22", 22);
        variables.put("variable23", 23.23);
        variables.put("variable24", 242424l);
        variables.put("variable25", true);
        variables.put("variable26", "value26");
        variables.put("variable27", 27);
        variables.put("variable28", 28.28);
        variables.put("variable29", 292929l);
        variables.put("variable30", false);

        return variables;
    }
}
