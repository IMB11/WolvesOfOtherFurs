package mine.block.woof.client;

import mine.block.woof.Woof;
import mine.block.woof.item.DyeableLeadItem;
import mine.block.woof.item.WoofItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class WoofClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if(stack.getItem() instanceof DyeableLeadItem leadDye) {
                return leadDye.getColor(stack);
            } else return DyeColor.WHITE.getMapColor().color;
        }, WoofItems.DYEABLE_LEAD_ITEM);
    }
}
