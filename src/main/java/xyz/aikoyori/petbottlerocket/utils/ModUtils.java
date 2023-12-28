package xyz.aikoyori.petbottlerocket.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static java.lang.Double.NaN;

public class ModUtils {
    public static String MODID = "petbottlerocket";
    public static Identifier makeID(String thing)
    {
        return new Identifier(MODID,thing);
    }
    public static Random random = new Random();
    public static double[] getYawPitchFromVec3d(Vec3d vec){
        double pitch = Math.asin(vec.y / Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z));
        double yaw = Math.atan2(-vec.x, vec.z);
        if(Double.isNaN(pitch)) pitch = 9999;
        return new double[]{Math.toDegrees(yaw), Math.toDegrees(pitch)};
    }
    public static Vec3d getRamdomVector(net.minecraft.util.math.random.Random random, double amplitude){
        return new Vec3d(((random.nextDouble()-0.5)*amplitude)*2,((random.nextDouble()-0.5)*amplitude)*2,((random.nextDouble()-0.5)
                *amplitude)*2).normalize().multiply(amplitude);
    }
    public static Vec3d getRamdomUpVector(net.minecraft.util.math.random.Random random, double amplitude){
        Vec3d vec = getRamdomVector(random,amplitude);
        if(vec.y <=0){
            vec.multiply(1,-1,1);
        }
        return vec;
    }
    public static Vec3d getXYDeviation(net.minecraft.util.math.random.Random random, double amplitude){
        Vec3d vec = getRamdomVector(random,amplitude);
        vec.multiply(1,0,1);
        return vec;
    }
    public static float getRandomCenterZeroFloat(net.minecraft.util.math.random.Random random,float amplitude)
    {
        return (random.nextFloat()-.5f)*2*amplitude;
    }
    public static DamageSource damageOf(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key),source,attacker);
    }
}
