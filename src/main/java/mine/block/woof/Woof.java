/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof;

import mine.block.woof.api.WoofAPI;
import mine.block.woof.register.WoofRegistries;
import mine.block.woof.server.SendDogCommandC2S;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registries.ITEM.getKey(),new Identifier("woof:meats"));

    public static Identifier id(String name) {
        return new Identifier("woof", name);
    }

    public static final SendDogCommandC2S sendDogCommandC2S = new SendDogCommandC2S();

    @Override
    public void onInitialize() {
        WoofRegistries.initialize();
        WoofAPI.initialize();

        sendDogCommandC2S.register();
    }
}
