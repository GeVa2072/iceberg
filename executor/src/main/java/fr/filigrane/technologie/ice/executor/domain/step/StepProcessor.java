package fr.filigrane.technologie.ice.executor.domain.step;

import fr.filigrane.technologie.ice.executor.domain.AsynchroniousProcessor;
import fr.filigrane.technologie.ice.executor.strategy.StrategiesResolver;
import fr.filigrane.technologie.observable.Observer;

public class StepProcessor extends AsynchroniousProcessor {

    public StepProcessor(final StrategiesResolver strategiesResolver) {
        
        STRATEGY_RESOLVE =
                ((Observer<StepExecution, StepExecutionEventType>) (observable, event) ->
                        submit(() -> observable.setStrategy(strategiesResolver.getStrategy(observable.getStrategyName())))
                ).accept(StepExecutionEventType.CREATED);

        EXECUTOR =
                ((Observer<StepExecution, StepExecutionEventType>) (observable, event) -> submit(observable::execute))
                        .accept(StepExecutionEventType.STRATEGY_RESOLVED);

        STATUS_COMPUTE =
                ((Observer<StepExecution, StepExecutionEventType>) (observable, event) -> submit(observable::computeStatus))
                        .accept(StepExecutionEventType.EXECUTED);

    }


    public final Observer<StepExecution, StepExecutionEventType> STRATEGY_RESOLVE;

    public final Observer<StepExecution, StepExecutionEventType> EXECUTOR;

    public final Observer<StepExecution, StepExecutionEventType> STATUS_COMPUTE;

}
