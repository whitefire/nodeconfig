package src.Json;

import src.Es6Case;

public class ConfigCompletionTest extends Es6Case
{
    public void testJsonCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.json");
        verifyCompletions();
    }
}
