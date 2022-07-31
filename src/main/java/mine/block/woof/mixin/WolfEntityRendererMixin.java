package mine.block.woof.mixin;

import mine.block.woof.SkinType;
import mine.block.woof.Woof;
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
        SkinType type = entity.getDataTracker().get(Woof.WOLF_SKIN_TYPE);
        return getTexture(entity, type);
    }

    private Identifier getTexture(WolfEntity entity, SkinType type) {
        if (entity.isTamed()) {
            return new Identifier(type.getTameTexture());
        } else {
            return entity.hasAngerTime() ? new Identifier(type.getAngryTexture()) : new Identifier(type.getBaseTexture());
        }
    }
}
