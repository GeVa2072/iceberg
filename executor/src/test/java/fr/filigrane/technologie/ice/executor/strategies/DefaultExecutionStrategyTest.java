package fr.filigrane.technologie.ice.executor.strategies;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.github.ledoyen.ice.parser.Scenario;
import com.github.ledoyen.ice.parser.Scenario.Step;

import fr.filigrane.technologie.ice.executor.domain.step.StepProcessor;
import fr.filigrane.technologie.ice.executor.strategy.StrategiesResolver;

public class DefaultExecutionStrategyTest {

	
	@Test
	public void validate_step_execution_order() throws Exception {
		
		Step stepParent = mock(Step.class);
		
		List<Step> steps = new ArrayList<>();

		when(stepParent.steps()).thenReturn(steps);
		
		
        // steps
        // -> Step1
        //      -> Step2
        //      -> Step3
        // -> Step4
        // -> Step5

        Step step1 = mock(Step.class);
        steps.add(step1);

        Step step2 = mock(Step.class);
        Step step3 = mock(Step.class);
        when(step1.steps()).thenReturn(new ArrayList<Step>() {{add(step2); add(step3);}});

        Step step4 = mock(Step.class);
        steps.add(step4);

        Step step5 = mock(Step.class);
        steps.add(step5);
        
	}
}
