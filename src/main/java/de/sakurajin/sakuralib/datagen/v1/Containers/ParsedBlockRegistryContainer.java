package de.sakurajin.sakuralib.datagen.v1.Containers;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.Parsers.BlockDataGenerationParser;
import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import java.lang.reflect.Field;
import net.minecraft.block.Block;

/**
 * A ParsedBlockRegistryContainer is a Block Registry Container that works with
 * the DatagenAPI. This connects the owo registration system with the DatagenAPI
 * and allows generating Blockstates, Models, Recipes and more during Runtime.
 * If a Block does not Implement the Datagen Interfaces, it will only be
 * registered and the DatagenAPI will ignore it. All you have to do is create
 * all your Block as static public variables and implement a constructor with no
 * parameters that passes a DatagenModContainer to the super constructor.
 *
 * The following parser are added by default:
 *
 * * BlockModelGenerationParser
 * * BlockStateGenerationParser
 * * BlockItemGenerationParser
 * * ItemModelGenerationParser
 * * RecepieGenerationParser
 * * LootTableParser
 * * TagGenerationParser
 *
 * You can add your own parsers to the DatagenModContainer to add your own
 * annotations or handle interface implementations.
 * @see ParsedContainerBase for more information about adding Parsers
 * @see <a href="https://docs.wispforest.io/owo/registration/#blocks">Owo
 *     registration on blocks</a>
 */
public abstract class ParsedBlockRegistryContainer
    extends ParsedContainerBase implements BlockRegistryContainer {
  /**
   * This constructor is used to create a ParsedItemRegistryContainer.
   * @note You need to define a constructor with no parameters that passes a
   * DatagenModContainer to the super constructor. If this is not done the game
   * will start during startup.
   * @param initializer The DatagenModContainer
   */
  protected ParsedBlockRegistryContainer(DatagenModContainer initializer) {
    super(initializer);
    addParser(new BlockDataGenerationParser());
  }

  /**
   * This method is called by the owo registration system after each block was
   * registered. It will call all parsers that are registered.
   */
  @Override
  public void postProcessField(String namespace, Block value, String identifier,
                               Field field) {
    parseFields(namespace, value, identifier, field);
  }
}
