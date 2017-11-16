package fr.filigrane.technologie.ice.executor.strategy;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecution;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecutionEventType;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecution;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionEventType;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionFactory;
import fr.filigrane.technologie.observable.Observable;
import fr.filigrane.technologie.observable.Observer;

public class DefaultExecutionStrategy {

    private final StepExecutionFactory stepExecutionFactory;

    public DefaultExecutionStrategy(final StepExecutionFactory stepExecutionFactory) {
        this.stepExecutionFactory = stepExecutionFactory;
    }

    public void execute(Observable<ScenarioExecutionEventType> scenarioExecution, final Scenario scenario) {
        Observer<StepExecution, StepExecutionEventType> observer = (stepExecution, stepExecutionEventType) -> {};

        scenario.steps().stream().forEach((s) -> stepExecutionFactory.create(s).addObserver(observer));

        scenarioExecution.notifyObservers(ScenarioExecutionEventType.EXECUTING);
    }
}
