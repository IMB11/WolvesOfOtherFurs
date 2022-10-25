package mine.block.woof;

import mine.block.woof.entity.WolfDataTracker;
import mine.block.woof.register.WoofRegistries;
import mine.block.woof.server.WoofPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM_KEY, new Identifier("woof:meats"));

    @Override
    public void onInitialize() {
        TrackedDataHandlerRegistry.register(WolfDataTracker.SKIN_TYPE_TRACKER_ENUM);
        WoofRegistries.initialize();

        WoofPackets.SEND_DOG_COMMAND.register();

        BiomeModifications.addSpawn(ctx -> {
            var biome = ctx.getBiomeRegistryEntry();
            return biome.isIn(ConventionalBiomeTags.FOREST)
                    || biome.isIn(ConventionalBiomeTags.DESERT)
                    || biome.isIn(ConventionalBiomeTags.BADLANDS)
                    || biome.isIn(ConventionalBiomeTags.SAVANNA)
                    || biome.isIn(ConventionalBiomeTags.JUNGLE)
                    || biome.isIn(ConventionalBiomeTags.MOUNTAIN)
                    || biome.isIn(ConventionalBiomeTags.MOUNTAIN_PEAK)
                    || biome.isIn(ConventionalBiomeTags.MOUNTAIN_SLOPE)
                    || biome.isIn(ConventionalBiomeTags.SNOWY)
                    || biome.isIn(ConventionalBiomeTags.TAIGA)
                    || biome.matchesId(new Identifier("soul_sand_valley"))
                    || biome.isIn(ConventionalBiomeTags.SWAMP);
        }, SpawnGroup.CREATURE, EntityType.WOLF, 5, 1, 2);
    }
}
