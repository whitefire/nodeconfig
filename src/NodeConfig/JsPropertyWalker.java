package NodeConfig;

import com.intellij.lang.javascript.psi.JSProperty;
import com.intellij.lang.javascript.psi.JSRecursiveWalkingElementVisitor;
import java.util.HashMap;

public class JsPropertyWalker extends JSRecursiveWalkingElementVisitor
{
    private HashMap<String, String> completions = new HashMap<>();

    public HashMap<String, String> getCompletions() { return completions; }

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

        completions.put(qualifiedName, "");
    }
}
