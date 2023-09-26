package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;

/**
 * A page containing 1 or 2 crafting recipes.
 * This page type can be used to display a single recipe or a recipe with a secondary item.
 * @apiNote While the first recipe is required, the second one is optional.
 */
public class JCraftingPage extends JPageBase {
    final private String recipe;
    private String recipe2 = null;
    private String title = null;
    private String text = null;

    protected JCraftingPage(String item1){
        super("patchouli:crafting");
        this.recipe = item1;
    }

    /**
     * Creates a new recipe page with the given recipe.
     * @param item1 The first item which will have its recipe displayed.
     * @return The page itself for method chaining.
     */
    public static JCraftingPage create(String item1){
        return new JCraftingPage(item1);
    }

    /**
     * Sets the second item which will have its recipe displayed.
     * @apiNote This is optional.
     * @param item2 The second item which will have its recipe displayed.
     * @return The page itself for method chaining.
     */
    public JCraftingPage setRecipe2(String item2){
        this.recipe2 = item2;
        return this;
    }

    /**
     * Sets the title of the page.
     * @param title The title of the page shown above the recipe(s).
     * @return The page itself for method chaining.
     */
    public JCraftingPage setTitle(String title){
        this.title = title;
        return this;
    }

    /**
     * Sets the text of the page.
     * @apiNote the text will not display if there are two recipes with two different outputs, and "title" is not set.
     * @param text The text shown below the recipe(s).
     * @return The page itself for method chaining.
     */
    public JCraftingPage setText(String text){
        this.text = text;
        return this;
    }

    /**
     * Returns the serialized json object for this page.
     * @return The serialized json object for this page.
     */
    @Override
    public JsonObject toJson() {
        JsonObject RecipeJson = super.toJson();

        RecipeJson.addProperty("recipe", recipe);
        if(recipe2 != null) RecipeJson.addProperty("recipe2", recipe2);
        if(title != null) RecipeJson.addProperty("title", title);
        if(text != null) RecipeJson.addProperty("text", text);

        return RecipeJson;
    }
}
