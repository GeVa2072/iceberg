package fr.filigrane.technologie.ice.executor.domain.scenario;

import com.github.ledoyen.ice.parser.IceLanguageParser;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ScenarioExecutionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioExecutionFactory.class);
    private final ScenarioProcessor scenarioProcessor;
    private final IceLanguageParser parser;
    private final StepExecutionFactory stepExecutionFactory;


    public ScenarioExecutionFactory(ScenarioProcessor scenarioProcessor, IceLanguageParser parser, final StepExecutionFactory stepExecutionFactory) {
        this.scenarioProcessor = scenarioProcessor;
        this.parser = parser;
        this.stepExecutionFactory = stepExecutionFactory;
    }

    public ScenarioExecution create(InputStream scenario) {
        LOGGER.trace("Reading scenario");
        ScenarioExecution scenarioExecution = new ScenarioExecution(parser, scenario, scenarioProcessor.PARSER, scenarioProcessor.VALIDATED, scenarioProcessor.PARSE_ERROR, scenarioProcessor.EXECUTOR);
        LOGGER.trace("Scenario execution created");
        return scenarioExecution;
    }
}
