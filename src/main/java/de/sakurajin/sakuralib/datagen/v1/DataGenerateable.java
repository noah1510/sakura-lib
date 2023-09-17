package de.sakurajin.sakuralib.datagen.v1;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

public interface DataGenerateable {
    default ItemConvertible generateData(DatagenModContainer container, String identifier){
        if(this instanceof Block){
            return container.generateBlockItem((Block)this, container.settings());
        }
        if(this instanceof ItemConvertible){
            return (ItemConvertible)this;
        }
        return null;
    }
}
