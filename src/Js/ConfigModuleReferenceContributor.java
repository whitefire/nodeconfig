package Js;

import Json.JsonConfigReference;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.lang.ecmascript6.psi.ES6FromClause;
import com.intellij.lang.ecmascript6.psi.ES6ImportDeclaration;
import com.intellij.lang.ecmascript6.psi.impl.ES6ImportPsiUtil;
import com.intellij.lang.javascript.psi.resolve.JSModuleReferenceContributor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigModuleReferenceContributor implements JSModuleReferenceContributor
{
    @NotNull
    @Override
    public PsiReference[] getCommonJSModuleReferences(@NotNull String s, @NotNull PsiElement psiElement, int i, @Nullable PsiReferenceProvider psiReferenceProvider)
    {
        ES6FromClause fromClause;

        try
        {
            fromClause = (ES6FromClause)psiElement;
        }
        catch (Exception e)
        {
            return PsiReference.EMPTY_ARRAY;
        }

        Project project = psiElement.getProject();

        List<PsiReference> references = new ArrayList<>();

        getConfigFiles(project, "js")
            .stream()
            .map(configFile -> new ConfigReference(fromClause, configFile))
            .forEach(references::add);

        getConfigFiles(project, "json")
            .stream()
            .map(configFile -> new JsonConfigReference(fromClause, configFile))
            .forEach(references::add);

        return references.toArray(PsiReference.EMPTY_ARRAY);
    }

    @NotNull
    @Override
    public PsiReference[] getAllReferences(@NotNull String s, @NotNull PsiElement psiElement, int i, @Nullable PsiReferenceProvider psiReferenceProvider)
    {
        return getCommonJSModuleReferences(s, psiElement, i, psiReferenceProvider);
    }

    @Override
    public boolean isApplicable(@NotNull PsiElement psiElement)
    {
        String referenceText;

        try
        {
            ES6ImportDeclaration declaration = (ES6ImportDeclaration)psiElement.getParent();
            referenceText = ES6ImportPsiUtil.getUnquotedFromClauseText(declaration);
        }
        catch (Exception e)
        {
            return false;
        }

        return referenceText != null && referenceText
            .trim()
            .replace(CompletionInitializationContext.DUMMY_IDENTIFIER_TRIMMED, "")
            .equals("config");
    }

    private static List<PsiFile> getConfigFiles(Project project, String extension)
    {
        VirtualFile[] sourceRoots = ProjectRootManager
            .getInstance(project)
            .getContentRoots();

        List<VirtualFile> configDirectories = Arrays
            .stream(sourceRoots)
            .map(sourceRoot -> sourceRoot.findChild("config"))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        List<VirtualFile> allConfigFiles = new ArrayList<>();

        configDirectories
            .stream()
            .map(VirtualFile::getChildren)
            .map(Arrays::stream)
            .forEach(children -> children.forEach(allConfigFiles::add));

        return allConfigFiles
            .stream()
            .map(configFile -> PsiManager.getInstance(project).findFile(configFile))
            .filter(Objects::nonNull)
            .filter(psiFile -> psiFile.getName().endsWith(extension))
            .collect(Collectors.toList());
    }
}
