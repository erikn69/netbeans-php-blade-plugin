package org.netbeans.modules.php.blade.editor.api;

import org.netbeans.modules.csl.api.ElementKind;

/**
 *
 * @author bhaidu
 */
public enum BladeElementKind {
    INDEX, PROGRAM, INCLUDE,
    SECTION, EXTEND, YIELD,
    METHOD, VARIABLE, CONSTANT, FUNCTION;
    
    public final ElementKind getElementKind() {
        ElementKind result;
        switch (this) {
            case SECTION:
            case EXTEND:
            case YIELD:
                result = ElementKind.TAG;
                break;
            case FUNCTION:
                result = ElementKind.METHOD;
                break;
            case METHOD:
                result = ElementKind.METHOD;
                break;
            case VARIABLE:
                result = ElementKind.VARIABLE;
                break;
            default:
                result = ElementKind.OTHER;
        }
        return result;
    }
}
