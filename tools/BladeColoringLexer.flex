   /*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2016 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.blade.editor.lexer;

import java.util.Objects;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.netbeans.modules.web.common.api.ByteStack;

@org.netbeans.api.annotations.common.SuppressWarnings({"SF_SWITCH_FALLTHROUGH", "URF_UNREAD_FIELD", "DLS_DEAD_LOCAL_STORE", "DM_DEFAULT_ENCODING", "EI_EXPOSE_REP"})
%%
%public
%class BladeColoringLexer
%type BladeTokenId
%function findNextToken
%unicode
%caseless
%char

%state ST_HTML
%state ST_PHP
%state ST_PHP_ECHO
%state ST_INLINE_PHP
%state ST_BLADE_PHP
%state ST_BLADE_ECHO
%state ST_BLADE_ECHO_ESCAPED
%state ST_DIRECTIVE
%state ST_DIRECTIVE_ARGUMENTS
%state ST_COMMENT
%state ST_HIGHLIGHTING_ERROR
%state ST_CLOSE_BLADE_PHP
%state ST_PHP_LOOKING_FOR_DIRECTIVE_ARG
%state ST_PHP_LOOKING_FOR_DIRECTIVE_PARAM
%state ST_PHP_LOOP_EXPR
%state ST_DIRECTIVE_ARG
%state ST_PHP_COND_EXPR
%state ST_CLOSE_PARANTHEIS

%{
    private ByteStack stack = new ByteStack();
    private LexerInput input;
    private int parenBalanceInDirective = 0; //for directive arguments

    public BladeColoringLexer(LexerRestartInfo info) {
        this.input = info.input();
        if(info.state() != null) {
            //reset state
            setState((LexerState) info.state(), parenBalanceInDirective);
        } else {
            //initial state
            stack.push(ST_HTML);
            zzState = ST_HTML;
            zzLexicalState = ST_HTML;
        }

    }

    public static final class LexerState  {
        final ByteStack stack;
        /** the current state of the DFA */
        final int zzState;
        /** the current lexical state */
        final int zzLexicalState;
        final int parenBalanceInDirective; 

        LexerState(ByteStack stack, int zzState, int zzLexicalState, int parenBalanceInDirective) {
            this.stack = stack;
            this.zzState = zzState;
            this.zzLexicalState = zzLexicalState;
            this.parenBalanceInDirective = parenBalanceInDirective;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            LexerState state = (LexerState) obj;
            return (this.stack.equals(state.stack)
                && (this.zzState == state.zzState)
                //&& (this.zzLexicalState == state.zzLexicalState)
                //&& (this.parenBalanceInDirective == state.parenBalanceInDirective)
                );
        }

        @Override
        public int hashCode() {
            int hash = 11;
            hash = 31 * hash + this.zzState;
            hash = 31 * hash + this.zzLexicalState;
            hash = 31 * hash + this.parenBalanceInDirective;
            if (stack != null) {
                hash = 31 * hash + this.stack.hashCode();
            }
            return hash;
        }
    }

    public LexerState getState() {
        return new LexerState(stack.copyOf(), zzState, zzLexicalState, parenBalanceInDirective);
    }

    public void setState(LexerState state, int parenBalanceInDirective) {
        this.stack.copyFrom(state.stack);
        this.zzState = state.zzState;
        this.zzLexicalState = state.zzLexicalState;
        this.parenBalanceInDirective = state.parenBalanceInDirective;
    }

    protected int getZZLexicalState() {
        return zzLexicalState;
    }

    //other functions

    protected void pushBack(int i) {
        yypushback(i);
    }

    protected void popState() {
        yybegin(stack.pop());
    }

    protected void pushState(final int state) {
        stack.push(getZZLexicalState());
        yybegin(state);
    }

    /**
     * Returns the smallest of multiple index values.
     *
     * @param values values
     * @return the smallest of multiple index values, -1 if all values are -1
     */
    private static int minIndex(int... values) {
        assert values.length != 0 : "No values"; // NOI18N
        boolean first = true;
        int min = -1;
        for (int value : values) {
            if (value == -1) {
                continue;
            }
            if (first) {
                first = false;
                min = value;
                continue;
            }
            min = Math.min(min, value);
        }
        return min;
    }

    /**
     * Get the first whitespace index of text.
     *
     * @param text the text
     * @return the first index of whitespace if whitespace exists, otherwise -1
     */
    private static int firstWhitespaceIndexOf(String text) {
        return minIndex(
            text.indexOf(' '),
            text.indexOf('\n'),
            text.indexOf('\r'),
            text.indexOf('\t')
        );
    }

