package xyz.aikoyori.petbottlerocket.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.client.entity.WaterRocketItemRenderer;
import xyz.aikoyori.petbottlerocket.client.entity.WaterRocketModel;
import xyz.aikoyori.petbottlerocket.client.entity.WaterRocketRenderer;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

public class PetbottleRocketClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(ModUtils.makeID("waterrocket"), "main");
    public static final EntityModelLayer MODEL_WATER = new EntityModelLayer(ModUtils.makeID("waterrocket_water"), "water");
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PetbottleRocket.WATER_ROCKET_ENTITY, WaterRocketRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, WaterRocketModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_WATER, WaterRocketModel::getTexturedModelData);
        BuiltinItemRendererRegistry.INSTANCE.register(PetbottleRocket.WATER_ROCKET_ITEM, new WaterRocketItemRenderer());


    }
}
