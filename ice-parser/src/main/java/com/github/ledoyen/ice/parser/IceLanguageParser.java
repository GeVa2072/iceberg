package com.github.ledoyen.ice.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class IceLanguageParser {

    private final Set<String> keywords;

    public IceLanguageParser(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ParsingResult parse(InputStream scenario) {
        Set<ParsingError> errors = new HashSet<>();
        ANTLRErrorListener errorListener = new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                errors.add(ImmutableParsingError.of(ImmutableLocation.of(line, charPositionInLine), msg));
            }
        };

        IceLexer lexer = new IceLexer(stream(scenario));
        lexer.getLexerLookup().put(IceLexer.STEP_KEYWORD, keywords);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        IceParser parser = new IceParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        IceParser.DocumentContext result = parser.document();
        if(result.children == null) {
            errors.add(ImmutableParsingError.of(ImmutableLocation.of(1, 0), "Could not parse first line"));
        }
        
        final ParsingResult parsingResult;
        if (errors.isEmpty()) {
            parsingResult = new ParsingResult(buildScenario(result));
        } else {
            parsingResult = new ParsingResult(errors);
        }

        return parsingResult;
    }

    private Scenario buildScenario(IceParser.DocumentContext result) {
        ImmutableScenario.Builder scenarioBuilder = ImmutableScenario.builder();

        scenarioBuilder.location(buildLocation(result));
        result.step().stream().map(this::buildStep).forEach(scenarioBuilder::addSteps);

        return scenarioBuilder.build();
    }

    private Scenario.Step buildStep(IceParser.StepContext stepContext) {
        ImmutableScenario.Step.Builder stepBuilder = ImmutableScenario.Step.builder();

        stepBuilder.location(buildLocation(stepContext));
        stepBuilder.keyword(stepContext.lineStep().STEP_KEYWORD().getText());
        stepBuilder.text(stepContext.lineStep().restOfLine().getText().trim());

        stepContext.step().stream().map(this::buildStep).forEach(stepBuilder::addSteps);

        return stepBuilder.build();
    }

    private Location buildLocation(ParserRuleContext context) {
        Token start = context.getStart();
        return ImmutableLocation.of(start.getLine(), start.getCharPositionInLine());
    }

    private CharStream stream(InputStream content) {
        try {
            return CharStreams.fromStream(content);
        } catch (IOException e) {
            throw new IllegalStateException("Should not happen", e);
        }
    }
}
