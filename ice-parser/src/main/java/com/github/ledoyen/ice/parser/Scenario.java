package com.github.ledoyen.ice.parser;

import org.immutables.value.Value;

@Value.Immutable
@Value.Enclosing
public interface Scenario extends StepContainer, Localizable {

    @Value.Immutable
    interface Step extends StepContainer, Localizable {
        String keyword();
        String text();
    }
}
