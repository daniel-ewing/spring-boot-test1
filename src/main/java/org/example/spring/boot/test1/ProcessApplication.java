package org.example.spring.boot.test1;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class ProcessApplication {
	private final static String processKey = "process";
	private RuntimeService runtimeService;

	public ProcessApplication(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		if (log.isDebugEnabled()) log.debug("-----> processPostDeploy: Enter");

		Map<String, Object> variables = new HashMap<>();
		variables.put("method", "POST");
//		variables.put("headers", "");
		variables.put("url", "https://postman-echo.com/post");
		String requestVariable1 = "A";
		int requestVariable2 = 1;
		String payload ="{\n" +
				"    \"requestVariable1\":\"" + requestVariable1 + "\",\n" +
				"    \"requestVariable2\":" + requestVariable2 + "\n" +
				"}";
		variables.put("payload", payload);

		for (int pi = 1; pi <= 1; pi++) {
			runtimeService.startProcessInstanceByKey(processKey, processKey + " bk " + pi, variables);
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