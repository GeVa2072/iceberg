package fr.filigrane.technologie.ice.executor.domain.step;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecution;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecutionEventType;
import fr.filigrane.technologie.ice.executor.strategy.Strategy;
import fr.filigrane.technologie.observable.Observable;
import fr.filigrane.technologie.observable.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepExecution extends Observable<StepExecutionEventType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecution.class);
    private final Scenario.Step step;
    private String strategyName;
    private Strategy strategy;

    public StepExecution(Scenario.Step step, final Observer<StepExecution, StepExecutionEventType>... observers) {
        super(observers);
        this.step=step;
        setChanged();
        notifyObservers(StepExecutionEventType.CREATED);
    }

    public void execute() {
        LOGGER.error("{}", step);


    }

    public void computeStatus() {
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategy(final Strategy strategy) {
        this.strategy = strategy;
    }
}
