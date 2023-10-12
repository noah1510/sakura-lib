package de.sakurajin.sakuralib.arrp.v1.worldgen.processor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.sakurajin.sakuralib.datagen.v1.DatagenModContainer;
import de.sakurajin.sakuralib.util.v1.SakuraJsonHelper;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;

public class JProcessor {
    public static class ProcessorType{
        public final String type;
        protected ProcessorType(String type){
            this.type = type;
        }
        public static final ProcessorType RULE = new ProcessorType("minecraft:rule");
        public static final ProcessorType BLOCK_ROT = new ProcessorType("minecraft:block_rot");
        public static final ProcessorType BLOCK_AGE = new ProcessorType("minecraft:block_age");
        public static final ProcessorType BLOCK_IGNORE = new ProcessorType("minecraft:block_ignore");
        public static final ProcessorType GRAVITY = new ProcessorType("minecraft:gravity");
        public static final ProcessorType PROTECTED_BLOCKS = new ProcessorType("minecraft:protected_blocks");
        public static final ProcessorType BLACKSTONE_REPLACE = new ProcessorType("minecraft:blackstone_replace");
        public static final ProcessorType LAVA_SUBMERGED = new ProcessorType("minecraft:lava_submerged_block");
        public static final ProcessorType CAPPED = new ProcessorType("minecraft:capped");
        public static final ProcessorType NOP = new ProcessorType("minecraft:nop");
    }

    protected final ProcessorType type;
    protected final JsonObject processor = new JsonObject();

    public JProcessor(ProcessorType type){
        this.type = type;
        processor.addProperty("processor_type", type.type);

        if(type == ProcessorType.RULE) {
            processor.add("rules", new JsonArray());
        }
    }

    public static JProcessor byRule(){
        return new JProcessor(ProcessorType.RULE);
    }

    public JProcessor rule(ProcessorRule rule){
        if (type != ProcessorType.RULE) throw new IllegalArgumentException("Processor type is not rule");
        JsonHelper.getArray(processor, "rules").add(rule.toJson());
        return this;
    }

    public String toString(){
        return SakuraJsonHelper.toPrettyJson(toJson());
    }

    public JsonElement toJson(){
        JsonObject rootObject = new JsonObject();
        JsonArray rootArray = new JsonArray();
        rootArray.add(processor);
        rootObject.add("processors", rootArray);
        return rootObject;
    }

    public void addToResourcePack(DatagenModContainer container, String outputName){
        container.RESOURCE_PACK.addData(
            container.getSimpleID("worldgen/processor_list/" + outputName + ".json"),
            this.toString().getBytes()
        );
    }

    public static JsonObject createProcessorList(JProcessor... processors){
        JsonObject rootObject = new JsonObject();
        JsonArray rootArray = new JsonArray();
        for (JProcessor processor : processors) {
            rootArray.add(processor.processor);
        }
        rootObject.add("processors", rootArray);
        return rootObject;
    }

    public static JsonObject createProcessorList(ArrayList<JProcessor> processors){
        return createProcessorList(processors.toArray(JProcessor[]::new));
    }

    public static void addToResourcePack(DatagenModContainer container, String outputName, JProcessor... processors){
        JsonObject rootObject = createProcessorList(processors);
        container.RESOURCE_PACK.addData(
            container.getSimpleID("worldgen/processor_list/" + outputName + ".json"),
            rootObject.toString().getBytes()
        );
    }
}
