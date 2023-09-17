package de.sakurajin.sakuralib.loot.v1;

import de.sakurajin.sakuralib.SakuraLib;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is used to inject data into loot tables.
 * Unlike the farbic API this also allows injecting entries into loot table pools.
 * All entries will automatically be added to the MODIFY loot table even from the fabric API.
 *
 * To inject pools into loot tables you can use the fabric API directly.
 */
public class LootTableManager {
    /**
     * This is the internal list of loot table entries that should be injected into loot tables.
     *
     * The first key is the Identifier of the loot table that should be injected into.
     * The second key is the index of the loot pool that should be injected into.
     * The value is a list of LootTableEntries that should be injected into the loot pool.
     */
    static final private HashMap<Identifier, HashMap<Integer, ArrayList<LootPoolEntry>>> lootTableEntries = new HashMap<>();

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
            if(!setter.isBuiltin() || ! lootTableEntries.containsKey(id)){
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
                for(var lootTableEntry: entry.getValue()){
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
     */
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry){
        insertEntry(lootTable, entry, 0);
    }

    /**
     * Insert a loot table entry into a loot table pool.
     * @see LootTableManager#insertEntries(Identifier, HashMap) for more information.
     *
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entry The entry that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     */
    static public void insertEntry(Identifier lootTable, LootPoolEntry entry, int LootPoolIndex){
        insertEntries(lootTable, new LootPoolEntry[]{entry}, LootPoolIndex);
    }

    /**
     * An overloaded version of insertEntries to simplify inserting several entries into the same pool.
     * This function inserts all entries into the pool with the given index.
     * @see LootTableManager#insertEntries(Identifier, HashMap) for more information.
     * @param lootTable The Identifier of the loot table that should be injected into.
     * @param entries A list of Entries that should be injected.
     * @param LootPoolIndex The index of the loot pool that should be injected into.
     */
    static public void insertEntries(Identifier lootTable, LootPoolEntry[] entries, int LootPoolIndex){
        HashMap<Integer, ArrayList<LootPoolEntry>> entryPairList = new HashMap<>();
        entryPairList.put(LootPoolIndex, new ArrayList<>(Arrays.asList(entries)));
        insertEntries(lootTable, entryPairList);
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
     * @param entries a List of Pairs containing the index of the loot pool and the entry that should be injected.
     */
    static public void insertEntries(Identifier lootTable, HashMap<Integer, ArrayList<LootPoolEntry>> entries){
        HashMap<Integer, ArrayList<LootPoolEntry>> lootTableEntryList = lootTableEntries.getOrDefault(lootTable, new HashMap<>());
        for (var entry: entries.entrySet()){
            ArrayList<LootPoolEntry> lootTableEntryArrayList = lootTableEntryList.getOrDefault(entry.getKey(), new ArrayList<>());
            lootTableEntryArrayList.addAll(entry.getValue());
            lootTableEntryList.put(entry.getKey(), lootTableEntryArrayList);
        }
        lootTableEntries.put(lootTable, lootTableEntryList);
    }
}
