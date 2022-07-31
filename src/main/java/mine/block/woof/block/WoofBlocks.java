package mine.block.woof.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WoofBlocks {
    public static final DogBowlBlock DOG_BOWL_BLOCK;

    static {
        DOG_BOWL_BLOCK = Registry.register(Registry.BLOCK, new Identifier("woof:dog_bowl_block"), new DogBowlBlock(AbstractBlock.Settings.of(Material.WOOD)));
    }

    public static void init() {
    }
}
