/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.register;

import mine.block.woof.Woof;
import mine.block.woof.api.WoofAPI;
import mine.block.woof.api.WoofDogGoalCallback;
import mine.block.woof.commands.DogCommand;
import mine.block.woof.commands.actions.BarkCommand;
import mine.block.woof.commands.actions.GoAwayCommand;
import mine.block.woof.commands.actions.HowlCommand;
import mine.block.woof.commands.actions.JumpCommand;
import mine.block.woof.commands.generic.PetCommand;
import mine.block.woof.entity.DogEatOutBowlGoal;
import mine.block.woof.entity.DogSitOnBlockGoal;
import mine.block.woof.entity.WolfDataTracker;
import mine.block.woof.entity.WolfVariantTracker;
import mine.block.woof.register.block.WoofBlocks;
import mine.block.woof.register.item.WoofItems;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class WoofRegistries {
    public static HashMap<Identifier, DogCommand> DOG_COMMAND_REGISTRY = new HashMap<>();

    public static void initialize() {
        WoofBlocks.init();
        WoofItems.init();

        TrackedDataHandlerRegistry.register(WolfDataTracker.SKIN_TYPE_TRACKER_ENUM);
        TrackedDataHandlerRegistry.register(WolfVariantTracker.VARIANT_TRACKER);

        DogCommand[] dogCommands = new DogCommand[]{new GoAwayCommand(), new PetCommand(), new BarkCommand(), new HowlCommand(), new JumpCommand()};

        for (DogCommand dogCommand : dogCommands) {
            DOG_COMMAND_REGISTRY.put(dogCommand.getID(), dogCommand);
        }

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


        // Builtin Goals

        WoofDogGoalCallback.EVENT.register((goalSelector, wolfEntity) -> {
            goalSelector.add(7, new DogSitOnBlockGoal(wolfEntity, 0.8));
            goalSelector.add(4, new DogEatOutBowlGoal(wolfEntity, 0.9));
        });
    }
}
