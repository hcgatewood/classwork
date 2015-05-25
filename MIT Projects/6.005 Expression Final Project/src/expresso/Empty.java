package expresso;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



/**
 * @see Expression
 *
 * Represents an immutable empty expression.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Empty implements Expression {

    private static final String EMPTY_REP = "";
    private static final String STRING_VALUE = "";

    /*
     * Abstraction function:
     * represents an empty expression
     * Rep invariant:
     * none
     */

    /**
     * @see Expression#differentiate(Expression)
     * Returns a new empty expression.
     */
    @Override
    public Expression differentiate(Variable variable) {
        return new Empty();
    }

    /**
     * @see Expression#simplify(Expression)
     */
    @Override
    public Expression simplify() {
        return new Empty();
    }

    /**
     * @see Expression#toString()
     * Returns the empty String.
     */
    @Override
    public String toString() {
        return EMPTY_REP;
    }

    /**
     * @see Expression#toStringAST()
     */
    @Override
    public String toStringAST() {
        return STRING_VALUE;
    }

    /**
     * @see Expression#hashCode()
     */
    @Override
    public int hashCode() {
        // Implemented using the Eclipse hashCode generation feature
        final int PRIME = 37;
        return PRIME;
    }

    /**
     * @see Expression#equals(Object)
     * Returns true if the numbers are of the same value, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Empty) {
            return true;
        }
        else return false;
    }

    /**
     * @see Expression#compileTerms()
     */
    @Override
    public Map<Map<Variable, Integer>, Double> compileTerms() {
        return Collections.unmodifiableMap(new HashMap<Map<Variable, Integer>, Double>());
    }

    /**
     * @see Expression#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean needsParens() {
        return false;
    }
}