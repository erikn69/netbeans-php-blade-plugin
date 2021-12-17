package org.netbeans.modules.php.blade.editor.parsing.astnodes;

/**
 *
 * @author bhaidu
 */
public class Scalar extends Expression {

    public enum Type {
        INT, // 'int'
        REAL, // 'real'
        STRING, // 'string'
        UNKNOWN, // unknown scalar in quote expression
        SYSTEM // system scalars (__CLASS__ / ...)

    }

    private String stringValue;
    private Type scalarType;

    public Scalar(int start, int end, String value, Scalar.Type type) {
        super(start, end);

        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.scalarType = type;
        this.stringValue = value;
    }

    /**
     * the scalar type
     * @return scalar type
     */
    public Scalar.Type getScalarType() {
        return scalarType;
    }

    /**
     * the scalar value
     * @return scalar value
     */
    public String getStringValue() {
        return this.stringValue;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getStringValue(); //NOI18N
    }

}
