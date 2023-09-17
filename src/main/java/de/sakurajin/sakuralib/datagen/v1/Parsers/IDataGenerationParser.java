package de.sakurajin.sakuralib.datagen.v1.Parsers;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import net.minecraft.item.ItemConvertible;

import java.lang.reflect.Field;

/**
 * This interface is used to parse annotations.
 * You can add your own parsers to the DatagenModContainer to add your own annotations or handle interface implementations.
 */
public interface IDataGenerationParser {
    /**
     * This method is called by the ParsedContainerBase to parse annotations.
     * Usually this function receives its values from the owo registration system.
     *
     * @param namespace The namespace of the mod
     * @param value The item/block object itself
     * @param identifier The name of the item/block
     * @param field All the annotation fields added to this item/block
     * @param container The DatagenModContainer
     */
    void parse(String namespace, ItemConvertible value, String identifier, Field field, DatagenModContainer container);
}
