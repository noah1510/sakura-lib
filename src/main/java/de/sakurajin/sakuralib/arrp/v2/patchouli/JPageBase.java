package de.sakurajin.sakuralib.arrp.v2.patchouli;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;

/**
 * Base class for all pages
 * This only contains the basic data common across all page types.
 * For detailed information about this look at the [patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types).
 */
public abstract class JPageBase {
    /**
     * Since type is a mandatory field for all pages, it is final.
     * The type is the name of the page type, e.g. "text" or "crafting".
     * It is set by the constructor of the subclass.
     */
    final protected String type;

    protected String advancement = null;
    protected String flag = null;
    protected String anchor = null;

    protected JPageBase(String type) {
        this.type = type;
    }

    /**
     * Sets the advancement for this page.
     * For more information look into [advancements locking](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/advancement-locking/)
     * @param advancement The advancement which unlocks this page.
     * @return The page itself for method chaining.
     */
    public JPageBase setAdvancement(String advancement) {
        this.advancement = advancement;
        return this;
    }

    /**
     * Sets the flag for this page.
     * For more information look into [using config flags](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/config-gating/)
     * @param flag A config flag to determine if this page should exist.
     * @return The page itself for method chaining.
     */
    public JPageBase setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    /**
     * Sets the anchor for this page.
     * This is used to link to this page from other pages see
     * [text formatting 101](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/text-formatting/)
     * for details.
     * @param anchor The anchor name for this page.
     * @return The page itself for method chaining.
     */
    public JPageBase setAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * Returns the serialized json object for this page.
     * Only fields that are specified will actually be added to this object.
     * @implNote If you create a child class of this remember to call super.getJson() and add your own properties to the returned object.
     * @return The serialized json object for this page.
     */
    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("type", type);
        if (advancement != null) json.addProperty("advancement", advancement);
        if (flag != null) json.addProperty("flag", flag);
        if (anchor != null) json.addProperty("anchor", anchor);
        return json;
    }

    /**
     * Returns the json string for this page.
     * Same as calling getJson().toString()
     * @return The json string for this page.
     */
    public String toString() {
        return SakuraJsonHelper.toPrettyJson(toJson());
    }
}
