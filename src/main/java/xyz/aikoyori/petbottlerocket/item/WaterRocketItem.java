package xyz.aikoyori.petbottlerocket.item;

import net.minecraft.client.MinecraftClient;
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
        if(!context.getWorld().getGameRules().getBoolean(PetbottleRocket.ALLOW_UNLIMITED_ROCKET_USAGE) && !context.getPlayer().isCreative() && !context.getWorld().isClient())
        {
            context.getPlayer().getStackInHand(context.getHand()).decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
        boolean didsomething = false;
        if(world.isClient())
            return TypedActionResult.pass(user.getStackInHand(hand));
        if(user.getStackInHand(hand).getItem() == PetbottleRocket.WATER_ROCKET_ITEM  )
        {

            if(world.getGameRules().getBoolean(PetbottleRocket.ALLOW_ROCKET_PLACEMENT_IN_ADVENTURE) && !user.getAbilities().allowModifyWorld)
            {
                if(hitResult.getType() == HitResult.Type.BLOCK)
                {
                    BlockHitResult bhr = (BlockHitResult) hitResult;
                    Vec3d pos = bhr.getBlockPos().add(bhr.getSide().getVector()).toCenterPos();

                    WaterRocketEntity waterRocketEntity = WaterRocketEntity.makeRocket(world,pos
                            ,0,-90);
                    waterRocketEntity.getDataTracker().set(WaterRocketEntity.DROP_ITEMS,!user.isCreative());
                    waterRocketEntity.getDataTracker().set(WaterRocketEntity.ALLOW_ADVENTURE,true);
                    world.spawnEntity(waterRocketEntity);
                    //world.playSound(pos.x,pos.y,pos.z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,4,1,true);
                    if(!world.getGameRules().getBoolean(PetbottleRocket.ALLOW_UNLIMITED_ROCKET_USAGE) && !user.isCreative() && !world.isClient())
                    {
                        user.getStackInHand(hand).decrement(1);
                    }
                    //user.setStackInHand(hand,)));
                    didsomething = true;
                }
            }
            if(world.getGameRules().getBoolean(PetbottleRocket.ALLOW_ROCKET_THROWING) && !didsomething)
            {
                if(!user.isCreative() && world.getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DROP_MATERIALS)&& !world.isClient())
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
                user.getItemCooldownManager().set(this,world.getGameRules().getInt(PetbottleRocket.ROCKET_COOLDOWN_TICKS));
                //world.playSound(pos.x,pos.y,pos.z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,4,1,true);
                if(!world.getGameRules().getBoolean(PetbottleRocket.ALLOW_UNLIMITED_ROCKET_USAGE) && !user.isCreative()&& !world.isClient())
                {
                    user.getStackInHand(hand).decrement(1);
                }
                didsomething = true;
                //user.setStackInHand(hand,)));
            }
            if(didsomething)
            {

                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }

}
