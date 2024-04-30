# Changelog

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
