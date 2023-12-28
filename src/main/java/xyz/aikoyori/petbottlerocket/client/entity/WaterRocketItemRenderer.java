package xyz.aikoyori.petbottlerocket.client.entity;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;

public class WaterRocketItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer{

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        WaterRocketEntity waterRocketEntity = new WaterRocketEntity(PetbottleRocket.WATER_ROCKET_ENTITY, MinecraftClient.getInstance().world);
        waterRocketEntity.getDataTracker().set(WaterRocketEntity.FUEL,stack.getOrCreateNbt().getInt("fuel"));
        waterRocketEntity.getDataTracker().set(WaterRocketEntity.MAX_FUEL,stack.getOrCreateNbt().getInt("max_fuel"));

        waterRocketEntity.setPitch(-90f);
        entityRenderDispatcher.render(waterRocketEntity, 0.0d, 0.0d, 0.0d, 0.0F, 1.0F, matrices, vertexConsumers, light);
    }
}
