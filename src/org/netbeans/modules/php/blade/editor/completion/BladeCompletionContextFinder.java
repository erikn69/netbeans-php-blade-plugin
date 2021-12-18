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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.modules.php.blade.editor.lexer.BladeLexerUtils;
import org.netbeans.modules.php.blade.editor.lexer.BladeTokenId;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.lexer.TokenUtilities;
import org.netbeans.modules.php.editor.lexer.PHPTokenId;

/**
 *
 * @author Haidu Bogdan
 */
public class BladeCompletionContextFinder {

    public static enum CompletionContext {
        ECHO,
        DIRECTIVE,
        PATH,
        EXTENDS,
        FILTER,
        PHP,
        NONE,
        ALL;
    }

    private static final List<Object[]> DIRECTIVE_KEYWORD_TOKENS = Arrays.asList(
            new Object[]{BladeTokenId.T_BLADE_DIRECTIVE_PREFIX},
            new Object[]{BladeTokenId.T_BLADE_DIRECTIVE});

    private static Collection<BladeTokenId> CTX_DELIMITERS = Arrays.asList(
            BladeTokenId.T_BLADE_DIRECTIVE_PREFIX, BladeTokenId.T_BLADE_DIRECTIVE,
            BladeTokenId.T_BLADE_IF, BladeTokenId.T_BLADE_FOR, BladeTokenId.T_BLADE_FOREACH,
            BladeTokenId.T_BLADE_INCLUDE
    );

    private static Collection<BladeTokenId> PATH_KEYWORDS_TOKEN = Arrays.asList(
            BladeTokenId.T_BLADE_INCLUDE, BladeTokenId.T_BLADE_EXTENDS
    );

    private static Collection<BladeTokenId> PHP_KEYWORDS_TOKEN = Arrays.asList(
            BladeTokenId.T_BLADE_PHP_OPEN, BladeTokenId.T_BLADE_PHP
    );

    public static CompletionContext find(final BladeParserResult info, final int offset) {
        assert info != null;
        CompletionContext result = CompletionContext.NONE;
        TokenHierarchy<?> th = info.getSnapshot().getTokenHierarchy();
        
        if (th == null) {
            return result;
        }

        //check for HTML content ... maybe we don't have this case
        TokenSequence<HTMLTokenId> html_ts = th.tokenSequence(HTMLTokenId.language());

        if (html_ts != null) {
            html_ts.move(offset);
            if (html_ts.movePrevious()) {
                while (true) {
                    if (!html_ts.movePrevious()) {
                        break;
                    }
                    Token<HTMLTokenId> cToken = html_ts.token();
                    int test = 1;
                }
            }
        }
 
        TokenSequence<BladeTokenId> ts = th.tokenSequence(BladeTokenId.language());

        ts.move(offset);
        final boolean moveNextSucces = ts.moveNext();
        if (!moveNextSucces && !ts.movePrevious()) {
            return CompletionContext.NONE;
        }

        Token<BladeTokenId> token = ts.token();
        BladeTokenId id = token.id();

        if (PATH_KEYWORDS_TOKEN.contains(id)) {
            return CompletionContext.PATH;
        }

        if (CTX_DELIMITERS.contains(id)) {
            return CompletionContext.DIRECTIVE;
        }

        if (PHP_KEYWORDS_TOKEN.contains(id)) {
            return CompletionContext.PHP;
        }

        int tokenIdOffset = ts.token().offset(th);
        result = findContext(token, (offset - tokenIdOffset), ts);
        return result;
    }

    private static CompletionContext findContext(Token<BladeTokenId> token, int tokenOffset, TokenSequence<BladeTokenId> tokenSequence) {
        CompletionContext result = CompletionContext.NONE;

        List<? extends Token<BladeTokenId>> preceedingLineTokens = getPreceedingLineTokens(token, tokenOffset, tokenSequence);

        for (int i = 0; i < preceedingLineTokens.size(); i++) {
            Token<BladeTokenId> cToken = preceedingLineTokens.get(i);
            BladeTokenId id = cToken.id();
            if (id.equals(BladeTokenId.T_BLADE_DIRECTIVE) || id.equals(BladeTokenId.T_BLADE_DIRECTIVE_PREFIX)) {
                return CompletionContext.DIRECTIVE;
            }
            if (PHP_KEYWORDS_TOKEN.contains(id)) {
                return CompletionContext.PHP;
            }
        }
        /*
        do {
            Token<? extends TokenId> token = tokenSequence.token();
            if (token == null) {
                result = CompletionContext.NONE;
                break;
            }
            TokenId tokenId = token.id();
            if (BladeTokenId.BLADE_PHP_TOKEN.equals(tokenId)) {
                List<? extends Token<? extends TokenId>> preceedingLineTokens = getPreceedingLineTokens(token, tokenSequence.offset(), tokenSequence);
                for (Token<? extends TokenId> t : preceedingLineTokens) {
                    if (BladeTokenId.T_BLADE_DIRECTIVE.equals(t.id())) {
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
         */
        return result;
    }

    private static List<? extends Token<BladeTokenId>> getPreceedingLineTokens(Token<BladeTokenId> token, int tokenOffset, TokenSequence<BladeTokenId> tokenSequence) {
        int orgOffset = tokenSequence.offset();
        LinkedList<Token<BladeTokenId>> tokens = new LinkedList<>();
        if (token.id() != BladeTokenId.WHITESPACE
                || TokenUtilities.indexOf(token.text().subSequence(0, Math.min(token.text().length(), tokenOffset)), '\n') == -1) { // NOI18N
            while (true) {
                if (!tokenSequence.movePrevious()) {
                    break;
                }
                Token<BladeTokenId> cToken = tokenSequence.token();
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
