package com.github.ledoyen;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.immutables.value.Value;

@Value.Immutable
@Value.Enclosing
public interface Lines {

    @Value.Parameter
    List<Line> lines();

    @Value.Immutable
    interface Line {

        int lineNumber();

        String lineText();

        Optional<String> gwtKeyword();

        @Value.Derived
        default String trimmedLineText() {
            return lineText().trim();
        }

        @Value.Derived
        default String withoutGwtKeyword() {
            return gwtKeyword()
                    .map(keyword -> Pattern
                            .compile(keyword, Pattern.LITERAL)
                            .matcher(lineText())
                            .replaceFirst("")
                    )
                    .orElse(lineText()).trim();
        }

        @Value.Derived
        default boolean empty() {
            return trimmedLineText().isEmpty();
        }

        @Value.Derived
        default int indentationLevel() {
            return lineText().indexOf(trimmedLineText());
        }
    }
}
