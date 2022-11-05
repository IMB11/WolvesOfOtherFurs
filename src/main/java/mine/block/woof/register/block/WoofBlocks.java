/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.register.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class WoofBlocks {
    public static final DogBowlBlock DOG_BOWL_BLOCK;
    public static final HashMap<String, Block> DOG_BEDS = new HashMap<>();

    static {
        DOG_BOWL_BLOCK = Registry.register(Registry.BLOCK, new Identifier("woof:dog_bowl_block"), new DogBowlBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD)));

        var dyeItems = Registry.ITEM.stream().filter(DyeItem.class::isInstance);
        for (Item item : dyeItems.toList()) {
            Identifier itemID = item.getRegistryEntry().getKey().get().getValue();
            String name = itemID.getPath().replace("_dye", "");
            DOG_BEDS.put(name, register(name + "_dog_bed", new DogBedBlock(AbstractBlock.Settings.of(Material.WOOL).sounds(BlockSoundGroup.WOOD))));
        }
    }

    private static <T extends Block> T register(String id, T block) {
        Registry.register(Registry.ITEM, new Identifier("woof:" + id), new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        return Registry.register(Registry.BLOCK, new Identifier("woof:" + id), block);
    }

    public static void init() {
    }
}
