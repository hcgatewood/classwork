package expresso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * @see Expression
 *
 * Abstract class representing an immutable operation on two nonempty expressions,
 * such as addition or multiplication.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public abstract class Operator implements Expression {

    protected final Expression left, right;

    /*
     * Abstraction function:
     * represents a mathematical operation on two expressions
     * Rep invariant:
     * left != null, right != null
     * left is not empty expression, right is not empty expression
     * Safety from rep exposure:
     * All fields are protected and final.
     * (this class is a parent for concrete variants and not itself a concrete variant)
     * All returned values are immutable or unmodifiable.
     * CheckRep is called before returning from any method
     * if code is executed within the method before then.
     */

    /**
     * Constructs a new operator.
     * @param left expression
     * @param right expression
     * @throws IllegalArgumentException if left or right expression is the empty expression
     */
    public Operator(Expression left, Expression right) throws IllegalArgumentException {
        if (left.isEmpty() || right.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.left = left;
        this.right = right;
        checkRep();
    }

    /**
     * @see Expression#differentiate(Expression)
     */
    @Override
    public abstract Expression differentiate(Variable variable);

    /**
     * @see Expression#simplify(Expression)
     */
    @Override
    public Expression simplify() {
        Map<Map<Variable, Integer>, Double> terms = compileTerms();
        List<Map<Variable, Integer>> termList = new ArrayList<Map<Variable, Integer>>();
        termList.addAll(terms.keySet());
        Expression simplified = new Empty();

        int currentHighestExponent = 0;
        while (!termList.isEmpty()) {
            Map<Variable, Integer> currentTerm = new HashMap<Variable, Integer>();
            boolean currentTermSet = false;
            for (Map<Variable, Integer> term: termList) {
                int highestExponent;
                try {
                    highestExponent = Collections.max(term.values());
                } catch (NoSuchElementException e) {
                    highestExponent = 0;
                }

                if (highestExponent == currentHighestExponent) {
                    currentTermSet = true;
                    currentTerm = term;
                }
            }

            if (!currentTermSet)
                currentHighestExponent++;
            else {
                termList.remove(currentTerm);
                Expression currentExpression = new Empty();

                for (Variable var: currentTerm.keySet()) {
                    for (int i = currentTerm.get(var); i > 0; i--) {
                        if(currentExpression.isEmpty())
                            currentExpression = var;
                        else
                            currentExpression = new Multiply(currentExpression, var);
                    }
                }

                if (!currentExpression.isEmpty()) {
                    if (terms.get(currentTerm) != 1.0)
                        currentExpression = new Multiply(new Number(terms.get(currentTerm)), currentExpression);
                }
                else
                    currentExpression = new Number(terms.get(currentTerm));

                if (terms.get(currentTerm) != 0) {
                    if (simplified.isEmpty())
                        simplified = currentExpression;
                    else
                        simplified = new Add(currentExpression, simplified);
                }
            }
        }

        if (simplified.isEmpty())
            simplified = new Number(0);

        return simplified;
    }

    /**
     * @see Expression#toString()
     */
    @Override
    public abstract String toString();

    /**
     * @see Expression#hashCode()
     */
    @Override
    public abstract int hashCode();

    /**
     * @see Expression#equals(Object)
     */
    @Override
    public abstract boolean equals(Object obj);

    protected void checkRep() {
        assert left != null && right != null;
        assert (! left.isEmpty()) && (! right.isEmpty());
    }
}