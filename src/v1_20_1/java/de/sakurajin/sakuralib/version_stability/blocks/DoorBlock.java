package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.BlockSetType;

public class DoorBlock extends net.minecraft.block.DoorBlock{
    public DoorBlock(Settings settings, BlockSetType blockSetType){
        super(settings, blockSetType);
    }
    public DoorBlock(BlockSetType blockSetType, Settings settings){
        super(settings, blockSetType);
    }
}
