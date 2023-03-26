/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.register.block;

import mine.block.woof.api.WoofAPI;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WoofBlocks {
    public static final HashMap<Identifier, DogBedBlock> DOG_BEDS = new HashMap<>();
    public static final HashMap<Identifier, DogBowlBlock> DOG_BOWLS = new HashMap<>();
    public static final TagKey<Block> DOG_BOWLS_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier("woof", "dog_bowls"));
    public static final TagKey<Block> DOG_BEDS_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier("woof", "dog_beds"));

    static {
        List<Identifier> validWoolBlocks = WoofAPI.getValidWoolTypes();
        List<Identifier> validWoodBlocks = WoofAPI.getValidWoodTypes();

        HashMap<String, Identifier> woolNames = new HashMap<>();
        HashMap<String, Identifier> woodNames = new HashMap<>();

        for (Identifier validWoodBlock : validWoodBlocks) {
            String val = validWoodBlock.getPath();
            val = val.replace("_log", "");
            val = val.replace("_stem", "");
            woodNames.put(val, validWoodBlock);
        }

        for (Identifier validWoolBlock : validWoolBlocks) {
            String val = validWoolBlock.getPath();
            val = val.replace("_wool", "");
            woolNames.put(val, validWoolBlock);
        }

        woodNames.forEach((String woodName, Identifier fullWoodName) -> {
            woolNames.forEach((String woolName, Identifier fullWoolName) -> {
                DogBedBlock dogBedBlock = register(woodName + "_" + woolName + "_dog_bed", new DogBedBlock(AbstractBlock.Settings.copy(Blocks.RED_WOOL), fullWoodName, fullWoolName));
                DOG_BEDS.put(new Identifier("woof:" + woodName + "_" + woolName + "_dog_bed"), dogBedBlock);
            });
            DogBowlBlock dogBowlBlock = register(woodName + "_dog_bowl", new DogBowlBlock(AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE_SLAB), fullWoodName));
            DOG_BOWLS.put(new Identifier("woof:" + woodName + "_dog_bowl"), dogBowlBlock);
        });
    }

    private static <T extends Block> T register(String id, T block) {
        Registry.register(Registry.ITEM, new Identifier("woof:" + id), new BlockItem(block, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        return Registry.register(Registry.BLOCK, new Identifier("woof:" + id), block);
    }

    public static void init() {

    }
}
