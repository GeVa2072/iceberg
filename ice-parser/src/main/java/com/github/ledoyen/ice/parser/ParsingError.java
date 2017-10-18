package com.github.ledoyen.ice.parser;

import org.immutables.value.Value;

@Value.Immutable
@Value.Enclosing
public interface ParsingError {

    @Value.Parameter
    Location location();

    @Value.Parameter
    String message();
}
