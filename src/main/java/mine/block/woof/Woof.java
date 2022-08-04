package mine.block.woof;

import mine.block.woof.block.WoofBlocks;
import mine.block.woof.item.WoofItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Woof implements ModInitializer {
    private static final TrackedDataHandler<SkinType> SKIN_TYPE_TRACKER_ENUM = new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, SkinType value) {
            buf.writeEnumConstant(value);
        }

        @Override
        public SkinType read(PacketByteBuf buf) {
            return buf.readEnumConstant(SkinType.class);
        }

        @Override
        public SkinType copy(SkinType value) {
            return SkinType.valueOf(value.name());
        }
    };

    public static TrackedData<SkinType> WOLF_SKIN_TYPE = DataTracker.registerData(WolfEntity.class, SKIN_TYPE_TRACKER_ENUM);
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM_KEY, new Identifier("woof:meats"));


    @Override
    public void onInitialize() {
        TrackedDataHandlerRegistry.register(SKIN_TYPE_TRACKER_ENUM);
        WoofBlocks.init();
        WoofItems.init();

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
                    || biome.isIn(ConventionalBiomeTags.TAIGA);
        }, SpawnGroup.CREATURE, EntityType.WOLF, 5, 1, 2);
    }
}
