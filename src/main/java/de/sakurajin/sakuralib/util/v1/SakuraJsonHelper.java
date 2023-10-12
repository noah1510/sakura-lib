package de.sakurajin.sakuralib.util.v1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class contains helper functions for working with json.
 * Most stuff in here exists to make code more readable.
 */
public class SakuraJsonHelper {
    /**
     * This function creates a json Array and adds all given elements to it.
     * @param elements The elements to add to the array.
     * @return The created array.
     */
    @SafeVarargs
    static public<T> JsonArray createArray(T... elements){
        var array = new JsonArray();
        for(var element : elements){
            try{
                addToJsonArray(array, element);
            }catch(Exception e){
                throw new IllegalArgumentException("The given element is not a valid json element");
            }
        }
        return array;
    }

    /**
     * Add a generic element to a json array.
     * @param array The array to add the element to.
     * @param data The element to add.
     * @param <T> The type of the element.
     */
    static public <T> void addToJsonArray(JsonArray array, T data){
        if (data.getClass().equals(String.class)) {
            array.add((String) data);
        } else if (GenericsHelper.isNumber(data)) {
            array.add((Number) data);
        } else if (data.getClass().equals(Boolean.class)) {
            array.add((Boolean) data);
        } else if (data.getClass().equals(Character.class)) {
            array.add((Character) data);
        } else {
            try{
                array.add((JsonElement) data);
            }catch (Exception e){
                throw new IllegalArgumentException("The given element is not a valid json element");
            }
        }
    }

    /**
     * This function mergers all keys from the given objects into a new object.
     * @param objects The objects to merge.
     * @return The merged object.
     */
    static public JsonObject merge(JsonObject... objects){
        var merged = new JsonObject();
        for(var object : objects){
            if(object == null) continue;
            for(var entry : object.entrySet()){
                merged.add(entry.getKey(), entry.getValue());
            }
        }
        return merged;
    }
}
