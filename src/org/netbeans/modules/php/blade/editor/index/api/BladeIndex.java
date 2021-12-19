package org.netbeans.modules.php.blade.editor.index.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Collection;
import org.netbeans.api.project.Project;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.php.blade.editor.gsf.BladeLanguage;
import org.netbeans.modules.php.blade.editor.index.BladeIndexModelSupport;
import org.netbeans.modules.php.blade.editor.index.BladeIndexer;
import org.netbeans.modules.parsing.spi.indexing.support.IndexResult;
import org.netbeans.modules.parsing.spi.indexing.support.QuerySupport;
import org.netbeans.modules.web.common.api.DependenciesGraph;
import org.netbeans.modules.web.common.api.DependenciesGraph.Node;
import org.netbeans.modules.web.common.api.FileReference;
import org.netbeans.modules.web.common.api.WebUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.ChangeSupport;
import org.openide.util.Exceptions;

/**
 *
 * @author bhaidu
 */
public class BladeIndex {

    private static final Logger LOGGER = Logger.getLogger(BladeIndex.class.getSimpleName());

    private static final String BLADE_EXT = "blade.php"; //NOI18N

    private static final Map<Project, BladeIndex> INDEXES = new WeakHashMap<>();
    private static final String VIRTUAL_ELEMENT_MARKER_STR = BladeIndexer.VIRTUAL_ELEMENT_MARKER;

     /**
     * Creates a new instance of {@link BladeIndex}.
     *
     * @param project The project for which you want to get the index for.
     * @return non null instance of the {@link BladeIndex}
     * @throws IOException
     */
    public static BladeIndex create(Project project) throws IOException {
        return new BladeIndex(project);
    }

    /**
     * Gets an instance of {@link BladeIndex}. The instance may be cached.
     *
     * @since 1.34
     * @param project The project for which you want to get the index for.
     * @return non null instance of the {@link BladeIndex}
     * @throws IOException
     */
    public static BladeIndex get(Project project) throws IOException {
        if(project == null) {
            throw new NullPointerException();
        }
        synchronized (INDEXES) {
            BladeIndex index = INDEXES.get(project);
            if(index == null) {
                index = create(project);
                INDEXES.put(project, index);
            }
            return index;
        }
    }

    private final QuerySupport querySupport;
    private final Collection<FileObject> sourceRoots;
    private final ChangeSupport changeSupport;

    private AllDependenciesMaps allDepsCache;
    private long allDepsCache_hashCode;

    /** Creates a new instance of JsfIndex */
    private BladeIndex(Project project) throws IOException {
        //QuerySupport now refreshes the roots indexes so it can held until the source roots are valid
        sourceRoots = QuerySupport.findRoots(project,
                null /* all source roots */,
                Collections.<String>emptyList(),
                Collections.<String>emptyList());
        this.querySupport = QuerySupport.forRoots(BladeIndexer.Factory.NAME, BladeIndexer.Factory.VERSION, sourceRoots.toArray(new FileObject[]{}));        
        this.changeSupport = new ChangeSupport(this);
    }

    /**
     * Adds a {@link ChangeListener} so one may listen on changes in the index.
     *
     * @param changeListener
     *
     * @since 1.34
     */
    public void addChangeListener(ChangeListener changeListener) {
        changeSupport.addChangeListener(changeListener);
    }

     /**
     * Removes the {@link ChangeListener}.
     *
     * @param changeListener
     *
     * @since 1.34
     */
    public void removeChangeListener(ChangeListener changeListener) {
        changeSupport.removeChangeListener(changeListener);
    }

    // TODO: should not be in the API; for now it is OK; need to talk to Marek
    // whether this approach to notification of changes makes any sense or should
    // be done completely differently
    public void notifyChange() {
        changeSupport.fireChange();
    }

    /**
     * Creates an instance of {@link BladeIndexModel} for the given file and factory type.
     *
     * @param <T> the type of requested {@link BladeIndexModel}
     * @param factoryClass class of the {@link BladeIndexModelFactory}
     * @param file the file you want to get the model for
     * @return instance of the model or null if the model cann't be build upon the requested file index data.
     * @throws IOException
     */
    public <T extends BladeIndexModel> T getIndexModel(Class factoryClass, FileObject file) throws IOException {
        if(file == null) {
            throw new NullPointerException("The file argument cannot be null!");
        }
        BladeIndexModelFactory<T> factory = BladeIndexModelSupport.getFactory(factoryClass);
        if(factory == null) {
            throw new IllegalArgumentException(String.format("No %s class registered as a system service!", factoryClass.getName()));
        }
        final Collection<String> fieldsToLoad = factory.getIndexKeys();
        final Collection<? extends IndexResult> results = querySupport.getQueryFactory().file(file).execute(
            fieldsToLoad.toArray(new String[fieldsToLoad.size()]));
        if (!results.isEmpty()) {
            return factory.loadFromIndex(results.iterator().next());
        }
        return null;
    }

