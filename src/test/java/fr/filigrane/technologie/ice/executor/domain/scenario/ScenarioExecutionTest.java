package fr.filigrane.technologie.ice.executor.domain.scenario;

import com.github.ledoyen.ice.parser.IceLanguageParser;
import com.github.ledoyen.ice.parser.Scenario;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import fr.filigrane.technologie.ice.executor.domain.step.StepExecutionFactory;
import fr.filigrane.technologie.observable.Observer;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScenarioExecutionTest {

    @Test
    public void flatMapOrder() {
        List<Scenario.Step> steps = new ArrayList<>();

        // steps
        // -> Step1
        //      -> Step2
        //      -> Step3
        // -> Step4
        // -> Step5

        Scenario.Step step1 = mock(Scenario.Step.class);
        steps.add(step1);

        Scenario.Step step2 = mock(Scenario.Step.class);
        Scenario.Step step3 = mock(Scenario.Step.class);
        when(step1.steps()).thenReturn(new ArrayList<Scenario.Step>() {{add(step2); add(step3);}});

        Scenario.Step step4 = mock(Scenario.Step.class);
        steps.add(step4);

        Scenario.Step step5 = mock(Scenario.Step.class);
        steps.add(step5);

        List<Scenario.Step> stepsResult = Streams.concat(Stream.of(steps)).flatMap(l -> l.stream()).collect(Collectors.toList());
        assertThat(stepsResult).containsExactly(step1, step2, step3, step4, step5);

    }

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