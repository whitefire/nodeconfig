package Json;

import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

class GotoPresentation implements ItemPresentation
{
    private final PresentableProperty property;

    GotoPresentation(PresentableProperty property)
    {
        this.property = property;
    }

    @Nullable
    @Override
    public String getPresentableText()
    {
        return property.getName();
    }

    @Nullable
    @Override
    public String getLocationString()
    {
        return property
            .getContainingFile()
            .getName();
    }

    @Nullable
    @Override
    public Icon getIcon(boolean unused)
    {
        return AllIcons.FileTypes.Json;
    }
}
