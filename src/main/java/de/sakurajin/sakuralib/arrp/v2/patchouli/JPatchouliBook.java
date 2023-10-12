package de.sakurajin.sakuralib.arrp.v2.patchouli;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;

import java.util.HashMap;

/**
 * A patchouli book.
 * This class represents the book.json file of a patchouli book.
 * For details about the files check out the [patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json)
 * @apiNote The default of i18n is true, while the default of patchouli is false. You can disable this with {@link #dontUseI18n()}.
 */
public class JPatchouliBook {
    private final String name;
    private final String landing_text;

    private String book_texture = null;
    private String filler_texture = null;
    private String crafting_texture = null;
    private String model = null;
    private String text_color = null;
    private String header_color = null;
    private String nameplate_color = null;
    private String link_color = null;
    private String link_hover_color = null;
    private String progress_bar_color = null;
    private String progress_bar_background = null;
    private String open_sound = null;
    private String flip_sound = null;
    private String index_icon = null;
    private boolean pamphlet = false;
    private boolean show_progress = true;
    private String version = null;
    private String subtitle = null;
    private String creative_tab = null;
    private String advancements_tab = null;
    private boolean dont_generate_book = false;
    private String custom_book_item = null;
    private boolean show_toasts = true;
    private boolean use_blocky_font = false;
    private boolean i18n = true;
    private final HashMap<String, String> macros = new HashMap<>();
    private boolean pause_game = false;

    private TextOverflowMode text_overflow = null;

    public enum TextOverflowMode {

        OVERFLOW("overflow"),
        RESIZE("resize"),
        TRUNCATE("truncate");

        private final String modeString;
        TextOverflowMode(String modeString){
            this.modeString = modeString;
        }

        public String toString(){
            return modeString;
        }
    }

    private JPatchouliBook(String name, String landing_text) {
        this.name = name;
        this.landing_text = landing_text;
    }

    /**
     * Creates a new book with the mandatory fields set.
     * @param name the name of the book
     * @param landing_text the landing text of the book
     * @return the book itself for method chaining
     */
    public static JPatchouliBook create(String name, String landing_text) {
        return new JPatchouliBook(name, landing_text);
    }

    /**
     * Sets the background texture of the book GUI.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#book_texture-string) for more information.
     * @param book_texture the texture of the book
     * @return the book itself for method chaining
     */
    public JPatchouliBook setBookTexture(String book_texture) {
        this.book_texture = book_texture;
        return this;
    }

    /**
     * Sets the filler texture for empty pages.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#filler_texture-string) for more information.
     * @param filler_texture the filler texture
     * @return the book itself for method chaining
     */
    public JPatchouliBook setFillerTexture(String filler_texture) {
        this.filler_texture = filler_texture;
        return this;
    }

    /**
     * Sets the texture for the crafting entry elements.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#crafting_texture-string) for more information.
     * @param crafting_texture the crafting texture
     * @return the book itself for method chaining
     */
    public JPatchouliBook setCraftingTexture(String crafting_texture) {
        this.crafting_texture = crafting_texture;
        return this;
    }

    /**
     * Set the model for the book item.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#model-string) for more information.
     * @param model the item model
     * @return the book itself for method chaining
     */
    public JPatchouliBook setModel(String model) {
        this.model = model;
        return this;
    }

    /**
     * Sets the color of the regular text.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#text_color-string) for more information.
     * @param text_color the text color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setTextColor(String text_color) {
        this.text_color = text_color;
        return this;
    }

    /**
     * Sets the color of the header text.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#header_color-string) for more information.
     * @param header_color the header color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setHeaderColor(String header_color) {
        this.header_color = header_color;
        return this;
    }

    /**
     * Sets the color of the book nameplate in the landing page.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#nameplate_color-string) for more information.
     * @param nameplate_color the nameplate color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setNameplateColor(String nameplate_color) {
        this.nameplate_color = nameplate_color;
        return this;
    }

    /**
     * Sets the color of the links.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#link_color-string) for more information.
     * @param link_color the link color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setLinkColor(String link_color) {
        this.link_color = link_color;
        return this;
    }

    /**
     * Sets the color of the links when hovered.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#link_hover_color-string) for more information.
     * @param link_hover_color the link hover color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setLinkHoverColor(String link_hover_color) {
        this.link_hover_color = link_hover_color;
        return this;
    }

    /**
     * Sets the color of the advancement progress bar.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#progress_bar_color-string) for more information.
     * @param progress_bar_color the progress bar color
     * @return the book itself for method chaining
     */
    public JPatchouliBook setProgressBarColor(String progress_bar_color) {
        this.progress_bar_color = progress_bar_color;
        return this;
    }

