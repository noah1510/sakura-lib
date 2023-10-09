package de.sakurajin.sakuralib.datagen.v2.patchouli;

import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPatchouliCategory;
import de.sakurajin.sakuralib.util.v1.NameIDPair;
import net.minecraft.util.Identifier;

import java.util.HashMap;

/**
 * This is the main class to add dynamic patchouli data.
 * Its goal is to have one book that contains a main category for each mod using this system.
 * This way dynamic 'books' can be added to patchouli without having to create a new book for each mod.
 * All your entries and categories shoul be added to the main category of your mod.
 * If or when patchouli adds support for loading books from datapacks this class will be updated to actually create a book.
 *
 * @see DynamicPatchouliCategoryContainer
 */
public class PatchouliDataManager {
    /**
     * The name and ID of the SakuraLib book.
     * This is the dynamic book which all dynamic categories are added to.
     */
    public static final NameIDPair SAKURALIB_BOOK = new NameIDPair("sakuralib_dynamic_book", SakuraLib.DATAGEN_CONTAINER);

    /**
     * Keep track of all registered dynamic categories.
     * This is used to rebuild the book when needed.
     * All data will be re-registered when /reload is called.
     */
    private static final HashMap<String, DynamicPatchouliCategoryContainer> DYNAMIC_MOD_CATEGORIES = new HashMap<>();

    /**
     * Get the path ID for the category file for the SakuraLib book.
     *
     * @param categoryName The name of the category.
     * @return The path ID for the category.
     */
    public static Identifier getCategoryPath(String categoryName) {
        return new Identifier(SakuraLib.MOD_ID, "patchouli_books/" + SAKURALIB_BOOK.name() + "/en_us/categories/" + categoryName + ".json");
    }

    /**
     * Get the path ID for an entry in the given category fot the SakuraLib book.
     *
     * @param categoryName The name of the category.
     * @param entryName    The name of the entry.
     * @return The path ID for the entry.
     */
    public static Identifier getEntryPath(String categoryName, String entryName) {
        return new Identifier(SakuraLib.MOD_ID, "patchouli_books/" + SAKURALIB_BOOK.name() + "/en_us/entries/" + categoryName + "/" + entryName + ".json");
    }

    /**
     * Get the dynamic category container for the given mod or null if there is none.
     *
     * @param modID The mod ID of the mod.
     * @return The dynamic category container for the given mod or null if there is none.
     */
    public static DynamicPatchouliCategoryContainer getDynamicCategory(String modID) {
        return DYNAMIC_MOD_CATEGORIES.get(modID);
    }

    /**
     * Get the dynamic category container for the given mod or create one if there is none.
     *
     * @param modID            The mod ID of the mod.
     * @param mainCategoryData The category data of the main mod category.
     * @return The dynamic category container for the given mod.
     */
    public static DynamicPatchouliCategoryContainer getOrCreateDynamicCategory(String modID, JPatchouliCategory mainCategoryData) {
        if (DYNAMIC_MOD_CATEGORIES.containsKey(modID)) {
            return DYNAMIC_MOD_CATEGORIES.get(modID);
        } else {
            var newCategory = new DynamicPatchouliCategoryContainer(modID, mainCategoryData);
            DYNAMIC_MOD_CATEGORIES.put(modID, newCategory);
            return newCategory;
        }
    }

    /**
     * Register all data into the sakuralib resource pack.
     * This function may be called by mods that add data if they want to make sure their data is added to the pack.
     * This function is called when the world is loaded.
     */
    public static void registerRRPData() {
        for (var category : DYNAMIC_MOD_CATEGORIES.values()) {
            category.registerData(SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK);
        }
    }

}
