package fr.filigrane.technologie.ice.executor.domain.step;

import com.github.ledoyen.ice.parser.Scenario;

public class StepExecutionFactory {

    public StepExecution create(final Scenario.Step s) {
        return new StepExecution(s);
    }
}
