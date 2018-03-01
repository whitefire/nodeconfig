package NodeConfig.ConfigParser;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class JsonPropertyWalker extends JsonRecursiveElementVisitor
{
    private HashSet<LookupElement> completions = new HashSet<>();

    public HashSet<LookupElement> getCompletions() { return completions; }

    @Override
    public void visitProperty(@NotNull JsonProperty property)
    {
        super.visitProperty(property);

        ArrayDeque<JsonProperty> nameProperties = new ArrayDeque<>();
        nameProperties.add(property);

        JsonProperty parentProperty = PsiTreeUtil.getParentOfType(property, JsonProperty.class);

        while (parentProperty != null)
        {
            nameProperties.addFirst(parentProperty);
            parentProperty = PsiTreeUtil.getParentOfType(parentProperty, JsonProperty.class);
        }

        String qualifiedName = nameProperties
            .stream()
            .map(JsonProperty::getName)
            .collect(Collectors.joining("."));

        completions.add(LookupElementBuilder.create(qualifiedName));
    }
}
