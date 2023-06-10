/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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
    }
}
