package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class BladeSectionStatement extends BladeBlock implements StructureModelItem {
    public enum Type {
        INLINE,
        BLOCK
    }
    private Expression label;
    private BladeSectionStatement.Type sectionType;

    public BladeSectionStatement(int start, int end, Expression label, Block body, BladeSectionStatement.Type type) {
        super(start, end, body);

        if (label == null) {
            throw new IllegalArgumentException();
        }
        this.label = label;
        this.sectionType = type;
    }

    /**
     *
     * @return the label label
     */
    public Expression getLabel() {
        return label;
    }
    
    public BladeSectionStatement.Type getSectionType() {
        return sectionType;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "@section " + label.toString() + " (" + getSectionType().toString() + ")"; //NOI18N
    }

}
