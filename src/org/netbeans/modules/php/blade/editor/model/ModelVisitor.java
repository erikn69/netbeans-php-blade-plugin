package org.netbeans.modules.php.blade.editor.model;

import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.*;

/**
 *
 * @author bhaidu
 */
public class ModelVisitor {

    private final BladeParserResult info;

    public ModelVisitor(final BladeParserResult info) {
        this.info = info;
    }

    public void scan(ASTNode node) {
        if (node != null) {
            node.accept((Visitor) this);
        }
    }

    public void scan(Iterable<? extends ASTNode> nodes) {
        if (nodes != null) {
            for (ASTNode n : nodes) {
                scan(n);
            }
        }
    }

    public void visit(BladeProgram program) {
        scan(program.getStatements());
    }

}
