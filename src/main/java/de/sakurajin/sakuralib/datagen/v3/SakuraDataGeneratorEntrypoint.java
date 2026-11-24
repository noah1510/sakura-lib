package de.sakurajin.sakuralib.datagen.v3;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.JsonKeySortOrderCallback;
import net.minecraft.registry.RegistryBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Reusable base {@link DataGeneratorEntrypoint} for mods building on SakuraLib's
 * build-time data generation foundation ({@code fabric-data-generation-api-v1}).
 *
 * <p>Subclasses register providers by overriding {@link #configurePack(SakuraDatagenPack)}
 * and can optionally override {@link #configureBuiltinPacks(FabricDataGenerator)},
 * {@link #configureRegistries(RegistryBuilder)}, and
 * {@link #configureJsonKeySortOrders(JsonKeySortOrderCallback)}.</p>
 *
 * <p>Register the concrete subclass under the {@code fabric-datagen} entrypoint key
 * in {@code fabric.mod.json}.</p>
 */
public abstract class SakuraDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        SakuraDatagenPack pack = SakuraDatagenPack.createDefault(fabricDataGenerator);
        configurePack(pack);
        configureBuiltinPacks(fabricDataGenerator);
    }

    /**
     * Configures the mod's default data generation {@link SakuraDatagenPack}.
     *
     * @param pack the default pack wrapper for the mod
     */
    protected abstract void configurePack(@NotNull SakuraDatagenPack pack);

    /**
     * Optional hook for creating and configuring builtin resource packs via
     * {@link SakuraDatagenPack#createBuiltinResourcePack(FabricDataGenerator, net.minecraft.util.Identifier)}
     * or {@link FabricDataGenerator#createBuiltinResourcePack(net.minecraft.util.Identifier)}.
     *
     * @param generator the active {@link FabricDataGenerator}
     */
    protected void configureBuiltinPacks(@NotNull FabricDataGenerator generator) {
    }

    @Override
    public void buildRegistry(@NotNull RegistryBuilder registryBuilder) {
        configureRegistries(registryBuilder);
    }

    /**
     * Optional hook for registering dynamic registry bootstraps (such as worldgen registries).
     *
     * @param registryBuilder the {@link RegistryBuilder} provided by Fabric datagen
     */
    protected void configureRegistries(@NotNull RegistryBuilder registryBuilder) {
    }

    @Override
    public void addJsonKeySortOrders(@NotNull JsonKeySortOrderCallback callback) {
        configureJsonKeySortOrders(callback);
    }

    /**
     * Optional hook for configuring custom JSON key sort orders in generated files.
     *
     * @param callback the {@link JsonKeySortOrderCallback} provided by Fabric datagen
     */
    protected void configureJsonKeySortOrders(@NotNull JsonKeySortOrderCallback callback) {
    }
}
