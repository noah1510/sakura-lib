package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.arrp.v2.patchouli.JPageBase;

public class JEmptyPage extends JPageBase{

    private boolean draw_filler = true;

    protected JEmptyPage() {
        super("patchouli:empty");
    }

    public static JEmptyPage create() {
        return new JEmptyPage();
    }

    public JEmptyPage noFiller() {
        this.draw_filler = false;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();
        if(!draw_filler) json.addProperty("draw_filler", false);
        return json;
    }
}
