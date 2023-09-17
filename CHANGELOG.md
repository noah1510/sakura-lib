# Changelog

## 1.2.1

* (bugfix) Use LootPoolEntry instead of LootTableEntry in LootTableManager

## 1.2.0

* Add a LootTableManager to allow injecting loot table entries into loot table pools
* moving the classes to other packages for a cleaner api
* Disable the warning screen by default and marking it as experimental
* Update the translations for the options screen
* No longer have remap=false in WorldEntryMixin

## 1.1.1

* making GeneratedWoodTypeBuilder.create public (bugiifx)
* updated readme with better description

## 1.1.0

* Add Ci builds and mc publish support
* Move away from deprecated WoodType API
* Create intial verison tracking (Singleplayer only for now)
  * Tracks the version of all mods that were used last time the world was opened
  * Opens a warning screen when mods have been downgraded or removed
  * Config option to also warn on major version updates or all version updates

## 1.0.0

* Initial release
* moving all datagen classes from even better archeology to here
