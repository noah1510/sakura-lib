package de.sakurajin.sakuralib;

import de.sakurajin.sakuralib.util.DatagenModContainer;
import de.sakurajin.sakuralib.util.SakuraLibConfig;
import net.fabricmc.api.ModInitializer;

public class SakuraLib implements ModInitializer {

    public static final SakuraLibConfig CONFIG = SakuraLibConfig.createAndLoad();

    public static final DatagenModContainer DATAGEN_CONTAINER = new DatagenModContainer("sakuralib");

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        DATAGEN_CONTAINER.LOGGER.info("SakuraLib is initializing");
        DATAGEN_CONTAINER.LOGGER.info("debug mode: " + CONFIG.DEBUG());
    }
}
