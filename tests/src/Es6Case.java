package src;

import com.intellij.lang.javascript.dialects.JSLanguageLevel;
import com.intellij.lang.javascript.settings.JSRootConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

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
    protected String getTestDataPath()
    {
        return "/Users/henkvinn/PhpstormProjects/node-config-next/testData";
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor()
    {
        return new src.ProjectDescriptor();
    }

    protected void verifyCompletions()
    {
        myFixture.completeBasic();

        List<String> strings = myFixture.getLookupElementStrings();
        assertNotNull(strings);
        assertEquals(7, strings.size());

        List<String> expectedResults = Arrays.asList(
            "auth_key",
            "database",
            "database.host",
            "database.host.uri",
            "database.name",
            "database.name.value",
            "user"
        );

        assertTrue(strings.containsAll(expectedResults));
    }
}
