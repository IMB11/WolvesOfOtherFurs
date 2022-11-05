/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.register.item;

import mine.block.woof.register.block.WoofBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WoofItems {
    public static final BlockItem DOG_BOWL_BLOCK;

    static {
        DOG_BOWL_BLOCK = Registry.register(Registry.ITEM, new Identifier("woof:dog_bowl_block"), new BlockItem(WoofBlocks.DOG_BOWL_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    public static void init() {
    }
}
