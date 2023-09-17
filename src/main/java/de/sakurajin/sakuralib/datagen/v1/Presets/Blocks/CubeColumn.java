package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import net.devtech.arrp.json.blockstate.JState;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CubeColumn extends PillarBlock implements DataGenerateable {
    private final String texture_end;
    private final String texture_side;

    public CubeColumn(FabricBlockSettings settings, String texture_end, String texture_side) {
        super(settings);
        this.texture_end = texture_end;
        this.texture_side = texture_side;
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String texture_end, String texture_side) {
        Map<String, String> textures = Map.of(
                "end", texture_end,
                "side", texture_side
        );
        container.generateBlockModel(
                identifier,
                textures,
                "minecraft:block/cube_column"
        );

        container.generateBlockModel(
                identifier+"_horizontal",
                textures,
                "minecraft:block/cube_column_horizontal"
        );
    }

    public static void generateBlockState(DatagenModContainer container, String identifier) {
        String modelBasePath = container.MOD_ID + ":block/";
        container.RESOURCE_PACK.addBlockState(JState.state(JState.variant()
                .put("axis=x", JState.model(modelBasePath+identifier+"_horizontal").x(90).y(90))
                .put("axis=y", JState.model(modelBasePath+identifier))
                .put("axis=z", JState.model(modelBasePath+identifier+"_horizontal").x(90))
        ), new Identifier(container.MOD_ID, identifier));
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        container.generateBlockItemModel(identifier);
        generateBlockModel(container, identifier, texture_end, texture_side);
        generateBlockState(container, identifier);
        container.createBlockLootTable(identifier, null);

        return container.generateBlockItem(this, container.settings());
    }
}
