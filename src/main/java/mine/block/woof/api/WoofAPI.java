package mine.block.woof.api;

import mine.block.woof.Woof;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class WoofAPI {
    public static final SimpleRegistry<Variant> VARIANT_REGISTRY = FabricRegistryBuilder.createSimple(Variant.class, new Identifier("woof_variants")).buildAndRegister();



    /**
     * Register a new wolf variant.
     * @param identifier The identifier of the variant.
     * @param biomePredicate Biome predicate stating which biome it should be in.
     * @return The variant registered.
     */
    public static Variant registerWolfVariant(Identifier identifier, Function<RegistryEntry<Biome>, Boolean> biomePredicate) {
        return Registry.register(VARIANT_REGISTRY, identifier, new Variant(identifier, biomePredicate));
    }

    @ApiStatus.Internal
    public static void initialize() {
        BiomeModifications.create(Woof.id("wolf_additions")).add(ModificationPhase.POST_PROCESSING, biomeSelectionContext -> true, (biomeSelectionContext, biomeModificationContext) -> {
            for (Map.Entry<RegistryKey<Variant>, Variant> variantRegistryKey : WoofAPI.VARIANT_REGISTRY.getEntrySet()) {
                if(variantRegistryKey.getValue().biomePredicate().apply(biomeSelectionContext.getBiomeRegistryEntry())) continue;
                biomeModificationContext.getSpawnSettings().removeSpawnsOfEntityType(EntityType.WOLF);
                if(biomeSelectionContext.hasTag(ConventionalBiomeTags.IN_NETHER)) {
                    biomeModificationContext.getSpawnSettings().addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 1, 2));
                }
                biomeModificationContext.getSpawnSettings().addSpawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 1, 2));
            }
        });
    }
}