    /**
     * Sets the color of the advancement progress bar's background.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#progress_bar_background-string) for more information.
     * @param progress_bar_background the progress bar background
     * @return the book itself for method chaining
     */
    public JPatchouliBook setProgressBarBackground(String progress_bar_background) {
        this.progress_bar_background = progress_bar_background;
        return this;
    }

    /**
     * Sets the sound that is played when the book is opened.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#open_sound-string) for more information.
     * @param open_sound the open sound
     * @return the book itself for method chaining
     */
    public JPatchouliBook setOpenSound(String open_sound) {
        this.open_sound = open_sound;
        return this;
    }

    /**
     * Sets the sound that is played when a page is flipped.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#flip_sound-string) for more information.
     * @param flip_sound the flip sound
     * @return the book itself for method chaining
     */
    public JPatchouliBook setFlipSound(String flip_sound) {
        this.flip_sound = flip_sound;
        return this;
    }

    /**
     * Sets the icon of the book in the index.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#index_icon-string) for more information.
     * @param index_icon the index icon
     * @return the book itself for method chaining
     */
    public JPatchouliBook setIndexIcon(String index_icon) {
        this.index_icon = index_icon;
        return this;
    }

    /**
     * Sets the book to be a pamphlet.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#pamphlet-string) for more information.
     * @apiNote If this is set make sure to only have one category.
     * @return the book itself for method chaining
     */
    public JPatchouliBook isPamphlet() {
        this.pamphlet = true;
        return this;
    }

    /**
     * Sets the book to not show the advancement progress bar.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#show_progress-boolean) for more information.
     * @return the book itself for method chaining
     */
    public JPatchouliBook dontShowProgress() {
        this.show_progress = false;
        return this;
    }

    /**
     * Sets the version of the book.
     * This value accepts non-numerical values.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#version-string) for more information.
     * @param version the version of the book
     * @return the book itself for method chaining
     */
    public JPatchouliBook setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Sets the subtitle of the book.
     * This will be displayed below the book name in the landing page and in the tooltip.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#subtitle-string) for more information.
     * @param subtitle the subtitle of the book
     * @return the book itself for method chaining
     */
    public JPatchouliBook setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    /**
     * Sets the creative tab to which the book will be added.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#creative_tab-string) for more information.
     * @param creative_tab the creative tab
     * @return the book itself for method chaining
     */
    public JPatchouliBook setCreativeTab(String creative_tab) {
        this.creative_tab = creative_tab;
        return this;
    }

    /**
     * Sets the advancements tab which this book is linked to.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#advancements_tab-string) for more information.
     * @param advancements_tab the advancements tab
     * @return the book itself for method chaining
     */
    public JPatchouliBook setAdvancementsTab(String advancements_tab) {
        this.advancements_tab = advancements_tab;
        return this;
    }

    /**
     * Sets a custom item for the book.
     * This automatically sets dont_generate_book to true in order to prevent conflicts.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#custom_book_item-string) for more information.
     * @param custom_book_item the custom book item
     * @return the book itself for method chaining
     */
    public JPatchouliBook setCustomBookItem(String custom_book_item) {
        this.dont_generate_book = true;
        this.custom_book_item = custom_book_item;
        return this;
    }

    /**
     * Sets the book to not show toasts when new entries are available.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#show_toasts-boolean) for more information.
     * @return the book itself for method chaining
     */
    public JPatchouliBook dontShowToasts() {
        this.show_toasts = false;
        return this;
    }

    /**
     * Use minecrafts blocky font instead of patchoulis slim font.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#use_blocky_font-boolean) for more information.
     * @return the book itself for method chaining
     */
    public JPatchouliBook useBlockyFont() {
        this.use_blocky_font = true;
        return this;
    }

    /**
     * Disable the i18n lookup for the text.
     * Unlike the default of patchouli this class defaults to true so this can be used to disable it.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#i18n-boolean) for more information.
     * @return the book itself for method chaining
     */
    public JPatchouliBook dontUseI18n() {
        this.i18n = false;
        return this;
    }

