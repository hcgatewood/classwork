package expresso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console interface to the expression system.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Main {

    private static final String COMMAND_PREFIX = "!";

    /**
     * Read expression and command inputs from the console and output results.
     * An empty input terminates the program.
     * @param args unused
     * @throws IOException if there is an error reading the input
     */
    public static void main(String[] args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Expression expression = new Empty();
        while (true) {
            System.out.print("> ");
            final String input = in.readLine();
            if (input.isEmpty()) {
                return;
            }
            final String output;
            if (input.startsWith(COMMAND_PREFIX)) {
                expression = handleCommand(input.substring(COMMAND_PREFIX.length()), expression);
                output = expression.toString();
            } else {
                expression = handleExpression(input, expression);
                output = expression.toString();
            }
            System.out.println(output);
        }
    }

    /**
     * Print user-written expression to console.
     * @param input user-written expression to be parsed
     * @return Expression if permitted by internal expression grammar, else
     * returns current expression along with a ParseError if the input is invalid
     */
    private static Expression handleExpression(String input, Expression expression) {
        try {
            expression = Expression.parse(input);
        } catch (IllegalArgumentException iae) {
            System.out.println("ParseError: Invalid expression \"" + input + "\"");
        }
        return expression;
    }

    /**
     * Execute command on current expression and return a string representing the resulting expression.
     * @param substring String user-written command to be executed
     * @return Expression result of executing command if permitted by internal command grammar, else
     * returns current expression along with a ParseError if command is invalid or cannot be performed
     * on current expression
     */
    private static Expression handleCommand(String substring, Expression expression) {
        final String simplifyPattern = "simplify";
        final String diffPattern = "d/d";
        final String variablePattern = "([A-Za-z]+)";
        if (substring.matches(simplifyPattern) || substring.matches(diffPattern+variablePattern)) {
            if (expression.isEmpty()) {
                System.out.println("ParseError: Missing expression");
            } else if (substring.matches(simplifyPattern)) {
                return expression.simplify();
            } else {
                return expression.differentiate(new Variable(substring.substring(diffPattern.length()))).simplify();
            }
        } else if(substring.matches(diffPattern)) {
            System.out.println("ParseError: Missing variable in derivative command");
        } else {
            System.out.println("ParseError: Unknown command \"" + substring + "\"");
        }
        return expression;
    }
}