    /**
     * Creates a map of file to {@link BladeIndexModel}.
     *
     * @see #getIndexModel(java.lang.Class, org.openide.filesystems.FileObject) 
     *
     * @param <T> the type of requested {@link BladeIndexModel}
     * @param factoryClass class of the {@link BladeIndexModelFactory}
     * @return instance of the model or null if the model cann't be build upon the requested file index data.
     * @throws IOException
     */
    public <T extends BladeIndexModel> Map<FileObject, T> getIndexModels(Class<BladeIndexModelFactory<T>> factoryClass) throws IOException {
        BladeIndexModelFactory<T> factory = BladeIndexModelSupport.getFactory(factoryClass);
        if(factory == null) {
            throw new IllegalArgumentException(String.format("No %s class registered as a system service!", factoryClass.getName()));
        }

        Collection<? extends IndexResult> results =
                    querySupport.query(BladeIndexer.BLADE_CONTENT_KEY, "", QuerySupport.Kind.PREFIX, factory.getIndexKeys().toArray(new String[0]));

        Map<FileObject, T> file2model = new HashMap<>();
        for(IndexResult result : results) {
            file2model.put(result.getFile(), factory.loadFromIndex(result));
        }
        return file2model;
    }

    /**
     * Returns a collection of file containing declaration of extends
     *
     *
     * @param id name of the @extend path
     * @return
     */
    public Collection<FileObject> findExtendsDeclarations(String extend) {
        return find(RefactoringElementType.FIELD_EXTENDS, extend, false);
    }

    /**
     *
     * @return map of all extends declarations
     */
    public Map<FileObject, Collection<String>> findAllExtendsDeclarations() {
        return findAll(RefactoringElementType.FIELD_EXTENDS, false);
    }

    /**
     * get all views path
     * 
     * @return 
     */
    public Map<FileObject, Collection<String>> findAllBladeViewPaths() {
        return findAll(RefactoringElementType.BLADE_VIEW_PATH, false);
    }

    /**
     *
     * @param colorCode
     * @return collection of files defining exactly the given element
     */
    public Collection<FileObject> findExtends(String extend) {
        return find(RefactoringElementType.FIELD_EXTENDS, extend);
    }
    
    /**
     *
     * @param prefix
     * @return map of fileobject to collection of ids defined in the file starting with prefix
     */
    public Map<FileObject, Collection<String>> findExtendsByPrefix(String prefix) {
        return findByPrefix(RefactoringElementType.FIELD_EXTENDS, prefix);
    }

