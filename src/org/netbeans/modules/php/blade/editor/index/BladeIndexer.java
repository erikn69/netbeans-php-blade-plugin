package org.netbeans.modules.php.blade.editor.index;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import static org.netbeans.lib.lexer.TokenList.LOG;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer;
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory;
import org.netbeans.modules.parsing.spi.indexing.Indexable;
import org.netbeans.modules.parsing.spi.indexing.support.IndexingSupport;
import org.netbeans.modules.php.blade.editor.gsf.BladeLanguage;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.parsing.spi.indexing.Context;
import org.netbeans.modules.php.editor.parser.ASTPHP5Parser;
import org.netbeans.modules.php.editor.parser.ASTPHP5Scanner;
import org.openide.filesystems.FileObject;
import java_cup.runtime.*;
import org.netbeans.modules.php.editor.parser.astnodes.ASTNode;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 *
 * @author bhaidu
 */
public class BladeIndexer extends EmbeddingIndexer {
    @Override
    protected void index(Indexable indxbl, Result result, Context context) {
        BladeParserResult r = (BladeParserResult) result;
        final FileObject fileObject = r.getSnapshot().getSource().getFileObject();
        String fileName = fileObject.getPath();
        
         try {
             String content = fileObject.asText();
            IndexingSupport  support = IndexingSupport.getInstance(context);
            ASTPHP5Scanner scanner = new ASTPHP5Scanner(new StringReader(content));
            ASTPHP5Parser parser = new ASTPHP5Parser(scanner);
            Symbol root = parser.parse();
            ASTNode node = (ASTNode)root.value;
            String debug = "";
        } catch (IOException ex) {
            LOG.log(Level.WARNING, null, ex);
//            return;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static class Factory extends EmbeddingIndexerFactory {

        public static final String NAME = "blade"; //NOI18N
        public static final int VERSION = 5;

        @Override
        public EmbeddingIndexer createIndexer(Indexable indexable, Snapshot snapshot) {
            if (isIndexable(snapshot)) {
                return new BladeIndexer();
            } else {
                return null;
            }
        }

        @Override
        public String getIndexerName() {
            return NAME;
        }

        @Override
        public int getIndexVersion() {
            return VERSION;
        }

        private boolean isIndexable(Snapshot snapshot) {
            //index all files possibly containing css
            return BladeLanguage.BLADE_MIME_TYPE.equals(snapshot.getMimeType());
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> itrbl, org.netbeans.modules.parsing.spi.indexing.Context cntxt) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> itrbl, org.netbeans.modules.parsing.spi.indexing.Context cntxt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
