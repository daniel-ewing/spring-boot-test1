package com.dewing.spring.boot.test1;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class ProcessApplication {
	private final static String processKey = "process";

	@Autowired
	private RuntimeService runtimeService;

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		if (log.isDebugEnabled()) log.info("-----> processPostDeploy: Enter");

		for (int pi = 1; pi <= 1; pi++) {
			runtimeService.startProcessInstanceByKey(processKey, processKey + " bk " + pi);
			if ((pi % 1000) == 0) {
				if (log.isDebugEnabled()) log.info("-----> processPostDeploy created: {} process instances", pi);
			}
		}

		if (log.isDebugEnabled()) log.info("-----> processPostDeploy: Exit");
	}

	public static void main(String... args) {
		SpringApplication.run(ProcessApplication.class, args);
	}
}