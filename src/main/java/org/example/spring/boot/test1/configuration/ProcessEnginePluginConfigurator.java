package org.example.spring.boot.test1.configuration;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessEnginePluginConfigurator extends AbstractProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
//        List<ProcessEnginePlugin> processEnginePlugins = processEngineConfiguration.getProcessEnginePlugins();
//        if(processEnginePlugins == null) {
//            processEnginePlugins = new ArrayList<ProcessEnginePlugin>();
//        }

        processEngineConfiguration.setTenantIdProvider(new CustomTenantProvider());
    }
}
