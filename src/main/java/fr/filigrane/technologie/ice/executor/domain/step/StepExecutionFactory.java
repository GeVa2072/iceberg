package fr.filigrane.technologie.ice.executor.domain.step;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.observable.Observer;

public class StepExecutionFactory {

    public StepExecution create(final Scenario.Step s, final Observer<StepExecution, StepExecutionEventType>... observers) {
        return new StepExecution(s, observers);
    }
}
