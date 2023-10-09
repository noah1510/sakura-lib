package de.sakurajin.sakuralib.arrp.v2.patchouli.pages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * A data structure to represent a multiblock structure.
 * This is used in {@link JMultiblockPage}.
 *
 * @apiNote This is not a page type, but a data structure as specified in the [Patchouli Docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks/).
 */
public class JMultiblockStructure {
    private final HashMap<String, String>      mappings    = new HashMap<>();
    private final ArrayList<ArrayList<String>> pattern     = new ArrayList<>();
    private final int[]                        offset      = new int[3];
    private       boolean                      symmetrical = false;

    private JMultiblockStructure() {
    }

    /**
     * Creates a new multiblock structure.
     *
     * @return The created structure.
     */
    public static JMultiblockStructure create() {
        return new JMultiblockStructure();
    }

    /**
     * Adds a mapping to the structure.
     * Check out {@link JMultiblockStructure#addMapping(Pair[])} for more information.
     *
     * @param key   The key to be replaced.
     * @param value The value to replace the key with.
     * @return The structure itself for method chaining.
     */
    public JMultiblockStructure addMapping(String key, String value) {
        return addMapping(new Pair<>(key, value));
    }

    /**
     * Adds multiple mappings to the structure.
     * Check [the patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks/#the-mapping) for more information.
     *
     * @param mappings The mappings to add.
     * @return The structure itself for method chaining.
     */
    @SafeVarargs
    public final JMultiblockStructure addMapping(Pair<String, String>... mappings) {
        for (Pair<String, String> mapping : mappings) {
            this.mappings.put(mapping.getLeft(), mapping.getRight());
        }
        return this;
    }

    /**
     * Adds a single row to the structure.
     * Check out {@link JMultiblockStructure#addRow(ArrayList[])} for more information.
     *
     * @param blocks The blocks in the row.
     * @return The structure itself for method chaining.
     */
    public JMultiblockStructure addRow(String... blocks) {
        ArrayList<String> row = new ArrayList<>();
        Collections.addAll(row, blocks);
        return addRow(row);
    }

    /**
     * Adds one or more rows to the structure.
     * Check out [the patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks/#the-pattern) for more information.
     *
     * @param row The row(s) to add.
     * @return The structure itself for method chaining.
     */
    @SafeVarargs
    public final JMultiblockStructure addRow(ArrayList<String>... row) {
        this.pattern.addAll(Arrays.asList(row));
        return this;
    }

    /**
     * Mark the structure as symmetrical.
     * Check out [the patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks/#some-extra-things) for more information.
     *
     * @return The structure itself for method chaining.
     * @apiNote This is optional and defaults to false set this for better performance.
     */
    public JMultiblockStructure isSymmetrical() {
        this.symmetrical = true;
        return this;
    }

    /**
     * Offsets the 0 0 0 position of the structure.
     * Check out [the patchouli docs](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks/#some-extra-things) for more information.
     *
     * @param x The x offset.
     * @param y The y offset.
     * @param z The z offset.
     * @return The structure itself for method chaining.
     */
    public JMultiblockStructure setOffset(int x, int y, int z) {
        this.offset[0] = x;
        this.offset[1] = y;
        this.offset[2] = z;
        return this;
    }

    /**
     * Returns the serialized json object for this structure.
     *
     * @return The serialized json object for this structure.
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonObject mappings = new JsonObject();
        for (String key : this.mappings.keySet()) {
            mappings.addProperty(key, this.mappings.get(key));
        }
        json.add("mappings", mappings);

        JsonArray pattern = new JsonArray();
        for (ArrayList<String> row : this.pattern) {
            JsonArray rowJson = new JsonArray();
            for (String block : row) {
                rowJson.add(block);
            }
            pattern.add(rowJson);
        }
        json.add("pattern", pattern);

        if (symmetrical) {
            json.addProperty("symmetrical", true);
        }
        ;

        if (offset[0] != 0 || offset[1] != 0 || offset[2] != 0) {
            JsonArray offset = new JsonArray();
            offset.add(this.offset[0]);
            offset.add(this.offset[1]);
            offset.add(this.offset[2]);
            json.add("offset", offset);
        }
        return json;
    }

    /**
     * Returns the serialized json object as string.
     * Same as calling `toJson().toString()`.
     *
     * @return The serialized json object as string.
     */
    public String toString() {
        return toJson().toString();
    }

}
