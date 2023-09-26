# Changelog

## 1.4.0

* Add the new loot.v2 API
  * The new loot api now uses a functional interface to add loot entries
  * The v1 api now uses the v2 api under the hood (it is still available for backwards compatibility but marked as deprecated)
  * 

## 1.3.4

* only add macros to book.json if they are not empty
* Adding a LootSourceHelper to combine multiple loot sources into value and extract that information again
* Non-breaking changes to the LootTableManager:
  * Adding the sources integer to the LootTableManager for better control where entries are injected
  * add the LootEntryInsert class to simplify the method signatures
  * marking several functions as deprecated (mostly those not using LootEntryInsert)
  * add a function to add one insert into several loot tables at once

## 1.3.3

* Add variant of registerPatchouliCategory which allows for a category ID that differs from the name (useful for translations)

## 1.3.2

* changing the type of JRecipePage from patchouli:recipe to patchouli:crafting (before was a bug)

## 1.3.1

* fixing the generated json for patchouli recipes (a call to super.toJson was missing)

## 1.3.0

* initial patchouli support in the arrp v2 package

## 1.2.2

* Add a TradeHelper to simplify creating trades (it has compatibility with numismatic overhaul)
* Add the StructureDataBuilder to arrp.v1.wordgen (this one will see large changes for v2)

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
