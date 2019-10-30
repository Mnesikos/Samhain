package com.github.mnesikos.samhain.common.world.dimension;

import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

//simple wrapper around ModDimension(thanks forge)
public class DimensionBase extends ModDimension {

    private final BiFunction<World, DimensionType, ? extends Dimension> factory;

    public DimensionBase(BiFunction<World, DimensionType, ? extends Dimension> factory) {
        this.factory = factory;
    }

    @Override
    public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
        return factory;
    }
}
