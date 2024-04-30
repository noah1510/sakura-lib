# Changelog

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
