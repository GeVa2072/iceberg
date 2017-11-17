package fr.filigrane.technologie.ice.executor.domain.scenario;

import fr.filigrane.technologie.ice.executor.domain.AsynchroniousProcessor;
import fr.filigrane.technologie.observable.Observer;

import java.util.function.Function;
import java.util.function.Predicate;

public class ScenarioProcessor extends AsynchroniousProcessor {

    public Observer<ScenarioExecution, ScenarioExecutionEventType> PARSER =
            ((Observer<ScenarioExecution, ScenarioExecutionEventType>) (observable, event) -> {
                submit(observable::parse);
            })
                    .accept(ScenarioExecutionEventType.READ);

    public Observer<ScenarioExecution, ScenarioExecutionEventType> VALIDATED =
            ((Observer<ScenarioExecution, ScenarioExecutionEventType>) (observable, event) -> submit(observable::validate))
                    .accept(ScenarioExecutionEventType.PARSED);

    public Observer<ScenarioExecution, ScenarioExecutionEventType> EXECUTOR =
            ((Observer<ScenarioExecution, ScenarioExecutionEventType>) (observable, event) -> submit(observable::execute))
                    .accept(ScenarioExecutionEventType.VALIDATED);

    public Observer<ScenarioExecution, ScenarioExecutionEventType> PARSE_ERROR =
            ((Observer<ScenarioExecution, ScenarioExecutionEventType>) (observable, event) -> { /* TODO: What to do ? */ })
                    .accept(ScenarioExecutionEventType.PARSE_ERROR);
}
