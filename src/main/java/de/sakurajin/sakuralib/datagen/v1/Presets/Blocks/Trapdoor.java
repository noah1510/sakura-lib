package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.item.ItemConvertible;

public class Trapdoor extends TrapdoorBlock implements DataGenerateable {
    private final String plankName;
    private final String textureBaseName;
    public Trapdoor(Settings settings, BlockSetType blockSetType, String textureBaseName, String plankName) {
        super(settings, blockSetType);
        this.plankName = plankName;
        this.textureBaseName = textureBaseName;
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String textureBaseName){
        JTextures textures = JModel.textures().var("texture", container.getStringID(textureBaseName, "block"));
        String[] parts = new String[]{"bottom", "open", "top"};

        for (String part : parts) {
            container.RESOURCE_PACK.addModel(
                JModel.model().parent("minecraft:block/template_trapdoor_" + part).textures(textures),
                container.getSimpleID("block/"+identifier + "_" + part)
            );
        }
    }

    private static JBlockModel getModelName(String identifier, String direction, boolean isTop, boolean open){
        if(!open){return JState.model(identifier + (isTop ? "_top" : "_bottom"));}

        int angle;
        switch (direction){
            case("east") -> angle = 90;
            case("south") -> angle = 180;
            case("west") -> angle = 270;
            case("north") -> angle = 0;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        return JState.model(identifier + "_open").y(angle);
    }

    private static String getPropertyString(String direction, boolean isUpper, boolean open){
        return "facing=" + direction + ",half=" + (isUpper ? "top" : "bottom") + ",open=" + open;
    }

    public static void generateBlockState(DatagenModContainer container, String identifier){
        String[] directions = new String[]{"north", "south", "east", "west"};
        JVariant variant = JState.variant();
        String modelBaseId = container.getStringID(identifier, "block");
        for(String direction : directions){
            variant.put(getPropertyString(direction, false, false), getModelName(modelBaseId, direction, false, false));
            variant.put(getPropertyString(direction, false, true), getModelName(modelBaseId, direction, false, true));
            variant.put(getPropertyString(direction, true, false), getModelName(modelBaseId, direction, true, false));
            variant.put(getPropertyString(direction, true, true), getModelName(modelBaseId, direction, true, true));
        }
        container.RESOURCE_PACK.addBlockState(JState.state(variant), container.getSimpleID(identifier));
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        generateBlockModel(container, identifier, this.textureBaseName);
        generateBlockState(container, identifier);
        container.createBlockLootTable(identifier, null);

        container.RESOURCE_PACK.addRecipe(container.getSimpleID(identifier),
                JRecipe.shaped(
                        JPattern.pattern("###", "###"),
                        JKeys.keys().key("#", JIngredient.ingredient().item(container.getStringID(this.plankName))),
                        JResult.stackedResult(container.getStringID(identifier), 2)
                )
        );

        container.addTag("minecraft:blocks/trapdoors", identifier);
        container.addTag("minecraft:items/trapdoors", identifier);

        container.generateItemModel(identifier, container.getStringID(identifier+"_bottom", "block"));
        return container.generateBlockItem(this, container.settings());
    }

}
