package xyz.aikoyori.petbottlerocket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.utils.PeBoRoUtils;

public class PetbottleRocket implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final EntityType<WaterRocketEntity> WATER_ROCKET_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            PeBoRoUtils.makeID("water_rocket"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,WaterRocketEntity::new).dimensions(EntityDimensions.fixed(4/16f,4/16f)).build()
            );
    @Override
    public void onInitialize() {

    }
}
