package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.WoodType;

public class FenceGateBlock extends net.minecraft.block.FenceGateBlock{
    public FenceGateBlock(AbstractBlock.Settings settings, WoodType type) {
        super(settings, type);
    }

    public FenceGateBlock(WoodType type, AbstractBlock.Settings settings) {
        super(settings, type);
    }
}
