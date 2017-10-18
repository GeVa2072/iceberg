package com.github.ledoyen.ice.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class ParsingResult {

    private final Scenario scenario;
    private final Set<ParsingError> errors;

    private ParsingResult(Scenario scenario, Collection<ParsingError> errors) {
        this.scenario = scenario;
        this.errors = ImmutableSet.copyOf(errors);
    }

    ParsingResult(Scenario scenario) {
        this(scenario, Collections.emptySet());
    }
    
    ParsingResult(Collection<ParsingError> errors) {
        this(null, errors);
    }

    public boolean isSuccess() {
        return scenario != null;
    }

    public Scenario getScenario() {
        if (isSuccess()) {
            return scenario;
        } else {
            throw new NoSuchElementException();
        }
    }

    public Set<ParsingError> getErrors() {
        return errors;
    }
}
