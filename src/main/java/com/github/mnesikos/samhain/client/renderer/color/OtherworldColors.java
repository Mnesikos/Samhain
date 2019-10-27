package com.github.mnesikos.samhain.client.renderer.color;

//import com.github.mnesikos.samhain.init.ModConfiguration;
import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.biome.BiomeColors;

public class OtherworldColors {

    public static int getGrassColor(IEnviromentBlockReader p_217613_0_, BlockPos p_217613_1_) {
        return getColor(0x3c2161, BiomeColors.getGrassColor(p_217613_0_, p_217613_1_));
    }

    public static int getFoliageColor(IEnviromentBlockReader p_217615_0_, BlockPos p_217615_1_) {
        return getColor(0x3c2161, BiomeColors.getFoliageColor(p_217615_0_, p_217615_1_));
    }

    public static int getWaterColor(IEnviromentBlockReader p_217612_0_, BlockPos p_217612_1_) {
        return getColor(0xf5a345, BiomeColors.getWaterColor(p_217612_0_, p_217612_1_));
    }

    public static int getWaterFogColor(IEnviromentBlockReader p_217612_0_, BlockPos p_217612_1_) {
        return getColor(0xb6752b, p_217612_0_.getBiome(p_217612_1_).getWaterFogColor());
    }

    public static int getSpruce(IEnviromentBlockReader p_217612_0_, BlockPos p_217612_1_) {
        if(inOtherworld() && p_217612_0_ != null && p_217612_1_ != null) return getFoliageColor(p_217612_0_, p_217612_1_);
        return FoliageColors.getSpruce();
    }

    public static int getBirch(IEnviromentBlockReader p_217612_0_, BlockPos p_217612_1_) {
        if(inOtherworld() && p_217612_0_ != null && p_217612_1_ != null) return getFoliageColor(p_217612_0_, p_217612_1_);
        return FoliageColors.getBirch();
    }

    /*@SuppressWarnings("unused")
    public static int getLiquidColor(IEnviromentBlockReader p_217612_0_, BlockPos p_217612_1_) {
        return ModConfiguration.INSTANCE.changeOtherworldWater.get() ? getWaterColor(p_217612_0_, p_217612_1_) : BiomeColors.getWaterColor(p_217612_0_, p_217612_1_);
    }*/

    private static int getColor(int otherworld, int overworld) {
        return inOtherworld() ? otherworld : overworld;
    }

    private static boolean inOtherworld() {
        return Minecraft.getInstance().world.dimension.getType() == ModDimensions.TYPES.get(ModDimensions.OTHERWORLD);
    }
}
