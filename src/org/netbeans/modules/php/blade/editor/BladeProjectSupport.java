package org.netbeans.modules.php.blade.editor;


import java.io.IOException;
import java.util.WeakHashMap;
import javax.swing.text.Document;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.modules.csl.api.DataLoadersBridge;
import org.netbeans.modules.php.project.PhpProject;

import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.php.blade.editor.index.api.BladeIndex;
import org.netbeans.spi.project.AuxiliaryConfiguration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 *
 * @author bhaidu
 */
@NbBundle.Messages("BladeResolver=Blade Files")
@MIMEResolver.ExtensionRegistration(
    mimeType="text/x-blade",
    position=300,
    displayName="#BladeResolver",
    extension={ "blade.php" }
)
public class BladeProjectSupport {

    private static final WeakHashMap<Project, BladeProjectSupport> INSTANCIES = new WeakHashMap<>();

    public static BladeProjectSupport findFor(Source source) {
	FileObject fo = source.getFileObject();
	if (fo == null) {
	    return null;
	} else {
	    return findFor(fo);
	}
    }

    public static BladeProjectSupport findFor(Document doc) {
	return findFor(DataLoadersBridge.getDefault().getFileObject(doc));
    }

    /**
     * the blade project support enables us to get the saved index
     * 
     * @param fo
     * @return 
     */
    public static BladeProjectSupport findFor(FileObject fo) {
	try {
	    Project p = FileOwnerQuery.getOwner(fo);
            
            //AuxiliaryConfiguration ac = ProjectUtils.getAuxiliaryConfiguration(p);
	    if (p == null) {
		return null;
	    }
            //might be an internal project
            if (!(p instanceof PhpProject)){
                FileObject parent = p.getProjectDirectory().getParent();
                p = FileOwnerQuery.getOwner(parent);
                if (p == null) {
                    return null;
                }
            }
            synchronized (INSTANCIES) {
		BladeProjectSupport instance = INSTANCIES.get(p);
		if (instance == null) {
		    instance = new BladeProjectSupport(p);
		    INSTANCIES.put(p, instance);
		}
                return instance;
	    }
	} catch (IOException ex) {
	    Exceptions.printStackTrace(ex);
	}

	return null;
    }
    private final Project project;
    private final BladeIndex index;

    public BladeProjectSupport(Project project) throws IOException {
	this.project = project;
	this.index = BladeIndex.create(project);
    }

    public BladeIndex getIndex() {
	return index;
    }

    public Project getProject() {
	return project;
    }
}