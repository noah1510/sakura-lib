package de.sakurajin.sakuralib.loot.v2.table_insert;

import java.util.ArrayList;

/**
 * This interface is used to provide loot table entries for the loot table manager.
 * It is used to insert entries into loot tables.
 * getInsertions is called when the loot table for the given identifier is generated.
 * This allows for deferred generation of loot entries.
 */
@FunctionalInterface
public interface LootTableEntryProvider {
    ArrayList<LootEntryInsert> getInsertions();
}
