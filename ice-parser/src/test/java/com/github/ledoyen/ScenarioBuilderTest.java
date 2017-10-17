package com.github.ledoyen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ScenarioBuilderTest {
    
    @Test
    public void one_level_scenario() {
        Lines lines = Parsers.FRENCH.parser.toLines(Scenarios.ONE_LEVEL.rawScenario);
        
        Step.Scenario scenario = new ScenarioBuilder().build(lines);

        assertThat(scenario).isNotNull();
        assertThat(scenario.steps()).hasSize(4);
        assertThat(scenario.steps().get(0).steps()).hasSize(0);
        assertThat(scenario.steps().get(1).steps()).hasSize(0);
        assertThat(scenario.steps().get(2).steps()).hasSize(0);
        assertThat(scenario.steps().get(3).steps()).hasSize(0);
    }

    @Test
    public void two_levels_scenario() {
        Lines lines = Parsers.FRENCH.parser.toLines(Scenarios.TWO_LEVELS.rawScenario);

        Step.Scenario scenario = new ScenarioBuilder().build(lines);

        assertThat(scenario).isNotNull();
        assertThat(scenario.steps()).hasSize(2);
        assertThat(scenario.steps().get(0).steps()).hasSize(2);
        assertThat(scenario.steps().get(0).steps().get(0).steps()).hasSize(0);
        assertThat(scenario.steps().get(0).steps().get(1).steps()).hasSize(0);
        assertThat(scenario.steps().get(1).steps()).hasSize(0);
    }
}
