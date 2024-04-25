package de.sakurajin.sakuralib.mixin.minecraft.compat;

import de.sakurajin.sakuralib.version_stability.blocks.BrushableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Mixin(BrushableBlock.class)
abstract public class BrushableBlockMixin {
    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BrushableBlock;<init>(Lnet/minecraft/block/Block;Lnet/minecraft/block/AbstractBlock$Settings;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundEvent;)V"
        )
    )
    private void redirectConstructorCall(Block baseBlock, AbstractBlock.Settings settings, SoundEvent brushingSound, SoundEvent brushingCompleteSound) {
        try {
            Constructor<?> superConstructor = getClass().getSuperclass().getDeclaredConstructor(Block.class, SoundEvent.class, SoundEvent.class, AbstractBlock.Settings.class);
            superConstructor.setAccessible(true);
            superConstructor.newInstance(baseBlock, brushingSound, brushingCompleteSound, settings);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
