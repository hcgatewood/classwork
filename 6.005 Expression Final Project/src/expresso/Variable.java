package expresso;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @see Expression
 *
 * Represents an immutable variable.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class Variable implements Expression {

    private final String name;

    /*
     * Abstraction function:
     * represents a variable
     * Rep invariant:
     * name.matches("[A-Za-z]+")
     * Safety from rep exposure:
     * All fields are private and final.
     * All returned values are immutable or unmodifiable.
     * CheckRep is called before returning from any method
     * if code is executed within the method before then.
     */

    /**
     * Constructs a new variable expression.
     * @param name of the variable
     */
    public Variable(String name) {
        this.name = name;
        checkRep();
    }

    /**
     * Gets the name of this variable.
     * @return name of this variable
     */
    public String getName() {
        return name;
    }

    /**
     * @see Expression#differentiate(Expression)
     * Returns 1 if the differentiating variable is the same as this variable,
     * 0 if different.
     */
    @Override
    public Expression differentiate(Variable variable) {
        if ((variable.getName()).equals(name))
            return new Number(1);
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
     * Returns the string representation of this variable.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * @see Expression#toStringAST()
     */
    @Override
    public String toStringAST() {
        return name;
    }

    /**
     * @see Expression#hashCode()
     */
    @Override
    public int hashCode() {
        // Implemented using the Eclipse hashCode generation feature
        final int PRIME = 31;
        int result = PRIME + name.hashCode();
        return result;
    }

    /**
     * @see Expression#equals(Object)
     * Returns true if the variables have the same name, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable other = (Variable) obj;
            return getName().equals(other.getName());
        }
        else return false;
    }

    /**
     * @see Expression#compileTerms()
     */
    @Override
    public Map<Map<Variable, Integer>, Double> compileTerms() {
        Map<Map<Variable, Integer>, Double> term = new HashMap<Map<Variable, Integer>, Double>();
        Map<Variable, Integer> var = new HashMap<Variable, Integer>();
        var.put(this, 1);
        term.put(var, 1.0);
        checkRep();
        return Collections.unmodifiableMap(term);
    }

    private void checkRep() {
        assert name.matches("[A-Za-z]+");
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