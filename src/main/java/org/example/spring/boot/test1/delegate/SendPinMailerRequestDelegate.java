package org.example.spring.boot.test1.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendPinMailerRequestDelegate implements JavaDelegate {

    private final ProcessEngine processEngine;

    public SendPinMailerRequestDelegate(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String pinMailerCase = (String)delegateExecution.getVariable("case");
        String card = (String)delegateExecution.getVariable("currentCard");
        
        log.info("---------> " + delegateExecution.getCurrentActivityName() + " for card: " + card);

        this.processEngine.getRuntimeService()
            .createMessageCorrelation("Pin_Mailer_Request")
            .setVariable("case", pinMailerCase)
            .setVariable("card", card)
            .correlateStartMessage();
    }
}
