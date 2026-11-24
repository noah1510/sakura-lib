package de.sakurajin.sakuralib.datagen.v3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Reusable abstract {@link DataProvider} for writing structured JSON assets or data files
 * (such as worldgen definitions, Patchouli documentation, or rich-text language files)
 * with optional Fabric resource load conditions.
 */
public abstract class SakuraJsonDataProvider implements DataProvider {
    protected final FabricDataOutput output;
    protected final DataOutput.OutputType outputType;
    protected final String directoryName;
    private final DataOutput.PathResolver pathResolver;

    /**
     * Creates a new JSON data provider writing to {@code <outputType>/<namespace>/<directoryName>/<path>.json}.
     *
     * @param output        the {@link FabricDataOutput} supplied by Fabric datagen
     * @param outputType    whether files belong under {@code assets} ({@link DataOutput.OutputType#RESOURCE_PACK})
     *                      or {@code data} ({@link DataOutput.OutputType#DATA_PACK})
     * @param directoryName the relative directory under the namespace (for example {@code "worldgen/processor_list"})
     */
    protected SakuraJsonDataProvider(
            @NotNull FabricDataOutput output,
            @NotNull DataOutput.OutputType outputType,
            @NotNull String directoryName
    ) {
        this.output = Objects.requireNonNull(output, "output must not be null");
        this.outputType = Objects.requireNonNull(outputType, "outputType must not be null");
        this.directoryName = Objects.requireNonNull(directoryName, "directoryName must not be null");
        this.pathResolver = output.getResolver(outputType, directoryName);
    }

    /**
     * Generates all JSON entries for this provider by submitting them to {@code jsonOutput}.
     *
     * @param jsonOutput the sink used to emit JSON files
     */
    protected abstract void generate(@NotNull JsonOutput jsonOutput);

    /**
     * Resolves an unqualified path under this mod's namespace.
     *
     * @param path the relative path (without {@code .json})
     * @return an {@link Identifier} in this provider's mod namespace
     */
    protected Identifier modId(@NotNull String path) {
        return new Identifier(output.getModId(), path);
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Set<Identifier> seenIds = new HashSet<>();
        List<CompletableFuture<?>> futures = new ArrayList<>();

        generate((id, json) -> {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(json, "json must not be null");
            if (!seenIds.add(id)) {
                throw new IllegalStateException("Duplicate generated JSON entry '" + id + "' in provider " + getName());
            }
            Path targetPath = pathResolver.resolveJson(id);
            futures.add(DataProvider.writeToPath(writer, json, targetPath));
        });

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    /**
     * Output sink passed to {@link #generate(JsonOutput)} for emitting JSON files.
     */
    @FunctionalInterface
    public interface JsonOutput {
        /**
         * Emits a JSON document at the resolved path for {@code id}.
         *
         * @param id   the resource identifier (without {@code .json})
         * @param json the JSON content to write
         */
        void accept(@NotNull Identifier id, @NotNull JsonElement json);

        /**
         * Emits a {@link JsonObject} with the specified Fabric resource load conditions attached.
         *
         * @param id         the resource identifier (without {@code .json})
         * @param json       the JSON object to write (a defensive copy is not made; conditions are written in place)
         * @param conditions optional resource conditions to attach under {@code fabric:load_conditions}
         */
        default void accept(
                @NotNull Identifier id,
                @NotNull JsonObject json,
                @NotNull ConditionJsonProvider... conditions
        ) {
            SakuraResourceConditions.write(json, conditions);
            accept(id, (JsonElement) json);
        }

        /**
         * Emits a {@link JsonObject} with the specified Fabric resource load conditions attached.
         *
         * @param id         the resource identifier (without {@code .json})
         * @param json       the JSON object to write
         * @param conditions optional collection of resource conditions to attach under {@code fabric:load_conditions}
         */
        default void accept(
                @NotNull Identifier id,
                @NotNull JsonObject json,
                @NotNull Collection<ConditionJsonProvider> conditions
        ) {
            SakuraResourceConditions.write(json, conditions);
            accept(id, (JsonElement) json);
        }
    }
}
