/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2016 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.blade.editor.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.php.blade.editor.parsing.astnodes.BladeProgram;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.php.editor.api.ElementQuery;
import org.netbeans.modules.php.editor.api.ElementQueryFactory;
import org.netbeans.modules.php.editor.api.QuerySupportFactory;
import org.netbeans.modules.php.editor.parser.PHPParseResult;
import org.netbeans.modules.php.editor.parser.astnodes.Comment;
import org.netbeans.modules.php.editor.parser.astnodes.Program;
import org.netbeans.modules.php.editor.parser.astnodes.Statement;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Haidu Bogdan
 */
public class BladeParserResult extends ParserResult {

    boolean valid = true;
    //private Model model;
    private final BladeProgram root;
    private List<Error> errors;
    private ElementQuery.Index phpIndexQuery;

    BladeParserResult(Snapshot snapshot) {
        super(snapshot);
        this.root = null;
    }

    public BladeParserResult(Snapshot snapshot, BladeProgram rootNode) {
        super(snapshot);
        this.root = rootNode;
        this.errors = Collections.<Error>emptyList();
    }

    public BladeProgram getProgram() {
        return root;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    protected void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public List<? extends org.netbeans.modules.csl.api.Error> getDiagnostics() {
        return errors;
    }

    public void setPhpIndexQuery(ElementQuery.Index indexQuery) {
        phpIndexQuery = indexQuery;
    }

    public ElementQuery.Index getPhpIndexQuery() {
        return phpIndexQuery;
    }
    
    public void createPhpIndexQuery(Snapshot snapshot, FileObject fileObject) {
        int end = snapshot.getText().toString().length();
        List<Statement> statements = new ArrayList<>();
        Program emptyProgram = new Program(0, end, statements, Collections.<Comment>emptyList());
        PHPParseResult result = new PHPParseResult(snapshot, emptyProgram);

        phpIndexQuery = ElementQueryFactory.createIndexQuery(QuerySupportFactory.get(result));
    }

//    public synchronized Model getModel() {
//        if (model == null) {
//            model = ModelFactory.getModel(this);
//        }
//        return model;
//    }
}
