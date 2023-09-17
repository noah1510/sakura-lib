package de.sakurajin.sakuralib.util.v1;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.CustomValue;

public class MetadataHelper {

    @SuppressWarnings("unchecked")
    static public <T> T getCustomOrDefault(String modID, String key, T defaultValue) {
        String[] keys = key.split(":");
        var container = FabricLoader.getInstance().getModContainer(modID);
        if (container.isEmpty()){
            return defaultValue;
        }

        var customValue = container.get().getMetadata().getCustomValue(keys[0]);

        if (customValue == null || customValue.getType() != CustomValue.CvType.OBJECT) {
            return defaultValue;
        }
        CustomValue.CvObject customObject = customValue.getAsObject();

        for(int i = 1; i < keys.length - 1; i++){
            customValue = customObject.get(keys[i]);
            if (customValue == null || customValue.getType() != CustomValue.CvType.OBJECT) {
                return defaultValue;
            }
            customObject = customValue.getAsObject();
        }

        customValue = customObject.get(keys[keys.length - 1]);
        if(customValue == null) {
            return defaultValue;
        }

        try {
            if (defaultValue.getClass() == Boolean.class) {
                return (T) (Boolean) customValue.getAsBoolean();
            }
            if (defaultValue.getClass() == Integer.class) {
                return (T) (Integer) customValue.getAsNumber().intValue();
            }
            if (defaultValue.getClass() == Double.class) {
                return (T) (Double) customValue.getAsNumber().doubleValue();
            }
            if (defaultValue.getClass() == Long.class) {
                return (T) (Long) customValue.getAsNumber().longValue();
            }
            if (defaultValue.getClass() == Float.class) {
                return (T) (Float) customValue.getAsNumber().floatValue();
            }
            if (defaultValue.getClass() == String.class) {
                return (T) customValue.getAsString();
            }
        } catch (Exception ignored) {}

        return defaultValue;
    }
}
