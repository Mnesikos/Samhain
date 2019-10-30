package com.github.mnesikos.samhain.common.block.trees;

import com.github.mnesikos.samhain.common.world.gen.feature.CrimsonKingMapleTreeFeature;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class CrimsonKingMapleTree extends BigTree {
    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return null;
    }

    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getBigTreeFeature(Random random) {
        return new CrimsonKingMapleTreeFeature(true);
    }
}
