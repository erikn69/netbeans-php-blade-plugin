package org.netbeans.modules.php.blade.editor.gsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.Document;
import org.netbeans.api.editor.fold.FoldTemplate;
import org.netbeans.api.editor.fold.FoldType;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.ASTNode;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeBlock;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeIfStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeProgram;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeSectionStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeVisitor;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Statement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Visitor;

/**
 *
 * it is just for demo
 * 
 * @author bhaidu
 */
public class FoldingScanner {

    public static final FoldType TYPE_CODE_BLOCKS = FoldType.CODE_BLOCK;
    private static final String LAST_CORRECT_FOLDING_PROPERTY = "LAST_CORRECT_FOLDING_PROPERY"; //NOI18N

    public static FoldingScanner create() {
        return new FoldingScanner();
    }

    private FoldingScanner() {
    }

    public Map<String, List<OffsetRange>> folds(ParserResult info) {
        final Map<String, List<OffsetRange>> folds = new HashMap<>();
        if (!(info instanceof BladeParserResult)) {
            return folds;
        }
        BladeProgram program = ((BladeParserResult) info).getProgram();

        if (program != null) {
            List<Statement> statements = program.getStatements();
            program.accept(new FoldingVisitor(folds));
            Source source = info.getSnapshot().getSource();
            assert source != null : "source was null";
            Document doc = source.getDocument(false);
            setFoldingProperty(doc, folds);
            return folds;
        }
        return folds;
    }

    private static void setFoldingProperty(Document document, Map<String, List<OffsetRange>> folds) {
        if (document != null) {
            document.putProperty(LAST_CORRECT_FOLDING_PROPERTY, folds);
        }
    }

    private class FoldingVisitor implements Visitor {

        private final Map<String, List<OffsetRange>> folds;

        public FoldingVisitor(final Map<String, List<OffsetRange>> folds) {
            this.folds = folds;
        }

        @Override
        public void visit(BladeProgram program) {
            scan(program.getStatements()); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void visit(ASTNode node) {
        }

        public void scan(ASTNode node) {
            if (node != null) {
                node.accept(this);
            }
        }

        public void scan(Iterable<? extends ASTNode> nodes) {
            if (nodes != null) {
                for (ASTNode n : nodes) {
                    if (n instanceof BladeBlock) {
                        BladeBlock nn = (BladeBlock) n;
                        visit(nn);
                    } else {
                        scan(n);
                    }
                }
            }
        }

        public void visit(BladeBlock node) {
            scan(node.getBody());
            if (node.getBody() != null) {
                addFold(node.getBody());
            }
        }

        private OffsetRange createOffsetRange(ASTNode node, int startShift) {
            return new OffsetRange(node.getStartOffset() + startShift, node.getEndOffset());
        }

        private OffsetRange createOffsetRange(ASTNode node) {
            return createOffsetRange(node, 0);
        }

        private List<OffsetRange> getRanges(Map<String, List<OffsetRange>> folds, FoldType kind) {
            List<OffsetRange> ranges = folds.get(kind.code());
            if (ranges == null) {
                ranges = new ArrayList<>();
                folds.put(kind.code(), ranges);
            }
            return ranges;
        }

        private void addFold(final ASTNode node) {
            // if (!(node instanceof ASTError) && !(node instanceof EmptyStatement)) {
            addFold(createOffsetRange(node));
            //}
        }

        private void addFold(final OffsetRange offsetRange) {
            if (offsetRange != null && offsetRange.getLength() > 1) {
                getRanges(folds, TYPE_CODE_BLOCKS).add(offsetRange);
            }
        }
    }
}
