package de.sakurajin.sakuralib.version_stability.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;

abstract public class BrushableBlock extends net.minecraft.block.BrushableBlock{
    public BrushableBlock(
            Block baseBlock,
            AbstractBlock.Settings settings,
            SoundEvent brushingSound,
            SoundEvent brushingCompleteSound
    ) {
        super(baseBlock, brushingSound, brushingCompleteSound, settings);
    }

    public BrushableBlock(
            Block baseBlock,
            SoundEvent brushingSound,
            SoundEvent brushingCompleteSound,
            AbstractBlock.Settings settings
    ) {
        super(baseBlock, brushingSound, brushingCompleteSound, settings);
    }
}
