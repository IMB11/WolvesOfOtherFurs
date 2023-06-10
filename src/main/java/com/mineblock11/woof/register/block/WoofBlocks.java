/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.register.block;

import com.mineblock11.woof.api.WoofAPI;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public class WoofBlocks {
    public static final HashMap<Identifier, DogBedBlock> DOG_BEDS = new HashMap<>();
    public static final HashMap<Identifier, DogBowlBlock> DOG_BOWLS = new HashMap<>();
    public static final TagKey<Block> DOG_BOWLS_TAG = TagKey.of(RegistryKeys.BLOCK, new Identifier("woof", "dog_bowls"));
    public static final TagKey<Block> DOG_BEDS_TAG = TagKey.of(RegistryKeys.BLOCK, new Identifier("woof", "dog_beds"));

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
        Registry.register(Registries.ITEM, new Identifier("woof:" + id), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, new Identifier("woof:" + id), block);
    }

    public static void init() {
        for (Block value : DOG_BEDS.values()) {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register((e) -> e.add(value.asItem()));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((e) -> e.add(value.asItem()));
        }

        for (Block value : DOG_BOWLS.values()) {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((e) -> e.add(value.asItem()));
        }
    }
}
