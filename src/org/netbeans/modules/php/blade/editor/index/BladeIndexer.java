package org.netbeans.modules.php.blade.editor.index;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
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
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.parsing.spi.indexing.support.IndexDocument;
import org.netbeans.modules.php.blade.editor.index.api.BladeIndex;
import org.netbeans.modules.php.blade.editor.index.api.BladeIndexModel;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.ASTErrorExpression;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeExtendsStatement;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeProgram;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Expression;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.Statement;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 *
 * @author bhaidu
 */
public class BladeIndexer extends EmbeddingIndexer {

    private static final RequestProcessor RP = new RequestProcessor();
    public static final String BLADE_VIEW_PATH = "blade_view_path";
    public static final String BLADE_CONTENT_KEY = "bladeContent"; //NOI18N
    public static final String FIELD_INCLUDE = "include";
    public static final String FIELD_EXTENDS = "extends";
    public static final String FIELD_YIELD = "yield";
    public static final String VIRTUAL_ELEMENT_MARKER = "virtualmarker";

    private static final Map<FileObject, AtomicLong> importsHashCodes = new HashMap<>();

    //final version used after the indexing finishes (immutable
    private static Map<FileObject, AtomicLong> computedImportsHashCodes = new HashMap<>();

    @Override
    protected void index(Indexable indxbl, Result result, Context context) {
        BladeParserResult r = (BladeParserResult) result;
        final FileObject fileObject = r.getSnapshot().getSource().getFileObject();
        Project project = FileOwnerQuery.getOwner(fileObject);
        FileObject projectRoot = project.getProjectDirectory();
        String relativeFilePath = fileObject.getPath().replace(projectRoot.getPath() + "/", "");
        int firstViewFolderIndex = relativeFilePath.indexOf("views/");
        if (firstViewFolderIndex >= 0) {
            relativeFilePath = relativeFilePath.substring(firstViewFolderIndex + "views/".length());
        }

        relativeFilePath = relativeFilePath.substring(0, relativeFilePath.length() - (".blade.php".length()));
        relativeFilePath = relativeFilePath.replace("/", ".");
        
        if (r.getProgram() == null) {
            return;
        }

        try {
            IndexingSupport support = IndexingSupport.getInstance(context);
            IndexDocument document = support.createDocument(indxbl);

            FileObject root = context.getRoot();
            Set<String> entryStrings = new TreeSet<>();

            BladeProgram program = r.getProgram();

            if (!program.getStatements().isEmpty()) {
                for (Statement statement : program.getStatements()) {
                    if (statement instanceof BladeExtendsStatement) {
                        Expression label = ((BladeExtendsStatement) statement).getLabel();
                        if (label == null || label instanceof ASTErrorExpression){
                            continue;
                        }
                        entryStrings.add(label.toString());
                    }
                }

                for (String e : entryStrings) {
                    document.addPair(FIELD_EXTENDS, e, true, true);
                }

                int entriesHashCode = entryStrings.hashCode();
                synchronized (importsHashCodes) {
                    AtomicLong aggregatedHash = importsHashCodes.get(root);
                    if (aggregatedHash == null) {
                        aggregatedHash = new AtomicLong(0);
                        importsHashCodes.put(root, aggregatedHash);
                    }
                    aggregatedHash.set(aggregatedHash.get() * 79 + entriesHashCode);
                }
            }
            
            document.addPair(BLADE_CONTENT_KEY, Boolean.TRUE.toString(), true, true);
            document.addPair(BLADE_VIEW_PATH, relativeFilePath, true, true);

            Collection<BladeIndexModel> indexModels = BladeIndexModelSupport.getModels(r);
            for (BladeIndexModel indexModel : indexModels) {
                indexModel.storeToIndex(document);
            }
            //model support
            support.addDocument(document);
        } catch (IOException ex) {
            LOG.log(Level.WARNING, null, ex);
            Exceptions.printStackTrace(ex);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public static long getImportsHashCodeForRoots(Collection<FileObject> sourceRoots) {
        long hash = 5;
        for (FileObject root : sourceRoots) {
            AtomicLong rootHash = computedImportsHashCodes.get(root);
            if (rootHash != null) {
                hash = hash * 51 + rootHash.longValue();
            }
        }
        return hash; //To change body of generated methods, choose Tools | Templates.
    }

    public static class Factory extends EmbeddingIndexerFactory {

        public static final String NAME = "blade"; //NOI18N
        public static final int VERSION = 1;

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
            //index all files possibly containing blade
            return BladeLanguage.BLADE_MIME_TYPE.equals(snapshot.getMimeType());
        }

        @Override
        public boolean scanStarted(Context context) {
            synchronized (importsHashCodes) {
                importsHashCodes.remove(context.getRoot()); //remove the computed hashcode for the given indexing root
            }
            return super.scanStarted(context);
        }

        @Override
        public void scanFinished(Context context) {
            synchronized (importsHashCodes) {
                computedImportsHashCodes = new HashMap<>(importsHashCodes); //shallow copy
            }
            FileObject root = context.getRoot();
            if (root != null) {
                fireChange(root);
            }
            super.scanFinished(context);
        }

        private static void fireChange(final FileObject fo) {
            // handle events firing in separate thread:
            RP.post(() -> fireChangeImpl(fo));
        }

        static private void fireChangeImpl(FileObject fo) {
            Project p = FileOwnerQuery.getOwner(fo);
            if (p == null) {
                // no project to notify
                return;
            }
            try {
                BladeIndex index = BladeIndex.get(p);
                if (index != null) {
                    index.notifyChange();
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        @Override
        public void filesDeleted(Iterable<? extends Indexable> deleted, org.netbeans.modules.parsing.spi.indexing.Context cntxt) {
            try {
                IndexingSupport is = IndexingSupport.getInstance(cntxt);
                for (Indexable i : deleted) {
                    is.removeDocuments(i);
                }
            } catch (IOException ioe) {
                //LOGGER.log(Level.WARNING, null, ioe);
            }
        }

        @Override
        public void filesDirty(Iterable<? extends Indexable> itrbl, org.netbeans.modules.parsing.spi.indexing.Context cntxt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public static long getImportsHashCodeForRoots(Collection<FileObject> roots) {
            long hash = 5;
            for (FileObject root : roots) {
                AtomicLong rootHash = computedImportsHashCodes.get(root);
                if (rootHash != null) {
                    hash = hash * 51 + rootHash.longValue();
                }
            }
            return hash;
        }
    }
}
