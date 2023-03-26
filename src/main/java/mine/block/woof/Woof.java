/*
 * Copyright (C) 2023 mineblock11 <https://github.com/mineblock11>
 *
 * All Rights Reserved
 */

package mine.block.woof;

import mine.block.woof.api.WoofAPI;
import mine.block.woof.register.WoofRegistries;
import mine.block.woof.server.SendDogCommandC2S;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Woof implements ModInitializer {
    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM.getKey(),new Identifier("woof:meats"));

    public static Identifier id(String name) {
        return new Identifier("woof", name);
    }

    public static final SendDogCommandC2S sendDogCommandC2S = new SendDogCommandC2S();

    public static Identifier withPrefixedPath(Identifier identifier, String path) {
        return new Identifier(identifier.getNamespace(), path + identifier.getPath());
    }

    @Override
    public void onInitialize() {
        WoofAPI.initialize();
        WoofRegistries.initialize();

        sendDogCommandC2S.register();
    }
}
