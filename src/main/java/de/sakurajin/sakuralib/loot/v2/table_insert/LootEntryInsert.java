package de.sakurajin.sakuralib.loot.v2.table_insert;

import de.sakurajin.sakuralib.loot.v2.LootSourceHelper;
import net.minecraft.loot.entry.LootPoolEntry;

/**
 * A class that represents an entry that should be inserted into a loot table.
 * It contains the entry that should be inserted, the index of the pool it should be inserted into and the loot sources
 */
public class LootEntryInsert {
    /**
     * The entry that should be inserted into a loot table.
     */
    public final LootPoolEntry entry;

    /**
     * The index of the pool the entry should be inserted into.
     */
    public final int poolIndex;

    /**
     * The loot sources that should be used for the insertion.
     *
     * @see LootSourceHelper for more information on how this integer works.
     */
    public final int lootSources;

    /**
     * Creates a new LootEntryInsert.
     *
     * @param entry       The entry that should be inserted into a loot table.
     * @param poolIndex   The index of the pool the entry should be inserted into.
     * @param lootSources The loot sources that should be used for the insertion.
     */
    public LootEntryInsert(LootPoolEntry entry, int poolIndex, int lootSources) {
        this.entry       = entry;
        this.poolIndex   = poolIndex;
        this.lootSources = lootSources;
    }

    /**
     * Creates a new LootEntryInsert with the default loot sources (all builtin sources).
     *
     * @param entry     The entry that should be inserted into a loot table.
     * @param poolIndex The index of the pool the entry should be inserted into.
     */
    public LootEntryInsert(LootPoolEntry entry, int poolIndex) {
        this(entry, poolIndex, LootSourceHelper.BUILTIN);
    }

    /**
     * Creates a new LootEntryInsert with the default loot sources (all builtin sources) and the default pool index (0).
     *
     * @param entry The entry that should be inserted into a loot table.
     */
    public LootEntryInsert(LootPoolEntry entry) {
        this(entry, 0, LootSourceHelper.BUILTIN);
    }
}
