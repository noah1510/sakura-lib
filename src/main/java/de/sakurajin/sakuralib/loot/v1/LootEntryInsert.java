package de.sakurajin.sakuralib.loot.v1;

import net.minecraft.loot.entry.LootPoolEntry;
import de.sakurajin.sakuralib.loot.v2.LootSourceHelper;

/**
 * A class that represents an entry that should be inserted into a loot table.
 * It contains the entry that should be inserted, the index of the pool it should be inserted into and the loot sources
 * @deprecated Use {@link de.sakurajin.sakuralib.loot.v2.table_insert.LootEntryInsert} instead.
 */
@Deprecated(since = "1.4.0", forRemoval = true)
public class LootEntryInsert extends de.sakurajin.sakuralib.loot.v2.table_insert.LootEntryInsert {

    /**
     * Creates a new LootEntryInsert.
     *
     * @param entry       The entry that should be inserted into a loot table.
     * @param poolIndex   The index of the pool the entry should be inserted into.
     * @param lootSources The loot sources that should be used for the insertion.
     */
    public LootEntryInsert(LootPoolEntry entry, int poolIndex, int lootSources) {
        super(entry, poolIndex, lootSources);
    }

    /**
     * Creates a new LootEntryInsert with the default loot sources (all builtin sources).
     *
     * @param entry     The entry that should be inserted into a loot table.
     * @param poolIndex The index of the pool the entry should be inserted into.
     */
    public LootEntryInsert(LootPoolEntry entry, int poolIndex) {
        super(entry, poolIndex, LootSourceHelper.BUILTIN);
    }

    /**
     * Creates a new LootEntryInsert with the default loot sources (all builtin sources) and the default pool index (0).
     *
     * @param entry The entry that should be inserted into a loot table.
     */
    public LootEntryInsert(LootPoolEntry entry) {
        super(entry, 0, LootSourceHelper.BUILTIN);
    }
}
