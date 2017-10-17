package com.github.ledoyen;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

public class ScenarioBuilder {

    public Step.Scenario build(Lines lines) {
        PeekingIterator<Lines.Line> lineIterator = Iterators.peekingIterator(lines.lines().iterator());
        ImmutableScenario.Builder scenarioBuilder = ImmutableScenario.builder();

        buildSteps(-1, lineIterator, scenarioBuilder);

        return scenarioBuilder.build();
    }

    private void buildSteps(int outerIndentationLevel, PeekingIterator<Lines.Line> lineIterator, Step.StepBuilder scenarioBuilder) {
        while(lineIterator.hasNext() && lineIterator.peek().indentationLevel() > outerIndentationLevel) {
            Lines.Line currentLine = lineIterator.next();
            if(currentLine.empty()) continue;
            ImmutableMeaningfulStep.Builder stepBuilder = ImmutableMeaningfulStep.builder().line(currentLine);
            if(lineIterator.hasNext() && lineIterator.peek().indentationLevel() > currentLine.indentationLevel()) {
                buildSteps(0, lineIterator, stepBuilder);
            }
            scenarioBuilder.addSteps(stepBuilder.build());
        }
    }
}
