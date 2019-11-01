package com.github.mnesikos.samhain.common.world.dimension;

import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class DimensionBase extends ModDimension {

    private final BiFunction<World, DimensionType, ? extends Dimension> factory;

    public DimensionBase(BiFunction<World, DimensionType, ? extends Dimension> factory) {
        this.factory = factory;
    }

    public final DimensionType getType() {
        return ModDimensions.TYPES.get(this);
    }

    @Override
    public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
        return factory;
    }
}
