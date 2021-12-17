package org.netbeans.modules.php.blade.editor.parsing.astnodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bhaidu
 */
public class BladeProgram extends ASTNode {
    private final ArrayList<Statement> statements = new ArrayList<>();

    private BladeProgram(int start, int end, Statement[] statements) {
        super(start, end);
        this.statements.addAll(Arrays.asList(statements));
    }

    public BladeProgram(int start, int end, List<Statement> statements) {
        this(start, end, (Statement[]) statements.toArray(new Statement[statements.size()]));
    }

    /**
     * Retrieves the statement list of this program.
     *
     * @return statement parts of this program
     */
    public List<Statement> getStatements() {
        return this.statements;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sbStatements = new StringBuilder();
        for (Statement statement : getStatements()) {
            sbStatements.append(statement).append(" "); //NOI18N
        }
        return sbStatements.toString(); //NOI18N
    }
}
