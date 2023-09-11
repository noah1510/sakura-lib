package de.sakurajin.sakuralib.Containers;

import de.sakurajin.sakuralib.util.DatagenModContainer;
import de.sakurajin.sakuralib.Parsers.IDataGenerationParser;
import net.minecraft.item.ItemConvertible;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * The base class to automatically parse all items and blocks in a class.
 * It is intended to be used in combination with the Owo registration system.
 *
 */
public abstract class ParsedContainerBase {
    protected final DatagenModContainer initializer;
    private final ArrayList<IDataGenerationParser> parsers = new ArrayList<>();
    protected ParsedContainerBase(DatagenModContainer initializer) {
        super();
        this.initializer = initializer;
    }

    /**
     * Add a parser to the list of parsers.
     * All parsers are called in the order they were added and on every item/block.
     *
     * @param parser The parser to add
     */
    protected void addParser(IDataGenerationParser parser){
        parsers.add(parser);
    }

    /**
     * This method should be called from the postProcessField of a class that extends this class and implements
     * the AutoRegistryContainer interface by the owo library.
     * Simply pass this method the parameters you received from the owo library and it will call all registered parsers.
     *
     * @param namespace The namespace of the mod
     * @param value The item/block object itself
     * @param identifier The name of the item/block
     * @param field All the annotation fields added to this item/block
     */
    public void parseFields(String namespace, ItemConvertible value, String identifier, Field field) {
        initializer.LOGGER.debug("Postprocessing block " + identifier);

        //call all registered parsers
        for (IDataGenerationParser parser : parsers) {
            parser.parse(namespace, value, identifier, field, initializer);
        }

    }

}
