package NodeConfig.Inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.ecmascript6.psi.ES6FromClause;
import com.intellij.lang.ecmascript6.psi.ES6ImportDeclaration;
import com.intellij.lang.javascript.psi.JSRecursiveWalkingElementVisitor;
import com.intellij.openapi.util.text.StringUtil;

import java.util.*;

public class ImportInspector extends JSRecursiveWalkingElementVisitor
{
    private ProblemsHolder holder;
    private HashSet<ES6ImportDeclaration> done;

    ImportInspector(ProblemsHolder holder)
    {
        this.holder = holder;
        done = new HashSet<>();
    }

    @Override
    public void visitES6ImportDeclaration(ES6ImportDeclaration importDeclaration)
    {
        super.visitES6ImportDeclaration(importDeclaration);

        if (done.contains(importDeclaration))
        {
            return;
        }

        done.add(importDeclaration);

        String fromClauseText = Optional
            .ofNullable(importDeclaration.getFromClause())
            .map(ES6FromClause::getReferenceText)
            .map(StringUtil::stripQuotesAroundValue)
            .orElse("");

        if (!fromClauseText.equals("config"))
        {
            return;
        }

        Boolean hasImportSpecifiers = importDeclaration
            .getImportSpecifiers()
            .length > 0;

        if (!hasImportSpecifiers)
        {
            return;
        }

        // Houston we have a problem.
        holder.registerProblem(importDeclaration, "Use has/get-methods instead.");
    }
}
