package com.github.ledoyen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class LineParserTest {

    @Test
    public void one_level_scenario() {
        Lines lines = Parsers.FRENCH.parser.toLines(Scenarios.ONE_LEVEL.rawScenario);

        assertThat(lines).isNotNull();
        assertThat(lines.lines()).hasSize(5);

        SoftAssertions softly = new SoftAssertions();

        assertLine(lines, softly, 0, "Etant donné", "un truc", 0);
        assertLine(lines, softly, 1, "Quand", "le chose", 0);
        assertLine(lines, softly, 2, "Alors", "le bidule", 0);
        assertLineIsEmpty(lines, softly, 3);
        assertLine(lines, softly, 4, "Et", "le machin", 0);


    }

    @Test
    public void two_levels_scenario() {
        Lines lines = Parsers.FRENCH.parser.toLines(Scenarios.TWO_LEVELS.rawScenario);

        assertThat(lines).isNotNull();
        assertThat(lines.lines()).hasSize(5);

        SoftAssertions softly = new SoftAssertions();

        assertLine(lines, softly, 0, "Etant donné", "un truc", 0);
        assertLine(lines, softly, 1, null, "un truc est un truc", 4);
        assertLine(lines, softly, 2, "Et", "un truc est bleu", 4);
        assertLineIsEmpty(lines, softly, 3);
        assertLine(lines, softly, 4, "Quand", "le chose", 0);

    }

    private void assertLineIsEmpty(Lines lines, SoftAssertions softly, int lineNumber) {
        Lines.Line line = lines.lines().get(lineNumber);
        softly.assertThat(line.gwtKeyword()).isEqualTo(Optional.empty());
        softly.assertThat(line.withoutGwtKeyword()).isEqualTo("");
        softly.assertThat(line.lineNumber()).isEqualTo(lineNumber);
        softly.assertThat(line.empty()).isEqualTo(true);
    }

    private void assertLine(Lines lines, SoftAssertions softly, int lineNumber, String keyword, String withoutGwtKeyword, int indentationLevel) {
        Lines.Line line = lines.lines().get(lineNumber);
        softly.assertThat(line.gwtKeyword()).isEqualTo(Optional.ofNullable(keyword));
        softly.assertThat(line.withoutGwtKeyword()).isEqualTo(withoutGwtKeyword);
        softly.assertThat(line.lineNumber()).isEqualTo(lineNumber);
        softly.assertThat(line.empty()).isEqualTo(false);
        softly.assertThat(line.indentationLevel()).isEqualTo(indentationLevel);
    }
}
