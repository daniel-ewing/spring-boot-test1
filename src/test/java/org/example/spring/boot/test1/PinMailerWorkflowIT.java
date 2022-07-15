package org.example.spring.boot.test1;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.RequiredHistoryLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {
        "camunda.bpm.job-execution.enabled=false",
        "camunda.bpm.defaultSerializationFormat=application/json",
        "spring.datasource.url=jdbc:h2:mem:PinMailerWorkflowIT;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE",
        "spring.cloud.config.enabled=false",
        "spring.cloud.bootstrap.enabled=false",
        "spring.cloud.kubernetes.enabled=false"
})
@DirtiesContext
@Deployment(resources = {"bpmn/PinMailer.bpmn"})
@ActiveProfiles(profiles = "test")
public class PinMailerWorkflowIT {

    @Autowired
    ProcessEngine processEngine;

    @BeforeEach
    public void setUp() {
        init(processEngine);
    }

    @AfterEach
    public void stop() {
        reset();
    }

    @Test
    @Deployment(resources = {"bpmn/PinMailer.bpmn"})
    @RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_FULL)
    public void pinMailertWorkflow_shouldEnd() {

        final RuntimeService runtimeService = processEngine().getRuntimeService();
        final TaskService taskService = processEngine().getTaskService();

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("PinMailer_Process");

        assertAll(
                () -> assertNotNull(processInstance),
                () -> assertThat(processInstance).isStarted(),
                () -> assertThat(processInstance).hasPassed("StartEvent_PinMailer"),
                () -> assertThat(processInstance).isWaitingAt("Activity_PinMailer")
        );

        taskService.claim(task().getId(), "pumukel");
        taskService.complete(task().getId());

        assertAll(
                () -> assertThat(processInstance).hasPassed("EndEvent_PinMailer"),
                () -> assertThat(processInstance).isEnded()
        );
    }

    @Test
    @Deployment(resources = {"bpmn/Draft-PinMailer.bpmn"})
    @RequiredHistoryLevel(ProcessEngineConfiguration.HISTORY_FULL)
    public void pinMailertWorkflow_happy_flow_shouldEnd()  {

        final RuntimeService runtimeService = processEngine().getRuntimeService();

        List<Object> cards = new ArrayList<>(Arrays.asList("123", "456"));
        Map<String,Object> pinMailerCase = new HashMap<>();
        pinMailerCase.put("cards", cards);
        Map<String,Object> vars = new HashMap<>();
        vars.put("pinMailerCase", pinMailerCase);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("PinMailer", vars);

        assertAll(
            () -> assertNotNull(processInstance),
            () -> assertThat(processInstance).isStarted(),
            () -> assertThat(processInstance).hasPassed("Event_PinMailer_Start")
        );

        execute(job());
        assertThat(processInstance).hasPassedInOrder(
            "ServiceTask_Validate_Inbound_Message",
            "SendTask_PinMailer_Request", // first card
            "SendTask_PinMailer_Request", // second card
            "Event_PinMailer_End");

        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        assertTrue( processInstanceList.size() == 2); // These two instances are each in a different process, waiting at activity "ServiceTask_CreatePinMailerCaseInDIP".
   }
}
