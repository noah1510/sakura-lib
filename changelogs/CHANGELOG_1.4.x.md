# Changelog

## 1.4.0

* switch to maven modrinth for arrp
* Add the new loot.v2 API
  * The new loot api now uses a functional interface to add loot entries
  * The v1 api now uses the v2 api under the hood (it is still available for backwards compatibility but marked as deprecated)
* Updates to arrp.patchouli
  * Renamed JRecipePage to JCraftingPage (JRecipePage is still available for backwards compatibility but marked as deprecated)
  * Adding JSmeltingPage
  * Adding JLinkPage (based on JTextPage so it can be the first page in an entry)
  * Adding JEmpltyPage
