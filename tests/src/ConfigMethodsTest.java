package src;

import java.util.Arrays;
import java.util.List;

public class ConfigMethodsTest extends Es6Case
{
    public void testConfigMethodsCompletion()
    {
        myFixture.configureByFiles("configMethods.js");

        myFixture.completeBasic();
        List<String> strings = myFixture.getLookupElementStrings();
        assertNotNull(strings);

        assertTrue(strings.containsAll(Arrays.asList("get", "has", "util")));
    }
}
