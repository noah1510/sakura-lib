package de.sakurajin.sakuralib.impl.ui;

import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.util.ModVersionTracker;
import de.sakurajin.sakuralib.util.VersionHelper;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class modUpdateScreen extends BaseOwoScreen<FlowLayout> {
    private final WorldListWidget.WorldEntry worldEntry;
    private boolean allowJoining = false;
    private HashMap<String, Pair<Pair<Version, Version>, Pair<Integer, Integer>>> updateInfo = new HashMap<>();
    public modUpdateScreen(WorldListWidget.WorldEntry parent) {
        super(Text.translatable("text.sakuralib.updatetracker.title"));
        this.worldEntry = parent;
        loadData();
    }

    private void loadData(){
        var saveDataDir = FabricLoader.getInstance().getGameDir().resolve("saves").resolve(worldEntry.level.getName()).resolve("data");
        File modDataFile = saveDataDir.resolve("sakuralib.nbt").toFile();
        if(!modDataFile.exists()){
            allowJoining = true;
            return;
        }

        try{
            String nbtString = Files.readString(modDataFile.toPath());
            var nbtData = NbtHelper.fromNbtProviderString(nbtString);
            var modVersionData = ModVersionTracker.createFromNbt(nbtData);
            updateInfo = ModVersionTracker.getDifference(modVersionData);
            allowJoining = checkIfDiffAllowsJoin();
        }catch (Exception e) {
            allowJoining = true;
        }
    }

    private boolean checkIfDiffAllowsJoin(){
        for(var entry:updateInfo.entrySet()){
            int oldFormat = entry.getValue().getRight().getLeft();
            int newFormat = entry.getValue().getRight().getRight();

            if(SakuraLib.CONFIG.WARN_ON_FORMAT_VERSION_UPDATE() && newFormat > oldFormat){
                return false;
            }else if(newFormat < oldFormat){
                return false;
            }

            try {
                Version oldVersion = entry.getValue().getLeft().getLeft();
                Version newVersion = entry.getValue().getLeft().getRight();
                if (newVersion == null) {
                    return false;
                }
                if (SakuraLib.CONFIG.WARN_ON_MAJOR_VERSION_CHANGE() && VersionHelper.compareMajor(oldVersion, newVersion) != 0){
                    return false;
                }
                if (SakuraLib.CONFIG.WARN_ON_MINOR_VERSION_CHANGE() && oldVersion.compareTo(newVersion) != 0) {
                    return false;
                }
            } catch (Exception ignored) {
                return false;
            }

        }

        return true;
    }

    public void saveData(){
        if(!allowJoining){
            return;
        }

        var saveDataDir = FabricLoader.getInstance().getGameDir().resolve("saves").resolve(worldEntry.level.getName()).resolve("data");
        File modDataFile = saveDataDir.resolve("sakuralib.nbt").toFile();
        try{
            var nbtData = ModVersionTracker.INSTANCE.toNbt();
            Files.writeString(modDataFile.toPath(), NbtHelper.toNbtProviderString(nbtData));
        }catch (Exception ignored) {}
    }

    public boolean canJoin(){
        return allowJoining;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
            .surface(Surface.OPTIONS_BACKGROUND)
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout updateInfoContainer = (FlowLayout) Containers
            .verticalFlow(Sizing.fill(75), Sizing.content())
            .surface(Surface.BLANK)
            .verticalAlignment(VerticalAlignment.CENTER)
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .padding(Insets.of(5));

        updateInfoContainer
            .child(Components.label(Text.translatable("text.sakuralib.updatetracker.warning_main")))
            .child(Components.label(Text.translatable("text.sakuralib.updateTracker.updates_title")));

        for(var entry: updateInfo.entrySet()){
            String modName = entry.getKey();
            Version oldVersion = entry.getValue().getLeft().getLeft();
            Version newVersion = entry.getValue().getLeft().getRight();
            int oldFormat = entry.getValue().getRight().getLeft();
            int newFormat = entry.getValue().getRight().getRight();

            if(newVersion == null){
                updateInfoContainer.child(Components.label(Text.translatable("text.sakuralib.updateTracker.mod_removed", modName)));
            }else{
                if(oldFormat != newFormat){
                    updateInfoContainer.child(Components.label(Text.translatable("text.sakuralib.updateTracker.mod_format_version_change", modName, oldFormat, newFormat)));
                }
                int versionCompare = oldVersion.compareTo(newVersion);
                if(versionCompare > 0){
                    updateInfoContainer.child(Components.label(Text.translatable("text.sakuralib.updateTracker.mod_version_downgraded", modName, oldVersion.getFriendlyString(), newVersion.getFriendlyString())));
                }else if(versionCompare < 0) {
                    updateInfoContainer.child(Components.label(Text.translatable("text.sakuralib.updateTracker.mod_version_updated", modName, oldVersion.getFriendlyString(), newVersion.getFriendlyString())));
                }
            }
        }

        rootComponent.child(
            Containers.verticalFlow(Sizing.content(), Sizing.content())
                .child(Containers.verticalScroll(Sizing.content(10), Sizing.fill(60), updateInfoContainer))
                .padding(Insets.of(5))
                .surface(Surface.BLANK)
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.CENTER)
        );

        rootComponent.child(
            Containers.horizontalFlow(Sizing.content(), Sizing.content())
                .child(
                    Components
                        .button(
                            Text.translatable("text.sakuralib.updateTracker.button.ignore_warning"),
                            button -> {
                                assert client != null;
                                allowJoining = true;
                                saveData();
                                worldEntry.start();
                            }
                        )
                        .tooltip(Text.translatable("text.sakuralib.updateTracker.button.ignore_warning.tooltip"))
                        .margins(Insets.of(10))
                        .sizing(Sizing.fill(25), Sizing.fixed(20))
                ).child(
                    Components
                        .button(
                            Text.translatable("text.sakuralib.updateTracker.button.back_to_menu"),
                            button -> {
                                assert client != null;
                                client.setScreen(worldEntry.screen);
                            }
                        )
                        .tooltip(Text.translatable("text.sakuralib.updateTracker.button.back_to_menu.tooltip"))
                        .margins(Insets.of(10))
                        .sizing(Sizing.fill(25), Sizing.fixed(20))
                )
                .padding(Insets.of(5))
                .surface(Surface.BLANK)
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }
}
