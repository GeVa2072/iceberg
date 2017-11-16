package fr.filigrane.technologie.ice.executor.domain.step;

import fr.filigrane.technologie.ice.executor.domain.AsynchroniousProcessor;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecution;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecutionEventType;
import fr.filigrane.technologie.observable.Observer;

public class StepProcessor extends AsynchroniousProcessor implements Observer<StepExecution, StepExecutionEventType> {

    public void update(StepExecution observable, StepExecutionEventType event) {

        switch (event) {
            case CREATED:
                submit(observable::execute);
                break;
            case EXECUTED:
                submit(observable::computeStatus);
                break;
        }
    }
}
