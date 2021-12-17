package org.netbeans.modules.php.blade.editor.index.api;

import org.netbeans.modules.php.blade.editor.index.BladeIndexer;

/**
 *
 * @author bhaidu
 */
public enum RefactoringElementType {

    FIELD_YIELD(BladeIndexer.FIELD_YIELD),
    FIELD_INCLUDE(BladeIndexer.FIELD_INCLUDE),
    FIELD_EXTENDS(BladeIndexer.FIELD_EXTENDS),
    BLADE_VIEW_PATH(BladeIndexer.BLADE_VIEW_PATH);

    private final String indexKey;

    private RefactoringElementType(String indexKey) {
        this.indexKey = indexKey;
    }

    public String getIndexKey() {
        return indexKey;
    }

}
