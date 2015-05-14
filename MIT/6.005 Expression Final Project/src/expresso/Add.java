package expresso;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @see Expression
 * @see Operator
 *
 * Represents the sum of two nonempty expressions.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Add extends Operator {
    private static final String CHAR_REP = "+";
    private static final String PAD = " ";

    /*
     * Abstraction function:
     * represents a sum of two expressions
     * Rep invariant:
     * super.checkRep() (same as that of Operator class)
     * Safety from rep exposure:
     * All fields are private and final.
     * All returned values are immutable or unmodifiable.
     * CheckRep is called before returning from any method
     * if code is executed within the method before then.
     */

    /**
     * Constructs a new Add operator.
     * @param left expression
     * @param right expression
     */
    public Add(Expression left, Expression right) {
        super(left, right);
        checkRep();
    }

    /**
     * @see Expression#differentiate(Expression)
     * Differentiates using sum rule,
     * returns derivative(left) + derivative(right).
     */
    @Override
    public Expression differentiate(Variable variable) {
        return new Add(left.differentiate(variable), right.differentiate(variable));
    }

    /**
     * @see Expression#simplify(Expression)
     */
    @Override
    public Expression simplify() {
        return super.simplify();
    }

    /**
     * @see Expression#toConsole()
     * Returns left + right.
     */
    @Override
    public String toString() {
        return left.toString() + PAD + CHAR_REP + PAD + right.toString();
    }

    /**
     * @see Expression#toString()
     */
    @Override
    public String toStringAST() {
        return "ADD(" + left.toString() + ", " + right.toString() + ")";
    }

    /**
     * @see Expression#compileTerms()
     */
    @Override
    public Map<Map<Variable, Integer>, Double> compileTerms() {
        Map<Map<Variable, Integer>, Double> leftTerms = left.compileTerms();
        Map<Map<Variable, Integer>, Double> rightTerms = right.compileTerms();
        Map<Map<Variable, Integer>, Double> allTerms = new HashMap<Map<Variable, Integer>, Double>();

        allTerms.putAll(leftTerms);
        for (Map<Variable, Integer> term: rightTerms.keySet())
            if(allTerms.containsKey(term))
                allTerms.put(term, allTerms.get(term) + rightTerms.get(term));
            else
                allTerms.put(term, rightTerms.get(term));

        checkRep();
        return Collections.unmodifiableMap(allTerms);
    }

    /**
     * @see Expression#hashCode()
     */
    @Override
    public int hashCode() {
        final int BIGNUMBER = 53;
        return (2*left.hashCode() + right.hashCode())*BIGNUMBER / 3;
    }

    /**
     * @see Expression#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Add) {
            Add other = (Add) obj;
            return left.equals(other.left) && right.equals(other.right);
        }
        else return false;
    }

    @Override
    protected void checkRep() {
        super.checkRep();
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
        return true;
    }
}