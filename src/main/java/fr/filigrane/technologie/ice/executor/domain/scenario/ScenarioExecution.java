package fr.filigrane.technologie.ice.executor.domain.scenario;

import com.github.ledoyen.ice.parser.IceLanguageParser;
import com.github.ledoyen.ice.parser.ParsingResult;
import com.github.ledoyen.ice.parser.Scenario;
import fr.filigrane.technologie.ice.executor.strategy.DefaultExecutionStrategy;
import fr.filigrane.technologie.observable.Observable;
import fr.filigrane.technologie.observable.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;

public class ScenarioExecution extends Observable<ScenarioExecutionEventType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioExecution.class);

    private final InputStream scenarioInputStream;
    private final IceLanguageParser parser;
    private ParsingResult parsingResult;
    private Scenario scenario;

    public ScenarioExecution(IceLanguageParser parser,
                             InputStream scenarioInputStream,
                             final Observer<ScenarioExecution,
            ScenarioExecutionEventType>... scenarioExecutionObservers) {
        super(scenarioExecutionObservers);
        this.parser = parser;
        this.scenarioInputStream = scenarioInputStream;
        LOGGER.trace("Scenario execution created");
        Arrays.stream(scenarioExecutionObservers).forEach(s -> this.addObserver(s));
        LOGGER.trace("Scenario execution observers Added {}", scenarioExecutionObservers);
        setChanged();
        notifyObservers(ScenarioExecutionEventType.READ);
    }

    public void parse() {
        parsingResult = parser.parse(scenarioInputStream);
        LOGGER.trace("Scenario parsing");
        setChanged();
        notifyObservers(ScenarioExecutionEventType.PARSED);
    }

    public void validate() {
        if(parsingResult.isSuccess()) {
            LOGGER.trace("Scenario parsed {}", parsingResult.getScenario());
            scenario = parsingResult.getScenario();
            setChanged();
            notifyObservers(ScenarioExecutionEventType.VALIDATED);
        } else {
            LOGGER.info("Scenario parsing errors : ", parsingResult.getErrors());
            setChanged();
            notifyObservers(ScenarioExecutionEventType.PARSE_ERROR);
        }
    }

    public void preparing() {
        LOGGER.trace("Preparing scenario !");

        //scenario.steps().stream().flatMap();
    }

    public void execute() {
        LOGGER.trace("Executing scenario !");

        //new DefaultExecutionStrategy(stepExecutionFactory).execute(this, scenario);
        setChanged();
        notifyObservers(ScenarioExecutionEventType.EXECUTED);
    }

    public void storeResult() {
        LOGGER.trace("Scenario stored!");
        setChanged();
        notifyObservers(ScenarioExecutionEventType.STORED);
    }
}
