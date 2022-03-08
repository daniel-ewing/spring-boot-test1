package com.dewing.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueryHistoricTasks implements JavaDelegate {
    private final static String processKey0 = "process-0";
    private final static String processKey1 = "process-1";
    private HistoryService historyService;

    public QueryHistoricTasks(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (log.isDebugEnabled()) log.debug("-----> execute: Enter - {}", delegateExecution.getCurrentActivityName());

        long count0 = historyService.createHistoricTaskInstanceQuery()
                .or()
                .processDefinitionKey(processKey0)
                .endOr()
                .count();

        long count1 = historyService.createHistoricTaskInstanceQuery()
                .or()
                .processDefinitionKey(processKey1)
                .endOr()
                .count();

        long countBoth = historyService.createHistoricTaskInstanceQuery()
                .or()
                .processDefinitionKey(processKey0)
                .processDefinitionKey(processKey1)
                .endOr()
                .count();

        log.info("-----> execute: Count of historic tasks for {} = {}", processKey0, count0);
        log.info("-----> execute: Count of historic tasks for {} = {}", processKey1, count1);
        log.info("-----> execute: Count of historic tasks for both processes = {}", countBoth);

        if (log.isDebugEnabled()) log.debug("-----> execute: Exit - {}", delegateExecution.getCurrentActivityName());
    }
}
