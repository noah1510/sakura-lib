package de.sakurajin.sakuralib.util.v1;

import com.google.gson.*;

/**
 * A simple builder for JsonObjects.
 */
public class JsonObjectBuilder {
    /**
     * The internal JsonObject.
     * All functions of this builder will modify this object.
     * The add functions just forward the call to the add function of the JsonObject.
     * Since for whatever reason the JsonObject does neither have a builder nor does
     * it return itself in the add function a custom builder like this is needed.
     * This object is copied when build is called.
     */
    private final JsonObject jsonObject;

    /**
     * Create a new JsonObjectBuilder.
     */
    private JsonObjectBuilder() {
        jsonObject = new JsonObject();
    }

    /**
     * Create a new JsonObjectBuilder.
     *
     * @return A new JsonObjectBuilder.
     */
    public static JsonObjectBuilder create() {
        return new JsonObjectBuilder();
    }

    /**
     * Add a property to the JsonObject.
     * This simply calls JsonObject.add(property, value) and returns the builder
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @return The builder.
     */
    public JsonObjectBuilder add(String property, JsonElement value) {
        this.jsonObject.add(property, value);
        return this;
    }

    /**
     * Add a property to the JsonObject.
     * This simply calls JsonObject.addProperty(property, value) and returns the builder
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @return The builder.
     */
    public JsonObjectBuilder add(String property, String value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    /**
     * Add a property to the JsonObject.
     * This simply calls JsonObject.addProperty(property, value) and returns the builder
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @return The builder.
     */
    public JsonObjectBuilder add(String property, Number value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    /**
     * Add a property to the JsonObject.
     * This simply calls JsonObject.addProperty(property, value) and returns the builder
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @return The builder.
     */
    public JsonObjectBuilder add(String property, Boolean value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    /**
     * Add a property to the JsonObject.
     * This simply calls JsonObject.addProperty(property, value) and returns the builder
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @return The builder.
     */
    public JsonObjectBuilder add(String property, Character value) {
        this.jsonObject.addProperty(property, value);
        return this;
    }

    /**
     * Adds a property to the JsonObject.
     * This a wrapper around the other functions to simplify adding values.
     * It uses a generic type to determine which function to call.
     * This can simply be used if you are too lazy for the other functions.
     *
     * @param property The property to add.
     * @param value    The value to add.
     * @param <T>      The type of the value.
     * @return The builder.
     */
    public <T> JsonObjectBuilder addGeneric(String property, T value) {
        if (value instanceof JsonElement) {
            this.add(property, (JsonElement) value);
        } else if (value instanceof String) {
            this.add(property, (String) value);
        } else if (value instanceof Number) {
            this.add(property, (Number) value);
        } else if (value instanceof Boolean) {
            this.add(property, (Boolean) value);
        } else if (value instanceof Character) {
            this.add(property, (Character) value);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass().getName());
        }
        return this;
    }

    /**
     * Build the JsonObject.
     *
     * @return A copy of the internal JsonObject.
     */
    public JsonObject build() {
        return this.jsonObject.deepCopy();
    }
}
