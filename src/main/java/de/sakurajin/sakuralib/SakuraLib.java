package de.sakurajin.sakuralib;

import de.sakurajin.sakuralib.loot.v2.LootTableManager;
import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.internal.SakuraLibConfig;
import de.sakurajin.sakuralib.versionTracker.v1.ModVersionTracker;
import net.fabricmc.api.ModInitializer;

public class SakuraLib implements ModInitializer {

    public static final String MOD_ID = "sakuralib";
    public static final SakuraLibConfig CONFIG = SakuraLibConfig.createAndLoad();

    public static final DatagenModContainer DATAGEN_CONTAINER = new DatagenModContainer(MOD_ID);

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        DATAGEN_CONTAINER.LOGGER.info("SakuraLib is initializing");
        DATAGEN_CONTAINER.LOGGER.info("debug mode: " + CONFIG.DEBUG());

        DATAGEN_CONTAINER.LOGGER.info("loading format and/or mod versions");
        ModVersionTracker.init();

        //init the loot table manager
        LootTableManager.init();
    }
}