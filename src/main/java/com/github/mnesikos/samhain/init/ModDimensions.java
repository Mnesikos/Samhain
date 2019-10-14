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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

//this'll probably be changed later
public class ModDimensions {
    static final List<ModDimension> LIST = new ArrayList<>();
    public static final DimensionType OTHERWORLD = create("otherworld", OtherworldDimension::new, false);

    private static DimensionType create(String name, BiFunction<World, DimensionType, ? extends Dimension> factory, boolean light) {
        ResourceLocation id = new ResourceLocation(Samhain.MOD_ID, name);
        ModDimension dimension = new DimensionBase(factory).setRegistryName(id);
        LIST.add(dimension);
        return DimensionManager.registerDimension(id, dimension, null, light);
    }

    //if we eventually make a dimension that uses more data i guess
    private static DimensionType create(String name, BiFunction<World, DimensionType, ? extends Dimension> factory, BiConsumer<PacketBuffer, Boolean> write, BiConsumer<PacketBuffer, Boolean> read, PacketBuffer data, boolean light) {
        ResourceLocation id = new ResourceLocation(Samhain.MOD_ID, name);
        ModDimension dimension = new DimensionBase(factory, write, read).setRegistryName(id);
        LIST.add(dimension);
        return DimensionManager.registerDimension(id, dimension, data, light);
    }
}
