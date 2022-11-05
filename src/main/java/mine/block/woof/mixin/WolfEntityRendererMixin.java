/*
 * Copyright (C) 2022 mineblock11 <https://github.com/mineblock11>
 *
 * All code in Wolves Of Other Furs is licensed under the Academic Free License version 3.0
 */

package mine.block.woof.mixin;

import mine.block.woof.SkinType;
import mine.block.woof.api.Variant;
import mine.block.woof.api.WoofAPI;
import mine.block.woof.entity.WolfDataTracker;
import mine.block.woof.entity.WolfVariantTracker;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WolfEntityRenderer.class)
public class WolfEntityRendererMixin {
    /**
     * @author mineblock11
     * @reason Base textures no longer valid.
     */
    @Overwrite
    public Identifier getTexture(WolfEntity entity) {
        return getTexture(entity, entity.getDataTracker().get(WolfVariantTracker.VARIANT_TYPE));
    }

    private Identifier getTexture(WolfEntity entity, Identifier type) {
        Variant variant = WoofAPI.VARIANT_REGISTRY.get(type);
        if(variant == null) return new Identifier("null");
        if (entity.isTamed()) {
            return variant.getTameTexture();
        } else {
            return entity.hasAngerTime() ? variant.getAngryTexture() : variant.getUntameTexture();
        }
    }
}
