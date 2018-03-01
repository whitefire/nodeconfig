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

Know issues
-----------
    

Roadmap
-------    
    Improve completion-ux.
    add completion-confidence.
    Auto-enable/download typescript-library.
    goto-implementations.
    Tests.
    Missing setting-inspection.
        - Add setting quickfix.
    Add inspection encouraging people to use best-practices.
    More tests!
    Add support for find usages/rename refactoring.
    Add replacement-inspection for imported properties.
    Add support for correct syntax: `config.get('api.basePath');` 
    
    Suggestions?
