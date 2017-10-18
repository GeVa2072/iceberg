package com.github.ledoyen.ice.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;

import org.junit.Test;

import com.google.common.collect.Sets;

public class IceLanguageParserTest {

    @Test
    public void parse_one_level_scenario() {
        IceLanguageParser parser = new IceLanguageParser(Sets.newHashSet("Given", "And", "When", "Quand", "Then"));

        ParsingResult parsingResult = parser.parse(streamResource("one_level.ice"));

        assertThat(parsingResult).as("parsing result").isNotNull();
        assertThat(parsingResult.isSuccess()).as("parsing status (" + parsingResult.getErrors() + ")").isTrue();
        assertThat(parsingResult.getErrors()).as("parsing errors").isEmpty();

        assertThat(parsingResult.getScenario()).isNotNull();
        assertThat(parsingResult.getScenario().location()).isEqualTo(ImmutableLocation.of(1, 0));
        assertThat(parsingResult.getScenario().steps()).hasSize(3);

        assertThat(parsingResult.getScenario().steps().get(0).keyword()).isEqualTo("Given");
        assertThat(parsingResult.getScenario().steps().get(0).text()).isEqualTo("some precondition");
        assertThat(parsingResult.getScenario().steps().get(0).location()).isEqualTo(ImmutableLocation.of(1, 0)); // starts at line 1 because of the comment
        assertThat(parsingResult.getScenario().steps().get(0).steps()).hasSize(0);

        assertThat(parsingResult.getScenario().steps().get(1).keyword()).isEqualTo("Quand");
        assertThat(parsingResult.getScenario().steps().get(1).text()).isEqualTo("some action by the actor");
        assertThat(parsingResult.getScenario().steps().get(1).location()).isEqualTo(ImmutableLocation.of(3, 0));
        assertThat(parsingResult.getScenario().steps().get(1).steps()).hasSize(0);

        assertThat(parsingResult.getScenario().steps().get(2).keyword()).isEqualTo("Then");
        assertThat(parsingResult.getScenario().steps().get(2).text()).isEqualTo("some testable outcome is achieved");
        assertThat(parsingResult.getScenario().steps().get(2).location()).isEqualTo(ImmutableLocation.of(4, 0));
        assertThat(parsingResult.getScenario().steps().get(2).steps()).hasSize(0);
    }

    @Test
    public void parse_three_levels_scenario() {
        IceLanguageParser parser = new IceLanguageParser(Sets.newHashSet("Given", "And", "When", "Quand", "Then"));

        ParsingResult parsingResult = parser.parse(streamResource("three_levels.ice"));

        assertThat(parsingResult).as("parsing result").isNotNull();
        assertThat(parsingResult.isSuccess()).as("parsing status (" + parsingResult.getErrors() + ")").isTrue();
        assertThat(parsingResult.getErrors()).as("parsing errors").isEmpty();

        assertThat(parsingResult.getScenario()).isNotNull();
        assertThat(parsingResult.getScenario().location()).isEqualTo(ImmutableLocation.of(1, 0));
        assertThat(parsingResult.getScenario().steps()).hasSize(3);

        assertThat(parsingResult.getScenario().steps().get(0).keyword()).isEqualTo("Given");
        assertThat(parsingResult.getScenario().steps().get(0).text()).isEqualTo("some precondition");
        assertThat(parsingResult.getScenario().steps().get(0).location()).isEqualTo(ImmutableLocation.of(1, 0)); // starts at line 1 because of the comment
        assertThat(parsingResult.getScenario().steps().get(0).steps()).hasSize(1);

        assertThat(parsingResult.getScenario().steps().get(1).keyword()).isEqualTo("Quand");
        assertThat(parsingResult.getScenario().steps().get(1).text()).isEqualTo("some action by the actor");
        assertThat(parsingResult.getScenario().steps().get(1).location()).isEqualTo(ImmutableLocation.of(4, 0));
        assertThat(parsingResult.getScenario().steps().get(1).steps()).hasSize(2);

        assertThat(parsingResult.getScenario().steps().get(2).keyword()).isEqualTo("Then");
        assertThat(parsingResult.getScenario().steps().get(2).text()).isEqualTo("some testable outcome is achieved");
        assertThat(parsingResult.getScenario().steps().get(2).location()).isEqualTo(ImmutableLocation.of(8, 0));
        assertThat(parsingResult.getScenario().steps().get(2).steps()).hasSize(1);
    }

    public static InputStream streamResource(String name) {
        return IceLanguageParserTest.class.getClassLoader().getResourceAsStream(name);
    }
}