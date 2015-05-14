// Generated from Expression.g4 by ANTLR 4.5

package expresso.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(ExpressionParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(ExpressionParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(ExpressionParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(ExpressionParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#times}.
	 * @param ctx the parse tree
	 */
	void enterTimes(ExpressionParser.TimesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#times}.
	 * @param ctx the parse tree
	 */
	void exitTimes(ExpressionParser.TimesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#plus}.
	 * @param ctx the parse tree
	 */
	void enterPlus(ExpressionParser.PlusContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#plus}.
	 * @param ctx the parse tree
	 */
	void exitPlus(ExpressionParser.PlusContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(ExpressionParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(ExpressionParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ExpressionParser.NumberContext ctx);
}