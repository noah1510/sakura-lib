package de.sakurajin.sakuralib.versionTracker.v1;

import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.util.v1.MetadataHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;

import java.util.HashMap;

/**
 * @warning this class is experimental
 */
public class ModVersionTracker {
    static public final ModVersionTracker INSTANCE = new ModVersionTracker(false);
    private final HashMap<String, Pair<Integer, Version>> loadedModVersions = new HashMap<>();
    private NbtCompound oldNbtData = null;

    private ModVersionTracker(boolean fromNbt){
        if(!fromNbt){
            for (var mod : FabricLoader.getInstance().getAllMods()) {
                //get the format version and mod version and cache it
                int formatVersion = MetadataHelper.getCustomOrDefault(mod.getMetadata().getId(), "sakuralib:format_version", 0);
                Version modVersion = mod.getMetadata().getVersion();

                loadedModVersions.put(mod.getMetadata().getId(), new Pair<>(formatVersion, modVersion));

                SakuraLib.DATAGEN_CONTAINER.LOGGER.debug("mod " + mod.getMetadata().getId() + " has format version " + formatVersion);
            }
        }
    }

    public static void init(){}

    public static ModVersionTracker createFromNbt(NbtCompound tag) throws VersionParsingException {
        ModVersionTracker tracker = new ModVersionTracker(true);
        tracker.oldNbtData = tag.getCompound("versiondata");
        for (var entry : tracker.oldNbtData.getKeys()) {
            NbtCompound modNbt = tracker.oldNbtData.getCompound(entry);
            int formatVersion;
            Version modVersion;
            try{
                formatVersion = modNbt.getInt("format_version");
            }catch(Exception e){
                SakuraLib.DATAGEN_CONTAINER.LOGGER.error("error while parsing mod format version for mod " + entry, e);
                formatVersion = 0;
            }

            try{
                modVersion = Version.parse(modNbt.getString("mod_version"));
            }catch(Exception e){
                SakuraLib.DATAGEN_CONTAINER.LOGGER.error("error while parsing mod version for mod " + entry, e);
                modVersion = null;
            }

            tracker.loadedModVersions.put(entry, new Pair<>(formatVersion, modVersion));
        }

        return tracker;
    }

    public NbtCompound toNbt(){
        NbtCompound nbt = new NbtCompound();
        NbtCompound versiondata = new NbtCompound();
        for (var entry : loadedModVersions.entrySet()) {
            NbtCompound modNbt = new NbtCompound();
            modNbt.putInt("format_version", entry.getValue().getLeft());
            modNbt.putString("mod_version", entry.getValue().getRight().getFriendlyString());
            versiondata.put(entry.getKey(), modNbt);
        }
        nbt.put("versiondata", versiondata);
        return nbt;
    }

    static public HashMap<String, Pair<Pair<Version, Version>, Pair<Integer, Integer>>> getDifference(ModVersionTracker oldData, ModVersionTracker newData){
        HashMap<String, Pair<Pair<Version, Version>, Pair<Integer, Integer>>> difference = new HashMap<>();

        for(var entry:oldData.loadedModVersions.entrySet()){
            var newEntry = newData.loadedModVersions.getOrDefault(entry.getKey(), new Pair<>(0, null));
            int oldFormat = entry.getValue().getLeft();
            int newFormat = newEntry.getLeft();

            Version oldVersion = entry.getValue().getRight();
            Version newVersion = newEntry.getRight();

            if(oldFormat != newFormat || newVersion == null || oldVersion.compareTo(newVersion) != 0){
                difference.put(
                    entry.getKey(),
                    new Pair<>(
                        new Pair<>(oldVersion, newVersion),
                        new Pair<>(oldFormat, newFormat)
                    )
                );
            }
        }
        return difference;
    }

    static public HashMap<String, Pair<Pair<Version, Version>, Pair<Integer, Integer>>> getDifference(ModVersionTracker oldData){
        return getDifference(oldData, INSTANCE);
    }

    /**
     * returns the format version of the given mod or 0 if the mod is not loaded or has not specified a version.
     * @param modID the mod id
     * @return the format version or 0
     */
    static public int getFormatVersion(String modID){
        return INSTANCE.loadedModVersions.getOrDefault(modID, new Pair<>(0, null)).getLeft();
    }

    /**
     * returns the mod version of the given mod or null if the mod is not loaded.
     * If the Tracker was not initialized before, it will be initialized.
     * @param modID the mod id
     * @return the mod version or null
     */
    static public Version getModVersion(String modID){
        return INSTANCE.loadedModVersions.getOrDefault(modID, new Pair<>(0, null)).getRight();
    }

}
