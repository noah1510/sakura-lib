package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

/**
 * A page containing 1 or 2 recipes.
 * This page type can be used to display a single recipe or a recipe with a secondary item.
 * @apiNote While the first recipe is required, the second one is optional.
 * @deprecated Use {@link JCraftingPage} instead. Will be removed in 2.0.0
 */
@Deprecated(since = "1.4.0", forRemoval = true)
public class JRecipePage extends JCraftingPage {
    protected JRecipePage(String item1){
        super(item1);
    }

    /**
     * Creates a new recipe page with the given recipe.
     * @param item1 The first item which will have its recipe displayed.
     * @return The page itself for method chaining.
     */
    public static JRecipePage create(String item1){
        return new JRecipePage(item1);
    }
}
