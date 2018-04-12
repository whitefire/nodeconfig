package src.Js;

import src.Es6Case;

public class ConfigCompletionTest extends Es6Case
{
    public void testJsCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.js");
        verifyCompletions();
    }
}
