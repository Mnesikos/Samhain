package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.block.GravestoneBlock;
import com.github.mnesikos.samhain.common.world.gen.feature.AreaPatchFeature;
import com.github.mnesikos.samhain.common.world.gen.feature.CrimsonKingMapleTreeFeature;
import com.github.mnesikos.samhain.common.world.gen.feature.OtherworldPortalFeature;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ModFeatures extends ModRegistry<Feature<?>> {

    public static final Feature<NoFeatureConfig> PUMPKIN_PATCH = new AreaPatchFeature("pumpkin_patch", rand -> Blocks.PUMPKIN.getDefaultState(), block -> block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND);
    public static final Feature<NoFeatureConfig> GRAVEYARD = new AreaPatchFeature("graveyard", rand -> ModBlocks.GRAVESTONE.getDefaultState().with(GravestoneBlock.STYLE, GravestoneBlock.Style.values()[rand.nextInt(GravestoneBlock.Style.values().length)]));
    public static final AbstractTreeFeature<NoFeatureConfig> CRIMSON_KING_MAPLE_TREE = new CrimsonKingMapleTreeFeature(false);
    public static final Feature<NoFeatureConfig> OTHERWORLD_PORTAL = new OtherworldPortalFeature();
}
