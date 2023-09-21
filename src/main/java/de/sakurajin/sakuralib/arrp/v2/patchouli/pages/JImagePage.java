package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;

import java.util.ArrayList;

/**
 * A page containing images.
 * This page type can be used to either display a single image or a slideshow of multiple images.
 */
public class JImagePage extends JPageBase {

    private final ArrayList<String> images = new ArrayList<>();
    private String title = null;
    private boolean border = false;
    private String text = null;

    private JImagePage() {
        super("patchouli:image");
    }

    /**
     * Creates a new image page.
     * @return The page itself for method chaining.
     */
    public static JImagePage create() {
        return new JImagePage();
    }

    /**
     * Creates a new image page with the given image.
     * @param image The image to add to the page.
     * @return The page itself for method chaining.
     */
    public static JImagePage create(String image) {
        return new JImagePage().addImage(image);
    }

    /**
     * Adds an image to the page.
     * @param image The image to add to the page.
     * @return The page itself for method chaining.
     */
    public JImagePage addImage(String image) {
        this.images.add(image);
        return this;
    }

    /**
     * Sets the title displayed above the image.
     * @param title The title of the page.
     * @return The page itself for method chaining.
     */
    public JImagePage setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the border of the image.
     * @return The page itself for method chaining.
     */
    public JImagePage hasBorder() {
        this.border = true;
        return this;
    }

    /**
     * The text displayed below the image.
     * @param text The text to display.
     * @return The page itself for method chaining.
     */
    public JImagePage setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Returns the serialized json object for this page.
     * @apiNote This only adds fields that are set to the json object.
     * @throws IllegalStateException If the page has no images.
     * @return The serialized json object for this page.
     */
    @Override
    public JsonObject toJson() throws IllegalStateException{
        JsonObject imageJson = super.toJson();

        if (images.isEmpty()){
            throw new IllegalStateException("Image page must have at least one image");
        }

        JsonArray imagesArray = new JsonArray();
        for(var image: images){
            imagesArray.add(image);
        }
        imageJson.add("images", imagesArray);

        if(title != null) imageJson.addProperty("title", title);
        if(border) imageJson.addProperty("border", true);
        if(text != null) imageJson.addProperty("text", text);

        return imageJson;
    }
}
