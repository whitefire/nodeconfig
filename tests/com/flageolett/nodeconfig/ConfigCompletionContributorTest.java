package com.flageolett.nodeconfig;

import com.flageolett.nodeconfig.Utilities.Es6Case;
import com.flageolett.nodeconfig.Utilities.TypeScriptStubLibrary;
import com.intellij.codeInsight.completion.CompletionType;
import org.hamcrest.Matcher;

import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ConfigCompletionContributorTest extends Es6Case
{
    private Matcher<Iterable<String>> expectedCompletions = hasItems(
        "auth_key",
        "database",
        "database.host",
        "database.host.uri",
        "database.name",
        "database.name.value",
        "user"
    );

    public void testJsCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.js");
        myFixture.completeBasic();
        verifyCompletions();
    }

    public void testNoJsCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.js");

        TypeScriptStubLibrary.PLUGIN_ENABLED = false;
        myFixture.completeBasic();

        verifyNoCompletions();
    }

    public void testJsonCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.json");
        myFixture.completeBasic();
        verifyCompletions();
    }

    public void testNoJsonCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.json");

        TypeScriptStubLibrary.PLUGIN_ENABLED = false;
        myFixture.completeBasic();

        verifyNoCompletions();
    }

    public void testExtendedCompletion()
    {
        myFixture.configureByFiles("configCompletions.js", "config/default.js");
        myFixture.complete(CompletionType.BASIC, 2);
        verifyNoCompletions();
    }

    public void testNoQuoteCompletion()
    {
        myFixture.configureByFiles("noQuoteConfigCompletions.js", "config/default.js");
        myFixture.completeBasic();
        verifyNoCompletions();
    }

    private void verifyCompletions()
    {
        List<String> strings = myFixture.getLookupElementStrings();
        String notNullReason = "Completions should not be null.";
        assertThat(notNullReason, strings, notNullValue());

        String completionLengthReason = "7 completions should be available.";
        assertThat(completionLengthReason, strings.size(), is(7));

        String availableCompletionsReason = "Completions should be fecthed from config-files.";
        assertThat(availableCompletionsReason, strings, expectedCompletions);
    }

    private void verifyNoCompletions()
    {
        List<String> strings = myFixture.getLookupElementStrings();
        String notNullReason = "Completions should not be null.";
        assertThat(notNullReason, strings, notNullValue());

        String noCompletionReason = "Should not return completions from config-file.";
        assertThat(noCompletionReason, strings, not(expectedCompletions));
    }
}
