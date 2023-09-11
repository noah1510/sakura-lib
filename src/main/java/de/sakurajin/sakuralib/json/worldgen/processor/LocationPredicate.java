package de.sakurajin.sakuralib.json.worldgen.processor;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public class LocationPredicate implements Comparable<LocationPredicate>{
    @Override
    public int compareTo(@NotNull LocationPredicate locationPredicate) {
        return location_predicate.toString().compareTo(locationPredicate.toString());
    }

    public static class locationPredicateType {
        protected final String predicate_type;
        protected locationPredicateType(String predicate_type){
            this.predicate_type = predicate_type;
        }

        public static final locationPredicateType ALWAYS_TRUE = new locationPredicateType("minecraft:always_true");
        public static final locationPredicateType BLOCK_MATCH = new locationPredicateType("minecraft:block_match");
        public static final locationPredicateType BLOCKSTATE_MATCH = new locationPredicateType("minecraft:blockstate_match");
        public static final locationPredicateType RANDOM_BLOCK_MATCH = new locationPredicateType("minecraft:random_block_match");
        public static final locationPredicateType TAG_MATCH = new locationPredicateType("minecraft:tag_match");
    }

    protected JsonObject location_predicate = new JsonObject();
    protected final locationPredicateType type;
    public LocationPredicate(locationPredicateType type){
        this.type = type;
        location_predicate.addProperty("predicate_type", type.predicate_type);
    }

    public LocationPredicate block(String block){
        if(type != locationPredicateType.BLOCK_MATCH && type != locationPredicateType.RANDOM_BLOCK_MATCH) throw new IllegalArgumentException("This predicate type does not support the block property");
        location_predicate.addProperty("block", block);
        return this;
    }

    public LocationPredicate blockstate(JsonObject blockstate){
        if(type != locationPredicateType.BLOCKSTATE_MATCH) throw new IllegalArgumentException("This predicate type does not support the blockstate property");
        location_predicate.add("blockstate", blockstate);
        return this;
    }

    public LocationPredicate probability(double probability){
        if(type != locationPredicateType.RANDOM_BLOCK_MATCH) throw new IllegalArgumentException("This predicate type does not support the probability property");
        location_predicate.addProperty("probability", probability);
        return this;
    }

    public LocationPredicate tag(String tag){
        if(type != locationPredicateType.TAG_MATCH) throw new IllegalArgumentException("This predicate type does not support the tag property");
        location_predicate.addProperty("tag", tag);
        return this;
    }

    public JsonObject toJson(){
        return location_predicate.deepCopy();
    }

    public String toString(){
        return location_predicate.toString();
    }

}
