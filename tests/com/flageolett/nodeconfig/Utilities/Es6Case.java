package com.flageolett.nodeconfig.Utilities;

import com.intellij.lang.javascript.dialects.JSLanguageLevel;
import com.intellij.lang.javascript.settings.JSRootConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

abstract public class Es6Case extends LightCodeInsightFixtureTestCase
{
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        Project project = myFixture.getProject();

        JSRootConfiguration
            .getInstance(project)
            .storeLanguageLevelAndUpdateCaches(JSLanguageLevel.JSX);
    }

    @Override
    protected String getTestDataPath() { return "testData"; }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor()
    {
        return new NodeConfigProjectDescriptor();
    }
}
