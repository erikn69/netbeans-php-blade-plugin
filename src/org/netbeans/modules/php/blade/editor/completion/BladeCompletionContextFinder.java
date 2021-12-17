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
package org.netbeans.modules.php.blade.editor.completion;

import java.util.LinkedList;
import java.util.List;
import org.netbeans.modules.php.blade.editor.lexer.BladeLexerUtils;
import org.netbeans.modules.php.blade.editor.lexer.BladeTokenId;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.lexer.TokenUtilities;

/**
 *
 * @author Haidu Bogdan
 */
public class BladeCompletionContextFinder {

    public static enum CompletionContext {
        ECHO,
        DIRECTIVE,
        EXTENDS,
        FILTER,
        PHP,
        NONE,
        ALL;
    }

    public static CompletionContext find(final BladeParserResult parserResult, final int offset) {
        assert parserResult != null;
        CompletionContext result = CompletionContext.NONE;
        TokenSequence<? extends TokenId> tokenSequence = BladeLexerUtils.getBladeMarkupTokenSequence(parserResult.getSnapshot(), offset);
        if (tokenSequence != null) {
            tokenSequence.move(offset);
            if (!tokenSequence.moveNext()) {
                tokenSequence.movePrevious();
            }
            result = findContext(tokenSequence);
        }
        return result;
    }

    private static CompletionContext findContext(TokenSequence<? extends TokenId> tokenSequence) {
        CompletionContext result = CompletionContext.ALL;
        do {
            Token<? extends TokenId> token = tokenSequence.token();
            if (token == null) {
                break;
            }
            TokenId tokenId = token.id();
            if (BladeTokenId.BLADE_PHP_TOKEN.equals(tokenId)) {
                List<? extends Token<? extends TokenId>> preceedingLineTokens = getPreceedingLineTokens(token, tokenSequence.offset(), tokenSequence);
                for (Token<? extends TokenId> t : preceedingLineTokens) {
                    if (BladeTokenId.T_BLADE_DIRECTIVE.equals(t.id())){
                        result = CompletionContext.EXTENDS;
                        break;
                    }
                }
                break;
            } else if (BladeTokenId.T_BLADE_OTHER.equals(tokenId)) {
                result = CompletionContext.NONE;
                break;
            } else if (BladeTokenId.T_BLADE_OPEN_ECHO.equals(tokenId)) {
                result = CompletionContext.ECHO;
                break;
            } else if (BladeTokenId.T_BLADE_DIRECTIVE.equals(tokenId)) {
                result = CompletionContext.DIRECTIVE;
                break;
            } else if (BladeTokenId.T_BLADE_PHP_VAR.equals(tokenId)) {
                result = CompletionContext.PHP;
                break;
            }
        } while (tokenSequence.movePrevious());
        return result;
    }

    private static List<? extends Token<? extends TokenId>> getPreceedingLineTokens(Token<? extends TokenId> token, int tokenOffset, TokenSequence<? extends TokenId> tokenSequence) {
        int orgOffset = tokenSequence.offset();
        LinkedList<Token<? extends TokenId>> tokens = new LinkedList<>();
        if (token.id() != BladeTokenId.WHITESPACE
                || TokenUtilities.indexOf(token.text().subSequence(0, Math.min(token.text().length(), tokenOffset)), '\n') == -1) { // NOI18N
            while (true) {
                if (!tokenSequence.movePrevious()) {
                    break;
                }
                Token<? extends TokenId> cToken = tokenSequence.token();
                if (cToken.id() == BladeTokenId.WHITESPACE
                        && TokenUtilities.indexOf(cToken.text(), '\n') != -1) { // NOI18N
                    break;
                }
                tokens.addLast(cToken);
            }
        }

        tokenSequence.move(orgOffset);
        tokenSequence.moveNext();

        return tokens;
    }
    
    static enum KeywordCompletionType {
        SIMPLE, WITH_ARG, WITH_ARG_AND_ENDTAG, WITH_ENDTAG
    };

}
