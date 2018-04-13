package com.flageolett.nodeconfig.Inspections;

import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;

public class ConfigInspectionProvider implements InspectionToolProvider
{
    @NotNull
    @Override
    public Class[] getInspectionClasses()
    {
        return new Class[] {BestPractice.class};
    }
}
