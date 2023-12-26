package xyz.aikoyori.petbottlerocket.client.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xyz.aikoyori.petbottlerocket.client.PetbottleRocketClient;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.utils.PeBoRoUtils;

public class WaterRocketRenderer extends EntityRenderer<WaterRocketEntity> {
    protected WaterRocketModel model;
    public WaterRocketRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);

        model =  new WaterRocketModel(ctx.getPart(PetbottleRocketClient.MODEL_CUBE_LAYER));
    }

    @Override
    public void render(WaterRocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(WaterRocketEntity entity) {
        return PeBoRoUtils.makeID("textures/entity/waterrocket.ong");
    }

}
