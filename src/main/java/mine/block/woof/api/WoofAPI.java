/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.api;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class WoofAPI {
    public static final SimpleRegistry<Variant> VARIANT_REGISTRY = FabricRegistryBuilder.createSimple(Variant.class, new Identifier("woof_variants")).buildAndRegister();
    public static final HashSet<Identifier> VALID_WOOL_TYPES = new HashSet<>();
    public static final HashSet<Identifier> VALID_WOOD_TYPES = new HashSet<>();

    /**
     * Register a new wolf variant.
     *
     * @param identifier     The identifier of the variant.
     * @param biomePredicate Biome predicate stating which biome it should be in.
     * @return The variant registered.
     */
    public static Variant registerWolfVariant(Identifier identifier, Function<RegistryEntry<Biome>, Boolean> biomePredicate) {
        return Registry.register(VARIANT_REGISTRY, identifier, new Variant(identifier, biomePredicate));
    }



    public static List<Identifier> getValidWoolTypes() {
        return VALID_WOOL_TYPES.parallelStream().toList();
    }

    private static void populateWoolTypesVanilla() {
        for (DyeColor value : DyeColor.values()) {
            VALID_WOOL_TYPES.add(new Identifier("minecraft:" + value.getName() + "_wool"));
        }
    }

    public static List<Identifier> getValidWoodTypes() {
        return VALID_WOOD_TYPES.parallelStream().toList();
    }

    private static Identifier of(String path) {
        return new Identifier(path);
    }

    private static void populateWoodTypesVanilla() {
        VALID_WOOD_TYPES.addAll(List.of(
                of("minecraft:oak_log"),
                of("minecraft:spruce_log"),
                of("minecraft:birch_log"),
                of("minecraft:jungle_log"),
                of("minecraft:acacia_log"),
                of("minecraft:dark_oak_log"),
                of("minecraft:mangrove_log"),
                of("minecraft:crimson_stem"),
                of("minecraft:warped_stem")
                )
        );
    }

    @ApiStatus.Internal
    public static void initialize() {

        // Init blocks class.
        System.out.println(Blocks.JUKEBOX);

        populateWoolTypesVanilla();
        populateWoodTypesVanilla();

        for (Map.Entry<RegistryKey<Variant>, Variant> variantRegistryKey : WoofAPI.VARIANT_REGISTRY.getEntrySet()) {
            BiomeModifications.addSpawn(biomeSelectionContext -> variantRegistryKey.getValue().canSpawnIn(biomeSelectionContext.getBiomeRegistryEntry()), SpawnGroup.CREATURE, EntityType.WOLF, 5, 1, 2);
        }

        for (WoofAddonEntrypoint entrypoint : FabricLoader.getInstance().getEntrypoints("woof", WoofAddonEntrypoint.class)) {
            entrypoint.initialize();
        }
    }
}
