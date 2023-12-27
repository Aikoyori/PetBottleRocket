package xyz.aikoyori.petbottlerocket.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.aikoyori.petbottlerocket.PetbottleRocket;
import xyz.aikoyori.petbottlerocket.utils.ModUtils;

import java.util.Objects;

public class WaterRocketEntity extends Entity {
    public static final TrackedData<Boolean> START_FLYING = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> FUEL = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> MAX_FUEL = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> FLY_TICK = DataTracker.registerData(WaterRocketEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public WaterRocketEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setPitch(-90f);
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
        if(source.getAttacker()!=null && source.getAttacker().isPlayer()){
            this.discard();
            return true;
        }
        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        this.addVelocity(new Vec3d(0,-0.07f,0));
        if(!this.isOnGround())
        {

            dataTracker.set(FLY_TICK,dataTracker.get(FLY_TICK)+1);

            //if(MinecraftClient.getInstance().player!=null)MinecraftClient.getInstance().player.sendMessage(Text.literal("GET TROLLED "+dataTracker.get(FLY_TICK)));
        }
        else {
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
                this.setVelocity(this.getVelocity().add(new Vec3d(0,0.08,0)).multiply(1.25));
            }
        }

        this.velocityDirty = true;
        move(MovementType.SELF,this.getVelocity());
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(START_FLYING,false);
        dataTracker.startTracking(FUEL,7);
        dataTracker.startTracking(MAX_FUEL,7);
        dataTracker.startTracking(FLY_TICK,0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(START_FLYING,nbt.getBoolean("started_flying"));
        dataTracker.set(FUEL,nbt.getInt("fuel"));
        dataTracker.set(MAX_FUEL,nbt.getInt("max_fuel"));
        dataTracker.set(FLY_TICK,nbt.getInt("fly_tick"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("started_flying",dataTracker.get(START_FLYING));
        nbt.putInt("fuel",dataTracker.get(FUEL));
        nbt.putInt("max_fuel",dataTracker.get(MAX_FUEL));
        nbt.putInt("fly_tick",dataTracker.get(FLY_TICK));
    }
}
