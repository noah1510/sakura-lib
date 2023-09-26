package de.sakurajin.sakuralib.loot.v2;

import de.sakurajin.sakuralib.loot.v2.table_insert.LootEntryInsert;
import de.sakurajin.sakuralib.loot.v2.table_insert.LootTableEntryProvider;
import de.sakurajin.sakuralib.loot.v2.table_insert.LootTableInsertManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LootTableManager {
    static final private HashMap<Identifier, ArrayList<LootEntryInsert>> Insertions = new HashMap<>();

    static public void init() {
        LootTableInsertManager.init();
    }

    /**
     * Adds one or more insertions to a loot table.
     * <p>
     * This function is a wrapper for {@link LootTableInsertManager#addProvider(Identifier, LootTableEntryProvider...)}.
     * It creates a LootTableEntryProvider that returns the insertions that are passed to this function.
     *
     * @param identifier The identifier of the loot table that the insertion should be added to.
     * @param insertion  The insertions that should be added.
     * @apiNote It is recommended to use the LootTableInsertManager with custom LootTableEntryProvider functions directly.
     */
    static public void addInsertion(Identifier identifier, LootEntryInsert... insertion) {
        var insertionList = Insertions.getOrDefault(identifier, new ArrayList<>());
        if (insertionList.isEmpty()) {
            LootTableInsertManager.addProvider(identifier, () -> Insertions.get(identifier));
        }

        insertionList.addAll(Arrays.asList(insertion));
        Insertions.put(identifier, insertionList);
    }

    /**
     * Adds an insertion to multiple loot tables.
     * See {@link #addInsertion(Identifier, LootEntryInsert...)} for more information.
     *
     * @param insertion   The insertion that should be added.
     * @param Identifiers The identifiers of the loot tables that the insertion should be added to.
     */
    static public void addInsertion(LootEntryInsert insertion, Identifier... Identifiers) {
        for (var identifier : Identifiers) {
            addInsertion(identifier, insertion);
        }
    }

}
