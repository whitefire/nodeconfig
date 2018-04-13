package com.flageolett.nodeconfig;

import com.flageolett.nodeconfig.Utilities.Es6Case;

import java.util.Arrays;
import java.util.List;

public class ConfigCompletionContributorTest extends Es6Case
{
    public void testJsCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.js");
        verifyCompletions();
    }

    public void testJsonCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.json");
        verifyCompletions();
    }

    private void verifyCompletions()
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
