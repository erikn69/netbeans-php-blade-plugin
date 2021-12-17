package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class BladeExtendsStatement extends BladeBlock {

    private Block body;
    private Expression label;

    public BladeExtendsStatement(int start, int end, Expression label, Block body) {
        super(start, end, body);

        if (label == null) {
            throw new IllegalArgumentException();
        }
        this.label = label;
    }

    /**
     * Returns the label of this goto label.
     *
     * @return the label label
     */
    public Expression getLabel() {
        return this.label;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getLabel().toString(); //NOI18N
    }

}
