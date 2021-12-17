package org.netbeans.modules.php.blade.editor.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import javax.swing.text.BadLocationException;
import org.netbeans.lib.editor.util.CharSubSequence;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.GsfUtilities;
import org.netbeans.modules.parsing.api.ParserManager;
import org.netbeans.modules.parsing.api.ResultIterator;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.api.UserTask;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.php.blade.editor.gsf.BladeLanguage;
import org.netbeans.modules.php.blade.editor.index.api.Entry;
import org.netbeans.modules.php.blade.editor.index.api.RefactoringElementType;
import org.netbeans.modules.php.blade.editor.model.ModelVisitor;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.ASTNode;
import org.netbeans.modules.web.common.api.LexerUtils;
import org.netbeans.modules.web.common.api.WebUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;


/**
 *
 * maybe I didn't need it ???
 * 
 * @author bhaidu
 */
public class BladeFileModel {

    private Collection<Entry> classes, ids, htmlElements, imports, colors;
    private final Snapshot snapshot;
    private final Snapshot topLevelSnapshot;

    public static BladeFileModel create(Source source) throws ParseException {
        final AtomicReference<BladeFileModel> model = new AtomicReference<>();
        ParserManager.parse(Collections.singletonList(source), new UserTask() {
            @Override
            public void run(ResultIterator resultIterator) throws Exception {
                ResultIterator cssRi = WebUtils.getResultIterator(resultIterator, BladeLanguage.BLADE_MIME_TYPE);
                Snapshot topLevelSnapshot = resultIterator.getSnapshot();
                if (cssRi != null) {
                    Parser.Result parserResult = cssRi.getParserResult();
                    if (parserResult != null) {
                        model.set(new BladeFileModel((BladeParserResult) parserResult, topLevelSnapshot));
                        return;
                    }
                }
                model.set(new BladeFileModel(topLevelSnapshot));
            }
        });
        return model.get();
    }

    public static BladeFileModel create(BladeParserResult result) {
        return new BladeFileModel(result, null);
    }

    private BladeFileModel(Snapshot topLevelSnapshot) {
        this.snapshot = this.topLevelSnapshot = topLevelSnapshot;
    }

