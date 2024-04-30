package de.sakurajin.sakuralib.compat;

import net.fabricmc.loader.api.FabricLoader;

/**
 * This class is used to load the compat modules.
 * It is called from the mod initializer and simply calls the init functions of the compat modules.
 * Its whole pupose is to make the mod initializer a bit cleaner.
 */
public class CompatLoader {
    public static void init(){
        if (FabricLoader.getInstance().isModLoaded("patchouli")){
            PatchouliCompat.init();
        }
    }
}
