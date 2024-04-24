package de.sakurajin.sakuralib.datagen.v1.Presets.Item;

import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

/**
 * CraftableItem
 */
public abstract class CraftableItem extends Item implements DataGenerateable {

  public CraftableItem(Settings settings) { super(settings); }

  @Override
  public ItemConvertible generateData(DatagenModContainer container,
                                      String identifier) {
    container.generateItemModel(identifier);
    return this;
  }
}
