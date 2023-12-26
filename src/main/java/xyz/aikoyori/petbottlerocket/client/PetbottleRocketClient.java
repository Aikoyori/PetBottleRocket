package xyz.aikoyori.petbottlerocket.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.client.entity.WaterRocketRenderer;
import xyz.aikoyori.petbottlerocket.utils.PeBoRoUtils;

public class PetbottleRocketClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(PeBoRoUtils.makeID("waterrocket"), "main");
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PetbottleRocket.WATER_ROCKET_ENTITY, WaterRocketRenderer::new);


    }
}
