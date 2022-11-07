/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.Objects;
import java.util.function.Function;

@Deprecated(forRemoval = true)
public enum SkinType {

    NULL("", "", "", Objects::isNull),
    TAIGA("woof:textures/variants/taiga.png", "woof:textures/variants/taiga_angry.png", "woof:textures/variants/taiga_tame.png", (biome) -> biome.isIn(ConventionalBiomeTags.TAIGA)),
    DEFAULT("minecraft:textures/entity/wolf/wolf.png", "minecraft:textures/entity/wolf/wolf_angry.png", "minecraft:textures/entity/wolf/wolf_tame.png", null),
    SNOWY("woof:textures/variants/snowy.png", "woof:textures/variants/snowy_angry.png", "woof:textures/variants/snowy_tame.png", (biome) -> biome.isIn(ConventionalBiomeTags.SNOWY)),
    DESERT("woof:textures/variants/desert.png", "woof:textures/variants/desert_angry.png", "woof:textures/variants/desert_tame.png", (biome) -> biome.isIn(ConventionalBiomeTags.DESERT) || biome.isIn(ConventionalBiomeTags.BADLANDS) || biome.isIn(ConventionalBiomeTags.SAVANNA) || biome.isIn(ConventionalBiomeTags.JUNGLE)),
    MOUNTAIN("woof:textures/variants/mountain.png", "woof:textures/variants/mountain_angry.png", "woof:textures/variants/mountain_tame.png", (biome) -> biome.isIn(ConventionalBiomeTags.MOUNTAIN) || biome.isIn(ConventionalBiomeTags.MOUNTAIN_PEAK) || biome.isIn(ConventionalBiomeTags.MOUNTAIN_SLOPE)),
    SKELETON("woof:textures/variants/skeleton.png", "woof:textures/variants/skeleton.png", "woof:textures/variants/skeleton.png", (biome) -> biome.isIn(ConventionalBiomeTags.IN_NETHER)),
    SWAMP("woof:textures/variants/swamp.png", "woof:textures/variants/swamp_angry.png", "woof:textures/variants/swamp_tame.png", (biome) -> biome.isIn(ConventionalBiomeTags.SWAMP));

    private final String baseTexture;
    private final String angryTexture;
    private final String tameTexture;
    private final Function<RegistryEntry<Biome>, Boolean> spawnConditions;

    SkinType(String baseTexture, String angryTexture, String tameTexture, Function<RegistryEntry<Biome>, Boolean> spawnConditions) {
        this.baseTexture = baseTexture;
        this.angryTexture = angryTexture;
        this.tameTexture = tameTexture;
        this.spawnConditions = spawnConditions;
    }

    public String getAngryTexture() {
        return angryTexture;
    }

    public String getTameTexture() {
        return tameTexture;
    }

    public String getBaseTexture() {
        return baseTexture;
    }

    public Function<RegistryEntry<Biome>, Boolean> getSpawnConditions() {
        return spawnConditions;
    }
}
