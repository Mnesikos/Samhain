package com.github.mnesikos.samhain.common.world.dimension;

import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class DimensionBase extends ModDimension {

    private final BiFunction<World, DimensionType, ? extends Dimension> factory;
    private final BiConsumer<PacketBuffer, Boolean> write;
    private final BiConsumer<PacketBuffer, Boolean> read;

    public DimensionBase(BiFunction<World, DimensionType, ? extends Dimension> factory) {
        this(factory, null, null);
    }

    public DimensionBase(BiFunction<World, DimensionType, ? extends Dimension> factory, BiConsumer<PacketBuffer, Boolean> write, BiConsumer<PacketBuffer, Boolean> read) {
        this.factory = factory;
        this.write = write;
        this.read = read;
    }

    @Override
    public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
        return factory;
    }

    @Override
    public void write(PacketBuffer buffer, boolean network) {
        super.write(buffer, network);
        if(write != null) write.accept(buffer, network);
    }

    @Override
    public void read(PacketBuffer buffer, boolean network) {
        super.read(buffer, network);
        if(read != null) read.accept(buffer, network);
    }
}
