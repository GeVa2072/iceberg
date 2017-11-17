package fr.filigrane.technologie.ice.executor.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StrategiesResolver {

    private final Map<String, Strategy> strategies;


    public StrategiesResolver(final List<Strategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(Strategy::getName,
                Function.identity()));
    }

    public Strategy getStrategy(String name) {
        return strategies.get(Optional.of(name).orElse("default"));
    }
}
