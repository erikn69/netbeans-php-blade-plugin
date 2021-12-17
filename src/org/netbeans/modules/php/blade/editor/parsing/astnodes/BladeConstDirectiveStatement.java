package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class BladeConstDirectiveStatement extends Statement implements StructureModelItem {

    private Expression label;

    public BladeConstDirectiveStatement(int start, int end, Expression label) {
        super(start, end);

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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getLabel().toString(); //NOI18N
    }

}
