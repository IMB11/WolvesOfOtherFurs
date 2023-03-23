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
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class WoofLootProvider extends FabricBlockLootTableProvider {
    public WoofLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (DogBedBlock block : WoofBlocks.DOG_BEDS.values()) {
            addDrop(block);
        }

        for (DogBowlBlock block : WoofBlocks.DOG_BOWLS.values()) {
            addDrop(block);
        }
    }
}
