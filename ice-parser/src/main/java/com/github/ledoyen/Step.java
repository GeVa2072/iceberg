package com.github.ledoyen;

import java.util.List;

import org.immutables.value.Value;

public interface Step {

    List<Step> steps();

    @Value.Immutable
    interface MeaningfulStep extends Step {
        abstract class Builder implements StepBuilder {}
        Lines.Line line();
    }

    @Value.Immutable
    interface Scenario extends Step {
        abstract class Builder implements StepBuilder {}
    }

    /**
     * Common builder interface for sub-types of {@link Step}.
     */
    interface StepBuilder {
        StepBuilder addSteps(Step e);
    }
}
