/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.datagen.providers;

import mine.block.woof.register.block.DogBedBlock;
import mine.block.woof.register.block.DogBowlBlock;
import mine.block.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class WoofRecipeProvider extends FabricRecipeProvider {
    public WoofRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
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
            CraftingRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, value)
                            .input('#', wood).input('_', Blocks.SMOOTH_STONE)
                            .pattern("   ")
                            .pattern("# #")
                            .pattern("___").criterion("has_wood", conditionsFromItem(Registries.BLOCK.get(value.getParentWoodType())));

            recipe.offerTo(exporter);
        }
    }
}
