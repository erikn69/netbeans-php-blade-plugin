package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public interface Visitor {
    public void visit(BladeProgram program);
    public void visit(ASTNode node);
}
