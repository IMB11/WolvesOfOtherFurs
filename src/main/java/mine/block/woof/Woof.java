package mine.block.woof;

import mine.block.woof.api.Variant;
import mine.block.woof.api.WoofAPI;
import mine.block.woof.entity.WolfDataTracker;
import mine.block.woof.entity.WolfVariantTracker;
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
import net.minecraft.util.registry.RegistryKey;

import java.util.Map;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM_KEY, new Identifier("woof:meats"));

    public static Identifier id(String name) {
        return new Identifier("woof", name);
    }

    @Override
    public void onInitialize() {
        WoofRegistries.initialize();
        WoofAPI.initialize();

        WoofPackets.SEND_DOG_COMMAND.register();
    }
}
