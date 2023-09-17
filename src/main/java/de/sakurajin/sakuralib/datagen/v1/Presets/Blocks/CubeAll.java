package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

import java.util.Map;

public class CubeAll extends Block implements DataGenerateable {
    private final String texture;

    public CubeAll(FabricBlockSettings settings, String texture) {
        super(settings);
        this.texture = texture;
    }

    public CubeAll(Block parentBlock, String texture) {
        this(FabricBlockSettings.copyOf(parentBlock), texture);
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String texture) {
        container.generateBlockModel(
            identifier,
            Map.of(
                    "all", texture
            ),
            "minecraft:block/cube_all"
        );
    }


    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        container.generateBlockState(identifier);
        container.generateBlockItemModel(identifier);
        generateBlockModel(container, identifier, texture);
        container.createBlockLootTable(identifier, null);

        return container.generateBlockItem(this, container.settings());
    }
}
