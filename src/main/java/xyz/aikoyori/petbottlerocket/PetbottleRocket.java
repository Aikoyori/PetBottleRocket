package xyz.aikoyori.petbottlerocket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.item.WaterRocketItem;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

public class PetbottleRocket implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    public static final EntityType<WaterRocketEntity> WATER_ROCKET_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            ModUtils.makeID("water_rocket"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC,WaterRocketEntity::new).dimensions(EntityDimensions.fixed(4/16f,4/16f)).build()
            );
    public static final WaterRocketItem WATER_ROCKET_ITEM = new WaterRocketItem(new FabricItemSettings().maxCount(1));
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, ModUtils.makeID("water_rocket"),WATER_ROCKET_ITEM);

    }
}
