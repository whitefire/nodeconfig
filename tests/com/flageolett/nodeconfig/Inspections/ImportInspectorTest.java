package com.flageolett.nodeconfig.Inspections;

import com.flageolett.nodeconfig.Utilities.Es6Case;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;

import java.util.List;

public class ImportInspectorTest extends Es6Case
{
    public void testBestPractices()
    {
        myFixture.configureByFile("bestPractices.js");
        myFixture.enableInspections(new BestPractice());

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();
        assertEquals(highlightInfos.size(), 2);

        HighlightInfo bestPracticeHighlight = highlightInfos.get(0);
        assertEquals(bestPracticeHighlight.getDescription(), ImportInspector.PROBLEM_DESCRIPTION);
    }
}
