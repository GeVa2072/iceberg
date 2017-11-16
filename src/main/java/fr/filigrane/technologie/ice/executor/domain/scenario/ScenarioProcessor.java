package fr.filigrane.technologie.ice.executor.domain.scenario;

import fr.filigrane.technologie.ice.executor.domain.AsynchroniousProcessor;
import fr.filigrane.technologie.observable.Observer;

public class ScenarioProcessor extends AsynchroniousProcessor implements Observer<ScenarioExecution, ScenarioExecutionEventType> {

    public void update(ScenarioExecution observable, ScenarioExecutionEventType event) {

        switch (event) {

            case READ:
                submit(observable::read);
                break;
            case PARSING:
                submit(observable::parsing);
                break;
            case PARSED:
                submit(observable::execute);
                break;
            case EXECUTED:
                submit(observable::storeResult);
        }
    }
}
