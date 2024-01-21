package xyz.aikoyori.petbottlerocket.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.entity.WaterRocketEntity;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

public class WaterRocketItem extends Item {

    public WaterRocketItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Vec3d pos = context.getBlockPos().add(context.getSide().getVector()).toCenterPos();
        WaterRocketEntity waterRocketEntity = WaterRocketEntity.makeRocket(context.getWorld(),pos,0,-90);
        context.getWorld().spawnEntity(waterRocketEntity);
        if(context.getPlayer()!=null)
        {
            waterRocketEntity.getDataTracker().set(WaterRocketEntity.DROP_ITEMS,!context.getPlayer().isCreative());
        }
        context.getStack().decrement(1);
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
        if(user.getStackInHand(hand).getItem() == PetbottleRocket.WATER_ROCKET_ITEM  )
        {
            if(world.getGameRules().getBoolean(PetbottleRocket.ALLOW_ROCKET_THROWING))
            {
                if(!user.isCreative() && world.getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DROP_MATERIALS))
                {
                    user.giveItemStack(new ItemStack(PetbottleRocket.BOTTLE_CAP_ITEM));
                }
                Vec3d pos = user.getEyePos().add(user.getRotationVector().normalize().multiply(0.5));
                WaterRocketEntity waterRocketEntity = WaterRocketEntity.makeRocket(world,pos
                        ,user.getYaw(),user.getPitch());
                waterRocketEntity.setOwner(user);
                waterRocketEntity.setVelocity(user.getRotationVector().normalize().multiply(1));
                waterRocketEntity.getDataTracker().set(WaterRocketEntity.START_FLYING,true);
                waterRocketEntity.getDataTracker().set(WaterRocketEntity.DROP_ITEMS,!user.isCreative());
                world.spawnEntity(waterRocketEntity);
                world.playSound(pos.x,pos.y,pos.z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,4,1,true);
                user.getStackInHand(hand).decrement(1);
                //user.setStackInHand(hand,)));
                return TypedActionResult.success(user.getStackInHand(hand));
            }
            if(world.getGameRules().getBoolean(PetbottleRocket.ALLOW_ROCKET_PLACEMENT_IN_ADVENTURE) && !user.getAbilities().allowModifyWorld)
            {
                if(hitResult.getType() == HitResult.Type.BLOCK)
                {
                    BlockHitResult bhr = (BlockHitResult) hitResult;
                    Vec3d pos = bhr.getBlockPos().add(bhr.getSide().getVector()).toCenterPos();

                    WaterRocketEntity waterRocketEntity = WaterRocketEntity.makeRocket(world,pos
                            ,0,-90);
                    waterRocketEntity.setOwner(user);
                    waterRocketEntity.getDataTracker().set(WaterRocketEntity.DROP_ITEMS,!user.isCreative());
                    world.spawnEntity(waterRocketEntity);
                    world.playSound(pos.x,pos.y,pos.z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,4,1,true);
                    user.getStackInHand(hand).decrement(1);
                    //user.setStackInHand(hand,)));
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            }
        }
        return super.use(world, user, hand);
    }

}
