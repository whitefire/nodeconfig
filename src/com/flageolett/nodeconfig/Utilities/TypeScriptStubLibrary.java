package com.flageolett.nodeconfig.Utilities;

import com.flageolett.nodeconfig.ConfigParser.ConfigUtilities;
import com.intellij.lang.javascript.library.JSLibraryManager;
import com.intellij.lang.javascript.library.download.TypeScriptAllStubsFile;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.util.download.DownloadableFileSetDescription;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import org.jetbrains.annotations.NotNull;

public class TypeScriptStubLibrary implements StartupActivity, DumbAware
{
    public static Boolean PLUGIN_ENABLED = false;

    static String getNameForLibrary(DownloadableFileSetDescription description)
    {
        return description.getVersionString() + "/" + description.getName();
    }

    @Override
    public void runActivity(@NotNull Project project)
    {
        DownloadableFileSetDescription description = TypeScriptAllStubsFile
            .INSTANCE
            .getDownloadableFileSetDescription("config");

        String libraryName = getNameForLibrary(description);
        JSLibraryManager libraryManager = JSLibraryManager.getInstance(project);
        LibraryTable libraryTable = libraryManager.getLibraryTable(ScriptingLibraryModel.LibraryLevel.GLOBAL);

        Boolean libraryIsEnabled = libraryTable.getLibraryByName(libraryName) != null;
        Boolean hasConfigFiles = ConfigUtilities
            .getConfigFiles(project)
            .size() > 0;

        PLUGIN_ENABLED = libraryIsEnabled && hasConfigFiles;
    }
}
