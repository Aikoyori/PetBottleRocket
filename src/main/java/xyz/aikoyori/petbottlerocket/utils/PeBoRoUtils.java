package xyz.aikoyori.petbottlerocket.utils;

import net.minecraft.util.Identifier;

public class PeBoRoUtils {
    public static String MODID = "petbottlerocket";
    public static Identifier makeID(String thing)
    {
        return new Identifier(MODID,thing);
    }
}
