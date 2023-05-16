package mine.block.woof.datagen.providers;

import mine.block.woof.register.block.DogBedBlock;
import mine.block.woof.register.block.DogBowlBlock;
import mine.block.woof.register.block.WoofBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class WoofBlockLootProvider extends FabricBlockLootTableProvider {
    public WoofBlockLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (DogBedBlock value : WoofBlocks.DOG_BEDS.values()) {
            addDrop(value, value.asItem());
        }
        for (DogBowlBlock value : WoofBlocks.DOG_BOWLS.values()) {
            addDrop(value, value.asItem());
        }
    }
}
