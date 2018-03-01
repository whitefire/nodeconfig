NodeConfig Plugin
=================

Installation
------------
    Install as you would any JetBrains-plugin.
    Make sure to add TypeScript-definitions for NodeConfig.

Usage
-----
    Enable the @types/config TypeScript library.
    Request completions within string literals when using the "get" or "has"
    methods of the config-object. If you wish the regular-completions, request
    completions an additional time to get access to them.

Supported products
------------------
    IntelliJ IDEA, PhpStorm, WebStorm, PyCharm, RubyMine, AppCode, CLion, Gogland and Rider.

Todo this version
------------    
    Tests.

Roadmap
-------    
    Improve completion-ux.
        - Requesting completions after a period yields irrelevant results.
    add completion-confidence.
        - Auto-popup that bad-boy!
    Auto-enable/download typescript-library.
    goto-implementations.
        - js seems to work for no reason.
            - json not so much.     
    Missing setting-inspection.
        - Add setting quickfix.
       
    Add support for find usages/rename refactoring.
    Add replacement-quickfix for imported properties.
       
    Suggestions?
