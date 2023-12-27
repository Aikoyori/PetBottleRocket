package xyz.aikoyori.petbottlerocket.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;

public class WaterRocketItem extends Item {

    public WaterRocketItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Vec3d pos = context.getBlockPos().add(context.getSide().getVector()).toCenterPos();
        WaterRocketEntity waterRocketEntity = new WaterRocketEntity(PetbottleRocket.WATER_ROCKET_ENTITY,context.getWorld());
        waterRocketEntity.setPosition(pos);
        waterRocketEntity.setPitch(-90f);
        context.getWorld().spawnEntity(waterRocketEntity);

        return ActionResult.SUCCESS;
    }
}
