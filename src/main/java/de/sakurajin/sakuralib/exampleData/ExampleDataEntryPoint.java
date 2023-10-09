package de.sakurajin.sakuralib.exampleData;

import com.mojang.brigadier.CommandDispatcher;
import de.sakurajin.sakuralib.SakuraLib;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;

/**
 * The entry point for the example data.
 * This basically works as the mod initializer for the example data.
 * It is called by the mod initializer of SakuraLib if the example data is enabled.
 */
public class ExampleDataEntryPoint {
    private static final Logger LOGGER = SakuraLib.DATAGEN_CONTAINER.LOGGER;
    public static void init(){
        LOGGER.info("Adding example data");

        LOGGER.info("Adding rrp dump command");
        CommandRegistrationCallback.EVENT.register(ExampleDataEntryPoint::dumpRRP);

    }

    /**
     * This function adds a command to dump the rrp data to the log.
     * @param dispatcher the command dispatcher
     * @param registryAccess the command registry access
     * @param environment the command registration environment
     */
    public static void dumpRRP(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment){
        dispatcher.register(CommandManager.literal("sakuralib_rrp_dump").executes(context -> {
            SakuraLib.DATAGEN_CONTAINER.LOGGER.info("Dumping rrp data");
            SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK.dump();
            return 1;
        }));
    }
}
