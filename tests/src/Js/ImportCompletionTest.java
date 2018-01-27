package src.Js;

import com.intellij.codeInsight.completion.CompletionType;
import src.Es6Case;

import java.util.Arrays;
import java.util.List;

public class ImportCompletionTest extends Es6Case
{
    public void testCompletion()
    {
        myFixture.configureByFiles("importCompletion.js", "config/default.js");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();
        assertNotNull(strings);

        assertTrue(strings.containsAll(Arrays.asList("firstProperty", "secondProperty", "some")));
        assertEquals(3, strings.size());
    }
    public void testSubCompletion()
    {
        myFixture.configureByFiles("subCompletion.js", "config/default.js");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();
        assertNotNull(strings);

        assertTrue(strings.contains("deeper"));
        assertTrue(strings.contains("under"));

        assertEquals(9, strings.size());
    }
}
