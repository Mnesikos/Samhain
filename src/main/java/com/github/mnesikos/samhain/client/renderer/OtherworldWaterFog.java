package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.client.renderer.color.OtherworldColors;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IEnviromentBlockReader;

public class OtherworldWaterFog {

    private static OtherworldWaterFog instance = new OtherworldWaterFog();
    private int lastWaterFogColor = -1;
    private int waterFogColor = -1;
    private long waterFogUpdateTime = -1L;

    public static Vector3f getColors(IEnviromentBlockReader world, ActiveRenderInfo info) {
        //instead of using reflection to edit the fields in FogRenderer we just use our own
        long i = Util.milliTime();
        int j = OtherworldColors.getWaterFogColor(world, new BlockPos(info.getProjectedView()));
        if (instance.waterFogUpdateTime < 0L) {
            instance.lastWaterFogColor = j;
            instance.waterFogColor = j;
            instance.waterFogUpdateTime = i;
        }
        int k = instance.lastWaterFogColor >> 16 & 255;
        int l = instance.lastWaterFogColor >> 8 & 255;
        int i1 = instance.lastWaterFogColor & 255;
        int j1 = instance.waterFogColor >> 16 & 255;
        int k1 = instance.waterFogColor >> 8 & 255;
        int l1 = instance.waterFogColor & 255;
        float f = MathHelper.clamp((float) (i - instance.waterFogUpdateTime) / 5000.0F, 0.0F, 1.0F);
        float f1 = MathHelper.lerp(f, (float) j1, (float) k);
        float f2 = MathHelper.lerp(f, (float) k1, (float) l);
        float f3 = MathHelper.lerp(f, (float) l1, (float) i1);
        Vector3f colors = new Vector3f(f1 / 255.0F, f2 / 255.F, f3 / 255.0F);
        if (instance.lastWaterFogColor != j) {
            instance.lastWaterFogColor = j;
            instance.waterFogColor = MathHelper.floor(f1) << 16 | MathHelper.floor(f2) << 8 | MathHelper.floor(f3);
            instance.waterFogUpdateTime = i;
        }
        return colors;
    }
}
