package de.sakurajin.sakuralib.datagen.v1.Presets;

import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.datagen.v1.Presets.Blocks.*;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JIngredients;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GeneratedWoodType {
    @FunctionalInterface
    public interface SettingsOverride{
        FabricBlockSettings override(FabricBlockSettings settings);
    }

    public static class GenerationSettings{
        public HashMap<String, ArrayList<String>> extraTags = new HashMap<>();
        public SettingsOverride[] settingsOverrides = null;
        public FabricBlockSettings blockSettings = null;
        public DatagenModContainer.BlockLootOptions lootOptions = null;

        public GenerationSettings addTags(String tag, String... values){
            if(!extraTags.containsKey(tag)){
                extraTags.put(tag, new ArrayList<>());
            }
            extraTags.get(tag).addAll(Arrays.asList(values));
            return this;
        }

        public GenerationSettings SettingsOverrides(SettingsOverride... settingsOverrides){
            this.settingsOverrides = settingsOverrides;
            return this;
        }

        public GenerationSettings BlockSettings(FabricBlockSettings blockSettings){
            this.blockSettings = blockSettings;
            return this;
        }

        public GenerationSettings setLootOptions(DatagenModContainer.BlockLootOptions lootOptions){
            this.lootOptions = lootOptions;
            return this;
        }

        public GenerationSettings(){}

        public GenerationSettings(DatagenModContainer.BlockLootOptions lootOptions){this.lootOptions = lootOptions;}
        public static GenerationSettings create(){
            return new GenerationSettings();
        }
    }

    public static class GeneratedWoodTypeBuilder{
        private final String name;
        private BlockSetType baseBlockSetType = BlockSetType.OAK;
        private WoodType baseWoodType = WoodType.OAK;
        private String baseWoodTypeString = "minecraft:oak";
        private final ArrayList<SettingsOverride> settingsOverrides = new ArrayList<>();

        private GeneratedWoodTypeBuilder(String name){
            this.name = name;
        }
        public static GeneratedWoodTypeBuilder create(String name){
            return new GeneratedWoodTypeBuilder(name);
        }

        public static GeneratedWoodTypeBuilder create(String name, BlockSetType baseBlockSetType, WoodType baseWoodType, String baseWoodTypeString){
            return create(name).baseBlockSetType(baseBlockSetType).baseWoodType(baseWoodType).baseWoodTypeString(baseWoodTypeString);
        }

        public GeneratedWoodTypeBuilder baseBlockSetType(BlockSetType baseBlockSetType){
            this.baseBlockSetType = baseBlockSetType;
            return this;
        }

        public GeneratedWoodTypeBuilder baseWoodType(WoodType baseWoodType){
            this.baseWoodType = baseWoodType;
            return this;
        }

        public GeneratedWoodTypeBuilder baseWoodTypeString(String baseWoodTypeString){
            this.baseWoodTypeString = baseWoodTypeString;
            return this;
        }

        public GeneratedWoodTypeBuilder addSettingsOverride(SettingsOverride settingsOverride){
            this.settingsOverrides.add(settingsOverride);
            return this;
        }

        public GeneratedWoodTypeBuilder addSettingsOverrides(SettingsOverride... settingsOverrides){
            this.settingsOverrides.addAll(Arrays.asList(settingsOverrides));
            return this;
        }

        public GeneratedWoodType build(DatagenModContainer container){
            var blockSetType = BlockSetTypeBuilder.copyOf(baseBlockSetType).register(container.getSimpleID(name));
            var woodType = WoodTypeBuilder.copyOf(baseWoodType).register(container.getSimpleID(name), blockSetType);
            return new GeneratedWoodType(this.name, blockSetType, woodType, baseWoodTypeString, settingsOverrides, container);
        }
    }

    private final String name;
    private final String baseWoodType;
    private final String textureFolder;
    public final WoodType woodType;
    public final BlockSetType blockSetType;
    public final ArrayList<SettingsOverride> settingsOverrides = new ArrayList<>();

    //generate blocks to prevent generation of the same block multiple times
    private CubeColumn log = null;
    private CubeAll planks = null;
    private Slab slabs = null;
    private Stairs stairs = null;
    private Fence fence = null;
    private FenceGate fenceGate = null;
    private Door door = null;
    private Trapdoor trapdoor = null;

    public GeneratedWoodType(
        String name,
        BlockSetType blockSetType,
        WoodType woodType,
        String baseWoodType,
        ArrayList<SettingsOverride> settingsOverrides,
        DatagenModContainer container
    ){
        this.name = name;
        this.blockSetType = blockSetType;
        this.woodType = woodType;
        this.textureFolder = container.getStringID(name, "block");
        this.baseWoodType = baseWoodType;
        this.settingsOverrides.addAll(settingsOverrides);
    }

    private String getTextureName(String suffix){
        return textureFolder + "/" + suffix;
    }

    private FabricBlockSettings getSettingsOf(String suffix, GenerationSettings settings){
        FabricBlockSettings blockSettings;
        if(settings == null || settings.blockSettings == null){
            blockSettings = FabricBlockSettings.copyOf(Registries.BLOCK.get(new Identifier(baseWoodType+"_"+suffix)));
        }else{
            blockSettings = settings.blockSettings;
        }

        blockSettings = blockSettings.sounds(this.woodType.soundType());

        for (SettingsOverride override : settingsOverrides) {
            blockSettings = override.override(blockSettings);
        }

        if(settings != null && settings.settingsOverrides != null){
            for (SettingsOverride override : settings.settingsOverrides) {
                blockSettings = override.override(blockSettings);
            }
        }

        return blockSettings;
    }

    public CubeColumn getLog(GenerationSettings genSettings){
        if(log == null){
            var blockSettings = getSettingsOf("log", genSettings);
            log = new CubeColumn(blockSettings, getTextureName("log_top"), getTextureName("log")){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {

                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/enderman_holdable", identifier);
                    container.addTag("minecraft:blocks/logs", identifier);
                    container.addTag("minecraft:blocks/logs_that_burn", identifier);
                    container.addTag("minecraft:items/logs", identifier);
                    container.addTag("minecraft:items/logs_that_burn", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    var item = super.generateData(container, identifier);
                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return log;
    }

    public CubeAll getPlanks(GenerationSettings genSettings){
        if (planks == null) {
            var settings = getSettingsOf("planks", genSettings);
            planks = new CubeAll(settings, getTextureName("planks")) {
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/enderman_holdable", identifier);
                    container.addTag("minecraft:blocks/planks", identifier);
                    container.addTag("minecraft:items/planks", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    container.RESOURCE_PACK.addRecipe(
                            container.getSimpleID(identifier),
                            JRecipe.shapeless(
                                    JIngredients.ingredients().add(JIngredient.ingredient().item(container.getStringID(name + "_log"))),
                                    JResult.result(container.getStringID(identifier))
                            )
                    );

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return planks;
    }

    public Slab getSlabs(GenerationSettings genSettings){
        if (slabs == null){
            var settings = getSettingsOf("slab", genSettings);
            slabs = new Slab(settings, name+"_planks", false, new String[]{getTextureName("planks")}){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/wodden_slabs", identifier);
                    container.addTag("minecraft:items/wooden_slabs", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return slabs;
    }

    public Stairs getStairs(GenerationSettings genSettings){
        if (stairs == null){
            if(planks == null){
                throw new IllegalStateException("Stairs can only be generated after planks have been generated");
            }
            var settings = getSettingsOf("stairs", genSettings);
            stairs = new Stairs(settings, planks, name+"_planks", new String[]{getTextureName("planks")}, false){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/wodden_stairs", identifier);
                    container.addTag("minecraft:items/wooden_stairs", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }

            };
        }
        return stairs;
    }

    public Fence getFence(GenerationSettings genSettings){
        if (fence == null){
            if(planks == null){
                throw new IllegalStateException("Fence can only be generated after planks have been generated");
            }
            var settings = getSettingsOf("fence", genSettings);
            fence = new Fence(settings, getTextureName("planks"), name+"_planks"){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/wodden_fences", identifier);
                    container.addTag("minecraft:items/wooden_fences", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return fence;
    }

    public FenceGate getFenceGate(GenerationSettings genSettings){
        if (fenceGate == null){
            if(planks == null){
                throw new IllegalStateException("FenceGate can only be generated after planks have been generated");
            }
            var settings = getSettingsOf("fence_gate", genSettings);
            fenceGate = new FenceGate(settings, this.woodType, getTextureName("planks"), name+"_planks"){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return fenceGate;
    }

    public Door getDoor(GenerationSettings genSettings){
        if (door == null){
            if(planks == null){
                throw new IllegalStateException("Door can only be generated after planks have been generated");
            }
            var settings = getSettingsOf("door", genSettings);
            door = new Door(settings, this.blockSetType, getTextureName("door"), name+"_planks"){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/wodden_doors", identifier);
                    container.addTag("minecraft:items/wooden_doors", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        genSettings.lootOptions.conditionAdder = (entry) -> addExtraConditions(container, identifier, entry);
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return door;
    }

    public Trapdoor getTrapdoor(GenerationSettings genSettings){
        if (trapdoor == null){
            if(planks == null){
                throw new IllegalStateException("Trapdoor can only be generated after planks have been generated");
            }
            var settings = getSettingsOf("trapdoor", genSettings);
            trapdoor = new Trapdoor(settings, this.blockSetType, getTextureName("trapdoor"), name+"_planks"){
                @Override
                public ItemConvertible generateData(DatagenModContainer container, String identifier) {
                    var item = super.generateData(container, identifier);
                    container.addTag("minecraft:blocks/mineable/axe", identifier);
                    container.addTag("minecraft:blocks/wodden_trapdoors", identifier);
                    container.addTag("minecraft:items/wooden_trapdoors", identifier);
                    container.addTag(name, identifier);
                    container.addTags(genSettings.extraTags);

                    if(genSettings.lootOptions != null){
                        container.createBlockLootTable(identifier, genSettings.lootOptions);
                    }

                    return item;
                }
            };
        }
        return trapdoor;
    }

}
