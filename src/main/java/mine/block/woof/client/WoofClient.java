/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof.client;

import mine.block.woof.client.render.WolfModRenderLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Items;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class WoofClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack.getItem() instanceof DyeableItem leadDye) {
                return leadDye.getColor(stack);
            } else return DyeColor.WHITE.getMapColor().color;
        }, Items.LEAD);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof WolfEntityRenderer) {
                registrationHelper.register(new WolfModRenderLayer((FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>>) entityRenderer));
            }
        });
    }
}
