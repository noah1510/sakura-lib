package de.sakurajin.sakuralib.exampleData;

import com.mojang.brigadier.CommandDispatcher;
import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPatchouliEntry;
import de.sakurajin.sakuralib.arrp.v2.patchouli.pages.JCraftingPage;
import de.sakurajin.sakuralib.arrp.v2.patchouli.pages.JTextPage;
import de.sakurajin.sakuralib.datagen.v2.DynamicOwOLangManager;
import de.sakurajin.sakuralib.datagen.v2.patchouli.PatchouliDataManager;
import de.sakurajin.sakuralib.util.v1.JsonObjectBuilder;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;
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

    public static void init() {
        LOGGER.info("Adding example data");

        LOGGER.info("Adding rrp dump command");
        CommandRegistrationCallback.EVENT.register(ExampleDataEntryPoint::dumpRRP);

        //add an example entry to the minecraft category and register all data
        //this uses getDynamicCategory to demonstrate how to get a dynamic category
        //if you don't keep a pointer to it somewhere.
        //It is recommended to create a static final variable for each dynamic category you create.
        //The function for that is getOrCreateDynamicCategory.
        //For this case the final variable would be PatchouliBookGeneration.MINECRAFT_CATEGORY.
        var minecraftCategory = PatchouliDataManager.getDynamicCategory("minecraft");
        if (minecraftCategory.isPresent()) {
            minecraftCategory.get().add(
                JPatchouliEntry
                    .create(
                        "most_important_item",
                        null,
                        "minecraft:netherite_hoe"
                    ).addPage(JTextPage.create("sakuralib_dynamic_book.minecraft.most_important_item.description"))
                    .addPage(JCraftingPage.create("minecraft:netherite_hoe"))
            );
            minecraftCategory.get().registerData();
        }

        //add an owo translation key for the description of the example page
        //This uses the DynamicOwOLangManager to add this rich translation key to the rrp.
        //Note that the color is ignored here since patchouli does use its own color syntax.
        DynamicOwOLangManager.addGlobalEntry(
            "sakuralib_dynamic_book.minecraft.most_important_item.description",
            SakuraJsonHelper.createArray(
                JsonObjectBuilder.create().add("text", "This is the most important item in the game").add("color", "red").build()
            )
        );

        //This adds the rich translation example from the owo lib documentation.
        //The shard part of the echo shard item is colored blue.
        DynamicOwOLangManager.addEntry(
            "en_us",
            "item.minecraft.echo_shard",
            SakuraJsonHelper.createArray(
                "Echo ",
                JsonObjectBuilder.create().add("text", "Shard").add("color", "#0096FF").build()
            )
        );
        DynamicOwOLangManager.updateRRP();
    }

    /**
     * This function adds a command to dump the rrp data to the log.
     *
     * @param dispatcher     the command dispatcher
     * @param registryAccess the command registry access
     * @param environment    the command registration environment
     */
    public static void dumpRRP(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("sakuralib_rrp_dump").executes(context -> {
            SakuraLib.DATAGEN_CONTAINER.LOGGER.info("Dumping rrp data");
            SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK.dump();
            return 1;
        }));
    }
}
