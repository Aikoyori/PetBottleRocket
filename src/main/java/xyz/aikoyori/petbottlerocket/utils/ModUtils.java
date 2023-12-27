package xyz.aikoyori.petbottlerocket.utils;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import static java.lang.Double.NaN;

public class ModUtils {
    public static String MODID = "petbottlerocket";
    public static Identifier makeID(String thing)
    {
        return new Identifier(MODID,thing);
    }

    public static double[] getYawPitchFromVec3d(Vec3d vec){
        double pitch = Math.asin(vec.y / Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z));
        double yaw = Math.atan2(-vec.x, vec.z);
        if(Double.isNaN(pitch)) pitch = 9999;
        return new double[]{Math.toDegrees(yaw), Math.toDegrees(pitch)};
    }
}
