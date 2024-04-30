package de.sakurajin.sakuralib.compat;

import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.datagen.v2.DynamicOwOLangManager;
import de.sakurajin.sakuralib.util.v1.JsonObjectBuilder;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;
import net.minecraft.util.Identifier;

public class PatchouliCompat {
    final static String CRAFTING_JSON = "{" +
        "\"type\": \"patchouli:shapeless_book_recipe\"," +
        "\"ingredients\": [" +
            "{\"item\": \"minecraft:book\"}," +
            "{\"item\": \"minecraft:pink_petals\"}" +
        "]," +
        "\"book\": \"sakuralib:sakuralib_dynamic_book\"" +
    "}";

    public static void init(){

        SakuraLib.DATAGEN_CONTAINER.GROUP.initialize();

        //create the json for the patchouli book recepie
        SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK.addData(
            new Identifier("sakuralib", "recipes/sakuralib_dynamic_book_shapeless.json"),
            CRAFTING_JSON.getBytes()
        );

        SakuraLib.DATAGEN_CONTAINER.LOGGER.info("PatchouliCompat initialized");
    }
}
