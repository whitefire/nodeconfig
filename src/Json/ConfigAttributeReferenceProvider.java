package Json;

import com.intellij.lang.javascript.psi.JSReferenceExpression;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class ConfigAttributeReferenceProvider extends PsiReferenceContributor
{
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar)
    {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(JSReferenceExpression.class), new AttributeReferenceProvider());
    }
}

