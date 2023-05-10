package listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestStep;

public class Listener implements ConcurrentEventListener{
	
	public static Logger logger = LogManager.getLogger(Listener.class.getName());

	@Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

	@SuppressWarnings("unused")
	private void handleTestCaseFinished(TestCaseFinished event) {
        TestCase testCase = event.getTestCase();
        Result result = event.getResult();
        Status status = result.getStatus();
        Throwable error = result.getError();
        String scenarioName = testCase.getName();

        TestStep testStep = testCase.getTestSteps().get(0);
        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStepTestStep  = (PickleStepTestStep) testStep;
            String text = pickleStepTestStep.getStep().getText();
        }
        
        logger.info("1. Scenario Name ==> "+scenarioName);
        
        logger.info("2. Scenario Status ==> "+status.name());
        
        if (error!= null) {
			
        	String message = error.getMessage();
            logger.info("3. Error Message ==> "+message);
		}
        
        logger.info("==========================================");
        
    }
}
