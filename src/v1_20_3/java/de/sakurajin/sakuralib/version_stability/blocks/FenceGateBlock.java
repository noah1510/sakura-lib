package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.WoodType;

public class FenceGateBlock extends net.minecraft.block.FenceGateBlock{
    public FenceGateBlock(Settings settings, WoodType type) {
        super(type, settings);
    }

    public FenceGateBlock(WoodType type, Settings settings) {
        super(type, settings);
    }
}
