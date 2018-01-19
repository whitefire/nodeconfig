package Json;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.lang.ecmascript6.psi.ES6ImportDeclaration;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

public class GotoConfigProperty implements GotoDeclarationHandler
{
    @Nullable
    @Override
    public PsiElement[] getGotoDeclarationTargets(@Nullable PsiElement sourceElement, int offset, Editor editor)
    {
        ES6ImportDeclaration importDeclaration = PsiTreeUtil.getParentOfType(sourceElement, ES6ImportDeclaration.class);

        if (importDeclaration == null)
        {
            return PsiElement.EMPTY_ARRAY;
        }

        return ImportCompletionContributor
            .getAllConfigVariants(importDeclaration.getFromClause())
            .stream()
            .filter(psiNamedElement -> {
                String name = psiNamedElement.getName();
                return name != null && name.equals(sourceElement.getText());
            })
            // Sure hope this one doesn't come back to haunt me.
            .map(property -> new PresentableProperty(property.getNode()))
            .toArray(PsiElement[]::new);
    }

    @Nullable
    @Override
    public String getActionText(DataContext context)
    {
        return "Goto config property.";
    }
}