    /**
     * Adds a macro to the book.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/text-formatting/#custom-macros) for more information.
     * @param key The macro key that will be replaced
     * @param value The value that will replace it
     * @return the book itself for method chaining
     */
    public JPatchouliBook addMacro(String key, String value) {
        this.macros.put(key, value);
        return this;
    }

    /**
     * Sets the book to pause the game when opened.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#pause_game-boolean) for more information.
     * @return the book itself for method chaining
     */
    public JPatchouliBook pauseGame() {
        this.pause_game = true;
        return this;
    }

    /**
     * Sets the text overflow mode.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json#text_overflow-string) for more information.
     * @param text_overflow the text overflow mode
     * @return the book itself for method chaining
     */
    public JPatchouliBook setTextOverflow(TextOverflowMode text_overflow) {
        this.text_overflow = text_overflow;
        return this;
    }

    /**
     * Returns the serialized book as a json object.
     * Check out [the patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/reference/book-json) for more information on the format.
     * @apiNote This only adds changes fields to the json object. The default values are not added except for i18n.
     * @apiNote The default of i18n is true, while the default of patchouli is false. You can disable this with {@link #dontUseI18n()}.
     * @return the serialized book as a json object
     */
    public JsonObject toJson(){
        //create the json object and set the mandatory fields
        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("name", name);
        bookJson.addProperty("landing_text", landing_text);
        bookJson.addProperty("use_resource_pack", true);

        //always set the value of i18n since the default of this class differs from the default of patchouli
        bookJson.addProperty("i18n", i18n);

        //add the optional fields if they are set

        //color and font related options
        if(text_color != null) bookJson.addProperty("text_color", text_color);
        if(header_color != null) bookJson.addProperty("header_color", header_color);
        if(nameplate_color != null) bookJson.addProperty("nameplate_color", nameplate_color);
        if(link_color != null) bookJson.addProperty("link_color", link_color);
        if(link_hover_color != null) bookJson.addProperty("link_hover_color", link_hover_color);
        if(progress_bar_color != null) bookJson.addProperty("progress_bar_color", progress_bar_color);
        if(progress_bar_background != null) bookJson.addProperty("progress_bar_background", progress_bar_background);
        if(use_blocky_font) bookJson.addProperty("use_blocky_font", true);
        if(text_overflow != null) bookJson.addProperty("text_overflow", text_overflow.toString());
        if(!show_toasts) bookJson.addProperty("show_toasts", false);

        //textures and sounds
        if(book_texture != null) bookJson.addProperty("book_texture", book_texture);
        if(filler_texture != null) bookJson.addProperty("filler_texture", filler_texture);
        if(crafting_texture != null) bookJson.addProperty("crafting_texture", crafting_texture);
        if(model != null) bookJson.addProperty("model", model);
        if(index_icon != null) bookJson.addProperty("index_icon", index_icon);
        if(custom_book_item != null) bookJson.addProperty("custom_book_item", custom_book_item);
        if(open_sound != null) bookJson.addProperty("open_sound", open_sound);
        if(flip_sound != null) bookJson.addProperty("flip_sound", flip_sound);

        //misc stuff
        if(dont_generate_book) bookJson.addProperty("dont_generate_book", true);
        if(pamphlet) bookJson.addProperty("pamphlet", true);
        if(!show_progress) bookJson.addProperty("show_progress", false);
        if(version != null) bookJson.addProperty("version", version);
        if(subtitle != null) bookJson.addProperty("subtitle", subtitle);
        if(creative_tab != null) bookJson.addProperty("creative_tab", creative_tab);
        if(advancements_tab != null) bookJson.addProperty("advancements_tab", advancements_tab);
        if(pause_game) bookJson.addProperty("pause_game", true);

        //add the macros
        if(!macros.isEmpty()) {
            JsonObject macrosJson = new JsonObject();
            for (var entry : macros.entrySet()) {
                macrosJson.addProperty(entry.getKey(), entry.getValue());
            }
            bookJson.add("macros", macrosJson);
        }

        return bookJson;
    }

    /**
     * Returns the serialized book as a json string.
     * @return the serialized book as a json string
     */
    public String toString(){
        return SakuraJsonHelper.toPrettyJson(toJson());
    }
}
