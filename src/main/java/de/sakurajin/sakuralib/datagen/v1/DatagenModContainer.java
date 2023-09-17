package de.sakurajin.sakuralib.datagen.v1;

import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.util.v1.TagIdentifier;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DatagenModContainer{
    public final String MOD_ID;
    public final RuntimeResourcePack RESOURCE_PACK;
    public final Logger LOGGER;

    @Nullable
    public final OwoItemGroup GROUP;

    @Nullable
    public final RegistryKey<ItemGroup> VANILLA_GROUP_KEY;

    private final HashMap<String, ArrayList<String>> tags = new HashMap<>();

    public DatagenModContainer(String MOD_ID){this(MOD_ID, null, RRPCallback.AFTER_VANILLA);}

    public DatagenModContainer(String MOD_ID, Supplier<io.wispforest.owo.itemgroup.Icon> groupIconSupplier){this(MOD_ID, groupIconSupplier, RRPCallback.AFTER_VANILLA);}

    public DatagenModContainer(
            String MOD_ID,
            @NotNull
            net.fabricmc.fabric.api.event.Event<RRPCallback> event
    ){
        this(MOD_ID, null, event);
    }

    public DatagenModContainer(
            String MOD_ID,
            Supplier<io.wispforest.owo.itemgroup.Icon> groupIconSupplier,
            @NotNull
            net.fabricmc.fabric.api.event.Event<RRPCallback> event
    ){
        this.MOD_ID = MOD_ID;
        RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID+":resources");
        LOGGER = LoggerFactory.getLogger(MOD_ID);

        if(groupIconSupplier == null){
            GROUP = null;
            VANILLA_GROUP_KEY = null;
        }else{
            GROUP = OwoItemGroup.builder(new Identifier(MOD_ID, MOD_ID), groupIconSupplier).build();
            var key = Registries.ITEM_GROUP.getKey(GROUP);
            if(key.isEmpty()){
                throw new RuntimeException("Failed to register item group for mod "+MOD_ID);
            }
            VANILLA_GROUP_KEY = key.get();
        }

        event.register(a -> a.add(RESOURCE_PACK));
    }

    public OwoItemSettings settings(){
        return settings(true);
    }

    public OwoItemSettings settings(boolean hasGroup){
        OwoItemSettings settings = new OwoItemSettings();
        if (hasGroup){
            settings.group(GROUP);
        }
        return settings;
    }

    public <T> void registerContainer(
            @NotNull Class<? extends io.wispforest.owo.registration.reflect.AutoRegistryContainer<T>> clazz,
            boolean recurseIntoInnerClasses
    ){
        FieldRegistrationHandler.register(clazz, MOD_ID, recurseIntoInnerClasses);
    }

    public void addTag(String tag, String... items){
        String tagID = getStringID(tag);
        if(!tags.containsKey(tagID)){
            tags.put(tagID, new ArrayList<>());
        }
        for(String item : items){
            tags.get(tagID).add(getStringID(item));
        }
    }

    public void addTags(HashMap<String, ArrayList<String>> tags){
        for(Map.Entry<String, ArrayList<String>> entry : tags.entrySet()){
            for(String item : entry.getValue()){
                addTag(entry.getKey(), item);
            }
        }
    }

    public void registerAllTags(){
        //first generate all tags to have them at all
        for(Map.Entry<String, ArrayList<String>> entry : tags.entrySet()){
            JTag tag = new JTag();
            try {
                RESOURCE_PACK.addTag(getSimpleID(entry.getKey()), tag);
            }catch (Exception e) {
                LOGGER.error("Failed to register empty tag "+entry.getKey(), e);
                throw e;
            }
        }

        //now regenerate the tags with the actual data
        for(Map.Entry<String, ArrayList<String>> entry : tags.entrySet()){
            JTag tag = new JTag();
            try {
                for (String id : entry.getValue()) {
                    tag.add(getSimpleID(id));
                }
                RESOURCE_PACK.addTag(getSimpleID(entry.getKey()), tag);
            }catch (Exception e) {
                LOGGER.error("Failed to register tag "+entry.getKey() + " with the following data:" + entry.getValue(), e);
                throw e;
            }
        }
    }

    public String getStringID(String name){
        return getSimpleID(name, null).toString();
    }

    public String getStringID(String name, String type){
        return getSimpleID(name, type).toString();
    }

    public Identifier getSimpleID(String name){
        return getSimpleID(name, null);
    }

    public Identifier getSimpleID(String name, String type){
        if(name.contains(":")){return new TagIdentifier(name);}

        if(type != null){
            if(name.startsWith("#")){
                name = name.substring(1);
                type = "#" + type;
            }
            name = type+"/"+name;
        }

        return TagIdentifier.ofDefault(name, MOD_ID);
    }

    public void generateBlockItemModel(String name){
        String parent = getStringID(name, "block");
        generateItemModel(name, parent, null);
    }

    public void generateBlockItemModel(String name, String texture){
        String parent = getStringID(name, "block");
        generateItemModel(name, parent, getStringID(texture, "block"));
    }

    public void generateItemModel(String name){
        generateItemModel(name, "minecraft:item/generated", getStringID(name, "item"));
    }

    public void generateItemModel(String name, String parent){
        generateItemModel(name, parent, null);
    }

    public void generateItemModel(String name, String parent, String texture){
        Identifier ItemID = getSimpleID("item/"+name);

        JTextures textures = null;
        if(texture != null){
            textures = new JTextures().var("layer0", getStringID(texture, "item"));
        }

        RESOURCE_PACK.addModel(new JModel().parent(parent).textures(textures), ItemID);
    }

    public void generateBlockModel(String name, Map<String, String> textures, String parent){
        Identifier BlockID = getSimpleID("block/"+name);
        JTextures texture = new JTextures();
        for (Map.Entry<String, String> entry : textures.entrySet()) {
            texture.var(entry.getKey(), getStringID(entry.getValue(), "block"));
        }

        RESOURCE_PACK.addModel((new JModel().parent(parent).textures(texture)), BlockID);
    }

    public void generateBlockState(String name){
        RESOURCE_PACK.addBlockState(JState.state(JState.variant()
                .put("", JState.model(getStringID(name, "block")))
        ), getSimpleID(name));
    }

    public void generateBlockStateOrientable(String name){
        generateBlockStateOrientable(name, new String[]{name});
    }
    public void generateBlockStateOrientable(String name, String[] alternatives){
        JVariant variants = JState.variant();

        for(String alternative : alternatives){
            Identifier model = getSimpleID("block/" + alternative);
            variants.put("facing=east", JState.model(model).y(90));
            variants.put("facing=west", JState.model(model).y(270));
            variants.put("facing=south", JState.model(model).y(180));
            variants.put("facing=north", JState.model(model));
        }

        RESOURCE_PACK.addBlockState(JState.state(variants), getSimpleID(name));
    }

    public ItemConvertible generateBlockItem(Block block, OwoItemSettings settings){
        return new BlockItem(block, settings);
    }

    public JEntry addSilkTouchRequirement(JEntry entry){
        JsonObject enchantments = JsonHelper.deserialize("{\"enchantments\":[{\"enchantment\":\"minecraft:silk_touch\"}]}");

        return entry.condition(
                JLootTable.predicate("minecraft:match_tool").parameter("predicate", enchantments)
        );
    }

    @FunctionalInterface
    public interface LootTableConditionAdder{
        public JEntry addCondition(JEntry entry);
    }

    public static class BlockLootOptions{
        public String alternativeDrop;
        public int alternativeDropCount;
        public boolean needsSilkTouch;
        public boolean survivesExplosion = true;
        public LootTableConditionAdder conditionAdder;

        public BlockLootOptions(){
            this(false, null, 0);
        }
        public BlockLootOptions(boolean needsSilkTouch){
            this(needsSilkTouch, null, 0);
        }
        public BlockLootOptions(boolean needsSilkTouch, String alternativeDrop, int alternativeDropCount){
            this.alternativeDrop = alternativeDrop;
            this.alternativeDropCount = alternativeDropCount;
            this.needsSilkTouch = needsSilkTouch;
        }
    }

    public void createBlockLootTable(String name, BlockLootOptions options){
        if(options == null){options = new BlockLootOptions();}

        JEntry entry = JLootTable.entry().type("minecraft:alternatives");

        JEntry baseDropEntry = JLootTable.entry()
                .type("minecraft:item")
                .name(getStringID(name))
                .function(JLootTable.function("minecraft:set_count").parameter("count", 1));

        if (!options.survivesExplosion) {
            baseDropEntry = baseDropEntry.condition(JLootTable.predicate("minecraft:survives_explosion"));
        }
        if (options.needsSilkTouch) {
            baseDropEntry = addSilkTouchRequirement(baseDropEntry);
        }
        if (options.conditionAdder != null) {
            baseDropEntry = options.conditionAdder.addCondition(baseDropEntry);
        }

        entry = entry.child(baseDropEntry);

        if(options.alternativeDrop != null){
            var alternative = JLootTable.entry().type("minecraft:item");

            if (options.conditionAdder != null) {
                alternative = options.conditionAdder.addCondition(alternative);
            }

            alternative = alternative.name(getStringID(options.alternativeDrop)).function(
                    JLootTable.function("minecraft:set_count").parameter("count", options.alternativeDropCount)
            );

            entry = entry.child(alternative);
        }

        RESOURCE_PACK.addLootTable(
                getSimpleID("blocks/"+name),
                JLootTable.loot("minecraft:block").pool(JLootTable.pool().rolls(1).bonus(0).entry(entry))
        );
    }

}
