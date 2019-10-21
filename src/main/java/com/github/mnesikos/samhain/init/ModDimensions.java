package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.world.dimension.DimensionBase;
import com.github.mnesikos.samhain.common.world.dimension.OtherworldDimension;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ModDimensions extends ModRegistry<ModDimension> {
    public static final Map<ModDimension, DimensionType> TYPES = new HashMap<>();
    public static final ModDimension OTHERWORLD = create("otherworld", OtherworldDimension::new);

    private static DimensionBase create(String name, BiFunction<World, DimensionType, ? extends Dimension> factory) {
        ResourceLocation id = new ResourceLocation(Samhain.MOD_ID, name);
        DimensionBase dimension = new DimensionBase(factory);
        dimension.setRegistryName(id);
        return dimension;
    }

    //if we eventually make a dimension that uses more data i guess
    private static DimensionBase create(String name, BiFunction<World, DimensionType, ? extends Dimension> factory, BiConsumer<PacketBuffer, Boolean> write, BiConsumer<PacketBuffer, Boolean> read) {
        ResourceLocation id = new ResourceLocation(Samhain.MOD_ID, name);
        DimensionBase dimension = new DimensionBase(factory, write, read);
        dimension.setRegistryName(id);
        return dimension;
    }

    public static void setType(ModDimension dimension, PacketBuffer data, boolean light) {
        //creates the type and stores it per key, key being the dimension, only if dimension type isn't already created
        ResourceLocation key = Objects.requireNonNull(dimension.getRegistryName());
        //todo find a way to do this without getting the registry
        ModDimensions.TYPES.put(dimension, DimensionManager.getRegistry().containsKey(key) ? DimensionManager.getRegistry().getOrDefault(key) : DimensionManager.registerDimension(key, dimension, data, light));
    }
}
