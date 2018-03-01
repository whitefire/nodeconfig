package NodeConfig;

import NodeConfig.ConfigParser.CompletionBuilder;
import NodeConfig.ConfigParser.ConfigUtilities;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.lang.javascript.psi.ecma6.TypeScriptFunctionSignature;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ConfigCompletionContributor extends CompletionContributor
{
    private Boolean isWithinQuotes(PsiElement element)
    {
        return Optional
            .ofNullable(PsiTreeUtil.getParentOfType(element, JSLiteralExpression.class))
            .map(JSLiteralExpression::isQuotedLiteral)
            .orElse(false);
    }

    private Boolean isCorrectMethod(PsiElement element)
    {
        String methodName = Optional
            .ofNullable(PsiTreeUtil.getParentOfType(element, JSCallExpression.class))
            .map(JSCallExpression::getMethodExpression)
            .map(PsiElement::getReference)
            .map(PsiReference::resolve)
            .map(TypeScriptFunctionSignature.class::cast)
            .map(NavigationItem::getName)
            .orElse("");

        return methodName.equals("has") || methodName.equals("get");
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result)
    {
        // User requested regular completions, don't interfere!
        if (parameters.isExtendedCompletion())
        {
            return;
        }

        PsiElement currentElement = parameters.getPosition();

        if (!isWithinQuotes(currentElement) || !isCorrectMethod(currentElement))
        {
            return;
        }

        // We are in the right spot, only display config-completions.
        result.stopHere();

        List<PsiFile> jsConfigFiles = ConfigUtilities.getConfigFiles(currentElement.getProject(), "js");
        List<PsiFile> jsonConfigFiles = ConfigUtilities.getConfigFiles(currentElement.getProject(), "json");

        Stream
            .concat(jsConfigFiles.stream(), jsonConfigFiles.stream())
            .map(CompletionBuilder::getCompletions)
            .forEach(result::addAllElements);
    }
}
