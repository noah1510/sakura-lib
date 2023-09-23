package de.sakurajin.sakuralib.loot.v1;

import de.sakurajin.sakuralib.SakuraLib;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to inject data into loot tables.
 * Unlike the farbic API this also allows injecting entries into loot table pools.
 * All entries will automatically be added to the MODIFY loot table even from the fabric API.
 * For more information on how to specify the loot sources see @ref LootSourceHelper.
 *
 * To inject pools into loot tables you can use the fabric API directly.
 */
public class LootTableManager {
    /**
     * This is the internal list of loot table entries that should be injected into loot tables.
     * To optimize the performance of the loot table building process all entries are stored in this complex HashMap
     * instead of a more simple list using LootEntryInsert.
     * This Data structure is pre sorted by loot table and loot pool index.
     * This removes the need to check every entry and exit early if the loot table or pool index does not match.
     * The loot sources are still checked for every entry since not every entry has the same loot sources.
     *
     * The first key is the Identifier of the loot table that should be injected into.
     * The second key is the index of the loot pool that should be injected into.
     * The value is a list of pairs with
     *                      a LootTableEntries that should be injected into the loot pool
     *                      an integer representing the loot sources that should be injected into (@ref LootSourceHelper).
     */
    static final private HashMap<Identifier, HashMap<Integer, ArrayList<Pair<LootPoolEntry, Integer>>>> lootTableEntries = new HashMap<>();

    // This is used to prevent the loot table manager from being injected multiple times.
    static boolean isInitialized = false;

