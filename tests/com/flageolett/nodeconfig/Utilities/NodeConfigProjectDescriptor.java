package com.flageolett.nodeconfig.Utilities;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.javascript.nodejs.NodeCommandLineUtil;
import com.intellij.javascript.nodejs.interpreter.local.NodeJsLocalInterpreter;
import com.intellij.javascript.nodejs.interpreter.local.NodeJsLocalInterpreterManager;
import com.intellij.lang.javascript.library.JSLibraryManager;
import com.intellij.lang.javascript.library.JSLibraryMappings;
import com.intellij.lang.javascript.library.download.TypeScriptAllStubsFile;
import com.intellij.lang.typescript.library.download.TypeScriptDefinitionFilesDirectory;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.download.DownloadableFileSetDescription;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;

class NodeConfigProjectDescriptor extends LightProjectDescriptor
{
    @Override
    public void setUpProject(@NotNull Project project, @NotNull SetupHandler handler) throws Exception
    {
        super.setUpProject(project, handler);

        String globalTypesTargetPath = TypeScriptDefinitionFilesDirectory.getGlobalTypesTopDirectory();
        File globalTypesTargetDirectory = new File(globalTypesTargetPath);

        File nodeModulesDir = new File(globalTypesTargetDirectory, "node_modules");

        DownloadableFileSetDescription description = TypeScriptAllStubsFile.INSTANCE.getDownloadableFileSetDescription("config");

        if (nodeModulesDir.exists())
        {
            FileUtil.delete(nodeModulesDir);
        }

        FileUtil.createDirectory(nodeModulesDir);

        NodeJsLocalInterpreter interpreter = NodeJsLocalInterpreterManager
            .getInstance()
            .detectMostRelevant();

        if (interpreter == null)
        {
            return;
        }

        List<String> args = ContainerUtil.newArrayList(NodeCommandLineUtil.getInstallPackageCommand(interpreter), getNameForLibrary(description));
        Collections.addAll(args, "--ignore-scripts".split(" +"));

        try
        {
            GeneralCommandLine commandLine = NodeCommandLineUtil.createNpmCommandLine(nodeModulesDir, interpreter, args);
            CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);
            processHandler.runProcess();

        }
        catch (ExecutionException ignored) {}

        LocalFileSystem.getInstance().refresh(true);
        updateLibraries(project, description);
    }

    private void updateLibraries(Project project, DownloadableFileSetDescription description)
    {
        ApplicationManager
            .getApplication()
            .runWriteAction(() -> {
                String libraryName = getNameForLibrary(description);
                JSLibraryManager libraryManager = JSLibraryManager.getInstance(project);
                libraryManager.createLibrary(libraryName, VirtualFile.EMPTY_ARRAY, VirtualFile.EMPTY_ARRAY, ArrayUtil.EMPTY_STRING_ARRAY, ScriptingLibraryModel.LibraryLevel.GLOBAL, true);
                libraryManager.commitChanges();
                JSLibraryMappings mappings = JSLibraryMappings.getInstance(project);
                mappings.associate(null, libraryName, false);
            });
    }

    private String getNameForLibrary(DownloadableFileSetDescription description)
    {
        return description.getVersionString() + "/" + description.getName();
    }
}

