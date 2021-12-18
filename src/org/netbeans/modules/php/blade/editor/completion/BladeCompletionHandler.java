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

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java_cup.runtime.Symbol;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.document.LineDocumentUtils;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.php.blade.editor.lexer.BladeTokenId;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.csl.api.CodeCompletionContext;
import org.netbeans.modules.csl.api.CodeCompletionHandler2;
import org.netbeans.modules.csl.api.CodeCompletionResult;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.Documentation;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ParameterInfo;
import org.netbeans.modules.csl.spi.DefaultCompletionResult;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.php.blade.editor.BladeProjectSupport;
import org.netbeans.modules.php.blade.editor.completion.BladeCompletionContextFinder.KeywordCompletionType;
import org.netbeans.modules.php.blade.editor.completion.BladeCompletionItem.CompletionRequest;
import org.netbeans.modules.php.editor.api.ElementQuery;
import org.netbeans.modules.php.editor.api.ElementQueryFactory;
import org.netbeans.modules.php.editor.api.NameKind;
import org.netbeans.modules.php.editor.api.QuerySupportFactory;
import org.netbeans.modules.php.editor.api.elements.ClassElement;
import org.netbeans.modules.php.editor.parser.ASTPHP5Scanner;
import org.netbeans.modules.php.editor.parser.ASTPHP5Symbols;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

public class BladeCompletionHandler implements CodeCompletionHandler2 {

    private static URL documentationUrl = null;

    static {
        try {
            documentationUrl = new URL("https://laravel.com/docs/8.x/blade"); //NOI18N
        } catch (MalformedURLException ex) {
            //LOGGER.log(Level.FINE, null, ex);
        }
    }

    static final Map<String, KeywordCompletionType> BLADE_KEYWORDS = new HashMap<>();

