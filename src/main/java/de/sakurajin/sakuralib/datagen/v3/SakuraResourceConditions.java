package de.sakurajin.sakuralib.datagen.v3;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * Convenience wrappers around Fabric's {@code fabric-resource-conditions-v1}
 * ({@link DefaultResourceConditions} and {@link ConditionJsonProvider}),
 * compatible with both Minecraft 1.20.1 and 1.20.3+.
 *
 * <p>Provides uniform factories for composing load conditions and attaching them
 * to generated JSON resources or conditional recipe exporters.</p>
 */
public final class SakuraResourceConditions {

    private SakuraResourceConditions() {
    }

    /**
     * Condition satisfied when all given mod IDs are loaded.
     *
     * @param modIds one or more mod IDs
     * @return a {@code fabric:all_mods_loaded} condition
     */
    public static ConditionJsonProvider allModsLoaded(@NotNull String... modIds) {
        return DefaultResourceConditions.allModsLoaded(modIds);
    }

    /**
     * Condition satisfied when all given mod IDs are loaded.
     *
     * @param modIds non-empty collection of mod IDs
     * @return a {@code fabric:all_mods_loaded} condition
     */
    public static ConditionJsonProvider allModsLoaded(@NotNull Collection<String> modIds) {
        Objects.requireNonNull(modIds, "modIds must not be null");
        return DefaultResourceConditions.allModsLoaded(modIds.toArray(String[]::new));
    }

    /**
     * Condition satisfied when at least one of the given mod IDs is loaded.
     *
     * @param modIds one or more mod IDs
     * @return a {@code fabric:any_mod_loaded} condition
     */
    public static ConditionJsonProvider anyModsLoaded(@NotNull String... modIds) {
        return DefaultResourceConditions.anyModLoaded(modIds);
    }

    /**
     * Condition satisfied when at least one of the given mod IDs is loaded.
     *
     * @param modIds non-empty collection of mod IDs
     * @return a {@code fabric:any_mod_loaded} condition
     */
    public static ConditionJsonProvider anyModsLoaded(@NotNull Collection<String> modIds) {
        Objects.requireNonNull(modIds, "modIds must not be null");
        return DefaultResourceConditions.anyModLoaded(modIds.toArray(String[]::new));
    }

    /**
     * Alias for {@link #anyModsLoaded(String...)} matching Fabric's singular method name.
     *
     * @param modIds one or more mod IDs
     * @return a {@code fabric:any_mod_loaded} condition
     */
    public static ConditionJsonProvider anyModLoaded(@NotNull String... modIds) {
        return anyModsLoaded(modIds);
    }

    /**
     * Condition satisfied when the given single mod ID is loaded.
     *
     * @param modId the required mod ID
     * @return a {@code fabric:all_mods_loaded} condition for {@code modId}
     */
    public static ConditionJsonProvider modLoaded(@NotNull String modId) {
        Objects.requireNonNull(modId, "modId must not be null");
        return allModsLoaded(modId);
    }

    /**
     * Condition satisfied when the given single mod ID is <em>not</em> loaded.
     *
     * @param modId the mod ID that must be absent
     * @return a negated {@code fabric:all_mods_loaded} condition for {@code modId}
     */
    public static ConditionJsonProvider modNotLoaded(@NotNull String modId) {
        return not(modLoaded(modId));
    }

    /**
     * Condition satisfied when each of the passed tags exists and has at least one element.
     *
     * @param tags one or more tags belonging to the same registry
     * @param <T>  the tag entry type
     * @return a {@code fabric:tags_populated} condition
     */
    @SafeVarargs
    public static <T> ConditionJsonProvider tagsPopulated(@NotNull TagKey<T>... tags) {
        return DefaultResourceConditions.tagsPopulated(tags);
    }

    /**
     * Condition satisfied when all child conditions are satisfied.
     *
     * @param conditions one or more child conditions
     * @return a {@code fabric:and} condition
     */
    public static ConditionJsonProvider and(@NotNull ConditionJsonProvider... conditions) {
        return DefaultResourceConditions.and(conditions);
    }

    /**
     * Condition satisfied when all child conditions are satisfied.
     *
     * @param conditions non-empty collection of child conditions
     * @return a {@code fabric:and} condition
     */
    public static ConditionJsonProvider and(@NotNull Collection<ConditionJsonProvider> conditions) {
        Objects.requireNonNull(conditions, "conditions must not be null");
        return DefaultResourceConditions.and(conditions.toArray(ConditionJsonProvider[]::new));
    }

    /**
     * Condition satisfied when at least one child condition is satisfied.
     *
     * @param conditions one or more child conditions
     * @return a {@code fabric:or} condition
     */
    public static ConditionJsonProvider or(@NotNull ConditionJsonProvider... conditions) {
        return DefaultResourceConditions.or(conditions);
    }

