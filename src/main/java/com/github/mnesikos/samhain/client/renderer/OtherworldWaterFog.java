package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.client.renderer.color.OtherworldColors;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IEnviromentBlockReader;

public class OtherworldWaterFog {

    private static int lastWaterFogColor = -1;
    private static int waterFogColor = -1;
    private static long waterFogUpdateTime = -1L;

    public static Vector3f getColors(IEnviromentBlockReader world, ActiveRenderInfo info) {
        long i = Util.milliTime();
        int j = OtherworldColors.getWaterFogColor(world, new BlockPos(info.getProjectedView()));
        if (waterFogUpdateTime < 0L) {
            lastWaterFogColor = j;
            waterFogColor = j;
            waterFogUpdateTime = i;
        }
        int k = lastWaterFogColor >> 16 & 255;
        int l = lastWaterFogColor >> 8 & 255;
        int i1 = lastWaterFogColor & 255;
        int j1 = waterFogColor >> 16 & 255;
        int k1 = waterFogColor >> 8 & 255;
        int l1 = waterFogColor & 255;
        float f = MathHelper.clamp((float) (i - waterFogUpdateTime) / 5000.0F, 0.0F, 1.0F);
        float f1 = MathHelper.lerp(f, (float) j1, (float) k);
        float f2 = MathHelper.lerp(f, (float) k1, (float) l);
        float f3 = MathHelper.lerp(f, (float) l1, (float) i1);
        Vector3f colors = new Vector3f(f1 / 255.0F, f2 / 255.F, f3 / 255.0F);
        if (lastWaterFogColor != j) {
            lastWaterFogColor = j;
            waterFogColor = MathHelper.floor(f1) << 16 | MathHelper.floor(f2) << 8 | MathHelper.floor(f3);
            waterFogUpdateTime = i;
        }
        return colors;
    }
}
