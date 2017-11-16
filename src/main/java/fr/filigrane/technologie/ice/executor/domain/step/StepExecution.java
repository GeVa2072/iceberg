package fr.filigrane.technologie.ice.executor.domain.step;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.observable.Observable;
import fr.filigrane.technologie.observable.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepExecution extends Observable<StepExecutionEventType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepExecution.class);
    private final Scenario.Step step;

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
}