    static {
        BLADE_KEYWORDS.put("@continue", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_KEYWORDS.put("@endfor", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_KEYWORDS.put("@endif", KeywordCompletionType.WITH_ARG_AND_ENDTAG);
        BLADE_KEYWORDS.put("@endforeach", KeywordCompletionType.WITH_ARG_AND_ENDTAG);
        BLADE_KEYWORDS.put("@endsection", KeywordCompletionType.WITH_ARG_AND_ENDTAG);
    }

    static final Map<String, KeywordCompletionType> BLADE_DIRECTIVES = new HashMap<>();

    static {
        BLADE_DIRECTIVES.put("@yield", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_DIRECTIVES.put("@extends", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_DIRECTIVES.put("@include", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_DIRECTIVES.put("@includeIf", KeywordCompletionType.WITH_ARG); //NOI18N
        BLADE_DIRECTIVES.put("@if", KeywordCompletionType.WITH_ARG_AND_ENDTAG); //NOI18N
        BLADE_DIRECTIVES.put("@for", KeywordCompletionType.WITH_ARG_AND_ENDTAG); //NOI18N
        BLADE_DIRECTIVES.put("@foreach", KeywordCompletionType.WITH_ARG_AND_ENDTAG); //NOI18N
        BLADE_DIRECTIVES.put("@section", KeywordCompletionType.WITH_ARG_AND_ENDTAG); //NOI18N
        BLADE_DIRECTIVES.put("@php", KeywordCompletionType.WITH_ENDTAG);
    }

    private static final Collection<Character> AUTOPOPUP_STOP_CHARS = new TreeSet<>(
            Arrays.asList('=', ';', '+', '-', '*', '/',
                    '%', '(', ')', '[', ']', '{', '}', '?'));

    @Override
    public CodeCompletionResult complete(CodeCompletionContext codeCompletionContext) {

        final List<CompletionProposal> completionProposals = new ArrayList<>();
        ParserResult parserResult = codeCompletionContext.getParserResult();

        if (!(parserResult instanceof BladeParserResult)) {
            return CodeCompletionResult.NONE;
        }

        BladeParserResult bladeParserResult = (BladeParserResult) parserResult;
        if (bladeParserResult.getProgram() == null) {
            return CodeCompletionResult.NONE;
        }

        final FileObject fileObject = bladeParserResult.getSnapshot().getSource().getFileObject();
        if (fileObject == null) {
            return CodeCompletionResult.NONE;
        }

        CompletionRequest request = new CompletionRequest();
        request.prefix = codeCompletionContext.getPrefix();
        int caretOffset = codeCompletionContext.getCaretOffset();
        String properPrefix = getPrefix(bladeParserResult, caretOffset, true);
        if (request.prefix.length() == 0) {
            request.prefix = properPrefix;
        }
        request.anchorOffset = caretOffset - (properPrefix == null ? 0 : properPrefix.length());
        request.parserResult = bladeParserResult;

        request.context = BladeCompletionContextFinder.find(request.parserResult, caretOffset);
        BladeProjectSupport sup = BladeProjectSupport.findFor(fileObject);
        if (sup != null) {
            request.index = sup.getIndex();
        }
        doCompletion(completionProposals, request);
        return new DefaultCompletionResult(completionProposals, false);
    }

    private void doCompletion(final List<CompletionProposal> completionProposals, final CompletionRequest request) {
        switch (request.context) {
            case PATH:
                completeBladeViews(completionProposals, request);
                break;
            case DIRECTIVE:
                completeDirectives(completionProposals, request);
                break;
            case PHP:
                //might need some optimisations
                if (request.prefix.length() > 0){
                    completePhpClasses(completionProposals, request);
                }
                break;
            case ALL:
                completeAll(completionProposals, request);
                break;
            case NONE:
                break;
            default:
                completeAll(completionProposals, request);
        }
    }

    private void completeAll(final List<CompletionProposal> completionProposals, final CompletionRequest request) {
        completeDirectives(completionProposals, request);
        completeBladeViews(completionProposals, request);
    }

    private void completeBladeViews(final List<CompletionProposal> completionProposals, final CompletionRequest request) {
        if (request.index == null) {
            return;
        }
        Map<FileObject, Collection<String>> search = request.index.findAllBladeViewPaths();
        for (Map.Entry<FileObject, Collection<String>> entry : search.entrySet()) {
            Collection<?> values = entry.getValue();
            for (Object value : values) {
                if (value == null) {
                    continue;
                }
                String path = value.toString();
                BladeElement element;
                if (request.prefix.contains("@extends") || request.prefix.contains("@include") || request.prefix.contains("@includeIf")) {
                    element = new BladeElement(request.prefix + "(\"" + path + "\")");
                } else {
                    element = new BladeElement(path);
                }
                completionProposals.add(new BladeCompletionItem(element, request));
            }
        }
    }

    private void completePhpClasses(final List<CompletionProposal> completionProposals, final CompletionRequest request) {
        ParserResult info = request.parserResult;
        final FileObject fileObject = info.getSnapshot().getSource().getFileObject();

        ElementQuery.Index indexQuery = ElementQueryFactory.createIndexQuery(QuerySupportFactory.get(fileObject));
        //if T_BLADE_PHP we need to scan text
        ASTPHP5Scanner scanner = new ASTPHP5Scanner(new StringReader("<?php " + request.prefix));
        Symbol symbol;
        Symbol lastSymbol = null;
        String lastPrefix = "";
        int count = 0;
        
        try {
            do {
                symbol = scanner.next_token();
                if (symbol.sym != 0 && symbol.value != null){
                    lastSymbol = symbol;
                }
                count++;
            } while (symbol.sym != ASTPHP5Symbols.EOF);
        } catch (Exception ex) {

        }
        if (lastSymbol != null && lastSymbol.sym == ASTPHP5Symbols.T_STRING){
            lastPrefix = lastSymbol.value.toString(); 
        }
        
        if (lastPrefix.length() == 0){
            return;
        }
        
        //filter only Php Classes
        final NameKind nameQuery = NameKind.caseInsensitivePrefix(lastPrefix);
        Set<ClassElement> classes = indexQuery.getClasses(nameQuery);

        for (ClassElement clazz : classes) {
            String className = clazz.getName();
            String classPath = clazz.getFilenameUrl();
            request.anchorOffset+= request.prefix.length() - lastPrefix.length();
            request.prefix = lastPrefix;       
            completionProposals.add(new BladeCompletionItem.KeywordItem(className, request));
        }

//                        NameKind.caseInsensitivePrefix(QualifiedName.create("$").toNotFullyQualified()));
        //Set<TypeElement>  cachedElements = indexQuery.getTypes(NameKind.empty());
        //String currentlyEditedFileURL = fileObject.toURL().toString();
    }

    private void completeDirectives(final List<CompletionProposal> completionProposals, final CompletionRequest request) {
        List<String> defaultDirectives = new ArrayList<>(BLADE_DIRECTIVES.keySet());
        for (String directive : defaultDirectives) {
            if (startsWith(directive, request.prefix)) {
                DirectiveElement element = new DirectiveElement(directive);
                completionProposals.add(new BladeCompletionItem.DirectiveItem(element, request));
            }
        }
        List<String> defaultKeywords = new ArrayList<>(BLADE_KEYWORDS.keySet());
        for (String keyword : defaultKeywords) {
            if (startsWith(keyword, request.prefix)) {
                completionProposals.add(new BladeCompletionItem.KeywordItem(keyword, request));
            }
        }
    }

    private static boolean startsWith(String theString, String prefix) {
        return prefix.length() == 0 ? true : theString.toLowerCase().startsWith(prefix.toLowerCase());
    }

    @Override
    public String document(ParserResult pr, ElementHandle eh) {
        return "";
    }

    @Override
    public ElementHandle resolveLink(String string, ElementHandle eh) {
        return null;
    }

    @Override
    public String getPrefix(ParserResult info, int offset, boolean upToOffset) {
        return PrefixResolver.create(info, offset, upToOffset).resolve();
    }

    @Override
    public QueryType getAutoQuery(JTextComponent jtc, String string) {
        if (string.length() == 0) {
            return QueryType.NONE;
        }
        char lastChar = string.charAt(string.length() - 1);
        Document document = jtc.getDocument();
        //TokenHierarchy th = TokenHierarchy.get(document);
        int offset = jtc.getCaretPosition();
//        TokenSequence<PHPTokenId> ts = LexUtilities.getPHPTokenSequence(document, offset);
//        if (ts == null) {
//            return QueryType.STOP;
//        }
        return QueryType.ALL_COMPLETION;
    }

    @Override
    public String resolveTemplateVariable(String string, ParserResult pr, int i, String string1, Map map) {
        return null;
    }

    @Override
    public Set<String> getApplicableTemplates(Document dcmnt, int i, int i1) {
        return Collections.emptySet();
    }

    @Override
    public ParameterInfo parameters(ParserResult pr, int i, CompletionProposal cp) {
        return new ParameterInfo(new ArrayList<String>(), 0, 0);
    }

    @Override
    public Documentation documentElement(ParserResult parserResult, ElementHandle elementHandle, Callable<Boolean> cancel) {
        Documentation result = null;
        if (elementHandle instanceof DirectiveElement) {
            //correspondence with Bundle.properties must be 1 to 1
            result = Documentation.create(NbBundle.getMessage(BladeCompletionHandler.class, "TAG_" + elementHandle.getName()));
        } else if (elementHandle instanceof BladeElement) {
            //we can add the filename
            String tooltip = elementHandle.getName();
            result = Documentation.create(String.format("<div align=\"right\"><font size=-1>%s</font></div>", tooltip));
        }
        return result;
    }

    private static final class PrefixResolver {

        private final ParserResult info;
        private final int offset;
        private final boolean upToOffset;
        private BaseDocument doc;
        private String result = "";

        static PrefixResolver create(ParserResult info, int offset, boolean upToOffset) {
            return new PrefixResolver(info, offset, upToOffset);
        }

        private PrefixResolver(ParserResult info, int offset, boolean upToOffset) {
            this.info = info;
            this.offset = offset;
            this.upToOffset = upToOffset;
            this.doc = (BaseDocument) info.getSnapshot().getSource().getDocument(false);
        }

        String resolve() {
            if (doc != null) {
                int lineBegin = LineDocumentUtils.getLineStart(doc, offset);
                try {
                    int lineEnd = LineDocumentUtils.getLineEnd(doc, offset);
                    if (lineBegin != -1 && lineEnd > 0) {
                        String line = doc.getText(lineBegin, lineEnd - lineBegin);
                        int lineOffset = offset - lineBegin;
                        int start = 0;
                        String prefix;
                        prefix = line.substring(start, lineOffset);
                        int lastIndexOfAt = prefix.lastIndexOf('@'); //NOI18N

                        if (lastIndexOfAt >= 0) {
                            prefix = prefix.substring(lastIndexOfAt);
                        } else {
                            prefix = line.substring(start);
                        }
                        result = prefix;
                    }
                } catch (BadLocationException ble) {
                    //Exceptions.printStackTrace(ble);
                }
            }

            return result;
        }

        private void processToken(TokenSequence<? extends TokenId> ts) {
            if (ts.offset() == offset) {
                ts.movePrevious();
            }
            Token<?> token = ts.token();
            if (token != null) {
                processSelectedToken(ts);
            }
        }

        private void processSelectedToken(TokenSequence<? extends TokenId> ts) {
            TokenId id = ts.token().id();
            if (isValidTokenId(id)) {
                createResult(ts);
            }
        }

        private void createResult(TokenSequence<? extends TokenId> ts) {
            if (upToOffset) {
                String text = ts.token().text().toString();
                result = text.substring(0, offset - ts.offset());
            }
        }

        private static boolean isValidTokenId(TokenId id) {
            return BladeTokenId.T_BLADE_DIRECTIVE.equals(id) || BladeTokenId.BLADE_PHP_TOKEN.equals(id);
        }

    }

}
