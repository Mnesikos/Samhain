package com.github.mnesikos.samhain.common.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class AreaPatchFeature extends Feature<NoFeatureConfig> {
    private final Function<Random, BlockState> stateGetter;
    private final Function<Block, Boolean> groundChecker;

    public AreaPatchFeature(String name, Function<Random, BlockState> getState) {
        this(name, getState, block -> true);
    }

    public AreaPatchFeature(String name, Function<Random, BlockState> getState, Function<Block, Boolean> canStay) {
        super(NoFeatureConfig::deserialize);
        this.stateGetter = getState;
        this.groundChecker = canStay;
        setRegistryName(name);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int size = 12;
        int lastPlaced = 0;
        BlockPos[] placed = new BlockPos[size];
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                BlockPos p = pos.add(i, 0, j);
                setBlockState(worldIn, p, Blocks.AIR.getDefaultState());
                if((size < 2 ? rand.nextBoolean() : rand.nextInt(size) == 0) && groundChecker.apply(worldIn.getBlockState(p.down()).getBlock())) {
                    if(lastPlaced == 0 || placed[lastPlaced - 1] == null || placed[lastPlaced - 1].distanceSq(p) > 2) {
                        if(++lastPlaced >= placed.length) break;
                        placed[lastPlaced] = p;
                        setBlockState(worldIn, p, stateGetter.apply(rand));
                        size--;
                    }
                }
            }
        }
        return false;
    }
}
