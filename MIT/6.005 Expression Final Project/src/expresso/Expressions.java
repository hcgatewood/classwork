package expresso;

/**
 * String-based interface to the expression system.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Expressions {

    /**
     * Differentiate an expression with respect to a variable.
     * @param expression the expression to differentiate; must be non-empty
     * @param variable the variable to differentiate by; must be non-empty
     * @return expression's derivative with respect to variable; will be a valid expression
     * @throws IllegalArgumentException if the expression or variable is invalid
     */
    public static String differentiate(String expression, String variable) throws IllegalArgumentException {
        Expression exp = Expression.parse(expression);
        String varString = Expression.parse(variable).toString();
        if (! varString.matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Error: Invalid differential input \"" + varString + "\"");
        }
        Variable var = new Variable(varString);
        String diff = exp.differentiate(var).simplify().toString();
        return diff;
    }

    /**
     * Simplify an expression.
     * @param expression the expression to simplify; must be non-empty
     * @return an expression equal to the input that is a sum of terms without parentheses,
     *         where for all variables var_i in the expression, for all exponents e_i, the
     *         term (var_1^e_1 x var_2^e_2 x ... x var_n^e_n) appears at most once; each
     *         term may be multiplied by a non-zero, non-identity constant factor; and read
     *         left-to-right, the largest exponent in each term is non-increasing
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static String simplify(String expression) {
        String simplified = Expression.parse(expression).simplify().toString();
        return simplified;
    }
}
