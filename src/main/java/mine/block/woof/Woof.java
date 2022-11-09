/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof;

import mine.block.woof.api.WoofAPI;
import mine.block.woof.register.WoofRegistries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM_KEY, new Identifier("woof:meats"));

    public static Identifier id(String name) {
        return new Identifier("woof", name);
    }

    @Override
    public void onInitialize() {
        WoofRegistries.initialize();
        WoofAPI.initialize();
    }
}
