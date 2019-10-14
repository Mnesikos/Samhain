package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.world.gen.OtherworldChunkGenerator;
import net.minecraft.world.gen.*;

import java.util.function.Supplier;

public class ModChunkGenerators extends ModRegistry<ChunkGeneratorType<?, ?>> {

    public static final ChunkGeneratorType<OverworldGenSettings, OtherworldChunkGenerator> OTHERWORLD = create("otherworld", OtherworldChunkGenerator::new, false, OverworldGenSettings::new);

    private static <C extends GenerationSettings, T extends ChunkGenerator<C>> ChunkGeneratorType<C, T> create(String name, IChunkGeneratorFactory<C, T> factory, boolean buffet, Supplier<C> settings) {
        ChunkGeneratorType<C, T> type = new ChunkGeneratorType<>(factory, buffet, settings);
        type.setRegistryName(name);
        return type;
    }
}
