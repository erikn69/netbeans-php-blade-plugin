package org.netbeans.modules.php.blade.editor.index.api;

import org.netbeans.modules.csl.api.OffsetRange;

/**
 *
 * @author bhaidu
 */
public interface Entry {

    /**
     * quite similar to isValidInSourceDocument() but here we do not use the
     * adjusted start offset to check if can be translated to the source
     * but rather use the real node start offset.
     * In case of virtually generated class or selector the isVirtual
     * is always true since the dot or has doesn't exist in the css source code
     *
     */
    public boolean isVirtual();

    public boolean isValidInSourceDocument();

    /**
     * 
     * @return a line offset of the document start offset in the underlying document.
     * The -1 value denotes that there has been a problem getting the line.
     */
    public int getLineOffset();

    public CharSequence getText();

    public CharSequence getLineText();

    public String getName();

    public OffsetRange getDocumentRange();

    public OffsetRange getRange();

    public OffsetRange getBodyRange();

    public OffsetRange getDocumentBodyRange();

}