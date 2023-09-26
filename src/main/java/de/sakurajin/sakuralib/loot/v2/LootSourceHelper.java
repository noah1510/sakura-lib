package de.sakurajin.sakuralib.loot.v2;

import net.fabricmc.fabric.api.loot.v2.LootTableSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This helper class allows decoding and encoding of loot table sources.
 * This allows to store multiple sources in a single integer value for easier storage.
 */
public class LootSourceHelper {
    //the bitmasks for the different loot table sources
    public static final int VANILLA   = 0b0001;
    public static final int MOD       = 0b0010;
    public static final int DATA_PACK = 0b0100;
    public static final int REPLACED  = 0b1000;

    //combined bitmasks for common use cases
    public static final int BUILTIN   = VANILLA | MOD;
    public static final int ADDITIONS = DATA_PACK | VANILLA | MOD;
    public static final int ALL       = VANILLA | MOD | DATA_PACK | REPLACED;

    /**
     * A map that maps loot table sources to a unique integer value.
     * These values are used to encode and decode the loot table sources.
     * The values are bit flags and can be combined using the bitwise or operator.
     */
    public static final Map<LootTableSource, Integer> sourceIDMap = Map.of(
        LootTableSource.VANILLA, VANILLA,
        LootTableSource.MOD, MOD,
        LootTableSource.DATA_PACK, DATA_PACK,
        LootTableSource.REPLACED, REPLACED
    );

    /**
     * This function is used to encode a list of loot table sources into a single integer value.
     *
     * @param sources The list of loot table sources that should be encoded.
     * @return The encoded integer value.
     */
    public static int getNumberFor(LootTableSource... sources) {
        int id = 0;
        for (var source : sources) {
            id |= sourceIDMap.get(source);
        }
        return id;
    }

    /**
     * This function is used to decode a single integer value into a list of loot table sources.
     *
     * @param id The integer value that should be decoded.
     * @return The list of loot table sources.
     */
    public static List<LootTableSource> getSources(int id) {
        ArrayList<LootTableSource> sources = new ArrayList<>();
        for (var source : sourceIDMap.keySet()) {
            if (inNumber(id, source)) {
                sources.add(source);
            }
        }
        return sources;
    }

    /**
     * This function is used to check if a loot table source is in a given integer value.
     *
     * @param number The integer value with the encoded loot table sources.
     * @param source The loot table source that should be checked.
     * @return True if the loot table source is in the integer value, false otherwise.
     */
    public static boolean inNumber(int number, LootTableSource source) {
        return (number & sourceIDMap.get(source)) != 0;
    }
}
