package org.netbeans.modules.php.blade.editor.completion;

import org.netbeans.modules.csl.api.Documentation;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.util.NbBundle;

/**
 *
 * @author bhaidu
 */
@NbBundle.Messages("BladeDocNotFound=BladeDoc not found")
final class DocRenderer {

    private DocRenderer() {
    }

    static Documentation document(ParserResult info, ElementHandle element) {
        return null;
    }
}
