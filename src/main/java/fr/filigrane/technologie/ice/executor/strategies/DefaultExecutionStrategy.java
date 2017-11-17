package fr.filigrane.technologie.ice.executor.strategies;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecution;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionEventType;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionFactory;
import fr.filigrane.technologie.ice.executor.domain.step.StepProcessor;
import fr.filigrane.technologie.observable.Observable;
import fr.filigrane.technologie.observable.Observer;

import java.util.Optional;

public class DefaultExecutionStrategy implements Strategy {

    private final StepExecutionFactory stepExecutionFactory;
    private final StepProcessor stepProcessor;

    public DefaultExecutionStrategy(final StepExecutionFactory stepExecutionFactory, final StepProcessor stepProcessor) {
        this.stepExecutionFactory = stepExecutionFactory;
        this.stepProcessor = stepProcessor;
    }

    @Override
    public void execute(Scenario.Step step) {
        StepExecution previousStepExecution = null;
        for(Scenario.Step innerStep : step.steps()) {
            // Create stepExecution and add some stepProcessor for Strategy resolution and status compute
            StepExecution innerstepExecution =
                    stepExecutionFactory.create(innerStep, stepProcessor.STRATEGY_RESOLVE, stepProcessor.STATUS_COMPUTE);
            if(previousStepExecution != null) {
                // Be notify when previousStepExecution is EXECUTED
                previousStepExecution.addObserver(
                        ((Observer<StepExecution, StepExecutionEventType>) (observable, event) -> innerstepExecution.execute())
                                .accept(StepExecutionEventType.EXECUTED));
            } else {
                innerstepExecution.addObserver(stepProcessor.EXECUTOR); //Run first step
            }
            previousStepExecution = innerstepExecution;
        }
    }

    @Override
    public String getName() {
        return "default";
    }
}
