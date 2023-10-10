package de.sakurajin.sakuralib.datagen.v2.patchouli;

import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPatchouliCategory;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPatchouliEntry;
import de.sakurajin.sakuralib.util.v1.NameIDPair;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains all data of a dynamic patchouli category.
 * This is used to add data to patchouli at runtime.
 * Usually you would not call the constructor of this class directly but use {@link PatchouliDataManager} instead.
 * You can add categories and entries to this container.
 * When you add a category you get a new container for that category.
 * You can then add entries to that category.
 * This way you can create a tree of categories and entries.
 *
 * @apiNote REMEMBER TO CALL {@link #registerData()} OF THE MAIN CATEGORY AFTER YOU ADDED ALL YOUR ENTRIES.
 */
public class DynamicPatchouliCategoryContainer {
    /**
     * This is true if this is container is a sub-category of another category.
     * This is false if this is the main category of a mod.
     */
    public final boolean isSubCategory;

    /**
     * The name of the main category.
     * This should always be a mod ID as the name and sakuralib:modid as the ID.
     */
    public final NameIDPair mainCategoryName;

    /**
     * The name of the category.
     * If this is a sub-category this is the name of the sub-category.
     * If this is the main category this is the same as {@link #mainCategoryName}.
     */
    public final NameIDPair categoryName;

    /**
     * The actual data of the category
     */
    private final JPatchouliCategory categoryData;

    /**
     * All added sub categories.
     */
    private final ArrayList<DynamicPatchouliCategoryContainer> subCategories = new ArrayList<>();

    /**
     * All added entries and their Identifiers.
     */
    private final HashMap<Identifier, JPatchouliEntry> addedEntries = new HashMap<>();

    /**
     * Construct a main category container.
     * This constructs a category container for a given mod.
     * This function should only be called by the PatchouliDataManager.
     *
     * @param modID        The mod ID of the mod this category belongs to.
     * @param categoryData The category data of the main mod category.
     */
    public DynamicPatchouliCategoryContainer(String modID, JPatchouliCategory categoryData) {
        this.mainCategoryName = new NameIDPair(modID, SakuraLib.MOD_ID);
        this.categoryData     = categoryData;
        this.categoryName     = this.mainCategoryName;
        this.isSubCategory    = false;
    }

    /**
     * Construct a sub-category container.
     *
     * @param mainCategoryName The name of the main category.
     * @param categoryName     The name of the sub-category.
     * @param subCategoryData  The category data of the sub-category.
     */
    private DynamicPatchouliCategoryContainer(
        NameIDPair mainCategoryName,
        String categoryName,
        JPatchouliCategory subCategoryData
    ) {
        this.mainCategoryName = mainCategoryName;
        this.categoryData     = subCategoryData;
        this.isSubCategory    = true;
        this.categoryName     = new NameIDPair(categoryName, SakuraLib.MOD_ID);
    }

    /**
     * Adds a sub category to this category.
     * This automatically adds this category as a sub-category of the main category.
     *
     * @param categoryData The category to add.
     * @param categoryName The name of the category.
     * @return The added category container that can be used to add entries to that category.
     */
    public DynamicPatchouliCategoryContainer addCategory(JPatchouliCategory categoryData, String categoryName) {
        categoryData.setParent(this.categoryName.IDString());
        var subContainer = new DynamicPatchouliCategoryContainer(this.categoryName, categoryName, categoryData);
        this.subCategories.add(subContainer);
        return subContainer;
    }

    /**
     * Same as {@link #addCategory(JPatchouliCategory, String)} but uses the name of the category as the ID.
     *
     * @param categoryData The category to add.
     * @return The added category container.
     */
    public DynamicPatchouliCategoryContainer addCategory(JPatchouliCategory categoryData) {
        return addCategory(categoryData, categoryData.getName());
    }

    /**
     * Generate and register a patchouli entry.
     * This will create the json and register it into the resource pack.
     *
     * @param locale The locale of the entry.
     * @param entry  The entry to register.
     */
    public void addPatchouliEntry(String locale, JPatchouliEntry entry) {
        var location       = PatchouliDataManager.getEntryPath(categoryName.name(), entry.getName());
        var correctedEntry = entry.copyWithCategory(categoryName.IDString());
        addedEntries.put(location, correctedEntry);
    }

    /**
     * Register multiple entries at once.
     * Check out {@link #addPatchouliEntry(String, JPatchouliEntry)} for more information.
     *
     * @param locale  The locale of the entry.
     * @param entries The entries to register.
     */
    public void addPatchouliEntries(String locale, JPatchouliEntry... entries) {
        for (JPatchouliEntry entry : entries) {
            addPatchouliEntry(locale, entry);
        }
    }

    /**
     * Register a patchouli entry with the en_us as locale.
     * see {@link #addPatchouliEntry(String, JPatchouliEntry)} for more information.
     *
     * @param entry The entry to register.
     */
    public void addPatchouliEntry(JPatchouliEntry entry) {
        addPatchouliEntry("en_us", entry);
    }

    /**
     * Register multiple entries at once with the en_us as locale.
     * see {@link #addPatchouliEntries(String, JPatchouliEntry...)} for more information.
     *
     * @param entries The entries to register.
     */
    public void addPatchouliEntries(JPatchouliEntry... entries) {
        addPatchouliEntries("en_us", entries);
    }

    /**
     * Registers the data of this category and all sub-categories into the sakuralib resource pack.
     *
     * @apiNote Make sure to call this function after all your entries have been added.
     * Calling this once on your main category is enough.
     */
    public void registerData() {
        registerData(SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK);
    }

    /**
     * Recursively adds all data to the resource pack.
     * This will add all sub-categories and entries to the resource pack.
     *
     * @param resourcePack The resource pack to add the data to.
     */
    protected void registerData(RuntimeResourcePack resourcePack) {
        //add all entries to the resource pack
        for (var entry : addedEntries.entrySet()) {
            resourcePack.addAsset(entry.getKey(), entry.getValue().toString().getBytes());
        }

        //add the category data to the resource pack
        resourcePack.addAsset(
            PatchouliDataManager.getCategoryPath(categoryName.name()),
            categoryData.toString().getBytes()
        );

        //recursively add all sub category data to the resource pack
        for (var category : subCategories) {
            category.registerData(resourcePack);
        }
    }

}
