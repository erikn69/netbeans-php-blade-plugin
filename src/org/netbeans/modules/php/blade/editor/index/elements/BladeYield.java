package org.netbeans.modules.php.blade.editor.index.elements;

import org.netbeans.modules.parsing.spi.indexing.support.IndexDocument;
import org.netbeans.modules.php.blade.editor.index.BladeIndexer;
import org.netbeans.modules.php.editor.index.Signature;

/**
 *
 * @author bhaidu
 */
public class BladeYield {

    public BladeYield(){
        
    }
    
    private String getIndexSignature() {
        StringBuilder sb = new StringBuilder();
//        sb.append(getName().toLowerCase()).append(Signature.ITEM_DELIMITER);
//        sb.append(getName()).append(Signature.ITEM_DELIMITER);
//        sb.append(getOffset()).append(Signature.ITEM_DELIMITER);
//        sb.append(getValue() != null ? Signature.encodeItem(getValue()) : "?").append(Signature.ITEM_DELIMITER); //NOI18N
//        sb.append(isDeprecated() ? 1 : 0).append(Signature.ITEM_DELIMITER);
//        sb.append(getFilenameUrl()).append(Signature.ITEM_DELIMITER);
//        sb.append(getPhpModifiers().toFlags()).append(Signature.ITEM_DELIMITER);
        return sb.toString();
    }
    
    public void addSelfToIndex(IndexDocument indexDocument) {
        indexDocument.addPair(BladeIndexer.FIELD_YIELD, getIndexSignature(), true, true);
    }
}
