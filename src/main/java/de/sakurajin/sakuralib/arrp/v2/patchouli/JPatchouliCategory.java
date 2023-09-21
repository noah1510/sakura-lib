package de.sakurajin.sakuralib.arrp.v2.patchouli;

import com.google.gson.JsonObject;

/**
 * A category for a patchouli book
 * For details about the files check out the [patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/category-json/)
 */
public class JPatchouliCategory {
    private final String name;
    private final String description;
    private final String icon;
    private String parent = null;
    private String flag = null;
    private int sortnum = 0;
    private boolean secret = false;

    private JPatchouliCategory(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    /**
     * Creates a new category with the mandatory fields set.
     * The name can be a translation key and the description can be a translation key or a text.
     * For information about the formatting options of the description check out the
     * [formatting 101](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/text-formatting/)
     * The icon can either be an ItemStack string or resource location pointing to a square image (the suffix is needed).
     * @param name the name of the category
     * @param description the description of the category
     * @param icon the icon of the category
     * @return a new category
     */
    public static JPatchouliCategory create(String name, String description, String icon) {
        return new JPatchouliCategory(name, description, icon);
    }

    /**
     * Set this if you want this category to be a sub-category of another category.
     * @param parent The name of the parent category.
     * @return The category itself for method chaining.
     */
    public JPatchouliCategory setParent(String parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Sets the flag for this category.
     * For more information look into [using config flags](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/config-gating/)
     * @param flag A config flag to determine if this page should exist.
     * @return The page itself for method chaining.
     */
    public JPatchouliCategory setFlag(String flag) {
        this.flag = flag;
        return this;
    }
    /**
     * Sets the sort number of this category.
     * You can use this for custom sorting of categories.
     * categories with a lower sort number will be displayed first.
     * categories with the same number will be sorted alphabetically.
     * @apiNote The default sort number is 0.
     * @param sortnum The sort number to set.
     * @return The entry itself for method chaining.
     */
    public JPatchouliCategory setSortnum(int sortnum) {
        this.sortnum = sortnum;
        return this;
    }

    /**
     * Sets the category to be secret.
     * This will cause it to be hidden until it is unlocked.
     * @return The entry itself for method chaining.
     */
    public JPatchouliCategory setSecret(boolean secret) {
        this.secret = secret;
        return this;
    }

    /**
     * Converts this category to a JsonObject.
     * @apiNote Fields that are not set will not be included in the JsonObject.
     * @return The JsonObject representing this category.
     */
    public JsonObject toJson(){
        JsonObject obj = new JsonObject();
        obj.addProperty("name", name);
        obj.addProperty("description", description);
        obj.addProperty("icon", icon);
        if (parent != null) obj.addProperty("parent", parent);
        if (flag != null) obj.addProperty("flag", flag);
        if (sortnum != 0) obj.addProperty("sortnum", sortnum);
        if (secret) obj.addProperty("secret", true);
        return obj;
    }

    /**
     * Converts this category to a string.
     * Same as calling toJson().toString()
     * @return The string representing this category.
     */
    public String toString() {
        return toJson().toString();
    }
}
