package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class BladeIncludeStatement extends BladeBlock implements StructureModelItem {
    private Expression label;

    public BladeIncludeStatement(int start, int end, Expression label, Block body) {
        super(start, end, body);

        if (label == null) {
            throw new IllegalArgumentException();
        }
        this.label = label;
    }

    /**
     *
     * @return the label label
     */
    public Expression getLabel() {
        return label;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "@include " + label.toString(); //NOI18N
    }

}
