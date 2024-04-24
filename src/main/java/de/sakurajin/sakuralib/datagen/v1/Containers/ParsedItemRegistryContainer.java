package de.sakurajin.sakuralib.datagen.v1.Containers;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.Parsers.DataGenerationParser;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import java.lang.reflect.Field;
import net.minecraft.item.Item;

/**
 * A ParsedItemRegistryContainer is a Item Registry Container that works with
 * the DatagenAPI. This connects the owo registration system with the DatagenAPI
 * and allows generating Models, Recipes and more during Runtime. If a Block
 * does not Implement the Datagen Interfaces, it will only be registered and the
 * DatagenAPI will ignore it. All you have to do is create all your Items as
 * static public variables and implement a constructor with no parameters that
 * passes a DatagenModContainer to the super constructor. If you use the Item
 * class you can use the @ref Presets.Item.CraftableItem class instead to have
 * an anonymous class that implements the Datagen Interfaces.
 *
 * The following parser are added by default:
 *
 * * ItemModelGenerationParser
 * * RecepieGenerationParser
 * * DataGenerationParser
 *
 * You can add your own parsers to the DatagenModContainer to add your own
 * annotations or handle interface implementations.
 * @see ParsedContainerBase for more information about adding Parsers
 * @see de.sakurajin.sakuralib.datagen.v1.Presets.Item.CraftableItem for an Item
 *     that allows easy data generation
 * @see <a href="https://docs.wispforest.io/owo/registration/#basics">Owo
 *     registration on items</a>
 */
public abstract class ParsedItemRegistryContainer
    extends ParsedContainerBase implements ItemRegistryContainer {
  /**
   * This constructor is used to create a ParsedItemRegistryContainer.
   * @note You need to define a constructor with no parameters that passes a
   * DatagenModContainer to the super constructor. If this is not done the game
   * will start during startup.
   * @param initializer The DatagenModContainer
   */
  protected ParsedItemRegistryContainer(DatagenModContainer initializer) {
    super(initializer);
    addParser(new DataGenerationParser());
  }

  /**
   * This method is called by the owo registration system after each item was
   * registered. It will call all parsers that are registered.
   */
  @Override
  public void postProcessField(String namespace, Item value, String identifier,
                               Field field) {
    parseFields(namespace, value, identifier, field);
  }
}
