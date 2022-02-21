package com.dewing.spring.boot.test1.configuration;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

@Slf4j
public class BpmnBehaviorConfigurator extends AbstractBpmnParseListener {

    @Override
    public void parseProcess(Element processElement, ProcessDefinitionEntity processDefinition) {
        log.debug("-----> parseProcess: Enter");
        // Configure new behavior for the Process.
        log.debug("-----> parseProcess: Exit");
    }

    @Override
    public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
        log.debug("-----> parseUserTask: Enter");
        // Configure new behavior for the UserTask.
        log.debug("-----> parseUserTask: Exit");
    }

    // TODO: Override other methods as needed.
}
