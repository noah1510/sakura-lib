package de.sakurajin.sakuralib.arrp.v2.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.pages.JTextPage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A patchouli entry.
 * This is the main class for creating patchouli entries.
 * The format is specified in the [patchouli docs](https://vazkiimods.github.io/Patchouli/docs/reference/entry-json/).
 */
public class JPatchouliEntry {
    private final String name;
    private final String category;
    private final String icon;
    private final ArrayList<JPageBase> pages = new ArrayList<>();

    private String advancement = null;
    private String flag = null;
    private boolean priority = false;
    private boolean secret = false;
    private boolean read_by_default = false;
    private int sortnum = 0;
    private String turnin = null;
    private final HashMap<String, Integer> extra_recipe_mappings = new HashMap<>();

    private JPatchouliEntry(String name, String category, String icon) {
        this.name = name;
        this.category = category;
        this.icon = icon;
    }

    /**
     * Creates a new entry with the mandatory fields set.
     * @param name The name of the entry.
     * @param category The category of the entry.
     * @param icon The icon of the entry.
     * @return The created entry.
     */
    public static JPatchouliEntry create(String name, String category, String icon) {
        return new JPatchouliEntry(name, category, icon);
    }

    /**
     * Adds a page to the entry.
     * @apiNote The first page has to be a text page without a title. You can use {@link #addTextPage(String)} for that.
     * @param page The page to add.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry addPage(JPageBase page) {
        pages.add(page);
        return this;
    }

    /**
     * Adds a simple text page with the given text to the entry.
     * @param text The text to add.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry addTextPage(String text) {
        pages.add(JTextPage.create(text));
        return this;
    }

    /**
     * Sets the advancement required to unlock this entry.
     * For more information look into [advancements locking](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/advancement-locking/)
     * @param advancement The advancement to set.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry setAdvancement(String advancement) {
        this.advancement = advancement;
        return this;
    }

    /**
     * Sets the flag required to unlock this entry.
     * For more information look into [using config flags](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/config-gating/)
     * @param flag The flag to set.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    /**
     * Sets the entry to be prioritized.
     * This will cause it to be displayed at the top of the category.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry prioritize() {
        this.priority = true;
        return this;
    }

    /**
     * Sets the entry to be secret.
     * This will cause it to be hidden until it is unlocked.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry isSecret() {
        this.secret = true;
        return this;
    }

    /**
     * Sets the entry to be read by default.
     * This will cause this entry to be displayed as read once it is unlocked.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry isReadByDefault() {
        this.read_by_default = true;
        return this;
    }

    /**
     * Sets the sort number of this entry.
     * You can use this for custom sorting of entries.
     * Entries with a lower sort number will be displayed first.
     * Entries with the same number will be sorted alphabetically.
     * @apiNote The default sort number is 0.
     * @param sortnum The sort number to set.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry setSortnum(int sortnum) {
        this.sortnum = sortnum;
        return this;
    }

    /**
     * Sets the advancement required to turn in this entry.
     * Until entries are completed they will be displayed with a ? next to them.
     * This is useful to create a quest book like system.
     * @param turnin The advancement to set.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry setTurnin(String turnin) {
        this.turnin = turnin;
        return this;
    }

    /**
     * Additional list of items this page teaches the crafting process for,
     * for use with the in-world right click and quick lookup feature.
     * Keys are ItemStack strings, values are 0-indexed page numbers.
     * @param itemStack The item stack which has a crafting recipe page.
     * @param page The page number of the crafting recipe page.
     * @return The entry itself for method chaining.
     */
    public JPatchouliEntry addExtraRecipeMapping(String itemStack, int page) {
        this.extra_recipe_mappings.put(itemStack, page);
        return this;
    }

    /**
     * Returns the serialized json object for this entry.
     * It also checks if the entry is valid and throws an exception if it is not.
     * @apiNote Only changed and mandatory fields will be added to the json object.
     * @throws IllegalArgumentException If the entry is not valid.
     * @return The serialized json object for this entry.
     */
    public JsonObject toJson() throws IllegalArgumentException{
        //check if the entry is valid
        if(pages.isEmpty()) throw new IllegalArgumentException("An entry must have at least one page");
        if((pages.get(0) instanceof JTextPage textPage)){
            if(textPage.hasTitle()){
                throw new IllegalArgumentException("The first page of an entry must not have a title");
            }
        }else{
            throw new IllegalArgumentException("The first page of an entry must be a JTextPage");
        }

        //create the entry json object with the mandatory fields
        JsonObject entryJson = new JsonObject();
        entryJson.addProperty("name", name);
        entryJson.addProperty("category", category);
        entryJson.addProperty("icon", icon);

        // add all pages as a json array
        JsonArray pagesJson = new JsonArray();
        for (JPageBase page : pages) {
            pagesJson.add(page.toJson());
        }
        entryJson.add("pages", pagesJson);

        //add the optional fields if they are set
        if(advancement != null) entryJson.addProperty("advancement", advancement);
        if(flag != null) entryJson.addProperty("flag", flag);
        if(priority) entryJson.addProperty("priority", true);
        if(secret) entryJson.addProperty("secret", true);
        if(read_by_default) entryJson.addProperty("read_by_default", true);
        if(sortnum != 0) entryJson.addProperty("sortnum", sortnum);
        if(turnin != null) entryJson.addProperty("turnin", turnin);
        if(!extra_recipe_mappings.isEmpty()){
            JsonObject extra_recipe_mappingsJson = new JsonObject();
            for (var entry : extra_recipe_mappings.entrySet()) {
                extra_recipe_mappingsJson.addProperty(entry.getKey(), entry.getValue());
            }
            entryJson.add("extra_recipe_mappings", extra_recipe_mappingsJson);
        }

        return entryJson;
    }

    /**
     * Returns the serialized json object for this entry as a string.
     * Same as calling toJson().toString()
     * @return The serialized json object for this entry as a string.
     */
    public String toString() {
        return toJson().toString();
    }

}
