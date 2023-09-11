package de.sakurajin.sakuralib.Parsers;

import de.sakurajin.sakuralib.util.DatagenModContainer;
import de.sakurajin.sakuralib.Interfaces.DataGenerateable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;

/**
 * This parser is used to generate block items for blocks that implement the BlockItemGenerateable interface
 */
public class BlockDataGenerationParser implements IDataGenerationParser {
    @Override
    public void parse(String namespace, ItemConvertible value, String identifier, Field field, DatagenModContainer container) {
        if (!(value instanceof Block)) return;
        if (!(value instanceof DataGenerateable)) return;

        //generate the block item
        Item blockItem = (Item) ((DataGenerateable) value).generateData(container, identifier);

        // register the block item
        Registry.register(Registries.ITEM, new Identifier(namespace, identifier), blockItem);

    }
}
