package Js;

import Json.AttributeReferenceProvider;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ecmascript6.psi.ES6ImportDeclaration;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class ImportCompletionContributor extends CompletionContributor
{
    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result)
    {
        ES6ImportDeclaration declaration = PsiTreeUtil.getParentOfType(parameters.getPosition(), ES6ImportDeclaration.class);

        if (declaration == null || !AttributeReferenceProvider.isConfigImport(declaration))
        {
            return;
        }

        LinkedHashSet<CompletionResult> completionResults = result.runRemainingContributors(parameters, false);
        HashMap<String, LookupElement> uniqueResults = new HashMap<>();

        completionResults
            .stream()
            .map(CompletionResult::getLookupElement)
            .forEach(lookupElement -> uniqueResults.put(lookupElement.getLookupString(), lookupElement));

        uniqueResults.remove("default");
        result.addAllElements(uniqueResults.values());
    }
}
