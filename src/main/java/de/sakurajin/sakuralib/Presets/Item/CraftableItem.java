package de.sakurajin.sakuralib.Presets.Item;

import de.sakurajin.sakuralib.util.DatagenModContainer;
import de.sakurajin.sakuralib.Interfaces.DataGenerateable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

/**
 * @name CraftableItem
 */
public abstract class CraftableItem extends Item implements DataGenerateable {

    public CraftableItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        container.generateItemModel(identifier);
        return this;
    }
}
