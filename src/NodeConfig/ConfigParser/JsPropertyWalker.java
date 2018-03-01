package NodeConfig.ConfigParser;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.javascript.psi.JSProperty;
import com.intellij.lang.javascript.psi.JSRecursiveWalkingElementVisitor;
import java.util.HashSet;

public class JsPropertyWalker extends JSRecursiveWalkingElementVisitor
{
    private HashSet<LookupElement> completions = new HashSet<>();

    public HashSet<LookupElement> getCompletions() { return completions; }

    @Override
    public void visitJSProperty(JSProperty node)
    {
        super.visitJSProperty(node);

        if (node.getNamespace() == null)
        {
            return;
        }

        String qualifiedName = node.getQualifiedName();

        if (qualifiedName == null)
        {
            return;
        }

        qualifiedName = qualifiedName.replace("module.exports.", "");

        if (qualifiedName.length() == 0)
        {
            return;
        }

        completions.add(LookupElementBuilder.create(qualifiedName));
    }
}
