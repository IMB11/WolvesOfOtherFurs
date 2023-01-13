/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.register.item;

import mine.block.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WoofItems {
//    public static final BlockItem DOG_BOWL_BLOCK;

    static {
//        DOG_BOWL_BLOCK = Registry.register(Registries.ITEM, new Identifier("woof:dog_bowl_block"), new BlockItem(WoofBlocks.DOG_BOWL_BLOCK, new FabricItemSettings()));
    }

    public static void init() {
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((e) -> e.add(DOG_BOWL_BLOCK.asItem()));
    }
}
