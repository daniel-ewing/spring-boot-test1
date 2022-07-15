package org.example.spring.boot.test1;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.OptimisticLockingException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.RequiredHistoryLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DirtiesContext
@SpringBootTest(properties = {
        "camunda.bpm.job-execution.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:ProcessApplicationTest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE",
        "spring.cloud.config.enabled=false",
        "spring.cloud.bootstrap.enabled=false",
        "spring.cloud.kubernetes.enabled=false",
})
@ExtendWith(MockitoExtension.class)
@Slf4j
class ProcessApplicationTest {
    private static final String MESSAGE_DEFINITION_KEY = "SolvencyProActiveTriggerEvent";
    private static final BigDecimal REQUESTED_LIMIT = new BigDecimal(11000);
    private static final String CIS = "cis";

    @Autowired
    ProcessEngine processEngine;

    @BeforeEach
    void setUp() {
        init(processEngine);
    }

    @AfterEach
    public void tearDown() {
        reset();
    }

    @Test
    @RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_FULL)
    void happyPath() {
        final int priority = 5;

//        registerMocks();
//
//        when(accountManagementService.getCisNumberByAccountID(any())).thenReturn(CIS);

        final RuntimeService runtimeService = processEngine().getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("priority", priority);
//        final ProcessInstance processInstance = runtimeService
//                .startProcessInstanceByMessage("message-0",
//                        withVariables("priority", priority));

        final ProcessInstance processInstance = runtimeService
                .startProcessInstanceByMessage("message-0", variables);

        assertAll(
                () -> assertThat(processInstance).isStarted(),
                () -> assertThat(processInstance).hasPassed("message-0")
        );
        // TODO: Workaround for Optimistic Lock excetion: https://jira/browse/DEVA-83195
        // final String jobId = processEngine().getManagementService().createJobQuery().singleResult().getId();
        // processEngine().getManagementService().executeJob(jobId);
        try {
            execute(job());
        } catch (OptimisticLockingException ex) {
            log.error("#_# OptimisticLockingException, going to retry: ", ex);
//            retry();
        }
        assertAll(
                () -> assertThat(processInstance).hasNotPassed("delegate-task"),
                () -> assertThat(processInstance).hasNotPassed("end-false")
        );

//        execute(job());

        assertAll(
                () -> assertThat(processInstance).hasPassed("end-true"),
                () -> assertThat(processInstance).isEnded()
        );


    }
}