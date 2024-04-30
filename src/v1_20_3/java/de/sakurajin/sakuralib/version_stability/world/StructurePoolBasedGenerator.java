package de.sakurajin.sakuralib.version_stability.world;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.alias.StructurePoolAliasLookup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class StructurePoolBasedGenerator extends net.minecraft.structure.pool.StructurePoolBasedGenerator{
    static public Optional<Structure.StructurePosition> generate_compat(
            Structure.Context context,
            RegistryEntry<StructurePool> structurePool,
            Optional<Identifier> id,
            int size,
            BlockPos pos,
            boolean useExpansionHack,
            Optional<Heightmap.Type> projectStartToHeightmap,
            int maxDistanceFromCenter
    ) {
        return net.minecraft.structure.pool.StructurePoolBasedGenerator.generate(
                context,
                structurePool,
                id,
                size,
                pos,
                useExpansionHack,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                StructurePoolAliasLookup.EMPTY
        );
    }
}
