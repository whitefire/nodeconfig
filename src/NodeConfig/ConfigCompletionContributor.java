package NodeConfig;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.lang.javascript.psi.JSExpression;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.lang.javascript.psi.ecma6.TypeScriptFunctionSignature;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class ConfigCompletionContributor extends CompletionContributor
{
    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result)
    {
        // Don't interfere!
        if (parameters.isExtendedCompletion())
        {
            return;
        }

        // Are we within quotes?
        JSLiteralExpression literalExpression = PsiTreeUtil.getParentOfType(parameters.getPosition(), JSLiteralExpression.class);

        if (literalExpression == null || !literalExpression.isQuotedLiteral())
        {
            return;
        }

        // Can we resolve that TypeScript-library reference?
        JSCallExpression callExpression = PsiTreeUtil.getParentOfType(parameters.getPosition(), JSCallExpression.class);

        if (callExpression == null)
        {
            return;
        }

        JSExpression methodExpression = callExpression.getMethodExpression();

        if (methodExpression == null)
        {
            return;
        }

        PsiReference reference = methodExpression.getReference();

        if (reference == null)
        {
            return;
        }

        TypeScriptFunctionSignature functionSignature;

        try
        {
            functionSignature = (TypeScriptFunctionSignature)reference.resolve();
        }
        catch (Exception e)
        {
            return;
        }

        if (functionSignature == null || functionSignature.getName() == null)
        {
            return;
        }

        String functionName = functionSignature.getName();

        if (!functionName.equals("get") && !functionName.equals("has"))
        {
            return;
        }

        // We are in the right spot, only display config-completions.
        result.stopHere();

        List<PsiFile> configFiles = ConfigUtilities.getConfigFiles(literalExpression.getProject(), "js");

        configFiles
            .forEach(configFile -> {
                JsPropertyWalker walker = new JsPropertyWalker();
                walker.visitFile(configFile);
                HashMap<String, String> completions = walker.getCompletions();
                completions
                    .forEach((qualifiedName, value) -> {
                        LookupElementBuilder completion = LookupElementBuilder
                            .create(qualifiedName);

                        result.addElement(completion);
                    });
            });
    }
}
