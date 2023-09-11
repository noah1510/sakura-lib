package de.sakurajin.sakuralib.Parsers;

import de.sakurajin.sakuralib.util.DatagenModContainer;
import de.sakurajin.sakuralib.Interfaces.DataGenerateable;
import net.minecraft.item.ItemConvertible;

import java.lang.reflect.Field;

public class DataGenerationParser implements IDataGenerationParser {
    @Override
    public void parse(String namespace, ItemConvertible value, String identifier, Field field, DatagenModContainer container) {
        if(!(value instanceof DataGenerateable)){ return; }
        ((DataGenerateable) value).generateData(container, identifier);
    }
}
