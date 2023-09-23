package de.sakurajin.sakuralib.loot.v1;

import net.minecraft.loot.entry.LootPoolEntry;

public class LootEntryInsert {
    public final LootPoolEntry entry;
    public final int poolIndex;
    public final int lootSources;

    public LootEntryInsert(LootPoolEntry entry, int poolIndex, int lootSources) {
        this.entry = entry;
        this.poolIndex = poolIndex;
        this.lootSources = lootSources;
    }

    public LootEntryInsert(LootPoolEntry entry, int poolIndex) {
        this(entry, poolIndex, LootSourceHelper.BUILTIN);
    }

    public LootEntryInsert(LootPoolEntry entry) {
        this(entry, 0, LootSourceHelper.BUILTIN);
    }
}
