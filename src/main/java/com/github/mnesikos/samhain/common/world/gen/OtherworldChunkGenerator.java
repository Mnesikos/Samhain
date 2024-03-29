package com.github.mnesikos.samhain.common.world.gen;

import com.github.mnesikos.samhain.init.ModEntities;
import com.github.mnesikos.samhain.init.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;

import java.util.*;

public class OtherworldChunkGenerator extends NoiseChunkGenerator<OverworldGenSettings> {

    private static final Map<EntityClassification, List<Biome.SpawnListEntry>> CREATURES = new HashMap<EntityClassification, List<Biome.SpawnListEntry>>() {{
        put(EntityClassification.CREATURE, Arrays.asList(new Biome.SpawnListEntry(ModEntities.LADY_GWEN, 5, 1, 1), new Biome.SpawnListEntry(ModEntities.DULLAHAN, 10, 1, 1)/*, new Biome.SpawnListEntry(ModEntities.SIDHE, 5, 4, 6)*/));
        put(EntityClassification.AMBIENT, Collections.singletonList(new Biome.SpawnListEntry(ModEntities.SPIRIT, 5, 1, 2)));
    }};

    private static final float[] field_222576_h = Util.make(new float[25], arr -> {
        for(int lvt_1_1_ = -2; lvt_1_1_ <= 2; ++lvt_1_1_) {
            for(int lvt_2_1_ = -2; lvt_2_1_ <= 2; ++lvt_2_1_) {
                float lvt_3_1_ = 10.0F / MathHelper.sqrt((float)(lvt_1_1_ * lvt_1_1_ + lvt_2_1_ * lvt_2_1_) + 0.2F);
                arr[lvt_1_1_ + 2 + (lvt_2_1_ + 2) * 5] = lvt_3_1_;
            }
        }
    });

    private final OctavesNoiseGenerator depthNoise;

    public OtherworldChunkGenerator(IWorld p_i49931_1_, BiomeProvider p_i49931_2_, OverworldGenSettings p_i49931_6_) {
        super(p_i49931_1_, p_i49931_2_, 4, 8, 256, p_i49931_6_, true);
        this.randomSeed.skip(2620);
        this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 16);
    }

    @Override
    protected double[] func_222549_a(int i, int i1) {
        double[] lvt_3_1_ = new double[2];
        float lvt_4_1_ = 0.0F;
        float lvt_5_1_ = 0.0F;
        float lvt_6_1_ = 0.0F;
        float lvt_8_1_ = this.biomeProvider.func_222366_b(i, i1).getDepth();

        for(int lvt_9_1_ = -2; lvt_9_1_ <= 2; ++lvt_9_1_) {
            for(int lvt_10_1_ = -2; lvt_10_1_ <= 2; ++lvt_10_1_) {
                Biome lvt_11_1_ = this.biomeProvider.func_222366_b(i + lvt_9_1_, i1 + lvt_10_1_);
                float lvt_12_1_ = lvt_11_1_.getDepth();
                float lvt_13_1_ = lvt_11_1_.getScale();
                if (this.world.getWorldInfo().getGenerator() == WorldType.AMPLIFIED && lvt_12_1_ > 0.0F) {
                    lvt_12_1_ = 1.0F + lvt_12_1_ * 2.0F;
                    lvt_13_1_ = 1.0F + lvt_13_1_ * 4.0F;
                }

                float lvt_14_1_ = field_222576_h[lvt_9_1_ + 2 + (lvt_10_1_ + 2) * 5] / (lvt_12_1_ + 2.0F);
                if (lvt_11_1_.getDepth() > lvt_8_1_) {
                    lvt_14_1_ /= 2.0F;
                }

                lvt_4_1_ += lvt_13_1_ * lvt_14_1_;
                lvt_5_1_ += lvt_12_1_ * lvt_14_1_;
                lvt_6_1_ += lvt_14_1_;
            }
        }

        lvt_4_1_ /= lvt_6_1_;
        lvt_5_1_ /= lvt_6_1_;
        lvt_4_1_ = lvt_4_1_ * 0.9F + 0.1F;
        lvt_5_1_ = (lvt_5_1_ * 4.0F - 1.0F) / 8.0F;
        lvt_3_1_[0] = (double)lvt_5_1_ + this.func_222574_c(i, i1);
        lvt_3_1_[1] = lvt_4_1_;
        return lvt_3_1_;
    }

    private double func_222574_c(int p_222574_1_, int p_222574_2_) {
        double lvt_3_1_ = this.depthNoise.func_215462_a(p_222574_1_ * 200, 10.0D, p_222574_2_ * 200, 1.0D, 0.0D, true) / 8000.0D;
        if (lvt_3_1_ < 0.0D) {
            lvt_3_1_ = -lvt_3_1_ * 0.3D;
        }

        lvt_3_1_ = lvt_3_1_ * 3.0D - 2.0D;
        if (lvt_3_1_ < 0.0D) {
            lvt_3_1_ /= 28.0D;
        } else {
            if (lvt_3_1_ > 1.0D) {
                lvt_3_1_ = 1.0D;
            }

            lvt_3_1_ /= 40.0D;
        }

        return lvt_3_1_;
    }

    @Override
    protected double func_222545_a(double v, double v1, int i) {
        double lvt_6_1_ = 8.5D;
        double lvt_8_1_ = ((double)i - (lvt_6_1_ + v * lvt_6_1_ / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / v1;
        if (lvt_8_1_ < 0.0D) lvt_8_1_ *= 4.0D;
        return lvt_8_1_;
    }

    @Override
    protected void func_222548_a(double[] doubles, int i, int i1) {
        double lvt_4_1_ = 684.4119873046875D;
        double lvt_6_1_ = 684.4119873046875D;
        double lvt_8_1_ = 8.555149841308594D;
        double lvt_10_1_ = 4.277574920654297D;
        this.func_222546_a(doubles, i, i1, lvt_4_1_, lvt_6_1_, lvt_8_1_, lvt_10_1_, 3, -10);
    }

    @Override
    public void decorate(WorldGenRegion p_202092_1_) {
        super.decorate(p_202092_1_);
        int chunk = 16;
        Random rand = p_202092_1_.getRandom();
        BlockPos pos = new BlockPos(p_202092_1_.getMainChunkX() * chunk + rand.nextInt(chunk * 2) - chunk, 0, p_202092_1_.getMainChunkZ() * chunk + rand.nextInt(chunk * 2) - chunk);
        if(!BiomeDictionary.hasType(p_202092_1_.getBiome(pos), BiomeDictionary.Type.WATER)) {
            switch (rand.nextInt(3)) {
                case 0:
                    p_202092_1_.setBlockState(p_202092_1_.getHeight(Heightmap.Type.WORLD_SURFACE, pos), Blocks.COBWEB.getDefaultState(), 2);
                    break;
                case 1:
                    Feature<NoFeatureConfig> feature = rand.nextBoolean() ? ModFeatures.GRAVEYARD : ModFeatures.PUMPKIN_PATCH;
                    feature.place(p_202092_1_, this, rand, pos, IFeatureConfig.NO_FEATURE_CONFIG);
                    break;
                case 2:
                    ModFeatures.CRIMSON_KING_MAPLE_TREE.place(world, this, rand, pos, IFeatureConfig.NO_FEATURE_CONFIG);
                    break;
            }
        }
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification p_177458_1_, BlockPos p_177458_2_) {
        return CREATURES.putIfAbsent(p_177458_1_, new ArrayList<>());
    }

    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    public int getSeaLevel() {
        return 63;
    }
}
