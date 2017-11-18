# iceberg
=======

[![Build Status](https://travis-ci.org/GeVa2072/iceberg.svg?branch=master)](https://travis-ci.org/GeVa2072/iceberg)
[![Coverage Status](https://coveralls.io/repos/github/GeVa2072/iceberg/badge.svg?branch=master)](https://coveralls.io/github/GeVa2072/iceberg?branch=master)

## ICE-parser

Parser for ICE language, similar to Gherkin, but allowing nested steps.

Parsing such a document
```
Given a blue dog
    Given a dog
    And the color is blue
When the color is changed to red
Then the dog is red
    the color is red
```

Can be done through :

```java
String rawScenario = ...

LineParser parser = new LineParser(Sets.newHashSet("Given", "When", "Then", "And"));
Lines lines = parser.parse(rawScenario);
Scenario scenario = new ScenarioBuilder().build(lines);
```

Each [`Step`](src/main/java/com/github/ledoyen/Step.java) of the [`Scenario`](src/main/java/com/github/ledoyen/Step.java) refers to a parsed [`Line`](src/main/java/com/github/ledoyen/Lines.java) so that errors can be attached to the initial document (lineNumber, rawText).
