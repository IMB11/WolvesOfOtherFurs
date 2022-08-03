package mine.block.woof;

import mine.block.woof.block.WoofBlocks;
import mine.block.woof.item.WoofItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Woof implements ModInitializer {
    private static final TrackedDataHandler<SkinType> SKIN_TYPE_TRACKER_ENUM = TrackedDataHandler.ofEnum(SkinType.class);
    public static TrackedData<SkinType> WOLF_SKIN_TYPE = DataTracker.registerData(WolfEntity.class, SKIN_TYPE_TRACKER_ENUM);
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM_KEY, new Identifier("woof:meats"));


    @Override
    public void onInitialize() {
        TrackedDataHandlerRegistry.register(SKIN_TYPE_TRACKER_ENUM);
        WoofBlocks.init();
        WoofItems.init();
    }
}