%}


%eofval{
        if(input.readLength() > 0) {
            String yytext = yytext();
            // backup eof
            input.backup(1);
            //and return the text as error token
            if (getState().equals(ST_DIRECTIVE){
                    return BladeTopTokenId.T_DIRECTIVE;
            }
            return BladeTopTokenId.T_HTML;
        } else {
            return null;
        }
%eofval}

WHITESPACE=[ \t\r\n]+
NEWLINE=("\r"|"\n"|"\r\n")

OPEN_ECHO="{{"
CLOSE_ECHO="}}"

OPEN_ECHO_ESCAPED="{!!"
CLOSE_ECHO_ESCAPED="!!}"

COMMENT_START="{{--"
COMMENT_END="--}}"

LABEL=([[:letter:]_]|[\u007f-\u00ff])([[:letter:][:digit:]_]|[\u007f-\u00ff])*
ANY_CHAR=[^]
DIRECTIVE_PREFIX = "@"
OPEN_PHP="<?php"
CLOSE_PHP="?>"
CLOSE_BLADE_PHP = "@endphp";
%%

<ST_HTML>{WHITESPACE} {
	String yytext = yytext();
	//whitespace
    return BladeTokenId.WHITESPACE;
}

<ST_HTML>(([^<@{}]|"<"[^?%(script)<])+)|"<script"|"<" {
    return BladeTokenId.T_HTML;
}

<ST_HTML>"<script"{WHITESPACE}+"language"{WHITESPACE}*"="{WHITESPACE}*( "php"|"\"php\""|"\'php\'"){WHITESPACE}*">" {
    return BladeTokenId.T_HTML;
}

<ST_HTML>"<?xml" [^<]* "?>" {
	return BladeTokenId.T_XML;
}


<ST_HTML> {OPEN_PHP} {
    pushState(ST_PHP);
    return BladeTokenId.T_OPEN_PHP;
}

<ST_HTML> "<?=" {
    pushState(ST_PHP_ECHO);
    return BladeTokenId.T_PHP_OPEN_ECHO;
}

<ST_PHP> {CLOSE_PHP} {
	if (yylength() == 2){
	    popState(); 
		return BladeTokenId.T_CLOSE_PHP;
	}
	yypushback(2);
    return BladeTokenId.T_PHP;
}

<ST_PHP>~{CLOSE_PHP} {
    if (yylength() == 2){
       popState();
       return BladeTokenId.T_CLOSE_PHP;
    }
    yypushback(2);
    return BladeTokenId.T_PHP;
}

<ST_PHP_ECHO> {CLOSE_PHP} {
	if (yylength() == 2){
	    popState(); 
		return BladeTokenId.T_CLOSE_PHP;
	}
	yypushback(2);
    return BladeTokenId.T_PHP_ECHO;
}

<ST_PHP_ECHO>~{CLOSE_PHP} {
    if (yylength() == 2){
       popState();
       return BladeTokenId.T_CLOSE_PHP;
    }
    yypushback(2);
    return BladeTokenId.T_PHP_ECHO;
}

<ST_PHP> <<EOF>> {
  if (input.readLength() > 0) {
    input.backup(1);  // backup eof
    return BladeTokenId.T_PHP;
  }
  else {
      return null;
  }
}

<ST_HTML> "@php"{WHITESPACE}? {
    pushState(ST_BLADE_PHP);
    return BladeTokenId.T_BLADE_PHP_OPEN;
}

<ST_BLADE_PHP> {CLOSE_BLADE_PHP} {
    String ttext = yytext();
    popState();
    return BladeTokenId.T_BLADE_ENDPHP;
}

<ST_BLADE_PHP>{WHITESPACE}?~"@endphp" {
	String ttext = yytext();
    popState();
    if (yylength() == "@endphp".length()){
        return BladeTokenId.T_BLADE_ENDPHP;
    }
    
    yypushback("@endphp".length());
    return BladeTokenId.T_BLADE_PHP;
}

<ST_HTML> {COMMENT_START} {
    pushState(ST_COMMENT);
    return BladeTokenId.T_BLADE_COMMENT;
}


<ST_COMMENT> {COMMENT_END} {
    popState();
    return BladeTokenId.T_BLADE_COMMENT;
}

<ST_COMMENT>~{COMMENT_END} {
    yypushback(4);
    return BladeTokenId.T_BLADE_COMMENT;
}

<ST_COMMENT> <<EOF>> {
  if (input.readLength() > 0) {
    input.backup(1);  // backup eof
    return BladeTokenId.T_BLADE_COMMENT;
  }
  else {
      return null;
  }
}

<ST_HTML> {CLOSE_BLADE_PHP} {
    return BladeTokenId.T_BLADE_ENDPHP;
}


<ST_HTML> "@yield"{WHITESPACE}? {
    return BladeTokenId.T_BLADE_YIELD;
}

<ST_HTML> "@section"{WHITESPACE}? {
    pushState(ST_PHP_LOOKING_FOR_DIRECTIVE_ARG);
    return BladeTokenId.T_BLADE_SECTION;
}

<ST_HTML> "@include"{WHITESPACE}? {
	pushState(ST_PHP_LOOKING_FOR_DIRECTIVE_ARG);
    return BladeTokenId.T_BLADE_INCLUDE;
}

<ST_HTML> "@extends"{WHITESPACE}? {
	pushState(ST_PHP_LOOKING_FOR_DIRECTIVE_ARG);
    return BladeTokenId.T_BLADE_EXTENDS;
}

<ST_HTML> "@endsection"{WHITESPACE}? {
    return BladeTokenId.T_BLADE_ENDSECTION;
}

<ST_PHP_LOOKING_FOR_DIRECTIVE_ARG>"(" {
	//directive paranthesis
    return BladeTokenId.BLADE_PHP_TOKEN;
}

<ST_PHP_LOOKING_FOR_DIRECTIVE_ARG>")" {
    //directive end
    if (yylength() == 1){
        popState();
        return BladeTokenId.BLADE_PHP_TOKEN;
    }
    yypushback(1);
	popState();
    return BladeTokenId.T_BLADE_PHP_VAR;
}

<ST_PHP_LOOKING_FOR_DIRECTIVE_ARG>{ANY_CHAR} {
    String yytext = yytext();
    int debug = 1;
}

<ST_HTML> "@foreach"{WHITESPACE}? {
    pushState(ST_PHP_LOOP_EXPR);
    return BladeTokenId.T_BLADE_FOREACH;
}

<ST_HTML> "@for"{WHITESPACE}? {
    pushState(ST_PHP_LOOP_EXPR);
    return BladeTokenId.T_BLADE_FOR;
}

<ST_HTML> "@if"{WHITESPACE}? {
    pushState(ST_PHP_COND_EXPR);
    return BladeTokenId.T_BLADE_IF;
}

<ST_PHP_LOOP_EXPR>~")" {
    yypushback(1);
    popState();
    return BladeTokenId.T_BLADE_PHP_LOOP_PARAM;
}

<ST_PHP_COND_EXPR>~")" {
    yypushback(1);
    popState();
    return BladeTokenId.T_BLADE_PHP_COND;
}


<ST_HTML> "@endforeach"{WHITESPACE}? {
    return BladeTokenId.T_BLADE_ENDFOREACH;
}

<ST_HTML> "@endfor"{WHITESPACE}? {
    return BladeTokenId.T_BLADE_ENDFOR;
}

<ST_HTML>{DIRECTIVE_PREFIX}{LABEL}{WHITESPACE}? {
	String yytext = yytext();
   return BladeTokenId.T_BLADE_DIRECTIVE; 
}

<ST_HTML>{DIRECTIVE_PREFIX}{WHITESPACE} {
   return BladeTokenId.T_BLADE_DIRECTIVE_PREFIX; 
}

<ST_HTML>{DIRECTIVE_PREFIX}{LABEL} {WHITESPACE}* "(" {
   //we have a fatal in php embedding in (:)
   pushState(ST_DIRECTIVE_ARG);
   yypushback(1);
   return BladeTokenId.T_BLADE_DIRECTIVE; 
}

<ST_DIRECTIVE_ARG>"(" {
    String yytext = yytext();
    parenBalanceInDirective++;
    if (parenBalanceInDirective == 1){
        //first paranthesis
    	return BladeTokenId.BLADE_PHP_TOKEN;
    }
}

<ST_DIRECTIVE_ARG>")" {
	String yytext = yytext();
    parenBalanceInDirective--;
    if (parenBalanceInDirective <= 0){
        if (yylength() == 1){
           popState();
           return BladeTokenId.BLADE_PHP_TOKEN;
        }
        yypushback(1);
        popState();
        pushState(ST_CLOSE_PARANTHEIS);
		return BladeTokenId.T_DIRECTIVE_ARG;
    }
}

<ST_DIRECTIVE_ARG>{ANY_CHAR} {
	String yytext = yytext();
	int test = 1;
}

<ST_CLOSE_PARANTHEIS>")" {
        String yytext = yytext();
       popState();
       return BladeTokenId.BLADE_PHP_TOKEN;
}

<ST_HTML> {OPEN_ECHO} {
    String yytext = yytext();
    pushState(ST_BLADE_ECHO);
    return BladeTokenId.T_BLADE_OPEN_ECHO;
}

<ST_HTML> {OPEN_ECHO_ESCAPED} {
    String yytext = yytext();
    pushState(ST_BLADE_ECHO_ESCAPED);
    return BladeTokenId.T_BLADE_OPEN_ECHO;
}


<ST_BLADE_ECHO> {CLOSE_ECHO} {
    String yytext = yytext();
    popState();
    return BladeTokenId.T_BLADE_CLOSE_ECHO;
}

<ST_BLADE_ECHO>~{CLOSE_ECHO} {
    String yytext = yytext();
    yypushback(2);
    return BladeTokenId.T_BLADE_PHP_ECHO;
}

<ST_BLADE_ECHO_ESCAPED> {CLOSE_ECHO_ESCAPED} {
    String yytext = yytext();
    popState();
    return BladeTokenId.T_BLADE_CLOSE_ECHO;
}

<ST_BLADE_ECHO_ESCAPED>~{CLOSE_ECHO_ESCAPED} {
    String yytext = yytext();
    yypushback(3);
    return BladeTokenId.T_BLADE_PHP_ECHO;
}


<ST_BLADE_ECHO, ST_BLADE_ECHO_ESCAPED> {NEWLINE} {
    String yytext = yytext();
    popState();
    return BladeTokenId.T_HTML;
}

<ST_HTML> {CLOSE_ECHO} | {CLOSE_ECHO_ESCAPED} {
    String yytext = yytext();
    return BladeTokenId.T_BLADE_CLOSE_ECHO;
}

<ST_BLADE_ECHO, ST_BLADE_ECHO_ESCAPED> {WHITESPACE}+ {
    //no break;
}


/* ============================================
   Stay in this state until we find a whitespace.
   After we find a whitespace we go the the prev state and try again from the next token.
   ============================================ */
<ST_HIGHLIGHTING_ERROR> {
    {WHITESPACE} {
        popState();
        return BladeTokenId.T_HTML;
    }
    . {
        return BladeTokenId.T_HTML;
    }
}

/* ============================================
   This rule must be the last in the section!!
   it should contain all the states.
   ============================================ */
<ST_HTML, ST_BLADE_ECHO, ST_PHP, ST_BLADE_ECHO_ESCAPED, ST_BLADE_PHP> {
    . {
        //if (yylength() > 1) {
        //    yypushback(1);
        //}
        //pushState(ST_HIGHLIGHTING_ERROR);
    }
}