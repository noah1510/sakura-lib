# Changelog

## 1.5.5

* Backport the BrushableBlock constructor from 1.20.3

## 1.5.4

* Updated to arrp 0.8.1
* Updated loom (1.5) and gradle (8.5)

## 1.5.3

* Pretty print most json stuff
* Revert arrp pack name change

## 1.5.2

* Simplified the DynamicPatchouliCategoryContainer functions
  * The old add functions are marked as deprecated
  * The new functions are simply called add (both for entries and categories)
* Adding DynamicOwOLangManager to add lang entries with rich text support to the rrp
* Adding SakuraJsonHelper to simplify some json annoyances
* Adding a JsonObjectBuilder to simplify creating Json Objects
* Adding a GenericsHelper to simplify some stuff around generics
* Updated translation files

## 1.5.1

* Remove the REGISTER_PATCHOULI_DATA_ON_RELOAD config option
* Fixing the `PatchouliDataManager` to actually work as intended
  * Adding a minecraft category by default to have a common place for all vanilla entries
  * **After you added your data to `DynamicPatchouliCategoryContainer` call `registerData` on the main container!**
  * getDynamicCategory now returns an optional

## 1.5.0

* Updates to arrp.patchouli
  * Adding JMultiblockPage
  * Deprecate JPachouliCategory#setSecret (use JPachouliCategory#isSecret instead)
* Added NameIDPair class (this isa pair of a name and its ID together with some helpful access functions)
* New Patchouli Book API:
  * Sakuralib now adds a single book
  * All mods can add categories to that book using the PatchouliDataManager
  * All categories can have sub categories and entries
  * The old API is still available but are marked as deprecated
* Added example data package (by default this data is only loaded in a dev environment can always be loaded by setting the config option)
  * Adds a command to dump the sakuralib arrp data
