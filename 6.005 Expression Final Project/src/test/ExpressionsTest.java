package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import expresso.Expressions;

/**
 * Tests all public methods of Expressions (API).
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class ExpressionsTest {
    /*
     * Testing strategy for each public method (similar to the respective
     * tests found in ExpressionTest).
     *
     *
     * differentiate
     *
     * Tests if calling differentiate on an expression produces the correct resulting expression,
     * where terms are ordered according to the post-condition of differentiate
     * (differentiation is applied in order from the innermost nested term outward, and
     * preserves left-to-right order)
     * Dependent on successful implementation of hashCode and equals.
     * Should not be dependent on successful implementation of simplify.
     * Partitions:
     * 1 variable, 1 number, add of 2 terms (sum rule),
     * multiply of 2 terms (product rule), add and multiply of 3+ terms,
     * same base expression with complementary order of operations/terms,
     *
     * simplify
     *
     * ***Due to the nature of simplified terms, the result from simplify is not
     *      deterministic. Thus, these tests will need to be updated if the
     *      simplify method is updated.***
     * Tests if calling simplify on an expression produces a correct resulting expression,
     * where terms are ordered according to the post-condition of simplify
     * (each term appears once and is a product of variable/name(s) multiplied by a constant factor,
     * and the largest exponent in each subsequent term is non-increasing)
     * Dependent on successful implementation of hashCode and equals.
     * Partitions:
     * empty expression, 1 variable, 1 number, add of 2 terms, multiply of 2 terms,
     * add and multiply of 3+ terms, same base expression with complementary order of operations/terms,
     * term * 0, term * 1, term + 1
     */

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /**
     * Tests simplifying empty input.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSimplifyEmpty() {
        Expressions.simplify("");
    }

    /**
     * Tests differentiating empty variable.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDifferentiateEmptyVariable() {
        Expressions.differentiate("x", "");
    }

    /**
     * Tests differentiating empty input.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDifferentiateEmptyInput() {
        Expressions.differentiate("", "x");
    }

    /**
     * Tests simplifying negative input.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSimplifyNegative() {
        Expressions.simplify("-1+2");
    }

    /**
     * Tests simplifying alphanumeric input.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSimplifyAlphaNumeric() {
        Expressions.simplify("abc123");
    }

    /**
     * Tests differentiating by a number instead of a variable.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDifferentiateByNumber() {
        Expressions.differentiate("1+2", "9");
    }

    /**
     * Tests differentiating by an invalid variable.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testDifferentiateIllegal() {
        Expressions.differentiate("1+2", "a9");
    }

    /**
     * Tests differentiating a number.
     */
    @Test
    public void testDifferentiateNumber() {
        assertTrue(Expressions.differentiate("5", "x").equals("0"));
    }

    /**
     * Tests differentiating a variable.
     */
    @Test
    public void testDifferentiateVariable() {
        assertTrue(Expressions.differentiate("x", "x").equals("1"));
        assertTrue(Expressions.differentiate("x", "y").equals("0"));
    }

    /**
     * Tests differentiating a sum of 2 terms.
     */
    @Test
    public void testDifferentiateAdd() {
        assertTrue(Expressions.differentiate("x+y", "x").equals("1"));
        assertTrue(Expressions.differentiate("1.5+x", "x").equals("1"));
    }

    /**
     * Tests differentiating a product of 2 terms.
     */
    @Test
    public void testDifferentiateMultiply() {
        assertTrue(Expressions.differentiate("x*y", "x").equals("y"));
        assertTrue(Expressions.differentiate("1.5*x", "x").equals("1.5"));
    }

    /**
     * Tests differentiating a combination of sums and products of 3+ terms.
     */
    @Test
    public void testDifferentiateComplex() {
        assertTrue(Expressions.differentiate("x*x*(y+x+x)", "x").equals("6*x*x + 2*y*x"));
    }

    /**
     * Tests differentiating by a variable not present in an expression of 3+ terms.
     */
    @Test
    public void testDifferentiateByNonPresentVar() {
        assertTrue(Expressions.differentiate("x+5+HIYALL", "y").equals("0"));
    }

    /**
     * Tests simplifying a sum of 2 numbers.
     */
    @Test
    public void testSimplifyBasic() {
        assertTrue(Expressions.simplify("3+1").equals("4"));
    }

    /**
     * Tests simplifying a combination of sums and products of 3+ terms.
     */
    @Test
    public void testSimplifyMedium() {
        assertTrue(Expressions.simplify("x+4*(1+y)").equals("4*y + x + 4"));
    }

    /**
     * Tests simplifying a large combination of sums and products.
     */
    @Test
    public void testSimplifyBig() {
        assertTrue(Expressions.simplify("x*(x+y*(y+3))").equals("y*y*x + x*x + 3*y*x"));
    }

    /**
     * Tests simplifying edge cases.
     */
    @Test
    public void testSimplifyEdge() {
        assertTrue(Expressions.simplify("1*0 + x*0 + x*1 + 5*1").equals("x + 5"));
    }

}
