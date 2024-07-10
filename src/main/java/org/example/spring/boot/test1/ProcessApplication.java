package org.example.spring.boot.test1;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.util.*;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class ProcessApplication {
	private final static String processKey = "test";
	private final RuntimeService runtimeService;

	public ProcessApplication(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		if (log.isDebugEnabled()) log.debug("-----> processPostDeploy: Enter");

//		List<String> ids = new ArrayList<>(Arrays.asList("1", "2")); // use this if you want to see synchronous modification work.
		List<String> ids = new ArrayList<>(Arrays.asList("1"));
		Map<String, Object> vars = new HashMap<>();
		vars.put("ids", ids);

		for (int pi = 1; pi <= 1; pi++) {
			runtimeService.startProcessInstanceByKey(processKey, processKey + " bk " + pi, vars);
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