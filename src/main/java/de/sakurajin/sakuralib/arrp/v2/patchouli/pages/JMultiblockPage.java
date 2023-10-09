package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;
import org.jetbrains.annotations.NotNull;

/**
 * A page to display a multiblock structure.
 * Either the mutiblock_id or the multiblock object has to be set.
 * Check out [the patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#multiblock-pages-patchoulimultiblock)
 * for more information on how to create a multiblock structure.
 */
public class JMultiblockPage extends JPageBase {
    final private String name;
    final private String multiblock_id;
    final private JMultiblockStructure multiblock;
    private boolean enable_visualize = false;
    private String text = null;

    protected JMultiblockPage(String name, String multiblock_id, JMultiblockStructure multiblock){
        super("patchouli:crafting");
        this.name = name;
        if(multiblock == null && multiblock_id == null) throw new IllegalArgumentException("Either multiblock or multiblock_id has to be set.");
        this.multiblock_id = multiblock_id;
        this.multiblock = multiblock;
    }

    /**
     * Creates a new multiblock page with the given multiblock id.
     * @param name The name of the multiblock page.
     * @param multiblock_id The identifier of the multiblock.
     * @return The page itself for method chaining.
     */
    public static JMultiblockPage create(String name, @NotNull String multiblock_id){
        return new JMultiblockPage(name, multiblock_id, null);
    }

    /**
     * Creates a new multiblock page with the given multiblock structure.
     * See {@link JMultiblockStructure} for more information on how to create a multiblock structure.
     * @param name The name of the multiblock page.
     * @param multiblock The multiblock structure.
     * @return The page itself for method chaining.
     */
    public static JMultiblockPage create(String name, @NotNull JMultiblockStructure multiblock){
        return new JMultiblockPage(name, null, multiblock);
    }

    /**
     * Enables the visualize button.
     * @return The page itself for method chaining.
     */
    public JMultiblockPage enableVisualize(){
        this.enable_visualize = true;
        return this;
    }

    /**
     * Sets the text of the page.
     * @apiNote the text will not display if there are two recipes with two different outputs, and "title" is not set.
     * @param text The text shown below the recipe(s).
     * @return The page itself for method chaining.
     */
    public JMultiblockPage setText(String text){
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

        RecipeJson.addProperty("name", name);
        if(multiblock_id != null) RecipeJson.addProperty("multiblock_id", multiblock_id);
        if(multiblock != null) RecipeJson.add("multiblock", multiblock.toJson());

        if(enable_visualize) RecipeJson.addProperty("enable_visualize", true);
        if(text != null) RecipeJson.addProperty("text", text);

        return RecipeJson;
    }
}
