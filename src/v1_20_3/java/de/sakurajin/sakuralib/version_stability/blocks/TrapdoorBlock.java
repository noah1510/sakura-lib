package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.BlockSetType;

public class TrapdoorBlock extends net.minecraft.block.TrapdoorBlock{
    public TrapdoorBlock(Settings settings, BlockSetType blockSetType) {
        super(blockSetType, settings);
    }

    public TrapdoorBlock(BlockSetType blockSetType, Settings settings) {
        super(blockSetType, settings);
    }
}
