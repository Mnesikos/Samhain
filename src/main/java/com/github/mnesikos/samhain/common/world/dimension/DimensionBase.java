package com.github.mnesikos.samhain.common.world.dimension;

import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

//this isn't really meant to be overridden, hence why everything is final
public final class DimensionBase extends ModDimension {
    private final BiFunction<World, DimensionType, ? extends Dimension> factory;
    private final PacketBuffer data;
    private final boolean light;

    public DimensionBase(BiFunction<World, DimensionType, ? extends Dimension> factory, boolean light, PacketBuffer data) {
        this.factory = factory;
        this.light = light;
        this.data = data;
    }

    public final DimensionType getType() {
        return ModDimensions.TYPES.get(this);
    }

    @Override
    public final BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
        return factory;
    }

    public final PacketBuffer getExtraData() {
        return data;
    }

    public final boolean hasSkyLight() {
        return light;
    }
}
