package mine.block.woof.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class WolfModRenderLayer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    public WolfModRenderLayer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WolfEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity.isFurWet()) {
            float blueModifier = Math.min(0.5F + MathHelper.lerp(tickDelta, entity.lastShakeProgress, entity.shakeProgress) / 2.0F * 0.5F, 1.0F);
            this.getContextModel().setColorMultiplier(blueModifier, blueModifier, blueModifier);
        }

        if(this.getContextModel() instanceof ModelWithHead) {
            boolean flag1 = entity.isBaby();
            matrices.push();
            if (flag1) {
                matrices.scale(0.75F, 0.75F, 0.75F);
                matrices.translate(0.0D, 0.65D, 0.0D);
            }

            matrices.translate(((ModelWithHead) this.getContextModel()).getHead().pivotX / 16.0F, ((ModelWithHead) this.getContextModel()).getHead().pivotY / 16.0F, ((ModelWithHead) this.getContextModel()).getHead().pivotZ / 16.0F);
            float f1 = entity.getBegAnimationProgress(animationProgress);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(f1));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
            matrices.translate(0.059D, 0.15D, -0.42D);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));

            ItemStack itemstack = entity.getMainHandStack();
            MinecraftClient.getInstance().getItemRenderer().renderItem(entity, itemstack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, entity.world, light, 0, 0);
            matrices.pop();
        }
    }
}
