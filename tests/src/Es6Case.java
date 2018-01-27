package src;

import com.intellij.lang.javascript.dialects.JSLanguageLevel;
import com.intellij.lang.javascript.settings.JSRootConfiguration;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

abstract public class Es6Case extends LightCodeInsightFixtureTestCase
{
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        JSRootConfiguration
            .getInstance(myFixture.getProject())
            .storeLanguageLevelAndUpdateCaches(JSLanguageLevel.JSX);
    }

    @Override
    protected String getTestDataPath()
    {
        return "/Users/henkvinn/PhpstormProjects/node-config/testData";
    }

}
