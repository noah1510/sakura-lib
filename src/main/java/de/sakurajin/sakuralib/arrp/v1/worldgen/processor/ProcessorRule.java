package de.sakurajin.sakuralib.arrp.v1.worldgen.processor;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class ProcessorRule implements Comparable<ProcessorRule> {

    protected final JsonObject rule = new JsonObject();

    public ProcessorRule locationPredicate(LocationPredicate predicate){
        rule.add("location_predicate", predicate.toJson());
        return this;
    }

    public ProcessorRule inputPredicate(InputPredicate predicate){
        rule.add("input_predicate", predicate.toJson());
        return this;
    }

    public ProcessorRule positionPredicate(PositionPredicate predicate){
        rule.add("position_predicate", predicate.toJson());
        return this;
    }

    public ProcessorRule outputState(String name){
        return outputState(name, null);
    }

    public ProcessorRule outputState(String name, JsonObject properties){
        rule.add("output_state", new JsonObject());
        rule.getAsJsonObject("output_state").addProperty("Name", name);
        if (properties != null) {
            rule.getAsJsonObject("output_state").add("Properties", properties);
        }
        return this;
    }

    public ProcessorRule appendLoot(String lootTable){
        rule.add("block_entity_modifier", new JsonObject());
        rule.getAsJsonObject("block_entity_modifier").addProperty("type", "minecraft:append_loot");
        rule.getAsJsonObject("block_entity_modifier").addProperty("loot_table", lootTable);
        return this;
    }

    public ProcessorRule appendStatic(JsonObject data){
        rule.add("block_entity_modifier", new JsonObject());
        rule.getAsJsonObject("block_entity_modifier").addProperty("type", "minecraft:append_static");
        rule.getAsJsonObject("block_entity_modifier").add("data", data);
        return this;
    }

    public ProcessorRule clearBlockEntity(){
        rule.add("block_entity_modifier", new JsonObject());
        rule.getAsJsonObject("block_entity_modifier").addProperty("type", "minecraft:clear");
        return this;
    }

    static public ProcessorRule replaceBlock(String inputBlock, String outputBlock, double probability){
        return new ProcessorRule()
                .locationPredicate(new LocationPredicate(LocationPredicate.locationPredicateType.ALWAYS_TRUE))
                .positionPredicate(new PositionPredicate(PositionPredicate.positionPredicateType.ALWAYS_TRUE))
                .inputPredicate(new InputPredicate(InputPredicate.inputPredicateType.RANDOM_BLOCK_MATCH).block(inputBlock).probability(probability))
                .outputState(outputBlock);
    }

    static public ProcessorRule replaceBlockWithLoot(String inputBlock, String outputBlock, double probability, String lootTable){
        return new ProcessorRule()
                .locationPredicate(new LocationPredicate(LocationPredicate.locationPredicateType.ALWAYS_TRUE))
                .positionPredicate(new PositionPredicate(PositionPredicate.positionPredicateType.ALWAYS_TRUE))
                .inputPredicate(new InputPredicate(InputPredicate.inputPredicateType.RANDOM_BLOCK_MATCH).block(inputBlock).probability(probability))
                .outputState(outputBlock)
                .appendLoot(lootTable);
    }

    static public ProcessorRule addLootTable(String inputBlock, String lootTable){
        return new ProcessorRule()
                .locationPredicate(new LocationPredicate(LocationPredicate.locationPredicateType.ALWAYS_TRUE))
                .positionPredicate(new PositionPredicate(PositionPredicate.positionPredicateType.ALWAYS_TRUE))
                .inputPredicate(new InputPredicate(InputPredicate.inputPredicateType.BLOCK_MATCH).block(inputBlock))
                .outputState(inputBlock)
                .appendLoot(lootTable);
    }

    public JsonObject toJson(){
        return rule.deepCopy();
    }

    public String toString(){
        return rule.toString();
    }

    @Override
    public int compareTo(@NotNull ProcessorRule processorRule) {
        return this.toString().compareTo(processorRule.toString());
    }
}
