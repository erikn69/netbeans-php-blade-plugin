package org.netbeans.modules.php.blade.editor.completion;

import java.util.Collections;
import java.util.Set;
import javax.swing.ImageIcon;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.modules.csl.api.CompletionProposal;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.php.blade.editor.completion.BladeCompletionContextFinder.KeywordCompletionType;
import org.netbeans.modules.php.blade.editor.index.api.BladeIndex;
import org.netbeans.modules.php.blade.editor.parsing.BladeParserResult;
import org.netbeans.modules.php.editor.indent.CodeStyle;

/**
 *
 * @author bhaidu
 */
public class BladeCompletionItem implements CompletionProposal {

    //@StaticResource
    final CompletionRequest request;
    private final ElementHandle element;

    BladeCompletionItem(ElementHandle element, CompletionRequest request) {
        this.element = element;
        this.request = request;
    }

    @Override
    public int getAnchorOffset() {
        return request.anchorOffset;
    }

    @Override
    public ElementHandle getElement() {
        return element;
    }

    @Override
    public String getName() {
        return element.getName();
    }

    @Override
    public String getSortText() {
        return getName();
    }

    @Override
    public int getSortPrioOverride() {
        return 0;
    }

    @Override
    public String getLhsHtml(HtmlFormatter formatter) {
        formatter.name(getKind(), true);
        String name = getName();
        formatter.appendText(name);
        formatter.name(getKind(), false);
        return formatter.getText();
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public String getCustomInsertTemplate() {
        return null;
    }

    @Override
    public String getInsertPrefix() {
        StringBuilder template = new StringBuilder();
        //ElementHandle elem = getElement();
        template.append(getName());
        return template.toString();

    }

    @Override
    public String getRhsHtml(HtmlFormatter formatter) {
        return formatter.getText();
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.CONSTRUCTOR;
    }

    @Override
    public boolean isSmart() {
        return true;
    }

    public static class KeywordItem extends BladeCompletionItem {

        private final String name;

        public KeywordItem(String keyword, CompletionRequest request) {
            super(null, request);
            this.name = keyword;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public ElementKind getKind() {
            return ElementKind.KEYWORD;
        }
    }

    /**
     * item with directive template code completion
     */
    public static class DirectiveItem extends BladeCompletionItem {

        public DirectiveItem(ElementHandle element, CompletionRequest request) {
            super(element, request);
        }

        @Override
        public ElementKind getKind() {
            return ElementKind.TAG;
        }

        @Override
        public String getCustomInsertTemplate() {
            StringBuilder builder = new StringBuilder();
            KeywordCompletionType type = BladeCompletionHandler.BLADE_DIRECTIVES.get(getName());
            if (type == null) {
                return getName();
            }
            //CodeStyle codeStyle = CodeStyle.get(EditorRegistry.lastFocusedComponent().getDocument());
            String name;
            switch (type) {
                case SIMPLE:
                    return null;
                case WITH_ARG:
                    name = getName();
                    builder.append(name);
                    builder.append("(${cursor})"); //NOI18N
                    break;
                case WITH_ARG_AND_ENDTAG:
                    name = getName();
                    builder.append(name);
                    if (name == "@section" || name == "@extends") {
                        builder.append("(\"${cursor}\")");
                    } else {
                        builder.append("(${cursor})");
                    }
                    builder.append("\n");
                    builder.append("\n@end");
                    builder.append(name.substring(1));
                    //builder.append("(${cursor})"); //NOI18N
                    break;
                case WITH_ENDTAG:
                    name = getName();
                    builder.append(name);
                    builder.append("\n${cursor}");
                    builder.append("\n@end");
                    builder.append(name.substring(1));
                    //builder.append("(${cursor})"); //NOI18N
                    break;
            }
            return builder.toString();
        }

    }

    public static class CompletionRequest {

        public int anchorOffset;
        public String prefix;
        public BladeParserResult parserResult;
        public BladeCompletionContextFinder.CompletionContext context;
        public BladeIndex index;
    }
}
