package de.sakurajin.sakuralib.mixin.minecraft;

import de.sakurajin.sakuralib.impl.ui.modUpdateScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = net.minecraft.client.gui.screen.world.WorldListWidget.WorldEntry.class, remap = false)
public abstract class WorldEntryMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final public LevelSummary level;

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    public void sakuralib$play(CallbackInfo ci){
        if(!this.level.isUnavailable()){
            modUpdateScreen sakuralib$updateScreen = new modUpdateScreen((WorldListWidget.WorldEntry)(Object)this);
            if(!sakuralib$updateScreen.canJoin()){
                this.client.setScreen(sakuralib$updateScreen);
                ci.cancel();
            }
        }
    }
}
