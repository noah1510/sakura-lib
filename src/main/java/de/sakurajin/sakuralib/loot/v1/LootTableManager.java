package de.sakurajin.sakuralib.loot.v1;

import de.sakurajin.sakuralib.loot.v2.table_insert.LootEntryInsert;
import de.sakurajin.sakuralib.loot.v2.LootSourceHelper;

import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to inject data into loot tables.
 * Unlike the farbic API this also allows injecting entries into loot table pools.
 * All entries will automatically be added to the MODIFY loot table even from the fabric API.
 * For more information on how to specify the loot sources see @ref LootSourceHelper.
 * <p>
 * To inject pools into loot tables you can use the fabric API directly.
 * @deprecated use the v2 LootTableManager instead. Will be removed in 2.0.0
 */
@Deprecated(since = "1.4.0", forRemoval = true)
public class LootTableManager {


    /**
     * This function is used to initialize the loot table manager.
     * It is called by the Init of sakuralib and does not need to be called form external code.
     * There is no harm in calling it multiple times since a safety check is in place, but it is useless.
     */
    static public void init() {}

    /**
     * An overloaded version of insertEntry to simplify inserting into the pool with index 0.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entry     The entry that should be injected.
     * @see LootTableManager#insertEntry(Identifier, LootPoolEntry, int)
     * @deprecated Use insertEntries(Identifier, LootEntryInsert...) instead. Will be removed in 2.0.0
     */
    @Deprecated(since = "1.3.4", forRemoval = true)
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry) {
        insertEntries(lootTable, new LootEntryInsert(entry));
    }

    /**
     * Insert a loot table entry into a loot table pool.
     *
     * @param lootTable     The Identifier of the loot table that should be injected into.
     * @param entry         The entry that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     * @deprecated Use insertEntries(Identifier, LootEntryInsert...) instead. Will be removed in 2.0.0
     */
    @Deprecated(since = "1.3.4", forRemoval = true)
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry, int LootPoolIndex) {
        insertEntries(lootTable, new LootEntryInsert(entry, LootPoolIndex));
    }

    /**
     * An overloaded version of insertEntries to simplify inserting several entries into the same pool.
     * This function inserts all entries into the pool with the given index with the LootSources set to builtin.
     *
     * @param lootTable     The Identifier of the loot table that should be injected into.
     * @param entries       A list of Entries that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     * @see LootTableManager#insertEntries(Identifier, LootPoolEntry[], int, int) for more information.
     */
    static public void insertEntries(Identifier lootTable, LootPoolEntry[] entries, int LootPoolIndex) {
        insertEntries(lootTable, entries, LootPoolIndex, LootSourceHelper.BUILTIN);
    }

    /**
     * An overloaded version of insertEntries to simplify inserting several entries into the same pool.
     * This function inserts all entries into the pool with the given index for the specified lootSources.
     *
     * @param lootTable     The Identifier of the loot table that should be injected into.
     * @param entries       A list of Entries that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     * @param lootSources   The loot sources that should be injected into.
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     */
    static public void insertEntries(Identifier lootTable, LootPoolEntry[] entries, int LootPoolIndex, int lootSources) {
        ArrayList<LootEntryInsert> inserts = new ArrayList<>();
        for (var entry : entries) {
            inserts.add(new LootEntryInsert(entry, LootPoolIndex, lootSources));
        }
        insertEntries(lootTable, inserts);
    }

    /**
     * Insert a list of loot table entries into loot table pools.
     * Each entry in the array is a HashMap with the loot pool index as key and a list of Loot table entries as value.
     * If you want to inject several entries into the same pool you can use LootTableManager#insertEntry(Identifier, LootTableEntry, int)
     * to simplify the process.
     * <p>
     * All entries will be stored into an internal list and all injections will happen at once when the loot table is loaded.
     * This should reduce the amount of function calls during loot table building and therefore improve performance.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entries   a List of Pairs containing the index of the loot pool and the entry that should be injected
     * @deprecated Use a method that needs LootEntryInsert instead. Will be removed in 2.0.0
     */
    @Deprecated(since = "1.3.4", forRemoval = true)
    static public void insertEntries(Identifier lootTable, HashMap<Integer, ArrayList<LootPoolEntry>> entries) {
        ArrayList<LootEntryInsert> insertions = new ArrayList<>();
        for (var entry : entries.entrySet()) {
            for (var lootTableEntry : entry.getValue()) {
                insertions.add(new LootEntryInsert(lootTableEntry, entry.getKey()));
            }
        }
        insertEntries(lootTable, insertions);
    }

    /**
     * A variant of insertEntries(Identifier, LootEntryInsert...) that takes a list of LootEntryInserts instead of an array.
     *
     * @param lootTable  The Identifier of the loot table that should be injected into.
     * @param insertions a list of LootEntryInserts containing the index of the loot pool, the entry that should be injected and the loot sources
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     */
    static public void insertEntries(Identifier lootTable, ArrayList<LootEntryInsert> insertions) {
        insertEntries(lootTable, insertions.toArray(new LootEntryInsert[0]));
    }

    /**
     * Insert a list of loot table entries into loot table pools.
     * Each entry in the array is a HashMap with the loot pool index as key and a list of Loot table entries as value.
     * If you want to inject several entries into the same pool you can use LootTableManager#insertEntry(Identifier, LootTableEntry, int)
     * to simplify the process.
     * <p>
     * All entries will be stored into an internal list and all injections will happen at once when the loot table is loaded.
     * This should reduce the amount of function calls during loot table building and therefore improve performance.
     *
     * @param lootTable  The Identifier of the loot table that should be injected into.
     * @param insertions a list of LootEntryInserts containing the index of the loot pool, the entry that should be injected and the loot sources
     */
    static public void insertEntries(Identifier lootTable, LootEntryInsert... insertions) {
        de.sakurajin.sakuralib.loot.v2.LootTableManager.addInsertion(lootTable, insertions);
    }

    /**
     * Insert a single entry into several loot tables.
     * This function is a wrapper around LootTableManager#insertEntries(Identifier, LootEntryInsert...) to simplify
     * the process for a single entry that needs to be injected into several loot tables.
     *
     * @param insert     The LootEntryInsert that should be injected.
     * @param lootTables The Identifiers of the loot tables that should be injected into.
     * @apiNote The pool and loot sources are the same for all loot tables and are specified in the LootEntryInsert.
     */
    static public void insertEntry(LootEntryInsert insert, Identifier... lootTables) {
        for (var lootTable : lootTables) {
            insertEntries(lootTable, insert);
        }
    }
}
