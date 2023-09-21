package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;

/**
 * A page containing only text.
 * This is the most basic page type and is required to be the first page of every entry.
 * @apiNote The first page in an entry can only be a text page without a title.
 */
public class JTextPage extends JPageBase {
    private final StringBuilder text = new StringBuilder();
    private String title = null;
    private JTextPage() {
        super("patchouli:text");
    }

    /**
     * Creates a new text page.
     * @return The created page.
     */
    public static JTextPage create() {
        return new JTextPage();
    }

    /**
     * Creates a new text page with the given text.
     * @param text The text that will be added to the page initially
     * @return The created page.
     */
    public static JTextPage create(String text) {
        return new JTextPage().addText(text);
    }

    /**
     * Add more text to the page.
     * For details about the text formatting look into [text formatting 101](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/text-formatting/)
     * The text can be a translation key to allow for localization of the text from the lang files.
     * @param text The text to add.
     * @return The page itself for method chaining.
     */
    public JTextPage addText(String text) {
        this.text.append(text);
        return this;
    }

    /**
     * Sets the title of the page.
     * @param title The title of the page.
     * @return The page itself for method chaining.
     */
    public JTextPage setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Checks if the page has a title.
     * This is used by the JPatchouliEntry class to determine if the first page is a text page without a title.
     * @return True if the page has a title, false otherwise.
     */
    public boolean hasTitle() {
        return title != null;
    }

    /**
     * Returns the serialized json object for this page.
     * This adds the text field and optionally the title if that was specified.
     * @return The serialized json object for this page.
     */
    @Override
    public JsonObject toJson() {
        JsonObject pageJson = super.toJson();

        if(hasTitle()) pageJson.addProperty("title", title);
        pageJson.addProperty("text", text.toString());

        return pageJson;
    }
}