    /**
     * Condition satisfied when at least one child condition is satisfied.
     *
     * @param conditions non-empty collection of child conditions
     * @return a {@code fabric:or} condition
     */
    public static ConditionJsonProvider or(@NotNull Collection<ConditionJsonProvider> conditions) {
        Objects.requireNonNull(conditions, "conditions must not be null");
        return DefaultResourceConditions.or(conditions.toArray(ConditionJsonProvider[]::new));
    }

    /**
     * Condition satisfied when the given child condition is <em>not</em> satisfied.
     *
     * @param condition the condition to negate
     * @return a {@code fabric:not} condition
     */
    public static ConditionJsonProvider not(@NotNull ConditionJsonProvider condition) {
        Objects.requireNonNull(condition, "condition must not be null");
        return DefaultResourceConditions.not(condition);
    }

    /**
     * Condition satisfied when all specified feature flags are enabled.
     *
     * @param features one or more feature flags
     * @return a {@code fabric:features_enabled} condition
     */
    public static ConditionJsonProvider featuresEnabled(@NotNull FeatureFlag... features) {
        return DefaultResourceConditions.featuresEnabled(features);
    }

    /**
     * Condition satisfied when all specified items are registered.
     *
     * @param items one or more items
     * @return a {@code fabric:registry_contains} condition targeting the item registry
     */
    public static ConditionJsonProvider itemsRegistered(@NotNull ItemConvertible... items) {
        return DefaultResourceConditions.itemsRegistered(items);
    }

    /**
     * Condition satisfied when the given registry contains all specified entries.
     *
     * @param registry the target registry
     * @param entries  one or more registered entries
     * @param <T>      the registry entry type
     * @return a {@code fabric:registry_contains} condition
     */
    @SafeVarargs
    public static <T> ConditionJsonProvider registryContains(@NotNull Registry<T> registry, @NotNull T... entries) {
        return DefaultResourceConditions.registryContains(registry, entries);
    }

    /**
     * Condition satisfied when all specified registry keys are present in their registry.
     *
     * @param entries one or more registry keys belonging to the same registry
     * @param <T>     the registry entry type
     * @return a {@code fabric:registry_contains} condition
     */
    @SafeVarargs
    public static <T> ConditionJsonProvider registryContains(@NotNull RegistryKey<T>... entries) {
        return DefaultResourceConditions.registryContains(entries);
    }

    /**
     * Condition satisfied when all specified entry identifiers are present in the given registry.
     *
     * @param registry the registry key
     * @param entries  one or more entry identifiers
     * @param <T>      the registry entry type
     * @return a {@code fabric:registry_contains} condition
     */
    public static <T> ConditionJsonProvider registryContains(
            @NotNull RegistryKey<Registry<T>> registry,
            @NotNull Identifier... entries
    ) {
        return DefaultResourceConditions.registryContains(registry, entries);
    }

    /**
     * Writes the given conditions onto {@code target} under {@code fabric:load_conditions}.
     * Does nothing if {@code conditions} is {@code null} or empty.
     *
     * @param target     the target JSON object
     * @param conditions optional conditions to write
     */
    public static void write(@NotNull JsonObject target, @Nullable ConditionJsonProvider... conditions) {
        Objects.requireNonNull(target, "target must not be null");
        if (conditions == null || conditions.length == 0) {
            return;
        }
        ConditionJsonProvider.write(target, conditions);
    }

    /**
     * Writes the given conditions onto {@code target} under {@code fabric:load_conditions}.
     * Does nothing if {@code conditions} is {@code null} or empty.
     *
     * @param target     the target JSON object
     * @param conditions optional collection of conditions to write
     */
    public static void write(@NotNull JsonObject target, @Nullable Collection<ConditionJsonProvider> conditions) {
        Objects.requireNonNull(target, "target must not be null");
        if (conditions == null || conditions.isEmpty()) {
            return;
        }
        ConditionJsonProvider.write(target, conditions.toArray(ConditionJsonProvider[]::new));
    }

    /**
     * Writes the given conditions onto {@code target} under {@code fabric:load_conditions}
     * and returns {@code target} for fluent chaining.
     *
     * @param target     the target JSON object
     * @param conditions optional conditions to write
     * @return {@code target}
     */
    public static JsonObject withConditions(@NotNull JsonObject target, @Nullable ConditionJsonProvider... conditions) {
        write(target, conditions);
        return target;
    }

    /**
     * Writes the given conditions onto {@code target} under {@code fabric:load_conditions}
     * and returns {@code target} for fluent chaining.
     *
     * @param target     the target JSON object
     * @param conditions optional collection of conditions to write
     * @return {@code target}
     */
    public static JsonObject withConditions(
            @NotNull JsonObject target,
            @Nullable Collection<ConditionJsonProvider> conditions
    ) {
        write(target, conditions);
        return target;
    }
}
