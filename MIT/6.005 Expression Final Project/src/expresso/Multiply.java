package expresso;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @see Expression
 * @see Operator
 *
 * Represents the product of two nonempty expressions.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Multiply extends Operator {
    private static final String CHAR_REP = "*";

    /*
     * Abstraction function:
     * represents a product of two expressions
     * Rep invariant:
     * super.checkRep() (same as that of Operator class)
     * Safety from rep exposure:
     * All fields are private and final.
     * All returned values are immutable or unmodifiable.
     * CheckRep is called before returning from any method
     * if code is executed within the method before then.
     */

    /**
     * Constructs a new multiply operator.
     * @param left expression
     * @param right expression
     */
    public Multiply(Expression left, Expression right) {
        super(left, right);
        checkRep();
    }

    /**
     * @see Expression#differentiate(Expression)
     * Differentiates using product rule,
     * returns derivative(left)*right + left*derivative(right).
     */
    @Override
    public Expression differentiate(Variable variable) {
        return new Add(new Multiply(left.differentiate(variable), right),
                new Multiply(left, right.differentiate(variable)));
    }

    /**
     * @see Expression#simplify(Expression)
     */
    @Override
    public Expression simplify() {
        return super.simplify();
    }

    /**
     * @see Expression#toString()
     * Left=multiply, right=add --> left+(right)
     * Left=add, right=multiply --> (left)+right
     * Otherwise --> left+right
     */
    @Override
    public String toString() {
        String leftString = left.toString();
        String rightString = right.toString();
        if (left.needsParens()) leftString = "(" + leftString + ")";
        if (right.needsParens()) rightString = "(" + rightString + ")";
        return leftString + CHAR_REP + rightString;
    }

    /**
     * @see Expression#toStringAST()
     */
    @Override
    public String toStringAST() {
        return "MULTIPLY(" + left.toString() + ", " + right.toString() + ")";
    }

    /**
     * @see Expression#compileTerms()
     */
    @Override
    public Map<Map<Variable, Integer>, Double> compileTerms() {
        Map<Map<Variable, Integer>, Double> leftTerms = left.compileTerms();
        Map<Map<Variable, Integer>, Double> rightTerms = right.compileTerms();
        Map<Map<Variable, Integer>, Double> allTerms = new HashMap<Map<Variable, Integer>, Double>();

        for (Map<Variable, Integer> term1: leftTerms.keySet())
            for (Map<Variable, Integer> term2: rightTerms.keySet())
            {
                Map<Variable, Integer> multipliedTerm = new HashMap<Variable, Integer>();
                multipliedTerm.putAll(term1);
                for (Variable var: term2.keySet())
                    if (multipliedTerm.containsKey(var))
                        multipliedTerm.put(var, multipliedTerm.get(var) + term2.get(var));
                    else
                        multipliedTerm.put(var, term2.get(var));
                if (allTerms.containsKey(multipliedTerm))
                    allTerms.put(multipliedTerm, allTerms.get(multipliedTerm) + leftTerms.get(term1) * rightTerms.get(term2));
                else
                    allTerms.put(multipliedTerm, leftTerms.get(term1) * rightTerms.get(term2));
            }
        checkRep();
        return Collections.unmodifiableMap(allTerms);
    }

    /**
     * @see Expression#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 59;
        return (left.hashCode() + 2*right.hashCode())*PRIME / 3;
    }

    /**
     * @see Expression#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Multiply) {
            Multiply other = (Multiply) obj;
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
        return false;
    }
}