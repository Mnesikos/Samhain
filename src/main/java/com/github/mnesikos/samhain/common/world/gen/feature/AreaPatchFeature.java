package com.github.mnesikos.samhain.common.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class AreaPatchFeature /*extends Feature<NoFeatureConfig>*/ {
    /*private final Function<Random, BlockState> stateGetter;
    private final Function<Block, Boolean> groundChecker;

    public AreaPatchFeature(Function<Random, BlockState> getState) {
        this(getState, block -> true);
    }

    public AreaPatchFeature(Function<Random, BlockState> getState, Function<Block, Boolean> canStay) {
        super(NoFeatureConfig::deserialize);
        this.stateGetter = getState;
        this.groundChecker = canStay;
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos[] placed = new BlockPos[12];
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                //if(rand.nextBoolean())
                //i'll finish this once i'm up :p
            }
        }
        return false;
    }*/
}
