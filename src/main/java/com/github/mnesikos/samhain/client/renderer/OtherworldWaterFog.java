package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.color.OtherworldColors;
import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class OtherworldWaterFog {

    private static final Map<String, String> MAPPINGS = new HashMap<>();
    private static final Map<String, Field> FIELDS = new HashMap<>();
    private final FogRenderer renderer;

    private OtherworldWaterFog(FogRenderer renderer) {
        this.renderer = renderer;
    }

    private <T extends Number> void set(String name, T value) {
        try {
            String mapped = MAPPINGS.putIfAbsent(name, ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, name));
            if(mapped != null) {
                Field field;
                if (FIELDS.containsKey(mapped)) field = FIELDS.get(mapped);
                else {
                    field = FogRenderer.class.getDeclaredField(mapped);
                    field.setAccessible(true);
                    FIELDS.put(mapped, field);
                }
                field.set(renderer, value);
            }
        } catch (ReflectiveOperationException e) {
            Samhain.LOGGER.fatal("Failed to set fog renderer value '" + name + "'", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Number> T get(String name) {
        try {
            String mapped = MAPPINGS.putIfAbsent(name, ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, name));
            if(mapped != null) {
                Field field;
                if (FIELDS.containsKey(mapped)) field = FIELDS.get(mapped);
                else {
                    field = FogRenderer.class.getDeclaredField(mapped);
                    field.setAccessible(true);
                    FIELDS.put(mapped, field);
                }
                return (T)field.get(renderer);
            }
        } catch (ReflectiveOperationException e) {
            Samhain.LOGGER.fatal("Failed to get fog renderer value '" + name + "'", e);
        }
        //yea...
        return null;
    }

    private long getLong(String name) {
        Number value = get(name);
        return value == null ? 0L : (long)value;
    }

    private int getInt(String name) {
        Number value = get(name);
        return value == null ? 0 : (int)value;
    }

    public static Vector3f getColors(IEnviromentBlockReader world, ActiveRenderInfo info, FogRenderer renderer) {
        long i = Util.milliTime();
        int j = OtherworldColors.getWaterFogColor(world, new BlockPos(info.getProjectedView()));
        OtherworldWaterFog handler = new OtherworldWaterFog(renderer);
        long waterFogUpdateTime = handler.getLong("waterFogUpdateTime");
        int lastWaterFogColor = handler.getInt("lastWaterFogColor");
        if (waterFogUpdateTime < 0L) {
            handler.set("lastWaterFogColor", j);
            handler.set("waterFogColor", j);
            handler.set("waterFogUpdateTime", i);
            lastWaterFogColor = j;
            waterFogUpdateTime = i;
        }
        int waterFogColor = handler.getInt("waterFogColor");
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
            handler.set("lastWaterFogColor", j);
            handler.set("waterFogColor", MathHelper.floor(f1) << 16 | MathHelper.floor(f2) << 8 | MathHelper.floor(f3));
            handler.set("waterFogUpdateTime", i);
        }
        return colors;
    }
}
