package de.sakurajin.sakuralib.arrp.v1.worldgen.processor;

import com.google.gson.JsonObject;

public class InputPredicate {
    public static class inputPredicateType {
        protected final String predicate_type;
        protected inputPredicateType(String predicate_type){
            this.predicate_type = predicate_type;
        }

        public static final inputPredicateType ALWAYS_TRUE = new inputPredicateType("minecraft:always_true");
        public static final inputPredicateType BLOCK_MATCH = new inputPredicateType("minecraft:block_match");
        public static final inputPredicateType BLOCKSTATE_MATCH = new inputPredicateType("minecraft:blockstate_match");
        public static final inputPredicateType RANDOM_BLOCK_MATCH = new inputPredicateType("minecraft:random_block_match");
        public static final inputPredicateType RANDOM_BLOCKSTATE_MATCH = new inputPredicateType("minecraft:random_blockstate_match");
        public static final inputPredicateType TAG_MATCH = new inputPredicateType("minecraft:tag_match");
    }

    protected final inputPredicateType type;
    protected final JsonObject input_predicate = new JsonObject();

    public InputPredicate(inputPredicateType type){
        this.type = type;
        input_predicate.addProperty("predicate_type", type.predicate_type);
    }

    public InputPredicate block(String block){
        if(type != inputPredicateType.BLOCK_MATCH && type != inputPredicateType.RANDOM_BLOCK_MATCH) throw new IllegalArgumentException("This predicate type does not support the block property");
        input_predicate.addProperty("block", block);
        return this;
    }

    public InputPredicate blockstate(JsonObject blockstate){
        if(type != inputPredicateType.BLOCKSTATE_MATCH && type != inputPredicateType.RANDOM_BLOCKSTATE_MATCH) throw new IllegalArgumentException("This predicate type does not support the blockstate property");
        input_predicate.add("blockstate", blockstate);
        return this;
    }

    public InputPredicate probability(double probability){
        if(type != inputPredicateType.RANDOM_BLOCK_MATCH && type != inputPredicateType.RANDOM_BLOCKSTATE_MATCH) throw new IllegalArgumentException("This predicate type does not support the probability property");
        input_predicate.addProperty("probability", probability);
        return this;
    }

    public InputPredicate tag(String tag){
        if(type != inputPredicateType.TAG_MATCH) throw new IllegalArgumentException("This predicate type does not support the tag property");
        input_predicate.addProperty("tag", tag);
        return this;
    }

    public JsonObject toJson() {
        return input_predicate.deepCopy();
    }

    public String toString() {
        return input_predicate.toString();
    }

}
