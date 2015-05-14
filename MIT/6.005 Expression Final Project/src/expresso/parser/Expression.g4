/**
 * Grammar for Expression.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */

grammar Expression;

/*
 * This puts "package differentiator.grammar;" at the top of the output Java
 * files. Do not change these lines.
 */
@header {
package expresso.parser;
}

/*
 * This adds code to the generated lexer and parser. Do not change these lines.
 */
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        removeErrorListeners();
        addErrorListener(new ExceptionThrowingErrorListener());
    }

    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

// Root
root : exp EOF;

// Expression (order of operations reflected/accounted for below)
exp : OPEN exp CLOSE | exp times exp | exp plus exp | var | number;
times : TIMES;
plus : PLUS;
var : VAR;
number : FLOAT | INT;
INT : [0-9]+;
FLOAT : [0-9]* '.' [0-9]+;
VAR : [A-Za-z]+;
TIMES : '*';
PLUS : '+';
OPEN : '(';
CLOSE : ')';

// Whitespace
WHITESPACE : [ ]+ -> skip;
