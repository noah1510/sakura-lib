package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;

public class TrapdoorBlock extends net.minecraft.block.TrapdoorBlock{
    public TrapdoorBlock(AbstractBlock.Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    public TrapdoorBlock(BlockSetType blockSetType, AbstractBlock.Settings settings) {
        super(settings, blockSetType);
    }
}
