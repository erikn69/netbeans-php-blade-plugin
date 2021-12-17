package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class BladeBlock extends Statement {
    private Block body;
    
    public BladeBlock(int start, int end, Block body) {
        super(start, end);
        this.body = body;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
