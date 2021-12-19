package org.netbeans.modules.php.blade.editor.gsf;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.swing.text.Document;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.php.blade.editor.BladeProjectSupport;
import org.netbeans.modules.php.blade.editor.index.api.BladeIndex;
import org.netbeans.modules.php.blade.editor.lexer.BladeLexerUtils;
import org.netbeans.modules.php.blade.editor.lexer.BladeTokenId;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author bhaidu
 */
public class BladeDeclarationFinder implements DeclarationFinder {

    @Override
    public OffsetRange getReferenceSpan(Document doc, int caretOffset) {
        final BaseDocument docx = (BaseDocument) doc;
        TokenHierarchy<Document> th = TokenHierarchy.get(doc);
        docx.readLock();
        OffsetRange range = OffsetRange.NONE;
        try {
            //BaseDocument doc = (BaseDocument)document;
            TokenSequence<? extends BladeTokenId> ts = BladeLexerUtils.getBladeMarkupTokenSequence(doc, caretOffset);

            if (ts == null) {
                return OffsetRange.NONE;
            }

            ts.move(caretOffset);

            if (!ts.moveNext() && !ts.movePrevious()) {
                return OffsetRange.NONE;
            }

            // Determine whether the caret position is right between two tokens
            boolean isBetween = (caretOffset == ts.offset());

            range = getReferenceSpan(ts, th, caretOffset);

            if ((range == OffsetRange.NONE) && isBetween) {
                // The caret is between two tokens, and the token on the right
                // wasn't linkable. Try on the left instead.
                if (ts.movePrevious()) {
                    range = getReferenceSpan(ts, th, caretOffset);
                }
            }
        } finally {
            docx.readUnlock();
        }

        return range;
    }

    private OffsetRange getReferenceSpan(TokenSequence<?> ts,
            TokenHierarchy<Document> th, int lexOffset) {
        Token<?> token = ts.token();
        TokenId id = token.id();

//        if (id == GdlTokenId.GDL_VAR) {
//            if (token.length() == 1 && id == GdlTokenId.GDL_VAR && token.text().toString().equals(",")) {
//                return OffsetRange.NONE;
//            }
//        }
        // TODO: Tokens.SUPER, Tokens.THIS, Tokens.SELF ...
        if (BladeTokenId.T_DIRECTIVE_ARG == id || BladeTokenId.T_BLADE_PHP_VAR == id) {
            return new OffsetRange(ts.offset(), ts.offset() + token.length());
        }

        // Look for embedded RDoc comments:
        TokenSequence<?> embedded = ts.embedded();

        if (embedded != null) {
            ts = embedded;
            embedded.move(lexOffset);

            if (embedded.moveNext()) {
                Token<?> embeddedToken = embedded.token();

//                if (embeddedToken.id() == GdlStringTokenId.URL) {
//                    return new OffsetRange(embedded.offset(),
//                            embedded.offset() + embeddedToken.length());
//                }
                // Recurse into the range - perhaps there is Ruby code (identifiers
                // etc.) to follow there
                OffsetRange range = getReferenceSpan(embedded, th, lexOffset);

                if (range != OffsetRange.NONE) {
                    return range;
                }
            }
        }

        return OffsetRange.NONE;
    }

    @Override
    public DeclarationLocation findDeclaration(ParserResult info, int carretOffset) {
        final Document document = info.getSnapshot().getSource().getDocument(false);
        if (document == null) {
            return DeclarationLocation.NONE;
        }
        final BaseDocument doc = (BaseDocument) document;
        BladeParserResult parseResult = (BladeParserResult) info;
        doc.readLock();
        try {
            //see if it's a routine
            DeclarationLocation extendsStatement = findExtendsStatement(parseResult, carretOffset, doc);
            if (extendsStatement != DeclarationLocation.NONE) {
                return extendsStatement;
            }
        } finally {
            doc.readUnlock();
        }

        return DeclarationLocation.NONE;
    }

