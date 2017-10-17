package com.github.ledoyen;

import com.google.common.collect.Sets;

public enum Parsers {
    FRENCH("Etant donné", "Quand", "Alors", "Et");

    public final LineParser parser;

    Parsers(String... keywords) {
        this.parser = new LineParser(Sets.newHashSet(keywords));
    }
}
