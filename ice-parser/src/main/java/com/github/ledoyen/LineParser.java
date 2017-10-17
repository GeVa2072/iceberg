package com.github.ledoyen;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LineParser {

    private final Set<String> keywords;

    public LineParser(Set<String> keywords) {
        this.keywords = keywords;
    }

    public Lines toLines(String rawScenario) {
        AtomicInteger lineNumberSequence = new AtomicInteger();
        List<String> lines = Arrays.asList(rawScenario.split("\\r?\\n"));
        return ImmutableLines.of(
                lines
                        .stream()
                        .map(line -> buildStep(line, lineNumberSequence::incrementAndGet))
                        .collect(Collectors.toList()));
    }

    private Lines.Line buildStep(String rawLine, Supplier<Integer> lineNumberSupplier) {
        Optional<String> gwtKeyword = startsWith(rawLine, keywords);
        
        return ImmutableLines.Line.builder()
                .lineNumber(lineNumberSupplier.get())
                .lineText(rawLine)
                .gwtKeyword(gwtKeyword)
                .build();
    }

    private Optional<String> startsWith(String rawLine, Set<String> keyWords) {
        String trimmedRawLine = rawLine.trim();
        return keyWords.stream().filter(trimmedRawLine::startsWith).findFirst();
    }
}
