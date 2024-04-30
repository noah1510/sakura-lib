# Changelog

## 1.6.2

* renamed the generate function in StructurePoolBasesGenerator to hopefully get it working properly
* changed the changelog structure
* updated the CI to use the version specific changelog files

## 1.6.1

* Removed the VANILLA_GROUP_KEY from the DatagenModContainer
* Refactored the NameIDPair (no longer a pair but a simple class)
* Added a StructurePoolBasesGenerator compatibility function (forward porting of the 1.20.0 generate function)
* Make the sakuralib patchouli book recipe a dynamic recipe (only load if patchouli is present)
* Removed more relics of the mod version api

## 1.6.0

* Removed the mod version API (wasn't really working anyways)
* Added the version_stability package, this package contains simple abstractions to keep ABI stability across minecraft versions
* Switched to multi-version project (atm 1.20.0-2 and 1.20.3-4)
