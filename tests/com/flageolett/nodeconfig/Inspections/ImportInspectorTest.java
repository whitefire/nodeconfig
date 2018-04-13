package com.flageolett.nodeconfig.Inspections;

import com.flageolett.nodeconfig.Utilities.Es6Case;
import com.flageolett.nodeconfig.Utilities.TypeScriptStubLibrary;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

public class ImportInspectorTest extends Es6Case
{
    public void testBestPractices()
    {
        myFixture.configureByFile("bestPractices.js");
        myFixture.enableInspections(new BestPractice());

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
        String highLightLengthReason = "There should be two highlights available.";
        assertThat(highLightLengthReason, highlightInfos.size(), is(2));

        HighlightInfo bestPracticeHighlight = highlightInfos.get(0);
        String bestPracticeReason = "The first highlight should be the best-practive inspection.";
        assertThat(bestPracticeReason, bestPracticeHighlight.getDescription(), is(ImportInspector.PROBLEM_DESCRIPTION));
    }

    public void testNoInspection()
    {
        TypeScriptStubLibrary.PLUGIN_ENABLED = false;

        myFixture.configureByFile("bestPractices.js");
        myFixture.enableInspections(new BestPractice());

        verifyNoInspection();
    }

    public void testDifferentModuleImport()
    {
        myFixture.configureByFile("bestPracticesNotConfigModule.js");
        myFixture.enableInspections(new BestPractice());
        verifyNoInspection();
    }

    public void testNoImports()
    {
        myFixture.configureByFile("bestPracticesNoImports.js");
        myFixture.enableInspections(new BestPractice());
        verifyNoInspection();
    }

    private void verifyNoInspection()
    {
        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
        String highLightLengthReason = "There should only be one highlight available.";
        assertThat(highLightLengthReason, highlightInfos.size(), is(1));

        HighlightInfo highlightInfo = highlightInfos.get(0);
        String noBestPracticeReason = "The best-practive inspection should not be triggered.";
        assertThat(noBestPracticeReason, highlightInfo.getDescription(), is(not(ImportInspector.PROBLEM_DESCRIPTION)));
    }
}
