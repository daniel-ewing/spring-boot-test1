package org.example.spring.boot.test1;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class ProcessApplication {
	private final static String defaultProcessKey = "default-process";
	private final static String tenant1ProcessKey = "tenant1-process";
	private final static String tenant2ProcessKey = "tenant2-process";
	private RuntimeService defaultRuntimeService;
	private RuntimeService tenant1RuntimeService;
	private RuntimeService tenant2RuntimeService;

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		if (log.isDebugEnabled()) log.debug("-----> processPostDeploy: Enter");

		this.defaultRuntimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
		this.tenant1RuntimeService = ProcessEngines.getProcessEngine("tenant1").getRuntimeService();
		this.tenant2RuntimeService = ProcessEngines.getProcessEngine("tenant2").getRuntimeService();

		for (int pi = 1; pi <= 1; pi++) {
			defaultRuntimeService.startProcessInstanceByKey(defaultProcessKey, defaultProcessKey + " bk " + pi);
//			tenant1RuntimeService.startProcessInstanceByKey(tenant1ProcessKey, tenant1ProcessKey + " bk " + pi);
//			tenant2RuntimeService.startProcessInstanceByKey(tenant2ProcessKey, tenant2ProcessKey + " bk " + pi);
			if ((pi % 1000) == 0) {
				if (log.isDebugEnabled()) log.debug("-----> processPostDeploy created: {} process instances", pi);
			}
		}

		if (log.isDebugEnabled()) log.debug("-----> processPostDeploy: Exit");
	}

	public static void main(String... args) {
		SpringApplication.run(ProcessApplication.class, args);
	}
}