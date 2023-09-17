package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.ItemConvertible;

public class Fence extends FenceBlock implements DataGenerateable {

    private final String texture;
    private final String plankName;
    public Fence(Settings settings, String texture, String plankName) {
        super(settings);
        this.texture = texture;
        this.plankName = plankName;
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String texture){
        JTextures textures = JModel.textures().var("texture", container.getStringID(texture,"block"));

        for (String model: new String[]{"inventory", "post", "side"}){
            container.RESOURCE_PACK.addModel(
                JModel.model("minecraft:block/fence_" + model).textures(textures),
                container.getSimpleID("block/"+identifier+"_"+model)
            );
        }
    }

    public static void generateBlockState(DatagenModContainer container, String identifier){
        JBlockModel sideModel = JState.model(container.getStringID(identifier+"_side", "block")).uvlock();
        container.RESOURCE_PACK.addBlockState(
            JState.state()
                .add(JState.multipart().addModel(JState.model(container.getStringID(identifier+"_post", "block"))))
                .add(JState.multipart().addModel(sideModel).when(new JWhen().add("north", "true")))
                .add(JState.multipart().addModel(sideModel.clone().y(90)).when(new JWhen().add("east", "true")))
                .add(JState.multipart().addModel(sideModel.clone().y(180)).when(new JWhen().add("south", "true")))
                .add(JState.multipart().addModel(sideModel.clone().y(270)).when(new JWhen().add("west", "true"))),
            container.getSimpleID(identifier)
        );
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        generateBlockModel(container, identifier, this.texture);
        generateBlockState(container, identifier);
        container.createBlockLootTable(identifier, null);

        container.RESOURCE_PACK.addRecipe(container.getSimpleID(identifier),
                JRecipe.shaped(
                        JPattern.pattern("#W#", "#W#"),
                        JKeys.keys().key("W", JIngredient.ingredient().item(container.getStringID(this.plankName))).key("#", JIngredient.ingredient().item("stick")),
                        JResult.stackedResult(container.getStringID(identifier),3)
                )
        );
        container.addTag("minecraft:blocks/fences", identifier);
        container.addTag("minecraft:items/fences", identifier);

        container.generateItemModel(identifier, container.getStringID(identifier + "_inventory", "block"));
        return container.generateBlockItem(this, container.settings());
    }
}
