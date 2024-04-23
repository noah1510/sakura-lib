package de.sakurajin.sakuralib.backport;

abstract public class BrushableBlock extends net.minecraft.block.BrushableBlock {
    /**
     * Constructor for BrushableBlock
     * This is a backport of the BrushableBlock constructor from Minecraft 1.20.3
     */
    public BrushableBlock(
            net.minecraft.block.Block baseBlock,
            net.minecraft.sound.SoundEvent brushingSound,
            net.minecraft.sound.SoundEvent brushingCompleteSound,
            Settings settings
    ) {
        super(baseBlock, settings, brushingSound, brushingCompleteSound);
    }
}
