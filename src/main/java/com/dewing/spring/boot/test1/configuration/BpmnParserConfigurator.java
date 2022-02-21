package com.dewing.spring.boot.test1.configuration;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class BpmnParserConfigurator implements ProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        log.debug("-----> preInit: Enter");

        List<BpmnParseListener> bpmnParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();
        if (bpmnParseListeners == null) {
            bpmnParseListeners = new ArrayList<>();
        }
        bpmnParseListeners.add(new BpmnBehaviorConfigurator());

        processEngineConfiguration.setCustomPreBPMNParseListeners(bpmnParseListeners);

        log.debug("-----> preInit: Exit");

    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