    /**
     * This function is used to initialize the loot table manager.
     * It is called by the Init of sakuralib and does not need to be called form external code.
     * There is no harm in calling it multiple times since a safety check is in place, but it is useless.
     */
    static public void init(){
        if (isInitialized) return;

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, setter) -> {
            if(!lootTableEntries.containsKey(id)){
                return;
            }

            var lootTableEntryList = lootTableEntries.get(id);
            var lootPools = tableBuilder.pools;

            for(var entry: lootTableEntryList.entrySet()){
                var lootPoolIndex = entry.getKey();
                if(lootPoolIndex >= lootPools.size()){
                    SakuraLib.DATAGEN_CONTAINER.LOGGER.error("Loot table {} has {} pools, expected {}", id, lootPools.size(), lootPoolIndex + 1);
                    continue;
                }

                var lootPoolBuilder = FabricLootPoolBuilder.copyOf(lootPools.get(lootPoolIndex));
                for(var arrayEntry: entry.getValue()){
                    LootPoolEntry lootTableEntry = arrayEntry.getLeft();
                    int sources = arrayEntry.getRight();

                    if(lootTableEntry == null) continue;
                    if(!LootSourceHelper.inNumber(sources, setter)) continue;

                    lootPoolBuilder.with(lootTableEntry);
                }

                lootPools.set(lootPoolIndex, lootPoolBuilder.build());
            }
        });
        isInitialized = true;
    }

    /**
     * An overloaded version of insertEntry to simplify inserting into the pool with index 0.
     * @see LootTableManager#insertEntry(Identifier, LootPoolEntry, int)
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entry The entry that should be injected.
     * @deprecated Use insertEntries(Identifier, LootEntryInsert...) instead. Will be removed in 2.0.0
     */
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry){
        insertEntries(lootTable, new LootEntryInsert(entry));
    }

    /**
     * Insert a loot table entry into a loot table pool.
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entry The entry that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     * @deprecated Use insertEntries(Identifier, LootEntryInsert...) instead. Will be removed in 2.0.0
     */
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry, int LootPoolIndex){
        insertEntries(lootTable, new LootEntryInsert(entry, LootPoolIndex));
    }

    /**
     * An overloaded version of insertEntries to simplify inserting several entries into the same pool.
     * This function inserts all entries into the pool with the given index with the LootSources set to builtin.
     * @see LootTableManager#insertEntries(Identifier, LootPoolEntry[], int, int) for more information.
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entries A list of Entries that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     */
    static public void insertEntries(Identifier lootTable, LootPoolEntry[] entries, int LootPoolIndex){
        insertEntries(lootTable, entries, LootPoolIndex, LootSourceHelper.BUILTIN);
    }

    /**
     * An overloaded version of insertEntries to simplify inserting several entries into the same pool.
     * This function inserts all entries into the pool with the given index for the specified lootSources.
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entries A list of Entries that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     * @param lootSources The loot sources that should be injected into.
     */
    static public void insertEntries(Identifier lootTable, LootPoolEntry[] entries, int LootPoolIndex, int lootSources){
        ArrayList<LootEntryInsert> inserts = new ArrayList<>();
        for (var entry: entries){
            inserts.add(new LootEntryInsert(entry, LootPoolIndex, lootSources));
        }
        insertEntries(lootTable, inserts);
    }

    /**
     * Insert a list of loot table entries into loot table pools.
     * Each entry in the array is a HashMap with the loot pool index as key and a list of Loot table entries as value.
     * If you want to inject several entries into the same pool you can use LootTableManager#insertEntry(Identifier, LootTableEntry, int)
     * to simplify the process.
     *
     * All entries will be stored into an internal list and all injections will happen at once when the loot table is loaded.
     * This should reduce the amount of function calls during loot table building and therefore improve performance.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entries a List of Pairs containing the index of the loot pool and the entry that should be injected
     * @deprecated Use a method that needs LootEntryInsert instead. Will be removed in 2.0.0
     */
    static public void insertEntries(Identifier lootTable, HashMap<Integer, ArrayList<LootPoolEntry>> entries){
        ArrayList<LootEntryInsert> insertions = new ArrayList<>();
        for (var entry: entries.entrySet()){
            for (var lootTableEntry: entry.getValue()){
                insertions.add(new LootEntryInsert(lootTableEntry, entry.getKey()));
            }
        }
        insertEntries(lootTable, insertions);
    }

    /**
     * A variant of insertEntries(Identifier, LootEntryInsert...) that takes a list of LootEntryInserts instead of an array.
     * @see LootTableManager#insertEntries(Identifier, LootEntryInsert...) for more information.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param insertions a list of LootEntryInserts containing the index of the loot pool, the entry that should be injected and the loot sources
     */
    static public void insertEntries(Identifier lootTable, ArrayList<LootEntryInsert> insertions){
        insertEntries(lootTable, insertions.toArray(new LootEntryInsert[0]));
    }

    /**
     * Insert a list of loot table entries into loot table pools.
     * Each entry in the array is a HashMap with the loot pool index as key and a list of Loot table entries as value.
     * If you want to inject several entries into the same pool you can use LootTableManager#insertEntry(Identifier, LootTableEntry, int)
     * to simplify the process.
     *
     * All entries will be stored into an internal list and all injections will happen at once when the loot table is loaded.
     * This should reduce the amount of function calls during loot table building and therefore improve performance.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param insertions a list of LootEntryInserts containing the index of the loot pool, the entry that should be injected and the loot sources
     */
    static public void insertEntries(Identifier lootTable, LootEntryInsert... insertions){
        HashMap<Integer, ArrayList<Pair<LootPoolEntry, Integer>>> lootTableEntryList = lootTableEntries.getOrDefault(lootTable, new HashMap<>());
        for (var insertion: insertions){
            ArrayList<Pair<LootPoolEntry, Integer>> lootTableEntryArrayList = lootTableEntryList.getOrDefault(insertion.poolIndex, new ArrayList<>());
            lootTableEntryArrayList.add(new Pair<>(insertion.entry, insertion.lootSources));
            lootTableEntryList.put(insertion.poolIndex, lootTableEntryArrayList);
        }
        lootTableEntries.put(lootTable, lootTableEntryList);
    }

    /**
     * Insert a single entry into several loot tables.
     * This function is a wrapper around LootTableManager#insertEntries(Identifier, LootEntryInsert...) to simplify
     * the process for a single entry that needs to be injected into several loot tables.
     * @apiNote The pool and loot sources are the same for all loot tables and are specified in the LootEntryInsert.
     * @param insert The LootEntryInsert that should be injected.
     * @param lootTables The Identifiers of the loot tables that should be injected into.
     */
    static public void insertEntry(LootEntryInsert insert, Identifier... lootTables){
        for(var lootTable: lootTables){
            insertEntries(lootTable, insert);
        }
    }
}
