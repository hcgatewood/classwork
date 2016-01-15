package expresso;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @see Expression
 *
 * Represents an immutable number, which can be either an integer or floating point number.
 * If a floating point number is printed to console, prints to 2 decimal places.
 * If a floating point number less than 1.0E-4 away from an integer is printed to console, prints the nearest integer.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Number implements Expression {

    /**
     * EQUALITY_PRECISION represents smallest recognized difference between integer and double, when printing to console.
     * DECIMALS_TO_PRINT represents number of decimal places to print, when printing to console.
     */
    private static final double EQUALITY_PRECISION = 0.0001;
    private static final int DECIMALS_TO_PRINT = 2;

    private final double value;

    /*
     * Abstraction function:
     * represents a real number
     * Rep invariant:
     * none
     * Safety from rep exposure:
     * All fields are private and final.
     * All returned values are immutable or unmodifiable.
     * CheckRep is called before returning from any method
     * if code is executed within the method before then.
     */

    /**
     * Constructs a new number expression.
     * @param value of the number
     */
    public Number(double value) {
        this.value = value;
        checkRep();
    }

    /**
     * Gets the value of this number.
     * @return value of this number
     */
    public double getValue() {
        return value;
    }

    /**
     * @see Expression#differentiate(Expression)
     * Returns 0.
     */
    @Override
    public Expression differentiate(Variable variable) {
        return new Number(0);
    }

    /**
     * @see Expression#simplify(Expression)
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * @see Expression#toString()
     * Returns the number's value to a certain accuracy; if all the decimals
     * are a zero, returns the number in an int form.
     */
    @Override
    public String toString() {
        double truncatedValue = Math.round(value);
        if (Math.abs(value - truncatedValue) < EQUALITY_PRECISION) {
            return String.valueOf((int) truncatedValue);
        } else {
            return getDecimalFormat().format(value);
        }
    }

    /**
     * @see Expression#toStringAST()
     */
    @Override
    public String toStringAST() {
        return value + "";
    }

    /**
     * @see Expression#hashCode()
     */
    @Override
    public int hashCode() {
        // Implemented using the Eclipse hashCode generation feature
        final int PRIME = 43;
        long temp;
        temp = Double.doubleToLongBits(value);
        int result = PRIME + (int) (temp ^ (temp >>> (PRIME + 1)));
        return result;
    }

    /**
     * @see Expression#equals(Object)
     * Returns true if the numbers are of the same value, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Number) {
            Number other = (Number) obj;
            return getValue() == other.getValue();
        }
        else return false;
    }

    /**
     * Gets the decimal format used for printing the number to console.
     * @return appropriate decimal format based on the number of decimals to print
     */
    private DecimalFormat getDecimalFormat() {
        String pattern = ".";
        while (pattern.length() < DECIMALS_TO_PRINT+1) pattern = pattern + "#";
        return new DecimalFormat(pattern);
    }

    /**
     * @see Expression#compileTerms()
     */
    @Override
    public Map<Map<Variable, Integer>, Double> compileTerms() {
        Map<Map<Variable, Integer>, Double> term = new HashMap<Map<Variable, Integer>, Double>();
        term.put(new HashMap<Variable, Integer>(), value);
        checkRep();
        return Collections.unmodifiableMap(term);
    }

    private void checkRep() {
        assert true;
    }

    /**
     * @see Expression#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * @see Expression#needsParens()
     */
    @Override
    public boolean needsParens() {
        return false;
    }
}