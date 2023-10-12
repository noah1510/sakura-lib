package de.sakurajin.sakuralib.datagen.v2;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.SakuraLib;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;

import java.util.HashMap;

/**
 * This class is used to manage rrp lang entries in using owo libs rich translation support.
 * <p>
 * Entries can either be added per lang or globally.
 * Global entries are merged with the lang specific entries.
 * This requires the language to be declared or having an entry added to it.
 *
 * @apiNote All entries are added to the SakuraLib datapack. This means you don't need to use other parts of this library to use this.
 */
public class DynamicOwOLangManager {
    /**
     * The internal map of all lang files.
     * The key is the lang name
     * The value is the lang Json object
     * This Json object has the same structure as a minecraft lang file.
     */
    private static final HashMap<String, JsonObject> langFiles = new HashMap<>();

    //the static setup for this class in which the default lang is declared
    static {
        declareLang("en_us");
    }

    /**
     * Declare a language to exist.
     * By default, only `en_us` is declared.
     *
     * @param lang The language to declare.
     */
    public static void declareLang(String lang) {
        if (!langFiles.containsKey(lang)) {
            langFiles.put(lang, new JsonObject());
        }
    }

    /**
     * Add a global entry to the rrp.
     * Global entries are added to every lang file.
     * The lang specific variant should override the global entry.
     *
     * @param key   The key to add the entry for.
     * @param value The value to add.
     * @apiNote For technical reasons global entries require the language to be declared.
     */
    public static void addGlobalEntry(String key, JsonArray value) {
        addEntry("global", key, value);
    }

    /**
     * Add a language specific entry to the rrp.
     *
     * @param lang  The language to add the entry for.
     * @param key   The key to add the entry for.
     * @param value The value to add.
     */
    public static void addEntry(String lang, String key, JsonArray value) {
        declareLang(lang);
        langFiles.get(lang).add(key, value);
    }

    /**
     * Actually update the rrp with the data.
     * This merges the global entries with the local entries for each lang and writes the result to the rrp.
     */
    public static void updateRRP() {
        //get the global data once to save time
        JsonObject globalData = langFiles.get("global");

        for (var entry : langFiles.entrySet()) {
            //skip if this is the global data
            if (entry.getKey().equals("global")) continue;

            //merge the global data with the data for this lang
            //in case the global data is null this will just return a copy of the data for this lang
            JsonObject mergedData = SakuraJsonHelper.merge(globalData, entry.getValue());

            //add the data to the rrp
            SakuraLib.DATAGEN_CONTAINER.RESOURCE_PACK.addAsset(
                SakuraLib.DATAGEN_CONTAINER.getSimpleID(entry.getKey() + ".json", "lang"),
                mergedData.toString().getBytes()
            );
        }
    }
}
