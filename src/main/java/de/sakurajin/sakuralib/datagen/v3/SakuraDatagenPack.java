package de.sakurajin.sakuralib.datagen.v3;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Fluent wrapper around {@link FabricDataGenerator} and {@link FabricDataGenerator.Pack}
 * that simplifies composing and registering build-time {@link DataProvider}s.
 */
public class SakuraDatagenPack {
    private final FabricDataGenerator generator;
    private final FabricDataGenerator.Pack pack;

    /**
     * Creates a new pack wrapper for the given generator and pack.
     *
     * @param generator the owning {@link FabricDataGenerator}
     * @param pack      the target {@link FabricDataGenerator.Pack}
     */
    public SakuraDatagenPack(
            @NotNull FabricDataGenerator generator,
            @NotNull FabricDataGenerator.Pack pack
    ) {
        this.generator = Objects.requireNonNull(generator, "generator must not be null");
        this.pack = Objects.requireNonNull(pack, "pack must not be null");
    }

    /**
     * Creates a {@link SakuraDatagenPack} wrapping the default pack of the given generator.
     *
     * @param generator the {@link FabricDataGenerator}
     * @return a wrapper around {@link FabricDataGenerator#createPack()}
     */
    public static SakuraDatagenPack createDefault(@NotNull FabricDataGenerator generator) {
        Objects.requireNonNull(generator, "generator must not be null");
        return new SakuraDatagenPack(generator, generator.createPack());
    }

    /**
     * Creates a {@link SakuraDatagenPack} for a builtin resource pack.
     *
     * @param generator the {@link FabricDataGenerator}
     * @param id        the identifier of the builtin resource pack
     * @return a wrapper around {@link FabricDataGenerator#createBuiltinResourcePack(Identifier)}
     */
    public static SakuraDatagenPack createBuiltinResourcePack(
            @NotNull FabricDataGenerator generator,
            @NotNull Identifier id
    ) {
        Objects.requireNonNull(generator, "generator must not be null");
        Objects.requireNonNull(id, "id must not be null");
        return new SakuraDatagenPack(generator, generator.createBuiltinResourcePack(id));
    }

    /**
     * Returns the underlying {@link FabricDataGenerator}.
     *
     * @return the generator
     */
    public FabricDataGenerator getGenerator() {
        return generator;
    }

    /**
     * Returns the underlying {@link FabricDataGenerator.Pack}.
     *
     * @return the pack
     */
    public FabricDataGenerator.Pack getPack() {
        return pack;
    }

    /**
     * Returns the effective mod ID for this data generator.
     *
     * @return the mod ID
     */
    public String getModId() {
        return generator.getModId();
    }

    /**
     * Returns whether strict datagen validation is enabled.
     *
     * @return {@code true} if strict validation is enabled
     */
    public boolean isStrictValidationEnabled() {
        return generator.isStrictValidationEnabled();
    }

    /**
     * Registers a {@link DataProvider} that only requires a
     * {@link net.fabricmc.fabric.api.datagen.v1.FabricDataOutput}.
     *
     * @param factory the provider factory
     * @param <T>     the provider type
     * @return the created provider instance
     */
    public <T extends DataProvider> T addProvider(@NotNull FabricDataGenerator.Pack.Factory<T> factory) {
        Objects.requireNonNull(factory, "factory must not be null");
        return pack.addProvider(factory);
    }

    /**
     * Registers a {@link DataProvider} that requires both a
     * {@link net.fabricmc.fabric.api.datagen.v1.FabricDataOutput} and registry lookup future.
     *
     * @param factory the registry-dependent provider factory
     * @param <T>     the provider type
     * @return the created provider instance
     */
    public <T extends DataProvider> T addProvider(@NotNull FabricDataGenerator.Pack.RegistryDependentFactory<T> factory) {
        Objects.requireNonNull(factory, "factory must not be null");
        return pack.addProvider(factory);
    }

    /**
     * Fluent variant of {@link #addProvider(FabricDataGenerator.Pack.Factory)}.
     *
     * @param factory the provider factory
     * @return this pack wrapper for method chaining
     */
    public SakuraDatagenPack withProvider(@NotNull FabricDataGenerator.Pack.Factory<?> factory) {
        addProvider(factory);
        return this;
    }

    /**
     * Fluent variant of {@link #addProvider(FabricDataGenerator.Pack.RegistryDependentFactory)}.
     *
     * @param factory the registry-dependent provider factory
     * @return this pack wrapper for method chaining
     */
    public SakuraDatagenPack withProvider(@NotNull FabricDataGenerator.Pack.RegistryDependentFactory<?> factory) {
        addProvider(factory);
        return this;
    }

    /**
     * Applies a modular configurator function to this pack wrapper.
     * Useful for splitting provider registrations across feature modules.
     *
     * @param configurator the configurator to run
     * @return this pack wrapper for method chaining
     */
    public SakuraDatagenPack apply(@NotNull Consumer<SakuraDatagenPack> configurator) {
        Objects.requireNonNull(configurator, "configurator must not be null");
        configurator.accept(this);
        return this;
    }

    /**
     * Creates a {@link SakuraDatagenPack} for a builtin resource pack using the same generator.
     *
     * @param id the identifier of the builtin resource pack
     * @return a new wrapper for the builtin resource pack
     */
    public SakuraDatagenPack createBuiltinResourcePack(@NotNull Identifier id) {
        return createBuiltinResourcePack(generator, id);
    }
}