    private DeclarationLocation findExtendsStatement(BladeParserResult info, int carretOffset, BaseDocument doc) {
        TokenSequence<? extends BladeTokenId> ts = BladeLexerUtils.getBladeMarkupTokenSequence(doc, carretOffset);
        String viewPath = "";
        if (ts != null) {
            ts.move(carretOffset);
            if (!ts.moveNext()) {
                ts.movePrevious();
            }
            do {
                Token<? extends TokenId> token = ts.token();
                if (token == null) {
                    break;
                }
                TokenId tokenId = token.id();
                 TokenSequence<?> embedded = ts.embedded();
                 Token<?> embeddedToken = embedded.token();
                if (BladeTokenId.T_DIRECTIVE_ARG.equals(tokenId) || BladeTokenId.T_BLADE_PHP_VAR.equals(tokenId)) {
                    viewPath = token.text().toString().trim();
                    viewPath = viewPath.substring(1, viewPath.length() - 1);
                    break;
                }
            } while (ts.movePrevious());
            //result = findContext(ts);
        }

        if (viewPath.length() == 0) {
            return DeclarationLocation.NONE;
        }
        FileObject fileObject = info.getSnapshot().getSource().getFileObject();
        BladeProjectSupport sup = BladeProjectSupport.findFor(fileObject);
        if (sup != null) {
            BladeIndex indx = sup.getIndex();
            Map<FileObject, Collection<String>> views = indx.findAllBladeViewPaths();
            DeclarationLocation alternatives = DeclarationLocation.NONE;
            for (Map.Entry<FileObject, Collection<String>> view : views.entrySet()) {
                Collection<?> values = view.getValue();
                for (Object value : values) {
                    if (value == null) {
                        continue;
                    }
                    if (viewPath.equals(value.toString()) && alternatives == DeclarationLocation.NONE) {
                         return new DeclarationLocation(
                                view.getKey(), 0);
                    }
                    if (value.toString().endsWith(viewPath)) {
                        DeclarationLocation declLocation = new DeclarationLocation(
                                view.getKey(), 0);
                        PathElement elem = new PathElement(viewPath);
                        AlternativeLocation al = new BladeAlternativeLocation(elem, declLocation);
                        if (alternatives == DeclarationLocation.NONE) {
                            alternatives = al.getLocation();
                        }
                        alternatives.addAlternative(al);
                    }
                }

            }
            return alternatives;
        }

        return DeclarationLocation.NONE;
    }

    public static class BladeAlternativeLocation implements AlternativeLocation {

        private PathElement modelElement;
        private DeclarationLocation declaration;

        public BladeAlternativeLocation(PathElement modelElement, DeclarationLocation declaration) {
            this.modelElement = modelElement;
            this.declaration = declaration;
        }

        @Override
        public ElementHandle getElement() {
            return modelElement;
        }

        @Override
        public String getDisplayHtml(HtmlFormatter formatter) {
            formatter.reset();
            //ElementKind ek = modelElement.getKind();
            formatter.appendText(modelElement.getName());

            if (declaration.getFileObject() != null) {
                formatter.appendText(" in ");
                formatter.appendText(FileUtil.getFileDisplayName(declaration.getFileObject()));
            }

            return formatter.getText();
        }

        @Override
        public DeclarationLocation getLocation() {
            return declaration;
        }

        @Override
        public int compareTo(AlternativeLocation o) {
            BladeAlternativeLocation i = (BladeAlternativeLocation) o;
            return this.modelElement.getName().compareTo(i.modelElement.getName());
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + (this.modelElement != null ? this.modelElement.hashCode() : 0);
            hash = 89 * hash + (this.declaration != null ? this.declaration.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final BladeAlternativeLocation other = (BladeAlternativeLocation) obj;
            if (this.modelElement != other.modelElement && (this.modelElement == null || !this.modelElement.equals(other.modelElement))) {
                return false;
            }
            if (this.declaration != other.declaration && (this.declaration == null || !this.declaration.equals(other.declaration))) {
                return false;
            }
            return true;
        }
    }

    public class PathElement implements ElementHandle {

        private final String name;

        public PathElement(String name) {
            //we can add a file object from element
            this.name = name;
        }

        @Override
        public FileObject getFileObject() {
            return null;
        }

        @Override
        public String getMimeType() {
            return BladeLanguage.BLADE_MIME_TYPE;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getIn() {
            return "";
        }

        @Override
        public ElementKind getKind() {
            return ElementKind.TAG;
        }

        @Override
        public Set<Modifier> getModifiers() {
            return Collections.EMPTY_SET;
        }

        @Override
        public boolean signatureEquals(ElementHandle eh) {
            return false;
        }

        @Override
        public OffsetRange getOffsetRange(ParserResult pr) {
            return OffsetRange.NONE;
        }
    }
}
