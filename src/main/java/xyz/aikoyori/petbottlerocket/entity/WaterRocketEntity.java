package xyz.aikoyori.petbottlerocket.entity;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.UUID;

public class WaterRocketEntity extends Entity implements Ownable {

    private float ANGLE_DEVIATION = 7.5f;
    public static final TrackedData<Boolean> START_FLYING = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> ALLOW_ADVENTURE = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> DROP_ITEMS = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> FUEL = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> MAX_FUEL = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> FLY_TICK = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Float> RANDOM_YAW_ADD = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> RANDOM_PITCH_ADD = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public float prevFuel = 7;
    public float prevFlyTick = 0;

    @Nullable
    private UUID ownerUuid;
    @Nullable
    private Entity owner;


    public WaterRocketEntity(EntityType<?> type, World world) {
        super(type, world);
        prevPitch = -90;

        this.setPitch(-90f);
    }
    public static WaterRocketEntity makeRocket(World world, double x, double y, double z, float yaw, float pitch,Entity owner) {

        WaterRocketEntity waterRocketEntity = new WaterRocketEntity(PetbottleRocket.WATER_ROCKET_ENTITY, world);
        waterRocketEntity.setOwner(owner);
        waterRocketEntity.prevPitch = pitch;
        waterRocketEntity.prevYaw = yaw;
        waterRocketEntity.prevX = x;
        waterRocketEntity.prevY = y;
        waterRocketEntity.prevZ = z;
        waterRocketEntity.setRotation(yaw, pitch);
        waterRocketEntity.setPosition(x,y,z);
        return waterRocketEntity;
    }
    public static WaterRocketEntity makeRocket(World world, Vec3d pos, float yaw, float pitch,Entity owner) {
        return makeRocket(world,pos.getX(),pos.getY(),pos.getZ(),yaw,pitch,owner);
    }
    public static WaterRocketEntity makeRocket(World world, Vec3d pos, float yaw, float pitch) {
        return makeRocket(world,pos.getX(),pos.getY(),pos.getZ(),yaw,pitch,null);
    }


    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getAttacker()!=null && source.getAttacker().isPlayer() && !getDataTracker().get(START_FLYING)){
            PlayerEntity player = (PlayerEntity) source.getAttacker();

            if(!player.isCreative()){
                ItemStack stack = new ItemStack(PetbottleRocket.WATER_ROCKET_ITEM);

                this.dropStack(stack);
            }
            this.discard();
            return true;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        this.prevFuel = getDataTracker().get(FUEL);
        this.prevPitch=this.getPitch();
        this.prevFlyTick=this.dataTracker.get(FLY_TICK);
        this.prevYaw=this.getYaw();
        super.tick();
        this.addVelocity(new Vec3d(0,-0.07f,0));

        Vec3d posget =  getPos().add(0,-this.getRotationVector().length()-0.5,0);
        BlockPos pots = BlockPos.ofFloored(posget.x,posget.y,posget.z);

        //this.setCustomName(Text.literal(getWorld().getBlockState(pots).toString() + " " + (this.getDataTracker().get(FUEL)).toString()));
        //this.setCustomNameVisible(true);
        if(!this.isOnGround())
        {

            dataTracker.set(FLY_TICK,dataTracker.get(FLY_TICK)+1);
            if(this.getDataTracker().get(FUEL)+1==this.getDataTracker().get(MAX_FUEL) && this.getDataTracker().get(START_FLYING)){
                this.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,1,0);

            }
            if(this.getDataTracker().get(FUEL)>0 && this.getDataTracker().get(START_FLYING)){
                for(int i=0;i<=20;i++)
                {

                    getWorld().addParticle(ParticleTypes.SPLASH,getPos().getX(),getPos().getY(),getPos().getZ(),
                            ModUtils.getRandomCenterZeroFloat(random,1),
                            ModUtils.getRandomCenterZeroFloat(random,1),
                            ModUtils.getRandomCenterZeroFloat(random,1));
                }

            }

            //if(MinecraftClient.getInstance().player!=null)MinecraftClient.getInstance().player.sendMessage(Text.literal("GET TROLLED "+dataTracker.get(FLY_TICK)));
        }
        else {
            if(this.getDataTracker().get(START_FLYING) && getVelocity().getY() < 0){

                for(int i=0;i<20;i++)
                {
                    getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM,new ItemStack(PetbottleRocket.PLASTIC_SCRAP_ITEM)),
                            prevX,prevY,prevZ,
                            ModUtils.getRandomCenterZeroFloat(random,0.9f),
                            ModUtils.getRandomCenterZeroFloat(random,0.9f),
                            ModUtils.getRandomCenterZeroFloat(random,0.9f));
                }
            }

            if(this.getDataTracker().get(START_FLYING) && this.getDataTracker().get(FUEL)<=1 ){

                if(getDataTracker().get(DROP_ITEMS))
                {
                    dropIngredients();
                }
                this.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,1,2);
                this.discard();

            }
            this.setVelocity(new Vec3d(0,this.getVelocity().y,0));

        }

        if(this.isSubmergedInWater()){
            this.setVelocity(this.getVelocity().multiply(0.8f));
        }
        if(this.getDataTracker().get(START_FLYING))
        {
            double[] pyr = ModUtils.getYawPitchFromVec3d(this.getVelocity().normalize());
            float realyaw = (pyr[1] > 999 ? (getVelocity().y>0? -90:90):-(float) pyr[1]);
            this.setRotation((float) pyr[0], realyaw);
            if(this.getDataTracker().get(FUEL) > 0)
            {
                this.getDataTracker().set(FUEL,this.getDataTracker().get(FUEL) - 1);

                this.setYaw(this.getYaw()+this.getDataTracker().get(RANDOM_YAW_ADD));
                this.setPitch(this.getPitch()+this.getDataTracker().get(RANDOM_PITCH_ADD));
                this.setVelocity(this.getVelocity().add(this.getRotationVector().normalize().multiply(0.15)));
                //this.setVelocity(this.getVelocity().add(ModUtils.getXYDeviation(random,0.1)).multiply(ModUtils.getRamdomUpVector(random,1.1).multiply(1.0,0.45,1.0).add(1.01,0.55,1.01)));
            }
        }


        this.velocityDirty = true;
        Vec3d vel = this.getVelocity();
        try{

            move(MovementType.SELF,vel);
        }
        catch (ConcurrentModificationException concurrent){

        }
    }
    
    @Override
    public boolean collidesWith(Entity other) {
        if(other.canHit() && other.canBeHitByProjectile() && this.getDataTracker().get(START_FLYING))
        {

            if(!Objects.equals(other.getUuid(),ownerUuid) && getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DEAL_DAMAGE)){
                // don't do damage if owner
                other.damage(ModUtils.damageOf(getWorld(),PetbottleRocket.ROCKET_HIT_DAMAGE,this,owner), (float) (2f*this.getVelocity().length()));
            }
            if(other instanceof LivingEntity living)
            {
                if(!(Objects.equals(other.getUuid(),ownerUuid)))
                {
                    if(getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DEAL_KNOCKBACK_OTHERS) &&  !getWorld().isClient())
                    {
                        living.takeKnockback(this.getVelocity().length()/2.0f,this.getRotationVector().multiply(-1).x,this.getRotationVector().multiply(-1).z);
                        living.updateTrackedPosition(living.getX(),living.getY(),living.getZ());
                        //living.updateVelocity((float) living.getVelocity().length(),living.getVelocity());
                    }

                }
                else{
                    {
                        //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(""+getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DEAL_KNOCKBACK_SELF)+" "+(getWorld().isClient()?"client":"server")));

                        if(getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DEAL_KNOCKBACK_SELF) && !getWorld().isClient()){
                            living.takeKnockback(this.getVelocity().length()/2.0f,this.getRotationVector().multiply(-1).x,this.getRotationVector().multiply(-1).z);
                            living.velocityModified = true;


                            if (living instanceof ServerPlayerEntity && living.velocityModified) {
                                ((ServerPlayerEntity)living).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(living));
                                living.velocityModified = false;
                            }
                        }
                        //living.updateVelocity((float) living.getVelocity().length(),living.getVelocity());
                    }
                }
            }
        }
        return super.collidesWith(other);
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    private void dropIngredients(){
        if(this.getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DROP_MATERIALS))
        {

            this.dropStack(new ItemStack(PetbottleRocket.PLASTIC_SCRAP_ITEM,4));
            this.dropStack(new ItemStack(Items.PAPER,6));
        }
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(PetbottleRocket.WATER_ROCKET_ITEM);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(hand).isEmpty()&&player.isSneaking() && !getDataTracker().get(START_FLYING) && (getDataTracker().get(ALLOW_ADVENTURE) || player.getAbilities().allowModifyWorld))
        {
            if(!player.isCreative() && this.getWorld().getGameRules().getBoolean(PetbottleRocket.SHOULD_ROCKET_DROP_MATERIALS)&& !getWorld().isClient())
            {
                player.giveItemStack(new ItemStack(PetbottleRocket.BOTTLE_CAP_ITEM));
            }
            this.getDataTracker().set(START_FLYING,true);
            this.getDataTracker().set(DROP_ITEMS,!player.isCreative());
            this.setOwner(player);
            this.setVelocity(ModUtils.getRandomCenterZeroFloat(random,1.4f),1,ModUtils.getRandomCenterZeroFloat(random,1.4f));
            return ActionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(START_FLYING,false);
        dataTracker.startTracking(ALLOW_ADVENTURE,false);
        dataTracker.startTracking(DROP_ITEMS,true);
        dataTracker.startTracking(RANDOM_YAW_ADD,ModUtils.getRandomCenterZeroFloat(random,getWorld().getGameRules().getInt(PetbottleRocket.ROCKET_DEVIATION)));
        dataTracker.startTracking(RANDOM_PITCH_ADD,ModUtils.getRandomCenterZeroFloat(random,getWorld().getGameRules().getInt(PetbottleRocket.ROCKET_DEVIATION)));
        dataTracker.startTracking(FUEL,7);
        dataTracker.startTracking(MAX_FUEL,7);
        dataTracker.startTracking(FLY_TICK,0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(START_FLYING,nbt.getBoolean("started_flying"));
        dataTracker.set(ALLOW_ADVENTURE,nbt.getBoolean("allow_adventure_interaction"));
        dataTracker.set(DROP_ITEMS,nbt.getBoolean("drop_items"));
        dataTracker.set(FUEL,nbt.getInt("fuel"));
        dataTracker.set(MAX_FUEL,nbt.getInt("max_fuel"));
        dataTracker.set(FLY_TICK,nbt.getInt("fly_tick"));
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
            this.owner = null;
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("started_flying",dataTracker.get(START_FLYING));
        nbt.putBoolean("drop_items",dataTracker.get(DROP_ITEMS));
        nbt.putBoolean("allow_adventure_interaction",dataTracker.get(ALLOW_ADVENTURE));
        nbt.putInt("fuel",dataTracker.get(FUEL));
        nbt.putInt("max_fuel",dataTracker.get(MAX_FUEL));
        nbt.putInt("fly_tick",dataTracker.get(FLY_TICK));
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else {
            if (this.ownerUuid != null) {
                World var2 = this.getWorld();
                if (var2 instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld)var2;
                    this.owner = serverWorld.getEntity(this.ownerUuid);
                    return this.owner;
                }
            }

            return null;
        }
    }
    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.owner = entity;
        }

    }
}
