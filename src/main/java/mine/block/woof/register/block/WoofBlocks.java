/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.register.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class WoofBlocks {
    public static final DogBowlBlock DOG_BOWL_BLOCK;
    public static final HashMap<String, Block> DOG_BEDS = new HashMap<>();

    static {
        DOG_BOWL_BLOCK = Registry.register(Registries.BLOCK, new Identifier("woof:dog_bowl_block"), new DogBowlBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD)));

        var dyeItems = Registries.ITEM.stream().filter(DyeItem.class::isInstance);
        for (Item item : dyeItems.toList()) {
            Identifier itemID = item.getRegistryEntry().getKey().get().getValue();
            String name = itemID.getPath().replace("_dye", "");
            DOG_BEDS.put(name, register(name + "_dog_bed", new DogBedBlock(AbstractBlock.Settings.of(Material.WOOL).sounds(BlockSoundGroup.WOOD))));
        }
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
    }
}
