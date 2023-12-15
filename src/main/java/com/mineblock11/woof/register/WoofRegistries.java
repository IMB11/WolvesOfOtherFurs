/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.register;

import com.mineblock11.woof.Woof;
import com.mineblock11.woof.api.WoofAPI;
import com.mineblock11.woof.api.WoofDogGoalCallback;
import com.mineblock11.woof.entity.DogEatOutBowlGoal;
import com.mineblock11.woof.entity.DogSitOnBlockGoal;
import com.mineblock11.woof.entity.WolfVariantTracker;
import com.mineblock11.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;

public class WoofRegistries {

    public static void initialize() {
        WoofBlocks.init();
//        WoofItems.init();

        TrackedDataHandlerRegistry.register(WolfVariantTracker.VARIANT_TRACKER);

        // Builtin variants.
        WoofAPI.registerWolfVariant(Woof.id("default"), (biome) -> false);
        WoofAPI.registerWolfVariant(Woof.id("taiga"), (biome) -> biome.isIn(ConventionalBiomeTags.TAIGA));
        WoofAPI.registerWolfVariant(Woof.id("snowy"), (biome) -> biome.isIn(ConventionalBiomeTags.SNOWY));
        WoofAPI.registerWolfVariant(Woof.id("desert"), (biome) -> biome.isIn(ConventionalBiomeTags.DESERT) || biome.isIn(ConventionalBiomeTags.BADLANDS) || biome.isIn(ConventionalBiomeTags.SAVANNA) || biome.isIn(ConventionalBiomeTags.JUNGLE));
        WoofAPI.registerWolfVariant(Woof.id("mountain"), (biome) -> biome.isIn(ConventionalBiomeTags.MOUNTAIN) || biome.isIn(ConventionalBiomeTags.MOUNTAIN_PEAK) || biome.isIn(ConventionalBiomeTags.MOUNTAIN_SLOPE));
        WoofAPI.registerWolfVariant(Woof.id("skeleton"), (biome) -> biome.matchesId(new Identifier("soul_sand_valley")));
        WoofAPI.registerWolfVariant(Woof.id("swamp"), (biome) -> biome.isIn(ConventionalBiomeTags.SWAMP));
        WoofAPI.registerWolfVariant(Woof.id("sculk"), (biome) -> biome.matchesId(new Identifier("deep_dark")));
        WoofAPI.registerWolfVariant(Woof.id("dripstone"), (biome) -> biome.matchesId(new Identifier("dripstone_caves")));
        WoofAPI.registerWolfVariant(Woof.id("lush"), (biome) -> biome.matchesId(new Identifier("lush_caves")));

        // Builtin Goals + Vanilla
        WoofDogGoalCallback.EVENT.register((goalSelector, targetSelector, wolfEntity) -> {
            goalSelector.add(2, new DogSitOnBlockGoal(wolfEntity, 0.8));
            goalSelector.add(2, new DogEatOutBowlGoal(wolfEntity, 0.9));
        });
    }
}
