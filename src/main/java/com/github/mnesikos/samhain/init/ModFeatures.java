package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.world.gen.feature.AreaPatchFeature;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ModFeatures extends ModRegistry<Feature<?>> {

    public static final Feature<NoFeatureConfig> PUMPKIN_PATCH = new AreaPatchFeature(rand -> Blocks.PUMPKIN.getDefaultState(), block -> block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND);
    public static final Feature<NoFeatureConfig> GRAVEYARD = new AreaPatchFeature(rand -> null);
}
