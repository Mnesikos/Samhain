package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.world.dimension.DimensionBase;
import com.github.mnesikos.samhain.common.world.dimension.OtherworldDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public class ModDimensions extends ModRegistry<ModDimension> {
    public static final Map<ModDimension, DimensionType> TYPES = new HashMap<>();
    public static final DimensionBase OTHERWORLD = create("otherworld", OtherworldDimension::new);

    private static DimensionBase create(String name, BiFunction<World, DimensionType, ? extends Dimension> factory) {
        ResourceLocation id = new ResourceLocation(Samhain.MOD_ID, name);
        DimensionBase dimension = new DimensionBase(factory);
        dimension.setRegistryName(id);
        return dimension;
    }

    public static void setType(ModDimension dimension, boolean light) {
        //creates the type and stores it per key, key being the dimension, only if dimension type isn't already created
        ResourceLocation key = Objects.requireNonNull(dimension.getRegistryName());
        ModDimensions.TYPES.put(dimension, DimensionType.byName(key) == null ? DimensionManager.registerDimension(key, dimension, null, light) : DimensionType.byName(key));
    }
}
