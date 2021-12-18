package org.netbeans.modules.php.blade.editor.parsing;

import java.util.LinkedList;
import java.util.List;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.ASTError;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.ASTErrorExpression;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeConstDirectiveStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeEchoStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeExtendsStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeForStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeForeachStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeIfStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeIncludeStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeProgram;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeSectionStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Block;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Expression;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.InLineBladePhp;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.InLineHtml;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.PhpExpression;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Scalar;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Statement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Variable;


/** Cup generated class to encapsulate user supplied action code.*/
class CUP$ASTBladeParser$actions {
  private final ASTBladeParser parser;

  /** Constructor */
  CUP$ASTBladeParser$actions(ASTBladeParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$ASTBladeParser$do_action(
    int                        CUP$ASTBladeParser$act_num,
    java_cup.runtime.lr_parser CUP$ASTBladeParser$parser,
    java.util.Stack            CUP$ASTBladeParser$stack,
    int                        CUP$ASTBladeParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$ASTBladeParser$result;

      /* select the action based on the action number */
      switch (CUP$ASTBladeParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 40: // path ::= error 
            {
              Expression RESULT =null;
		int exprleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int exprright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object expr = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = new ASTErrorExpression(exprleft, exprright);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("path",10, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 39: // path ::= T_STRING 
            {
              Expression RESULT =null;
		int pathleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int pathright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		String path = (String)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    if (path == null) {
        path = "";
    }
	RESULT = new Scalar(pathleft, pathright, path, Scalar.Type.STRING);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("path",10, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 38: // path_expression ::= error 
            {
              Expression RESULT =null;
		int exprleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int exprright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object expr = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = new ASTErrorExpression(exprleft, exprright);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("path_expression",9, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // path_expression ::= T_OPEN_PARENTHESE path T_CLOSE_PARENTHESE 
            {
              Expression RESULT =null;
		int pathleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int pathright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Expression path = (Expression)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		
	RESULT = path;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("path_expression",9, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // variable ::= variable T_NEKUDA variable 
            {
              Variable RESULT =null;
		int expr1left = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int expr1right = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Variable expr1 = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int expr2left = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int expr2right = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Variable expr2 = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		String expr1Str = "";
                if (expr1 != null){
                    expr1Str = expr1.toString();
                }
    //we will force just one string
    RESULT = new Variable(expr1left, expr1right, expr1Str );

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("variable",14, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // variable ::= T_STRING 
            {
              Variable RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int varright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    if (var == null) {
        var = "";
    }
        RESULT = new Variable(varleft, varright, var);  

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("variable",14, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // variable ::= T_VARIABLE 
            {
              Variable RESULT =null;
		int varleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int varright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		String var = (String)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    if (var == null) {
        var = "";
    }
    RESULT = new Variable(varleft, varright, var.toString());

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("variable",14, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // yield_label ::= error 
            {
              Expression RESULT =null;
		int exprleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int exprright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object expr = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = new ASTErrorExpression(exprleft, exprright);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("yield_label",12, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // yield_label ::= T_STRING 
            {
              Expression RESULT =null;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		String label = (String)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    if (label == null) {
        label = "";
    }
	RESULT = new Scalar(labelleft, labelright, label.toString(), Scalar.Type.STRING);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("yield_label",12, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // yield_label_expression ::= error 
            {
              Expression RESULT =null;
		int exprleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int exprright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object expr = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = new ASTErrorExpression(exprleft, exprright);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("yield_label_expression",11, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // yield_label_expression ::= T_OPEN_PARENTHESE yield_label T_CLOSE_PARENTHESE 
            {
              Expression RESULT =null;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Expression label = (Expression)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		
    RESULT = label;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("yield_label_expression",11, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // end_section ::= T_BLADE_SHOW 
            {
              Object RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = token;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("end_section",0, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // end_section ::= T_BLADE_ENDSECTION 
            {
              Object RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		 
    RESULT = token;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("end_section",0, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // statement ::= error 
            {
              Statement RESULT =null;
		int theErrorleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int theErrorright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object theError = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    ASTError error = new ASTError(theErrorleft, theErrorright);
    RESULT = error;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // statement ::= T_BLADE_OPEN_ECHO T_BLADE_PHP_ECHO T_BLADE_CLOSE_ECHO 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int phpleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int phpright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Object php = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Expression expr = new PhpExpression(phpleft, phpright, php.toString());
    RESULT = new BladeEchoStatement(tokenleft, endright, token.toString(), expr);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // statement ::= T_BLADE_COMMENT 
            {
              Statement RESULT =null;
		
    /* comment */

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // statement ::= T_NEKUDA 
            {
              Statement RESULT =null;
		int htmlleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int htmlright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object html = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    InLineHtml inLineHtml = new InLineHtml(htmlleft, htmlright);
    RESULT = inLineHtml;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // statement ::= T_STRING 
            {
              Statement RESULT =null;
		int htmlleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int htmlright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		String html = (String)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    //unkown string
    InLineHtml inLineHtml = new InLineHtml(htmlleft, htmlright);
    RESULT = inLineHtml;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // statement ::= T_BLADE_INLINE_PHP 
            {
              Statement RESULT =null;
		int phpleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int phpright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object php = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    InLineBladePhp inLineBladePhp = new InLineBladePhp(phpleft, phpright);
    RESULT = inLineBladePhp;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // statement ::= T_INLINE_PHP 
            {
              Statement RESULT =null;
		int htmlleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int htmlright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object html = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    InLineHtml inLineHtml = new InLineHtml(htmlleft, htmlright);
    RESULT = inLineHtml;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // statement ::= T_INLINE_HTML 
            {
              Statement RESULT =null;
		int htmlleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int htmlright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object html = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    InLineHtml inLineHtml = new InLineHtml(htmlleft, htmlright);
    RESULT = inLineHtml;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // statement ::= T_BLADE_DIRECTIVE 
            {
              Statement RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Scalar directive = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeConstDirectiveStatement(sleft, sright, directive);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // statement ::= T_BLADE_ELSEIF T_PHP_CONDITION_EXPRESSION 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Scalar directive = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeConstDirectiveStatement(sleft, sright, directive);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // statement ::= T_BLADE_DIRECTIVE T_OPEN_PARENTHESE variable T_CLOSE_PARENTHESE 
            {
              Statement RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int valueleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int valueright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Variable value = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    //directive statement with arguments
    Scalar directive = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeConstDirectiveStatement(sleft, sright, directive);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // statement ::= T_BLADE_DIRECTIVE T_OPEN_PARENTHESE variable T_COMMA variable T_CLOSE_PARENTHESE 
            {
              Statement RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).value;
		int valueleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int valueright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Variable value = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int value2left = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int value2right = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Variable value2 = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    //directive statement with arguments
    Scalar directive = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeConstDirectiveStatement(sleft, sright, directive);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // statement ::= T_BLADE_IF T_PHP_CONDITION_EXPRESSION inner_statement_list T_BLADE_ENDIF 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(statementListleft, statementListright, statementList);
    Scalar loopExpr = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeIfStatement(tokenleft, endright, loopExpr, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // statement ::= T_BLADE_FOR T_PHP_LOOP_EXPRESSION inner_statement_list T_BLADE_ENDFOR 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(statementListleft, statementListright, statementList);
    Scalar loopExpr = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeForStatement(tokenleft, endright, loopExpr, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // statement ::= T_BLADE_FOREACH T_PHP_LOOP_EXPRESSION inner_statement_list T_BLADE_ENDFOREACH 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(statementListleft, statementListright, statementList);
    Scalar loopExpr = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeForeachStatement(tokenleft, endright, loopExpr, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // statement ::= T_BLADE_SECTION yield_label_expression inner_statement_list end_section 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).right;
		Expression label = (Expression)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-2)).value;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(statementListleft, statementListright, statementList);
    RESULT = new BladeSectionStatement(labelleft, endright, label, block, BladeSectionStatement.Type.BLOCK);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // statement ::= T_BLADE_INCLUDE T_OPEN_PARENTHESE variable T_COMMA variable T_CLOSE_PARENTHESE 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).value;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Variable label = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int valueleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int valueright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Variable value = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(valueleft, valueright, value);
    RESULT = new BladeIncludeStatement(labelleft, endright, label, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // statement ::= T_BLADE_INCLUDE T_OPEN_PARENTHESE variable T_CLOSE_PARENTHESE 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Variable label = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
	//without arguments
    Block block = new Block(labelleft, labelright, label);
    RESULT = new BladeIncludeStatement(labelleft, endright, label, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // statement ::= T_BLADE_SECTION T_OPEN_PARENTHESE yield_label T_COMMA variable T_CLOSE_PARENTHESE 
            {
              Statement RESULT =null;
		int tokenleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).left;
		int tokenright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).right;
		Object token = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)).value;
		int labelleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).left;
		int labelright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).right;
		Expression label = (Expression)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-3)).value;
		int valueleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int valueright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Variable value = (Variable)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int endleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int endright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object end = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Block block = new Block(valueleft, valueright, value);
    RESULT = new BladeSectionStatement(labelleft, endright, label, block, BladeSectionStatement.Type.INLINE);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-5)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // statement ::= T_BLADE_YIELD 
            {
              Statement RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Object s = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    Scalar directive = new Scalar(sleft, sright, s.toString(), Scalar.Type.STRING);
    RESULT = new BladeConstDirectiveStatement(sleft, sright, directive);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // statement ::= T_BLADE_EXTENDS path_expression 
            {
              Statement RESULT =null;
		int extsleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int extsright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		Object exts = (Object)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int pathleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int pathright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Expression path = (Expression)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    //for the moment until we find to make use of <<EOL>>
    Block block = null;
    RESULT = new BladeExtendsStatement(extsleft, pathright, path, block);

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("statement",4, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // top_statement ::= statement 
            {
              Statement RESULT =null;
		int statementleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int statementright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Statement statement = (Statement)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    RESULT = statement;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("top_statement",3, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // inner_statement_list ::= 
            {
              List RESULT =null;
		
    RESULT = new LinkedList();

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("inner_statement_list",5, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // inner_statement_list ::= inner_statement_list top_statement 
            {
              List RESULT =null;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int statementleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int statementright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Statement statement = (Statement)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    // Ignore null statements
    if(statement != null) {
        statementList.add(statement);
    }
    RESULT = statementList;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("inner_statement_list",5, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // top_statement_list ::= 
            {
              List RESULT =null;
		
    RESULT = new LinkedList();

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("top_statement_list",2, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // top_statement_list ::= top_statement_list top_statement 
            {
              List RESULT =null;
		int sListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int sListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		List sList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		int statementleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int statementright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		Statement statement = (Statement)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    //check for extends
    if(statement != null) {
        sList.add(statement);
    }
    RESULT = sList;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("top_statement_list",2, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= thestart EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).right;
		BladeProgram start_val = (BladeProgram)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)).value;
		RESULT = start_val;
              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.elementAt(CUP$ASTBladeParser$top-1)), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$ASTBladeParser$parser.done_parsing();
          return CUP$ASTBladeParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // thestart ::= top_statement_list 
            {
              BladeProgram RESULT =null;
		int statementListleft = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).left;
		int statementListright = ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()).right;
		List statementList = (List)((java_cup.runtime.Symbol) CUP$ASTBladeParser$stack.peek()).value;
		
    ASTBladeScanner phpAstLexer5 = (ASTBladeScanner) parser.getScanner();
    int endOfProgram = statementListright > phpAstLexer5.getWhitespaceEndPosition() ? statementListright : phpAstLexer5.getWhitespaceEndPosition();
    BladeProgram program = new BladeProgram(statementListleft, endOfProgram, statementList);
    RESULT = program;

              CUP$ASTBladeParser$result = parser.getSymbolFactory().newSymbol("thestart",1, ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$ASTBladeParser$stack.peek()), RESULT);
            }
          return CUP$ASTBladeParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}


