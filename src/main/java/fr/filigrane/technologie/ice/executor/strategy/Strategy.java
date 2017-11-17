package fr.filigrane.technologie.ice.executor.strategy;

import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.ice.executor.domain.scenario.ScenarioExecution;

public interface Strategy {

    void execute(Scenario.Step step);

    String getName();

}
