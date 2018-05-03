NodeConfig Plugin
=================

Installation
------------
    Install as you would any JetBrains-plugin.
    Make sure to add TypeScript-definitions for NodeConfig.

Usage
-----
    Enable the @types/config TypeScript library when asked.
        - If this fails for some reason, make sure you have a working version of NodeJS installed.
    Request completions within string literals when using the "get" or "has" methods of the config-object.
    For regular completions, request completions an additional time to get access to them.

Supported products
------------------
    IntelliJ IDEA, PhpStorm, WebStorm, PyCharm, RubyMine, AppCode, CLion, Gogland and Rider.
       
TODO this version
-----------------
    Setup CI/build.

    Add goto-support for json-config.    
    Add reference-resolving/completion for js/json-files.
    Prefix log-message with project.
    Merge 2.0 into master.
    Add support for chained get-statements.       
         
Roadmap
-------
    Show values for leaf-values.       
    Improve completion-ux.
        - Requesting completions after a period yields irrelevant results.    
    add completion-confidence.
        - Auto-popup that bad-boy!
            - When the user types a ".", obviously.    
    goto-implementations.
        - js seems to work for no reason.
            - json not so much.     
    Missing setting-inspection.
        - Add setting quickfix.
             - Might the reference-resolving also provide the inspection?       
    Add support for find usages/rename refactoring.
    Add replacement-quickfix for imported properties.
       
    Suggestions?
