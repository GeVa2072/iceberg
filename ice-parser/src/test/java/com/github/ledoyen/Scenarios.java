package com.github.ledoyen;

public enum Scenarios {

    ONE_LEVEL("Etant donné un truc\n" +
            "Quand le chose\n" +
            "Alors le bidule\n\n" +
            "Et le machin\n\n"
    ),

    TWO_LEVELS("Etant donné un truc\n" +
            "    un truc est un truc\n"+
            "\tEt un truc est bleu\n\n" +
            "Quand le chose\n");


    public final String rawScenario;

    Scenarios(String rawScenario) {
        this.rawScenario = rawScenario;
    }
}
