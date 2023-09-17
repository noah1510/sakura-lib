package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.Map;

public class Slab extends SlabBlock implements DataGenerateable {
    private final String texture_bottom;
    private final String texture_top;
    private final String texture_side;
    private final String texture_double;
    private final String baseBlock;
    private final boolean stonecuttable;

    public Slab(FabricBlockSettings settings, String baseBlock, String[] textures){
        this(settings, baseBlock, true, textures);
    }

    public Slab(FabricBlockSettings settings, String baseBlock, boolean stonecuttable, String[] textures){
        super(settings);
        this.baseBlock = baseBlock;
        this.stonecuttable = stonecuttable;

        if (textures.length == 1) {
            this.texture_bottom = textures[0];
            this.texture_top = textures[0];
            this.texture_side = textures[0];
            this.texture_double = textures[0];
        }else if (textures.length == 2) {
            this.texture_bottom = textures[0];
            this.texture_top = textures[0];
            this.texture_side = textures[1];
            this.texture_double = textures[1];
        }else if (textures.length == 4){
            this.texture_bottom = textures[0];
            this.texture_top = textures[1];
            this.texture_side = textures[2];
            this.texture_double = textures[3];
        }else{
            throw new IllegalArgumentException("Slabs need 1, 2 or 4 textures");
        }
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String texture_bottom, String texture_top, String texture_side, String texture_double) {
        Map<String, String> textures = Map.of(
                "top", texture_top,
                "bottom", texture_bottom,
                "side", texture_side
        );
        container.generateBlockModel(
                identifier,
                textures,
                "minecraft:block/slab"
        );

        container.generateBlockModel(
                identifier+"_double",
                Map.of(
                        "all", texture_double
                ),
                "minecraft:block/cube_all"
        );

        container.generateBlockModel(
                identifier+"_top",
                textures,
                "minecraft:block/slab_top"
        );
    }

    public static void generateBlockState(DatagenModContainer container, String identifier) {
        String modelBasePath = container.MOD_ID + ":block/";
        container.RESOURCE_PACK.addBlockState(JState.state(JState.variant()
                .put("type=bottom", JState.model(modelBasePath+identifier))
                .put("type=double", JState.model(modelBasePath+identifier+"_double"))
                .put("type=top", JState.model(modelBasePath+identifier+"_top"))
        ), new Identifier(container.MOD_ID, identifier));
    }

    public static void generateRecepie(DatagenModContainer container, String identifier, String baseBlock, Slab slabBlock) {
        if(baseBlock == null) return;
        if(slabBlock == null) return;
        String baseBlockID = container.getStringID(baseBlock);
        Identifier blockItemID = container.getSimpleID(identifier);

        container.RESOURCE_PACK.addRecipe(
            new Identifier(container.MOD_ID, identifier+"_from_blocks"),
            JRecipe.shaped(
                JPattern.pattern("###"),
                JKeys.keys().key("#", JIngredient.ingredient().item(baseBlockID)),
                JResult.stackedResult(blockItemID.toString(), 6)
        ));

        container.RESOURCE_PACK.addRecipe(
            new Identifier(container.MOD_ID, identifier+"_from_slab"),
            JRecipe.shaped(
                JPattern.pattern("#", "#"),
                JKeys.keys().key("#", JIngredient.ingredient().item(blockItemID.toString())),
                JResult.result(baseBlockID)
        ));

        if(slabBlock.stonecuttable){
            container.RESOURCE_PACK.addRecipe(
                new Identifier(container.MOD_ID, identifier+"_cut"),
                JRecipe.stonecutting(
                    JIngredient.ingredient().item(baseBlockID),
                    JResult.stackedResult(blockItemID.toString(), 2)
            ));
        }
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        container.addTag("minecraft:blocks/slabs", identifier);
        container.addTag("minecraft:items/slabs", identifier);

        generateBlockModel(container, identifier, texture_bottom, texture_top, texture_side, texture_double);
        generateBlockState(container, identifier);
        generateRecepie(container, identifier, baseBlock, this);

        container.generateBlockItemModel(identifier);
        container.createBlockLootTable(identifier, null);

        return container.generateBlockItem(this, container.settings());
    }
}
