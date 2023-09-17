package de.sakurajin.sakuralib.datagen.v1.Presets.Blocks;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.DataGenerateable;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.JsonHelper;

public class Door extends DoorBlock implements DataGenerateable {
    private final String textureBaseName;
    private final String plankName;

    public Door(Settings settings, BlockSetType blockSetType, String textureBaseName, String plankName) {
        super(settings, blockSetType);
        this.textureBaseName = textureBaseName;
        this.plankName = plankName;
    }

    public static void generateBlockModel(DatagenModContainer container, String identifier, String textureBaseName){
        String textureBottom = container.getStringID(textureBaseName + "_bottom", "block");
        String textureTop = container.getStringID(textureBaseName + "_top", "block");
        JTextures textures = JModel.textures().var("bottom", textureBottom).var("top", textureTop);
        String[] parts = new String[]{
            "bottom_left", "bottom_left_open",
            "bottom_right", "bottom_right_open",
            "top_left", "top_left_open",
            "top_right", "top_right_open"
        };

        for (String part : parts) {
            container.RESOURCE_PACK.addModel(
                JModel.model().parent("minecraft:block/door_" + part).textures(textures),
                container.getSimpleID("block/"+identifier + "_" + part)
            );
        }
    }

    private static String getModelName(String identifier, boolean isTop, boolean isLeft, boolean open){
        return "block/" + identifier + "_" + (isTop ? "top" : "bottom") + "_" + (isLeft ? "left" : "right") + (open ? "_open" : "");
    }

    private static String getPropertyString(String direction, boolean isUpper, boolean isLeft, boolean open){
        return "facing=" + direction + ",half=" + (isUpper ? "upper" : "lower") + ",hinge=" + (isLeft ? "left" : "right") + ",open=" + open;
    }

    private static int getRotation(String direction, boolean isLeft, boolean open){
        int angle;
        switch (direction){
            case("east") -> angle = 0;
            case("south") -> angle = 90;
            case("west") -> angle = 180;
            case("north") -> angle = 270;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        if (open){ angle += isLeft ? 90 : -90; }
        while (angle < 0){ angle += 360; }
        while (angle >= 360){ angle -= 360; }

        return angle;
    }

    public static void generateBlockState(DatagenModContainer container, String identifier){
        String[] directions = new String[]{"north", "south", "east", "west"};
        JVariant variant = JState.variant();
        for(String direction : directions){
            for (boolean isUpper : new boolean[]{true, false}) {
                for (boolean isLeft : new boolean[]{true, false}) {
                    for (boolean open : new boolean[]{true, false}) {
                        String property = getPropertyString(direction, isUpper, isLeft, open);
                        String model = getModelName(identifier, isUpper, isLeft, open);
                        int rotation = getRotation(direction, isLeft, open);
                        if(rotation != 0){
                            variant.put(property, JState.model(container.getSimpleID(model)).y(rotation));
                        }else{
                            variant.put(property, JState.model(container.getSimpleID(model)));
                        }
                    }
                }
            }

        }
        container.RESOURCE_PACK.addBlockState(JState.state(variant), container.getSimpleID(identifier));
    }

    protected DatagenModContainer.BlockLootOptions getLootOptions(DatagenModContainer container, String identifier){
        DatagenModContainer.BlockLootOptions options = new DatagenModContainer.BlockLootOptions();
        options.conditionAdder = (JEntry) -> addExtraConditions(container, identifier, JEntry);
        return options;
    }

    @Override
    public ItemConvertible generateData(DatagenModContainer container, String identifier) {
        generateBlockModel(container, identifier, this.textureBaseName);
        generateBlockState(container, identifier);

        container.createBlockLootTable(identifier, getLootOptions(container, identifier));

        container.RESOURCE_PACK.addRecipe(container.getSimpleID(identifier),
                JRecipe.shaped(
                        JPattern.pattern("##", "##", "##"),
                        JKeys.keys().key("#", JIngredient.ingredient().item(container.getStringID(this.plankName))),
                        JResult.stackedResult(container.getStringID(identifier), 3)
                )
        );

        container.addTag("minecraft:blocks/doors", identifier);
        container.addTag("minecraft:items/doors", identifier);


        container.generateItemModel(identifier, "minecraft:item/generated", container.getStringID(identifier, "item"));

        return container.generateBlockItem(this, container.settings());
    }

    public JEntry addExtraConditions(DatagenModContainer container, String identifier, JEntry entry){
        return entry.condition(JLootTable
            .predicate("minecraft:block_state_property")
            .parameter("block", container.getStringID(identifier))
            .parameter("properties", JsonHelper.deserialize("{\"half\":\"lower\"}"))
        );
    }
}
