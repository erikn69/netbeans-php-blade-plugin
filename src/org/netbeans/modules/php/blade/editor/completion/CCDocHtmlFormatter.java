package org.netbeans.modules.php.blade.editor.completion;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.HtmlFormatter;
/**
 *
 * @author bhaidu
 */
public class CCDocHtmlFormatter extends HtmlFormatter {
    protected boolean isDeprecated;
    protected boolean isParameter;
    protected boolean isType;
    protected boolean isName;
    protected boolean isEmphasis;

    protected StringBuilder sb = new StringBuilder();

    public CCDocHtmlFormatter() {
    }

    @Override
    public void reset() {
        textLength = 0;
        sb.setLength(0);
    }

    @Override
    public void appendHtml(String html) {
        sb.append(html);
        // Not sure what to do about maxLength here... but presumably
    }

    @Override
    public void appendText(String text, int fromInclusive, int toExclusive) {
        for (int i = fromInclusive; i < toExclusive; i++) {
            if (textLength >= maxLength) {
                if (textLength == maxLength) {
                    sb.append("...");
                    textLength += 3;
                }
                break;
            }
            char c = text.charAt(i);

            switch (c) {
            case '<':
                sb.append("&lt;"); // NOI18N

                break;

            case '>': // Only ]]> is dangerous
                if ((i > 1) && (text.charAt(i - 2) == ']') && (text.charAt(i - 1) == ']')) {
                    sb.append("&gt;"); // NOI18N
                } else {
                    sb.append(c);
                }
                break;

            case '&':
                sb.append("&amp;"); // NOI18N

                break;

            default:
                sb.append(c);
            }

            textLength++;
        }
    }

    @Override
    public void name(ElementKind kind, boolean start) {
        assert start != isName;
        isName = start;

        if (isName) {
            sb.append("<b>");
        } else {
            sb.append("</b>");
        }
    }

    @Override
    public void parameters(boolean start) {
        assert start != isParameter;
        isParameter = start;
    }

    @Override
    public void active(boolean start) {
        emphasis(start);
    }

    @Override
    public void type(boolean start) {
        assert start != isType;
        isType = start;

        if (isType) {
            sb.append("<i>");
            sb.append("<font color=\"#404040\">");
        } else {
            sb.append("</font>");
            sb.append("</i>");
        }
    }

    @Override
    public void deprecated(boolean start) {
        assert start != isDeprecated;
        isDeprecated = start;

        if (isDeprecated) {
            sb.append("<s>");
        } else {
            sb.append("</s>");
        }
    }

    @Override
    public String getText() {
        assert !isParameter && !isDeprecated && !isName && !isType;

        return sb.toString();
    }

    @Override
    public void emphasis(boolean start) {
        assert start != isEmphasis;
        isEmphasis = start;

        if (isEmphasis) {
            sb.append("<b>");
        } else {
            sb.append("</b>");
        }
    }

}