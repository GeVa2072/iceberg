package com.github.ledoyen.ice.parser;

import org.immutables.value.Value;

@Value.Immutable
public interface Location {
    @Value.Parameter
    int line();

    @Value.Parameter
    int charPositionInLine();
}
