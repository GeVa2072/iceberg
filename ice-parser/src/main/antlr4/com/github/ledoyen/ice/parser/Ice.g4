grammar Ice;

tokens { INDENT, DEDENT }
@lexer::header {
  import com.github.ledoyen.ice.parser.LexerLookup;
  import com.yuvalshavit.antlr4.DenterHelper;
}
@lexer::members {
  private final LexerLookup lexerLookup = new LexerLookup();
  
  public LexerLookup getLexerLookup() {
    return lexerLookup;
  }

  private final DenterHelper denter = new DenterHelper(NL, IceParser.INDENT, IceParser.DEDENT) {
    @Override
    public Token pullToken() {
      return IceLexer.super.nextToken();
    }
  };

  @Override
  public Token nextToken() {
    return denter.nextToken();
  }

  public Token rawNextToken() {
    return super.nextToken();
  }
}

document: step*;
step: (LINE_COMMENT NL)* lineStep  (NL | INDENT step+ DEDENT);
lineStep: STEP_KEYWORD restOfLine;
restOfLine: ~NL*;

STEP_KEYWORD: {lexerLookup.contains(STEP_KEYWORD, _input)}? . ;

ANY : ~[\r\n];

NL: ('\r'? '\n' ' '*) | EOF;
WS: [ \t]+ -> skip;
LINE_COMMENT: '#' ~[\r\n]*;
