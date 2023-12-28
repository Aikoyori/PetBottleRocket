package xyz.aikoyori.petbottlerocket.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import xyz.aikoyori.petbottlerocket.client.PetbottleRocketClient;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

public class WaterRocketRenderer extends EntityRenderer<WaterRocketEntity> {
    protected WaterRocketModel model;
    public WaterRocketRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);

        model =  new WaterRocketModel(ctx.getPart(PetbottleRocketClient.MODEL_CUBE_LAYER));
    }

    @Override
    public void render(WaterRocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180+MathHelper.lerp(tickDelta,entity.prevYaw,entity.getYaw())));
        matrices.translate(0,-  .25,0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta,entity.prevPitch,entity.getPitch())+90));
        matrices.translate(0,-1.5,0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta,
                (entity.prevFlyTick)*10f,
                entity.getDataTracker().get(WaterRocketEntity.FLY_TICK)*10f)));

        this.model.render(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))),light, OverlayTexture.DEFAULT_UV,1.0F, 1.0F, 1.0F, entity.isInvisibleTo(MinecraftClient.getInstance().player) ? 0.0F : 1.0F);
        if(!entity.getDataTracker().get(WaterRocketEntity.START_FLYING)){
            this.model.renderLid(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))),light, OverlayTexture.DEFAULT_UV,1.0F, 1.0F, 1.0F, entity.isInvisibleTo(MinecraftClient.getInstance().player) ? 0.0F : 1.0F);
        }
        if(entity.getDataTracker().get(WaterRocketEntity.FUEL) > 0){
            float curPercent = entity.getDataTracker().get(WaterRocketEntity.MAX_FUEL)==0?0:((float) entity.getDataTracker().get(WaterRocketEntity.FUEL) / (entity.getDataTracker().get(WaterRocketEntity.MAX_FUEL)*1f));
            float prevPercent = entity.getDataTracker().get(WaterRocketEntity.MAX_FUEL)==0?0:((float) entity.prevFuel / (entity.getDataTracker().get(WaterRocketEntity.MAX_FUEL)*1f));
            float percent = MathHelper.lerp(tickDelta,prevPercent,curPercent);
            matrices.push();
            matrices.translate(0,1.5-(percent)*(3/2f),0);
            matrices.scale(1,percent ,1);

            this.model.renderWater(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))),light, OverlayTexture.DEFAULT_UV,1.0F, 1.0F, 1.0F, entity.isInvisibleTo(MinecraftClient.getInstance().player) ? 0.0F : 1.0F);

            matrices.pop();
            this.model.renderWaterStream(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))),light, OverlayTexture.DEFAULT_UV,1.0F, 1.0F, 1.0F, entity.isInvisibleTo(MinecraftClient.getInstance().player) ? 0.0F : 1.0F);
        }
        matrices.pop();
    }

    @Override
    public Identifier getTexture(WaterRocketEntity entity) {
        return ModUtils.makeID("textures/entity/waterrocket.png");
    }

}
