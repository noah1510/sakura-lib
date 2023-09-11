package de.sakurajin.sakuralib.json.worldgen.processor;

import com.google.gson.JsonObject;

public class PositionPredicate {
    public static class positionPredicateType {
        protected final String positionPredicateType;
        protected positionPredicateType(String positionPredicateType){
            this.positionPredicateType = positionPredicateType;
        }
        public static final positionPredicateType ALWAYS_TRUE = new positionPredicateType("minecraft:always_true");
        public static final positionPredicateType LINEAR_POS = new positionPredicateType("minecraft:linear_pos");
        public static final positionPredicateType AXIS_ALIGNED_LINEAR_POS = new positionPredicateType("minecraft:axis_aligned_linear_pos");
    }

    protected final positionPredicateType predicate_type;
    protected JsonObject positionPredicate = new JsonObject();

    public PositionPredicate(positionPredicateType type){

        this.predicate_type = type;
        this.positionPredicate.addProperty("predicate_type", type.positionPredicateType);
    }

    public PositionPredicate axis(char axis){
        if(predicate_type != positionPredicateType.AXIS_ALIGNED_LINEAR_POS) throw new IllegalArgumentException("axis can only be set for AXIS_ALIGNED_LINEAR_POS");
        this.positionPredicate.addProperty("axis", axis);
        return this;
    }

    public PositionPredicate minChance(double min_chance){
        if(predicate_type == positionPredicateType.ALWAYS_TRUE) throw new IllegalArgumentException("min_chance can only be set for LINEAR_POS and AXIS_ALIGNED_LINEAR_POS");
        this.positionPredicate.addProperty("min_chance", min_chance);
        return this;
    }

    public PositionPredicate maxChance(double max_chance){
        if(predicate_type == positionPredicateType.ALWAYS_TRUE) throw new IllegalArgumentException("max_chance can only be set for LINEAR_POS and AXIS_ALIGNED_LINEAR_POS");
        this.positionPredicate.addProperty("max_chance", max_chance);
        return this;
    }

    public PositionPredicate minDist(int min_dist){
        if(predicate_type != positionPredicateType.LINEAR_POS) throw new IllegalArgumentException("min_dist can only be set for LINEAR_POS");
        this.positionPredicate.addProperty("min_dist", min_dist);
        return this;
    }

    public PositionPredicate maxDist(int max_dist){
        if(predicate_type != positionPredicateType.LINEAR_POS) throw new IllegalArgumentException("max_dist can only be set for LINEAR_POS");
        this.positionPredicate.addProperty("max_dist", max_dist);
        return this;
    }

    public String toString(){
        return positionPredicate.toString();
    }

    public JsonObject toJson(){
        return positionPredicate.deepCopy();
    }
}
