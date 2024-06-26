package de.sakurajin.sakuralib.loot.v2.table_insert;

import de.sakurajin.sakuralib.SakuraLib;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;

import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;


public class LootTableInsertManager {

    /**
     * This is a list of LootTableEntryProviders that are used to generate loot table entries.
     * These can be used for more dynamic loot table entries and are called when the loot table is loaded.
     * <p>
     * The first key is the id of the loot table that should be injected into.
     * The second key is a list with all entries that should be injected into the loot table.
     *
     * @see LootTableEntryProvider for more information on how to use this.
     */
    static final private HashMap<Identifier, ArrayList<LootTableEntryProvider>> LootTableEntryProviders = new HashMap<>();
    static final private HashMap<Identifier, Integer> LootPoolIndexMap = new HashMap<>();

    // This is used to prevent the loot table manager from being injected multiple times.
    static boolean isInitialized = false;

    /**
     * This function is used to initialize the loot insert manager.
     * It is called by the Init of sakuralib and does not need to be called form external code.
     * There is no harm in calling it multiple times since a safety check is in place, but it is useless.
     */
    static public void init() {
        if (isInitialized) return;
        LootTableEvents.MODIFY.register(LootTableInsertManager::LootTableEventHandler);
        isInitialized = true;
    }

    /**
     * This function is used to add one or more loot table entry provider to the list of providers.
     *
     * @param lootTableID the id of the loot table that should be injected into
     * @param providers   the providers that should be added
     * @see LootTableEntryProvider for more information on how to use this.
     */
    static public void addProvider(Identifier lootTableID, LootTableEntryProvider... providers) {
        var providerList = LootTableEntryProviders.getOrDefault(lootTableID, new ArrayList<>());
        providerList.addAll(Arrays.asList(providers));
        LootTableEntryProviders.put(lootTableID, providerList);
    }

    /**
     * This function is called when a loot table is modified.
     * It injects all entries from entry providers into the loot table.
     *
     * @param resourceManager The resource manager that is used to load the loot table.
     * @param lootManager     The loot manager that is used to load the loot table.
     * @param tableID         The Identifier of the loot table that is being loaded.
     * @param tableBuilder    The loot table builder that is used to build the loot table.
     * @param tableSource     The loot table source that specifies where this table is from.
     */
    static private void LootTableEventHandler(
            ResourceManager resourceManager,
            LootManager lootManager,
            Identifier tableID,
            LootTable.Builder tableBuilder,
            LootTableSource tableSource
    ) {
        //if nothing is registered for this loot table, skip it
        if (!LootTableEntryProviders.containsKey(tableID)) {
            return;
        }

        //print debug message to notify that the loot table is being injected into
        SakuraLib.DATAGEN_CONTAINER.LOGGER.debug("Injecting loot table entries into loot table: {}", tableID.toString());

        LootPoolIndexMap.put(tableID, 0);
        tableBuilder.modifyPools(getPoolModifier(tableID));
    }

    static private Consumer<FabricLootPoolBuilder> getPoolModifier(Identifier tableID) {
        return pool_builder -> {
            var lootTableEntryProviders = LootTableEntryProviders.get(tableID);
            if (lootTableEntryProviders == null) return;
            Integer poolIndex = LootPoolIndexMap.get(tableID);

            for (LootTableEntryProvider entryProvider : lootTableEntryProviders) {
                var injections = entryProvider.getInsertions();

                for (LootEntryInsert injection : injections) {
                    var lootPoolIndex = injection.poolIndex;

                    if (lootPoolIndex != poolIndex) continue;
                    if (injection.entry == null) continue;

                    pool_builder.with(injection.entry);
                }
            }

            LootPoolIndexMap.put(tableID, poolIndex + 1);
        };
    }

}