    private BladeFileModel(BladeParserResult parserResult, Snapshot topLevelSnapshot) {
        this.snapshot = parserResult.getSnapshot();
        this.topLevelSnapshot = topLevelSnapshot;
        if (parserResult.getProgram() != null && !parserResult.getProgram().getStatements().isEmpty()) {
            ParseTreeVisitor visitor = new ParseTreeVisitor(parserResult);
            //do something
        } //else broken source, no parse tree

    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public Snapshot getTopLevelSnapshot() {
        return topLevelSnapshot;
    }

    public FileObject getFileObject() {
        return getSnapshot().getSource().getFileObject();
    }

    public Collection<Entry> get(RefactoringElementType type) {
        switch (type) {
            case FIELD_EXTENDS:
                return getExtends();
        }

        return null;
    }

    public Collection<Entry> getClasses() {
        return classes == null ? Collections.<Entry>emptyList() : classes;
    }

    public Collection<Entry> getExtends() {
        return ids == null ? Collections.<Entry>emptyList() : ids;
    }

    public Collection<Entry> getHtmlElements() {
        return htmlElements == null ? Collections.<Entry>emptyList() : htmlElements;
    }

    public Collection<Entry> getImports() {
        return imports == null ? Collections.<Entry>emptyList() : imports;
    }

    public Collection<Entry> getColors() {
        return colors == null ? Collections.<Entry>emptyList() : colors;
    }

    /**
     *
     * @return true if the model is empty - nothing interesting found in the
     * page.
     */
    public boolean isEmpty() {
        return null == classes && null == ids && null == htmlElements && null == imports && null == colors;
    }

    //single threaded - called from constructor only, no need for synch
    private Collection<Entry> getClassesCollectionInstance() {
        if (classes == null) {
            classes = new ArrayList<>();
        }
        return classes;
    }

    private Collection<Entry> getIdsCollectionInstance() {
        if (ids == null) {
            ids = new ArrayList<>();
        }
        return ids;
    }

    private Collection<Entry> getHtmlElementsCollectionInstance() {
        if (htmlElements == null) {
            htmlElements = new ArrayList<>();
        }
        return htmlElements;
    }

    private Collection<Entry> getImportsCollectionInstance() {
        if (imports == null) {
            imports = new ArrayList<>();
        }
        return imports;
    }

    private Collection<Entry> getColorsCollectionInstance() {
        if (colors == null) {
            colors = new ArrayList<>();
        }
        return colors;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(super.toString());
        buf.append(":");
        for (Entry c : getImports()) {
            buf.append(" imports=");
            buf.append(c);
            buf.append(',');
        }
        for (Entry c : getClasses()) {
            buf.append('.');
            buf.append(c);
            buf.append(',');
        }
        for (Entry c : getExtends()) {
            buf.append('@');
            buf.append(c);
        }
        for (Entry c : getHtmlElements()) {
            buf.append(c);
            buf.append(',');
        }

        return buf.toString();
    }

    private class ParseTreeVisitor extends ModelVisitor {

        private int[] currentBodyRange;

        public ParseTreeVisitor(final BladeParserResult info) {
            super(info);
        }

        public boolean visit(ASTNode node) {
            return false;
        }

        private Collection<Entry> getExtensFromString(ASTNode resourceIdentifier) {
            Collection<Entry> files = new ArrayList<>();
            //string value only from resourceIdentifier
            return files;
        }

        private Collection<Entry> getExtendsFromURI(ASTNode resourceIdentifier) {
            Collection<Entry> files = new ArrayList<>();
            //@import url("another.css");
            
            return files;
        }
    }

    private Entry createEntry(String name, OffsetRange range, boolean isVirtual) {
        return createEntry(name, range, null, isVirtual);
    }

    private Entry createEntry(String name, OffsetRange range, OffsetRange bodyRange, boolean isVirtual) {
        //do not create entries for virtual generated code
//        if (CssGSFParser.containsGeneratedCode(name)) {
//            return null;
//        }

        return new LazyEntry(getSnapshot(), getTopLevelSnapshot(), name, range, bodyRange, isVirtual);
    }

    private static int[] getTextWSPreAndPostLens(CharSequence text) {
        int preWSlen = 0;
        int postWSlen = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isWhitespace(c)) {
                preWSlen++;
            } else {
                break;
            }
        }

        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (Character.isWhitespace(c)) {
                postWSlen++;
            } else {
                break;
            }
        }

        return new int[]{preWSlen, postWSlen};
    }

    private static class LazyEntry implements Entry {

        private final String name;
        private final OffsetRange range, bodyRange;
        private final boolean isVirtual;

        //computed lazily
        private OffsetRange documentRange, documentBodyRange;
        private CharSequence elementText, elementLineText;
        private int lineOffset = -1;
        private final CharSequence snapshotText;
        private CharSequence topLevelSnapshotText;
        private final int documentFrom;
        private final int documentTo;
        private int bodyDocFrom;
        private int bodyDocTo;

        public LazyEntry(Snapshot snapshot, Snapshot topLevelSnapshot, String name, OffsetRange range, OffsetRange bodyRange, boolean isVirtual) {
            this.snapshotText = snapshot.getText();
            if (topLevelSnapshot != null) {
                this.topLevelSnapshotText = topLevelSnapshot.getText();
            }
            this.name = name;
            this.range = range;
            this.bodyRange = bodyRange;
            this.isVirtual = isVirtual;
            documentFrom = snapshot.getOriginalOffset(range.getStart());
            documentTo = snapshot.getOriginalOffset(range.getEnd());
            if (bodyRange != null) {
                bodyDocFrom = snapshot.getOriginalOffset(bodyRange.getStart());
                bodyDocTo = snapshot.getOriginalOffset(bodyRange.getEnd());
            }
        }

        @Override
        public boolean isVirtual() {
            return isVirtual;
        }

        @Override
        public boolean isValidInSourceDocument() {
            return getDocumentRange() != OffsetRange.NONE;
        }

        @Override
        public synchronized int getLineOffset() {
            if (lineOffset == -1) {
                if (topLevelSnapshotText != null && isValidInSourceDocument()) {
                    try {
                        lineOffset = LexerUtils.getLineOffset(topLevelSnapshotText, getDocumentRange().getStart());
                    } catch (BadLocationException ex) {
                        //no-op
                    }
                }
            }
            return lineOffset;
        }

        @Override
        public synchronized CharSequence getText() {
            if (elementText == null) {
                //delegate to the underlying source charsequence, do not duplicate any chars!
                elementText = new CharSubSequence(snapshotText, range.getStart(), range.getEnd());
            }
            return elementText;
        }

        @Override
        public synchronized CharSequence getLineText() {
            if (elementLineText == null) {
                try {
                    int astLineStart = GsfUtilities.getRowStart(snapshotText, range.getStart());
                    int astLineEnd = GsfUtilities.getRowEnd(snapshotText, range.getStart());

                    elementLineText = astLineStart != -1 && astLineEnd != -1
                            ? snapshotText.subSequence(astLineStart, astLineEnd)
                            : null;

                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

            return elementLineText;

        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public synchronized OffsetRange getDocumentRange() {
            if (documentRange == null) {
                documentRange = documentFrom != -1 && documentTo != -1 ? new OffsetRange(documentFrom, documentTo) : OffsetRange.NONE;
            }
            return documentRange;
        }

        @Override
        public OffsetRange getRange() {
            return range;
        }

        @Override
        public OffsetRange getBodyRange() {
            return bodyRange;
        }

        @Override
        public synchronized OffsetRange getDocumentBodyRange() {
            if (documentBodyRange == null) {
                if (bodyRange != null) {
                    documentBodyRange = bodyDocFrom != -1 && bodyDocTo != -1
                            ? new OffsetRange(bodyDocFrom, bodyDocTo)
                            : OffsetRange.NONE;
                }
            }

            return documentBodyRange;
        }

        @Override
        public String toString() {
            return "Entry[" + (!isValidInSourceDocument() ? "INVALID! " : "") + getName() + "; " + getRange().getStart() + " - " + getRange().getEnd() + "]"; //NOI18N
        }

    }

}
