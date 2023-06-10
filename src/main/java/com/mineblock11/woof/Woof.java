/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package com.mineblock11.woof;

import com.mineblock11.woof.api.WoofAPI;
import com.mineblock11.woof.register.WoofRegistries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registries.ITEM.getKey(),new Identifier("woof:meats"));

    public static Identifier id(String name) {
        return new Identifier("woof", name);
    }

    @Override
    public void onInitialize() {
        WoofAPI.initialize();
        WoofRegistries.initialize();
    }
}
