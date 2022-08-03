package mine.block.woof.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class WolfItemRenderLayer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    public WolfItemRenderLayer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WolfEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(this.getContextModel() instanceof ModelWithHead) {
            boolean flag = entity.isSleeping();
            boolean flag1 = entity.isBaby();
            matrices.push();
            if (flag1) {
                float f = 0.75F;
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
            MinecraftClient.getInstance().getEntityRenderDispatcher().getHeldItemRenderer().renderItem(entity, itemstack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, light);
            matrices.pop();
        }
    }
}
