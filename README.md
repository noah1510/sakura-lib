# Sakura Lib

While this is mainly a library mod, it also contains some features that are useful for players.

## Player focused features

It contains a mod version tracker for singleplayer worlds.
This adds a warning if the mod version was downgraded or the mod removed.
There is a config to also warn about updates.

## Developer focused features

This is a library mod for more convenient data generation in minecraft mods.
It provides a layer on top of owo-lib and ARRP to unify them.
This in turn allows for fully automatic data generation and registration.

In addition to that it also added a field in the mod json to specify the mods format version.
This is only used for the mod version tracker for now but will allow the use of datafixing in the future.
If no format version is specified it will be set to 0.

You can add the following in your `fabric.mod.json` to set the format version of your mod:

```json
"custom": {
  "sakuralib": {
    "format_version": 1
  }
}
```

More detailed documentation will be added later.
