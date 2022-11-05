/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.api;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

public record Variant(Identifier identifier, Function<RegistryEntry<Biome>, Boolean> biomePredicate) {
    public Identifier getUntameTexture() {
        return new Identifier(identifier.getNamespace(), "textures/woof/" + identifier.getPath() + "/untame.png");
    }

    public Identifier getTameTexture() {
        return new Identifier(identifier.getNamespace(), "textures/woof/" + identifier.getPath() + "/tame.png");
    }

    public Identifier getAngryTexture() {
        return new Identifier(identifier.getNamespace(), "textures/woof/" + identifier.getPath() + "/angry.png");
    }

    public boolean canSpawnIn(RegistryEntry<Biome> biome) {
        return biomePredicate.apply(biome);
    }
}
