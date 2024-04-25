package de.sakurajin.sakuralib;

import de.sakurajin.sakuralib.compat.CompatLoader;
import de.sakurajin.sakuralib.datagen.v2.patchouli.PatchouliDataManager;
import de.sakurajin.sakuralib.exampleData.ExampleDataEntryPoint;
import de.sakurajin.sakuralib.loot.v2.LootTableManager;
import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.internal.SakuraLibConfig;
import net.devtech.arrp.api.RRPCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SakuraLib implements ModInitializer {

    public static final String MOD_ID = "sakuralib";
    public static final SakuraLibConfig CONFIG = SakuraLibConfig.createAndLoad();
    public static final DatagenModContainer DATAGEN_CONTAINER = new DatagenModContainer(MOD_ID, RRPCallback.BETWEEN_MODS_AND_USER);

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        DATAGEN_CONTAINER.LOGGER.info("SakuraLib Common is initializing");
        DATAGEN_CONTAINER.LOGGER.info("debug mode: " + CONFIG.DEBUG());

        //init the loot table manager
        LootTableManager.init();
        CompatLoader.init();

        //just register the stuff during launch to make sure it is registered
        PatchouliDataManager.registerRRPData();

        //load the example data if enabled or if we are in a dev environment
        if(CONFIG.ALWAYS_ADD_EXAMPLE_DATA() || FabricLoader.getInstance().isDevelopmentEnvironment()){
            ExampleDataEntryPoint.init();
        }
    }
}