     public Map<FileObject, Collection<String>> findByPrefix(RefactoringElementType type, String prefix) {
        String keyName = type.getIndexKey();
        Map<FileObject, Collection<String>> map = new HashMap<>();
        try {
            Collection<? extends IndexResult> results = querySupport.query(keyName, prefix, QuerySupport.Kind.PREFIX, keyName);
            for (IndexResult result : results) {
                String[] elements = result.getValues(keyName);
                for(String e : elements) {
                    String val = e;
                    if(val.startsWith(prefix)) {
                        if(val.endsWith(VIRTUAL_ELEMENT_MARKER_STR)) {
                            //strip the marker
                            val = val.substring(0, val.length() - 1);
                        }
                        FileObject file = result.getFile();
                        if(file != null) {
                            map.computeIfAbsent(file, f -> new LinkedList<>())
                                .add(val);
                        } // else file deleted and index not updated yet
                    }
                }

            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return map;
    }

    public Map<FileObject, Collection<String>> findAll(RefactoringElementType type) {
        return findAll(type, true);
    }

    private Map<FileObject, Collection<String>> findAll(RefactoringElementType type, boolean includeVirtualElements) {
        String keyName = type.getIndexKey();
        Map<FileObject, Collection<String>> map = new HashMap<>();
        try {
            Collection<? extends IndexResult> results =
                    querySupport.query(keyName, "", QuerySupport.Kind.PREFIX, keyName);

            for (IndexResult result : filterDeletedFiles(results)) {
                String[] elements = result.getValues(keyName);
                for (String e : elements) {
                    String val;
                    if(e.endsWith(VIRTUAL_ELEMENT_MARKER_STR)) {
                        if(includeVirtualElements) {
                            //strip the marker
                            val = e.substring(0, e.length() - 1);
                        } else {
                            continue; //ignore
                        }
                    } else {
                        val = e;
                    }
                    map.computeIfAbsent(result.getFile(), f -> new LinkedList<>())
                        .add(val);
                }

            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return map;
    }

    /**
     *
     * @param type
     * @param value
     * @return returns a collection of files which contains elements with the
     * given name and with the given type
     */
    public Collection<FileObject> find(RefactoringElementType type, String value) {
        return find(type, value, true);
    }

    private Collection<FileObject> find(RefactoringElementType type, String value, boolean includeVirtualElements) {
        String keyName = type.getIndexKey();
        try {
            StringBuilder searchExpression = new StringBuilder();
            searchExpression.append(encodeValueForRegexp(value));
            if(includeVirtualElements) {
                searchExpression.append(BladeIndexer.VIRTUAL_ELEMENT_MARKER);
                searchExpression.append('?'); //!?
            }

            Collection<FileObject> matchedFiles = new LinkedList<>();
            Collection<? extends IndexResult> results = querySupport.query(keyName, searchExpression.toString(), QuerySupport.Kind.REGEXP, keyName);
            for (IndexResult result : filterDeletedFiles(results)) {
                matchedFiles.add(result.getFile());
            }
            return matchedFiles;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return Collections.emptyList();
    }

    /**
     * Get all blade files from the project.
     *
     * @return
     */
    public Collection<FileObject> getAllIndexedFiles() {
        try {
            Collection<? extends IndexResult> results = querySupport.query(BladeIndexer.BLADE_CONTENT_KEY, "", QuerySupport.Kind.PREFIX, BladeIndexer.BLADE_CONTENT_KEY);
            Collection<FileObject> bladeFiles = new LinkedList<>();
            for(IndexResult result : filterDeletedFiles(results)) {
                if(result.getFile().getMIMEType().equals(BladeLanguage.BLADE_MIME_TYPE)) {
                    bladeFiles.add(result.getFile());
                }
            }
            return bladeFiles;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return Collections.emptyList();
        }
    }

    //if an indexed file is delete and IndexerFactory.filesDeleted() hasn't removed
    //the entris from index yet, then we may receive IndexResult-s with null file.
    //Please note that the IndexResult.getFile() result is cached, so the IndexResult.getFile()
    //won't become null after the query is run, but the file will simply become invalid.
    private Collection<? extends IndexResult> filterDeletedFiles(Collection<? extends IndexResult> queryResult) {
        Collection<IndexResult> filtered = new ArrayList<>();
        for(IndexResult result : queryResult) {
            if(result.getFile() != null) {
                filtered.add(result);
            }
        }
        return filtered;
    }

    private static final String REGEXP_CHARS_TO_ENCODE = ".\\?*+&:{}[]()^$";
    static String encodeValueForRegexp(String value) {
        StringBuilder encoded = new StringBuilder();

        out: for(int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            for(int j = 0; j < REGEXP_CHARS_TO_ENCODE.length(); j++) {
                if(c == REGEXP_CHARS_TO_ENCODE.charAt(j)) {
                    encoded.append('\\');
                    encoded.append(c);
                    continue out;
                }
            }
            encoded.append(c);
        }
        return encoded.toString();
    }

    public static class AllDependenciesMaps {

        @SuppressWarnings("PackageVisibleField")
        Map<FileObject, Collection<FileReference>> source2dest, dest2source;

        public AllDependenciesMaps(Map<FileObject, Collection<FileReference>> source2dest, Map<FileObject, Collection<FileReference>> dest2source) {
            this.source2dest = source2dest;
            this.dest2source = dest2source;
        }

        /**
         *
         * @return reversed map of getSource2dest() (imported file -> collection of
         * importing files)
         */
        @SuppressWarnings("ReturnOfCollectionOrArrayField")
        public Map<FileObject, Collection<FileReference>> getDest2source() {
            return dest2source;
        }

        /**
         *
         * @return map of fileobject -> collection of fileobject(s) describing
         * relations between css file defined by import directive. The key represents
         * a fileobject which imports the files from the value's collection.
         */
        @SuppressWarnings("ReturnOfCollectionOrArrayField")
        public Map<FileObject, Collection<FileReference>> getSource2dest() {
            return source2dest;
        }

    }

}
