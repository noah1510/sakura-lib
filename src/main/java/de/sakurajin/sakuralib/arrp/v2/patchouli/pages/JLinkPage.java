package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;

/**
 * A page containing a link.
 * It is based on the text page and adds a link to the page.
 * This is done to have it match the patchouli format in which link pages contain all fields of a text page.
 * @apiNote Since this is also a text page, it can be used as the first page of an entry.
 */
public class JLinkPage extends JTextPage {
    private final String link_text;
    private final String url;
    private JLinkPage(String link_text, String url) {
        super("patchouli:text");
        this.link_text = link_text;
        this.url = url;
    }

    /**
     * Creates a new link page.
     * @return The created page.
     */
    public static JLinkPage create(String link_text, String url) {
        return new JLinkPage(link_text, url);
    }

    /**
     * Add more text to the page.
     * For details about the text formatting look into [text formatting 101](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/text-formatting/)
     * The text can be a translation key to allow for localization of the text from the lang files.
     * @param text The text to add.
     * @return The page itself for method chaining.
     */
    public JLinkPage addText(String text) {
        super.addText(text);
        return this;
    }

    /**
     * Sets the title of the page.
     * @param title The title of the page.
     * @return The page itself for method chaining.
     */
    public JLinkPage setTitle(String title) {
        super.setTitle(title);
        return this;
    }

    /**
     * Returns the serialized json object for this page.
     * This adds the text field and optionally the title if that was specified.
     * @return The serialized json object for this page.
     */
    @Override
    public JsonObject toJson() {
        JsonObject pageJson = super.toJson();

        pageJson.addProperty("url", url);
        pageJson.addProperty("link_text", link_text);

        return pageJson;
    }
}
