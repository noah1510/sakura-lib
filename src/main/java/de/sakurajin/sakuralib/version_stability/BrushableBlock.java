package de.sakurajin.sakuralib.version_stability;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;

abstract public class BrushableBlock extends net.minecraft.block.BrushableBlock {
    public BrushableBlock(
            Block baseBlock,
            AbstractBlock.Settings settings,
            SoundEvent brushingSound,
            SoundEvent brushingCompleteSound
    ) {
        super(baseBlock, settings, brushingSound, brushingCompleteSound);
    }

    public BrushableBlock(
            Block baseBlock,
            SoundEvent brushingSound,
            SoundEvent brushingCompleteSound,
            AbstractBlock.Settings settings
    ) {
        this(baseBlock, settings, brushingSound, brushingCompleteSound);
    }
}
