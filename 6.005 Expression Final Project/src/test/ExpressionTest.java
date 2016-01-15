package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import expresso.Add;
import expresso.Empty;
import expresso.Expression;
import expresso.Multiply;
import expresso.Number;
import expresso.Variable;

/**
 * Tests all public methods of Expression.
 *
 * @author Hunter Gatewood
 * @author Sidd Seethepalli
 * @author Giri Anand
 */
public class ExpressionTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * Testing strategy for each public method.
     *
     * hashCode and equals
     *
     * Given two expressions which are structurally equal, tests
     * if calling equals on both returns true, and if calling hashCode
     * on both expressions gives equal results.
     * Given two expressions which are not structurally equal, tests
     * if both of the above operations return false.
     * Tests that equals is reflexive, symmetric, and transitive.
     * Partitions:
     * empty expression, 1 variable, 1 number, multiply of 2 terms, add of 2 terms,
     * multiply of 3+ terms, add of 3+ terms, add and multiply of 3+ terms,
     * expressions with complementary order of operations/terms,
     * expressions with complementary operators (+ vs. *)
     *
     * parse
     *
     * Tests if expression AST produced by parse is equal to expected AST based on order
     * of operations and terms in the input expression.
     * Dependent on successful implementation of hashCode and equals.
     * Partitions:
     * empty input, invalid input, 1 variable, 1 number, 1 term + 1 term,
     * 1 term * 1 term, 3+ terms being added/multiplied
     * inputs with complementary parenthesis placement
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
     *
     * toString
     *
     * After a few base test cases, the important things to hit will be edge cases
     * which include the location and number of parentheses, which could potentially cause
     * issues.
     * Dependent on successful implementation of parse.
     * Partitions:
     * Variable only, number only, add only, multiply only, everything except parentheses,
     * parentheses around single terms, parentheses around operations, parentheses in
     * weird places.
     *
     * toStringAST
     * Same test cases as the parse tests, except testing equality to a predefined string.
     *
     * isEmpty
     * Every expression except the empty expression should return false.
     *
     * needsParens
     * Every expression except an add expression should return false.
     */

    /**
     * Tests adding empty expressions.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testEmptyAdd() {
        new Add(new Empty(), new Empty());
    }

    /**
     * Tests multiplying empty expressions.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testEmptyMul() {
        new Multiply(new Empty(), new Empty());
    }

    /**
     * Tests equality of 2 empty expressions.
     */
    @Test
    public void testEqualsEmpty() {
        Expression ex1 = new Empty();
        Expression ex2 = new Empty();
        Expression ex3 = new Number(0);
        assertTrue(ex1.equals(ex2));
        assertTrue(ex2.equals(ex1));
        assertFalse(ex2.equals(ex3));
        assertTrue(ex1.hashCode()==ex2.hashCode());
        assertFalse(ex2.hashCode()==ex3.hashCode());
    }

    /**
     * Tests equality of 2 numbers.
     */
    @Test
    public void testEqualsNumbers() {
        Expression ex1 = new Number(5);
        Expression ex2 = new Number(5);
        Expression ex3 = new Number(5.00);
        Expression ex4 = new Number(5.0001);
        assertTrue(ex1.equals(ex2));
        assertTrue(ex2.equals(ex1));
        assertTrue(ex2.equals(ex3));
        assertTrue(ex3.equals(ex1));
        assertTrue(ex1.hashCode()==ex2.hashCode());
        assertTrue(ex2.hashCode()==ex3.hashCode());
        assertFalse(ex3.equals(ex4));
        assertFalse(ex4.equals(ex3));
        assertFalse(ex1.equals(ex4));
        assertFalse(ex1.hashCode()==ex4.hashCode());
        assertFalse(ex3.hashCode()==ex4.hashCode());
    }

    /**
     * Tests equality of 2 variables.
     */
    @Test
    public void testEqualsVariables() {
        Expression ex1 = new Variable("foo");
        Expression ex2 = new Variable("foo");
        Expression ex3 = new Variable("foo");
        Expression ex4 = new Variable("foos");
        assertTrue(ex1.equals(ex2));
        assertTrue(ex2.equals(ex1));
        assertTrue(ex2.equals(ex3));
        assertTrue(ex3.equals(ex1));
        assertTrue(ex1.hashCode()==ex2.hashCode());
        assertTrue(ex2.hashCode()==ex3.hashCode());
        assertFalse(ex3.equals(ex4));
        assertFalse(ex4.equals(ex3));
        assertFalse(ex1.equals(ex4));
        assertFalse(ex1.hashCode()==ex4.hashCode());
        assertFalse(ex3.hashCode()==ex4.hashCode());
    }

    /**
     * Tests equality of 2 sums, with 2 and 3+ terms.
     */
    @Test
    public void testEqualsAdd() {
        Expression x1 = new Variable("x");
        Expression y1 = new Variable("y");
        Expression x2 = new Variable("x");
        Expression y2 = new Variable("y");
        Expression ex1 = new Add(x1, y1);
        Expression ex2 = new Add(x2, y2);
        Expression ex3 = new Add(y1, x1);
        assertTrue(ex1.equals(ex2));
        assertTrue(ex2.equals(ex1));
        assertTrue(ex1.hashCode() == ex2.hashCode());
        assertFalse(ex2.equals(ex3));
        assertFalse(ex3.equals(ex2));
        assertFalse(ex2.hashCode() == ex3.hashCode());

        Expression n1 = new Number(10);
        Expression n2 = new Number(20);
        Expression n3 = new Number(30);
        Expression n4 = new Number(40);
        Expression n5 = new Number(10);
        Expression n6 = new Number(20);
        Expression n7 = new Number(30);
        Expression n8 = new Number(40);
        Expression ex4 = new Add(n1, new Add(n2, new Add(n3, n4)));
        Expression ex5 = new Add(n5, new Add(n6, new Add(n7, n8)));
        Expression ex6 = new Add(new Add(n5, n6), new Add(n7, n8));
        Expression ex7 = new Multiply(n5, new Multiply(n6, new Multiply(n7, n8)));
        assertTrue(ex4.equals(ex5));
        assertTrue(ex4.hashCode() == ex5.hashCode());
        assertFalse(ex4.equals(ex6));
        assertFalse(ex4.hashCode() == ex6.hashCode());
        assertFalse(ex4.equals(ex7));
        assertFalse(ex4.hashCode() == ex7.hashCode());
    }

    /**
     * Tests equality of 2 products, with 2 and 3+ terms.
     */
    @Test
    public void testEqualsMultiply() {
        Expression x1 = new Variable("x");
        Expression y1 = new Variable("y");
        Expression x2 = new Variable("x");
        Expression y2 = new Variable("y");
        Expression ex1 = new Multiply(x1, y1);
        Expression ex2 = new Multiply(x2, y2);
        Expression ex3 = new Multiply(y1, x1);
        assertTrue(ex1.equals(ex2));
        assertTrue(ex2.equals(ex1));
        assertTrue(ex1.hashCode() == ex2.hashCode());
        assertFalse(ex2.equals(ex3));
        assertFalse(ex3.equals(ex2));
        assertFalse(ex2.hashCode() == ex3.hashCode());

        Expression n1 = new Number(10);
        Expression n2 = new Number(20);
        Expression n3 = new Number(30);
        Expression n4 = new Number(40);
        Expression n5 = new Number(10);
        Expression n6 = new Number(20);
        Expression n7 = new Number(30);
        Expression n8 = new Number(40);
        Expression ex4 = new Multiply(n1, new Multiply(n2, new Multiply(n3, n4)));
        Expression ex5 = new Multiply(n5, new Multiply(n6, new Multiply(n7, n8)));
        Expression ex6 = new Multiply(new Multiply(n5, n6), new Multiply(n7, n8));
        Expression ex7 = new Add(n5, new Add(n6, new Add(n7, n8)));
        assertTrue(ex4.equals(ex5));
        assertTrue(ex4.hashCode() == ex5.hashCode());
        assertFalse(ex4.equals(ex6));
        assertFalse(ex4.hashCode() == ex6.hashCode());
        assertFalse(ex4.equals(ex7));
        assertFalse(ex4.hashCode() == ex7.hashCode());
    }

    /**
     * Tests equality of 2 expressions, with a combination of add and multiply operations on 2 and 3+ terms.
     */
    @Test
    public void testEqualsAddAndMultiply() {
        Expression x1 = new Variable("x");
        Expression x2 = new Variable("x");
        Expression ex1 = new Multiply(x1, x2);
        Expression ex2 = new Add(x1, x2);
        assertFalse(ex1.equals(ex2));
        assertFalse(ex1.hashCode() == ex2.hashCode());

        Expression n1 = new Number(10);
        Expression n2 = new Number(20);
        Expression n3 = new Number(30);
        Expression n4 = new Number(40);
        Expression n5 = new Number(10);
        Expression n6 = new Number(20);
        Expression n7 = new Number(30);
        Expression n8 = new Number(40);
        Expression ex3 = new Multiply(n1, new Add(n2, new Add(n3, n4)));
        Expression ex4 = new Multiply(n5, new Add(n6, new Add(n7, n8)));
        Expression ex5 = new Multiply(n5, new Add(new Add(n6, n7), n8));
        Expression ex6 = new Multiply(new Add(n6, new Add(n7, n8)), n5);
        assertTrue(ex3.equals(ex4));
        assertTrue(ex4.equals(ex3));
        assertTrue(ex3.hashCode() == ex4.hashCode());
        assertFalse(ex3.equals(ex5));
        assertFalse(ex3.hashCode() == ex5.hashCode());
        assertFalse(ex3.equals(ex6));
        assertFalse(ex3.hashCode() == ex6.hashCode());
    }

    /**
     * Tests differentiating a number.
     */
    @Test
    public void testDifferentiateNumber() {
        assertTrue(new Number(2).differentiate(new Variable("x")).equals(new Number(0)));
    }

    /**
     * Tests differentiating a variable.
     */
    @Test
    public void testDifferentiateVariable() {
        assertTrue(new Variable("x").differentiate(new Variable("x")).equals(new Number(1)));
        assertTrue(new Variable("x").differentiate(new Variable("y")).equals(new Number(0)));
    }

    /**
     * Tests differentiating a sum of 2 terms.
     */
    @Test
    public void testDifferentiateAdd() {
        assertTrue(new Add(new Variable("x"), new Variable("y")).differentiate(new Variable("x")).equals(new Add(new Number(1), new Number(0))));
        assertTrue(new Add(new Variable("y"), new Variable("x")).differentiate(new Variable("x")).equals(new Add(new Number(0), new Number(1))));
        assertTrue(new Add(new Number(1.5), new Variable("x")).differentiate(new Variable("x")).equals(new Add(new Number(0), new Number(1))));
    }

    /**
     * Tests differentiating a product of 2 terms.
     */
    @Test
    public void testDifferentiateMultiply() {
        assertTrue(new Multiply(new Variable("x"), new Variable("y")).differentiate(new Variable("x")).equals(new Add(new Multiply(new Number(1), new Variable("y")), new Multiply(new Variable("x"), new Number(0)))));
        assertTrue(new Multiply(new Variable("y"), new Variable("x")).differentiate(new Variable("x")).equals(new Add(new Multiply(new Number(0), new Variable("x")), new Multiply(new Variable("y"), new Number(1)))));
        assertTrue(new Multiply(new Number(1.5), new Variable("x")).differentiate(new Variable("x")).equals(new Add(new Multiply(new Number(0), new Variable("x")), new Multiply(new Number(1.5), new Number(1)))));
    }

    /**
     * Tests differentiating a combination of sums and products of 3+ terms.
     */
    @Test
    public void testDifferentiateComplex() {
        // Testing x*x*(y+x+x) ->  (1*x+x*1)*(y+x+x)+x*x*(0+1+1)
        Expression exp = new Multiply(new Multiply(new Variable("x"), new Variable("x")), new Add(new Variable("y"), new Add(new Variable("x"), new Variable("x"))));
        Expression diff = exp.differentiate(new Variable("x"));
        assertEquals(diff, new Add(new Multiply(new Add(new Multiply(
                new Number(1.0), new Variable("x")),
                new Multiply(new Variable("x"), new Number(1.0))),
                new Add(new Variable("y"), new Add(new Variable("x"), new Variable("x")))),
                new Multiply(new Multiply(new Variable("x"), new Variable("x")),
                        new Add(new Number(0.0), new Add(new Number(1.0), new Number(1.0))))));
    }

    @Test
    public void testDifferentiateByNonPresentVar() {
        Expression exp = new Add(new Variable("x"), new Number(1));
        Expression diff = exp.differentiate(new Variable("y")).simplify();
        assertTrue(diff.equals(new Number(0)));
    }

    /**
     * Tests parsing empty input, expects IllegalArgumentException.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testParserEmpty() {
        String in = "";
        Expression.parse(in);
    }

    /**
     * Tests parsing alphanumeric input, expects IllegalArgumentException.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testParserAlphanumeric() {
        String in = "abc123";
        Expression.parse(in);
    }

    /**
     * Tests parsing a number.
     */
    @Test
    public void testParserNumber() {
        String in = ".5";
        Expression out = Expression.parse(in);
        Expression desired = new Number(0.5);
        assertTrue(out.equals(desired));
    }

    /**
     * Tests parsing a variable.
     */
    @Test
    public void testParserVariable() {
        String in = "vAr";
        Expression out = Expression.parse(in);
        Expression desired = new Variable("vAr");
        assertTrue(out.equals(desired));
    }

    /**
     * Tests parsing a sum of 2 terms.
     */
    @Test
    public void testParserSum() {
        String in = "     x + 1";
        Expression out = Expression.parse(in);
        Expression desired = new Add(new Variable("x"), new Number(1));
        assertTrue(out.equals(desired));
    }

    /**
     * Tests parsing a product of 2 terms.
     */
    @Test
    public void testParserProduct() {
        String in = "1 *   x   ";
        Expression out = Expression.parse(in);
        Expression desired = new Multiply(new Number(1), new Variable("x"));
        assertTrue(out.equals(desired));
    }

    /**
     * Tests parsing 2 expressions with complementary order of operations based on parenthesis placement.
     */
    @Test
    public void testParserComplementary() {
        String in1 = "x   + y*(z+2)";
        Expression out1 = Expression.parse(in1);
        Expression left1 = new Variable("x");
        Expression right1 = new Multiply(new Variable("y"), new Add(new Variable("z"), new Number(2)));
        Expression desired1 = new Add(left1, right1);
        assertTrue(out1.equals(desired1));

        String in2 = "x   + y*z+2";
        Expression out2 = Expression.parse(in2);
        Expression left2 = new Add(new Variable("x"), new Multiply(new Variable("y"), new Variable("z")));
        Expression right2 = new Number(2);
        Expression desired2 = new Add(left2, right2);
        assertTrue(out2.equals(desired2));
    }

    /**
     * Tests parsing a combination of sums and products of 3+ terms.
     */
    @Test
    public void testParserComplex() {
        String in = "x+x*x+(x*x+x)*x*x+(x+x*x)";
        Expression out = Expression.parse(in);
        Expression x = new Variable("x");
        Expression xtx = new Multiply(x, x);
        Expression parenLeft = new Add(xtx, x);
        Expression parenRight = new Add(x, xtx);
        Expression left = new Add(new Add(x, xtx), new Multiply(new Multiply(parenLeft, x), x));
        Expression right = parenRight;
        Expression desired = new Add(left, right);
        assertTrue(out.equals(desired));
    }

    /**
     * Tests toStringAST of a number.
     */
    @Test
    public void testToStringASTNumber() {
        String in = ".5";
        Expression out = Expression.parse(in);
        Expression desired = new Number(0.5);
        assertTrue(out.toStringAST().equals(desired.toStringAST()));
    }

    /**
     * Tests toStringAST of a variable
     */
    @Test
    public void testToStringASTVariable() {
        String in = "vAr";
        Expression out = Expression.parse(in);
        Expression desired = new Variable("vAr");
        assertTrue(out.toStringAST().equals(desired.toStringAST()));
    }

    /**
     * Tests toStringAST of a sum.
     */
    @Test
    public void testToStringASTSum() {
        String in = "     x + 1";
        Expression out = Expression.parse(in);
        Expression desired = new Add(new Variable("x"), new Number(1));
        assertTrue(out.toStringAST().equals(desired.toStringAST()));
    }

    /**
     * Tests toStringAST of a product.
     */
    @Test
    public void testToStringASTProduct() {
        String in = "1 *   x   ";
        Expression out = Expression.parse(in);
        Expression desired = new Multiply(new Number(1), new Variable("x"));
        assertTrue(out.toStringAST().equals(desired.toStringAST()));
    }

    /**
     * Tests toStringAST of expressions with complementary order of operations.
     */
    @Test
    public void testToStringASTComplementary() {
        String in1 = "x   + y*(z+2)";
        Expression out1 = Expression.parse(in1);
        Expression left1 = new Variable("x");
        Expression right1 = new Multiply(new Variable("y"), new Add(new Variable("z"), new Number(2)));
        Expression desired1 = new Add(left1, right1);
        assertTrue(out1.toStringAST().equals(desired1.toStringAST()));

        String in2 = "x   + y*z+2";
        Expression out2 = Expression.parse(in2);
        Expression left2 = new Add(new Variable("x"), new Multiply(new Variable("y"), new Variable("z")));
        Expression right2 = new Number(2);
        Expression desired2 = new Add(left2, right2);
        assertTrue(out2.toStringAST().equals(desired2.toStringAST()));
    }

    /**
     * Tests toStringAST of a combination of sums and products.
     */
    @Test
    public void testToStringASTComplex() {
        String in = "x+x*x+(x*x+x)*x*x+(x+x*x)";
        Expression out = Expression.parse(in);
        Expression x = new Variable("x");
        Expression xtx = new Multiply(x, x);
        Expression parenLeft = new Add(xtx, x);
        Expression parenRight = new Add(x, xtx);
        Expression left = new Add(new Add(x, xtx), new Multiply(new Multiply(parenLeft, x), x));
        Expression right = parenRight;
        Expression desired = new Add(left, right);
        assertTrue(out.toStringAST().equals(desired.toStringAST()));
    }

    /**
     * Tests toString of a variable.
     */
    @Test
    public void testToStringVariable() {
        Expression exp = new Variable("y");
        assertTrue(exp.toString().equals("y"));
    }

    /**
     * Tests toString of a number with zeros after the decimal point.
     */
    @Test
    public void testToStringNumberWithZeros() {
        Expression exp = new Number(5.0000);
        assertTrue(exp.toString().equals("5"));
    }

    /**
     * Tests toString of a number less than 1.0E-5 from the nearest int.
     */
    @Test
    public void testToStringNumberAlmostEqualToInt() {
        Expression exp = new Number(5.000001);
        assertTrue(exp.toString().equals("5"));
    }

    /**
     * Tests toString of a number with more than 2 numbers after the decimal point.
     */
    @Test
    public void testToStringNumberDouble() {
        Expression exp = new Number(5.459);
        assertTrue(exp.toString().equals("5.46"));
    }

    /**
     * Tests toString of a number instantiated with an integer.
     */
    @Test
    public void testToStringNumberInt() {
        Expression exp = new Number(5);
        assertTrue(exp.toString().equals("5"));
    }

    /**
     * Tests toString of a sum of 2 terms.
     */
    @Test
    public void testToStringAdd() {
        Expression exp1 = new Number(5.45);
        Expression exp2 = new Variable("HEYYY");
        Expression exp3 = new Add(exp1, exp2);
        assertTrue(exp3.toString().equals("5.45 + HEYYY"));
    }

    /**
     * Tests toString of a product of 2 terms.
     */
    @Test
    public void testToStringMultiply() {
        Expression exp1 = new Number(5.45);
        Expression exp2 = new Variable("YOOO");
        Expression exp3 = new Multiply(exp1, exp2);
        assertTrue(exp3.toString().equals("5.45*YOOO"));
    }

    /**
     * Tests toString of a combination of sums and products of 3+ terms.
     */
    @Test
    public void testToStringAddAndMultiply() {
        Expression exp1 = new Number(5.45);
        Expression exp2 = new Variable("DOUBLEOHFIVEFORLYFE");
        Expression exp3 = new Add(new Multiply(exp1, exp2), new Multiply(exp2, exp1));
        assertTrue(exp3.toString().equals("5.45*DOUBLEOHFIVEFORLYFE + DOUBLEOHFIVEFORLYFE*5.45"));
    }

    /**
     * Tests toString of parsed input with parentheses that don't affect order of operations.
     */
    @Test
    public void testToStringUselessParens() {
        Expression exp = Expression.parse("((x) + (y)*(y))");
        assertTrue(exp.toString().equals("x + y*y"));
    }

    /**
     * Tests toString of parsed input with parentheses that affect order of operations.
     */
    @Test
    public void testToStringImportantParens() {
        Expression exp = Expression.parse("(x*(x+y+z*(a*b+c)))+u");
        assertTrue(exp.toString().equals("x*(x + y + z*(a*b + c)) + u"));
    }

    /**
     * Tests toString of parsed input without parentheses.
     */
    @Test
    public void testToStringParensNotIncluded() {
        Expression exp = Expression.parse("x+y*z");
        assertTrue(exp.toString().equals("x + y*z"));
    }

    /**
     * Tests isEmpty on all concrete variants of Expression.
     */
    @Test
    public void testIsEmpty() {
        Expression var = new Variable("MADMAXG");
        Expression num = new Number(42);
        Expression add = new Add(var, num);
        Expression mult = new Multiply(num, var);
        Expression empty = new Empty();
        assertFalse(var.isEmpty());
        assertFalse(num.isEmpty());
        assertFalse(add.isEmpty());
        assertFalse(mult.isEmpty());
        assertTrue(empty.isEmpty());
    }

    /**
     * Tests needsParens on all concrete variants of Expression.
     */
    @Test
    public void testNeedsParens() {
        Expression var = new Variable("MADMAXG");
        Expression num = new Number(42);
        Expression add = new Add(var, num);
        Expression mult = new Multiply(num, var);
        Expression empty = new Empty();
        assertFalse(var.needsParens());
        assertFalse(num.needsParens());
        assertTrue(add.needsParens());
        assertFalse(mult.needsParens());
        assertFalse(empty.needsParens());
    }

    /**
     * Tests differentiating and simplifying the empty expression.
     */
    @Test
    public void testEmptyExpression() {
        Expression empty = new Empty();
        assertTrue(empty.differentiate(new Variable("x")).equals(empty));
        assertTrue(empty.simplify().equals(empty));
        assertTrue(empty.toStringAST().equals(""));
        assertTrue(empty.toString().equals(""));
    }

    /**
     * Tests simplifying the empty expression.
     */
    @Test
    public void testSimplifyEmpty() {
        assertTrue((new Empty()).simplify().toString().equals(""));
        assertTrue((new Empty()).simplify().equals(new Empty()));
    }

    /**
     * Tests simplifying a number.
     */
    @Test
    public void testSimplifyNumber() {
        assertTrue(Expression.parse("0").simplify().toString().equals("0"));
    }

    /**
     * Tests simplifying a variable.
     */
    @Test
    public void testSimplifyVariable() {
        assertTrue(Expression.parse("x").simplify().toString().equals("x"));
    }

    /**
     * Tests simplifying a sum of 2 numbers.
     */
    @Test
    public void testSimplifyBasic() {
        assertTrue(Expression.parse("3+1").simplify().toString().equals("4"));
    }

    /**
     * Tests simplifying a combination of sums and products of 3+ terms.
     */
    @Test
    public void testSimplifyMedium() {
        assertTrue(Expression.parse("x+4*(1+y)").simplify().toString().equals("4*y + x + 4"));
    }

    /**
     * Tests simplifying a large combination of sums and products.
     */
    @Test
    public void testSimplifyBig() {
        assertTrue(Expression.parse("x*(x+y*(y+3))").simplify().toString().equals("y*y*x + x*x + 3*y*x"));
    }

    /**
     * Tests simplifying edge cases.
     */
    @Test
    public void testSimplifyEdge() {
        assertTrue(Expression.parse("1*0 + x*0 + x*1 + 5*1").simplify().toString().equals("x + 5"));
    }



}