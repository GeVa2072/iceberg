package fr.filigrane.technologie.ice.executor.domain.scenario;

import com.github.ledoyen.ice.parser.IceLanguageParser;
import com.google.common.collect.Sets;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionFactory;
import fr.filigrane.technologie.observable.Observer;
import org.junit.Test;

import java.io.InputStream;

public class ScenarioExecutionTest {

    @Test
    public void execution() throws InterruptedException {
        ScenarioProcessor scenarioProcessor = new ScenarioProcessor();
        ScenarioExecution execution = new ScenarioExecutionFactory(
                scenarioProcessor,
                new IceLanguageParser(Sets.newHashSet("Given", "And", "When", "Quand", "Then")), new StepExecutionFactory())
                .create(streamResource("three_levels.ice"));

        Object o = new Object(); // this is monitor

        // Be notify on monitor when scenario EXECUTED
        Observer<ScenarioExecution, ScenarioExecutionEventType> observer = (observable, event) -> {
            if (event == ScenarioExecutionEventType.EXECUTED) {
                synchronized (o) {
                    o.notify();
                }
            }
        };

        execution.addObserver(observer);

        synchronized (o) {
            o.wait();
        }
    }

    public InputStream streamResource(String name) {
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }


}