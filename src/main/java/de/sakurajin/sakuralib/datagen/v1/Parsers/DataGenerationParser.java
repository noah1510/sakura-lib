package de.sakurajin.sakuralib.datagen.v1.Parsers;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.minecraft.item.ItemConvertible;

import java.lang.reflect.Field;

public class DataGenerationParser implements IDataGenerationParser {
    @Override
    public void parse(String namespace, ItemConvertible value, String identifier, Field field, DatagenModContainer container) {
        if(!(value instanceof DataGenerateable)){ return; }
        ((DataGenerateable) value).generateData(container, identifier);
    }
}
