/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.datagen.providers;

import com.mineblock11.woof.register.block.DogBedBlock;
import com.mineblock11.woof.register.block.DogBowlBlock;
import com.mineblock11.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class WoofRecipeProvider extends FabricRecipeProvider {
    public WoofRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        for (DogBedBlock value : WoofBlocks.DOG_BEDS.values()) {
            Block wood = Registries.BLOCK.get(value.getParentWoodType());
            Block wool = Registries.BLOCK.get(value.getParentWoolType());
            CraftingRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, value)
                            .input('#', wood).input('_', wool)
                            .pattern("   ")
                            .pattern("#_#")
                            .pattern("###").criterion("has_wood", conditionsFromItem(Registries.BLOCK.get(value.getParentWoodType()))).criterion("has_wool", conditionsFromItem(Registries.BLOCK.get(value.getParentWoolType())));

            recipe.offerTo(exporter);
        }

        for (DogBowlBlock value : WoofBlocks.DOG_BOWLS.values()) {
            Identifier slab = value.getParentWoodType().withPath(value.getParentWoodType().getPath().replace("_log", "") + "_slab");
            Block wood = Registries.BLOCK.get(slab);

            if(slab.getPath().contains("crimson")) {
                wood = Blocks.CRIMSON_SLAB;
            }
            if(slab.getPath().contains("warped")) {
                wood = Blocks.WARPED_SLAB;
            }

            CraftingRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, value)
                            .input('#', wood).input('_', Blocks.SMOOTH_STONE)
                            .pattern("   ")
                            .pattern("# #")
                            .pattern("___").criterion("has_wood", conditionsFromItem(Registries.BLOCK.get(value.getParentWoodType())));

            recipe.offerTo(exporter);
        }
    }
}
