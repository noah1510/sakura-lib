package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockSetType;

public class DoorBlock extends net.minecraft.block.DoorBlock{
    public DoorBlock(AbstractBlock.Settings settings, BlockSetType blockSetType){
        super(blockSetType, settings);
    }
    public DoorBlock(BlockSetType blockSetType, AbstractBlock.Settings settings){
        super(blockSetType, settings);
    }
}
