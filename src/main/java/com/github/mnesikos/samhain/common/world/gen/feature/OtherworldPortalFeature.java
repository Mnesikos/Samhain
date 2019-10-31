package com.github.mnesikos.samhain.common.world.gen.feature;

import com.github.mnesikos.samhain.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class OtherworldPortalFeature extends Feature<NoFeatureConfig> {
    public OtherworldPortalFeature() {
        super(NoFeatureConfig::deserialize);
        setRegistryName("otherworld_portal");
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = rand.nextInt(3) + rand.nextInt(2) + 6;
        int j = pos.getX();
        int k = pos.getY();
        int l = pos.getZ();
        if (k >= 1 && k + i + 1 < 256) {
            BlockPos blockpos = pos.down();
            this.setDirtAt(worldIn, blockpos, pos);
            this.setDirtAt(worldIn, blockpos.east(), pos);
            this.setDirtAt(worldIn, blockpos.south(), pos);
            this.setDirtAt(worldIn, blockpos.south().east(), pos);
            Direction direction = Direction.Plane.HORIZONTAL.random(rand);
            int i1 = i - rand.nextInt(4);
            int j1 = 2 - rand.nextInt(3);
            int k1 = j;
            int l1 = l;
            int i2 = k + i - 1;
            BlockPos portalPos = blockpos.up();
            for (int j2 = 0; j2 < i; ++j2) {
                if (j2 >= i1 && j1 > 0) {
                    k1 += direction.getXOffset();
                    l1 += direction.getZOffset();
                    --j1;
                }

                int k2 = k + j2;
                BlockPos blockpos1 = new BlockPos(k1, k2, l1);
                if (isAirOrLeaves(worldIn, blockpos1)) {
                    this.createHollow3x3(worldIn, blockpos1);
                    this.createHollow3x3(worldIn, blockpos1.east());
                    this.createHollow3x3(worldIn, blockpos1.south());
                    this.createHollow3x3(worldIn, blockpos1.east().south());
                }
            }

            for (int j3 = -2; j3 <= 0; ++j3) {
                for (int i4 = -2; i4 <= 0; ++i4) {
                    int l4 = -1;
                    this.replaceAirWithLeaves(worldIn, k1 + j3, i2 + l4, l1 + i4);
                    this.replaceAirWithLeaves(worldIn, 1 + k1 - j3, i2 + l4, l1 + i4);
                    this.replaceAirWithLeaves(worldIn, k1 + j3, i2 + l4, 1 + l1 - i4);
                    this.replaceAirWithLeaves(worldIn, 1 + k1 - j3, i2 + l4, 1 + l1 - i4);
                    if ((j3 > -2 || i4 > -1) && (j3 != -1 || i4 != -2)) {
                        l4 = 1;
                        this.replaceAirWithLeaves(worldIn, k1 + j3, i2 + l4, l1 + i4);
                        this.replaceAirWithLeaves(worldIn, 1 + k1 - j3, i2 + l4, l1 + i4);
                        this.replaceAirWithLeaves(worldIn, k1 + j3, i2 + l4, 1 + l1 - i4);
                        this.replaceAirWithLeaves(worldIn, 1 + k1 - j3, i2 + l4, 1 + l1 - i4);
                    }
                }
            }

            if (rand.nextBoolean()) {
                this.replaceAirWithLeaves(worldIn, k1, i2 + 2, l1);
                this.replaceAirWithLeaves(worldIn, k1 + 1, i2 + 2, l1);
                this.replaceAirWithLeaves(worldIn, k1 + 1, i2 + 2, l1 + 1);
                this.replaceAirWithLeaves(worldIn, k1, i2 + 2, l1 + 1);
            }

            for (int k3 = -3; k3 <= 4; ++k3) {
                for (int j4 = -3; j4 <= 4; ++j4) {
                    if ((k3 != -3 || j4 != -3) && (k3 != -3 || j4 != 4) && (k3 != 4 || j4 != -3) && (k3 != 4 || j4 != 4) && (Math.abs(k3) < 3 || Math.abs(j4) < 3)) {
                        this.replaceAirWithLeaves(worldIn, k1 + k3, i2, l1 + j4);
                    }
                }
            }

            for (int l3 = -1; l3 <= 2; ++l3) {
                for (int k4 = -1; k4 <= 2; ++k4) {
                    if ((l3 < 0 || l3 > 1 || k4 < 0 || k4 > 1) && rand.nextInt(3) <= 0) {
                        int i5 = rand.nextInt(3) + 2;

                        for (int l2 = 0; l2 < i5; ++l2) {
                            this.createHollow3x3(worldIn, new BlockPos(j + l3, i2 - l2 - 1, l + k4));
                        }

                        for (int j5 = -1; j5 <= 1; ++j5) {
                            for (int i3 = -1; i3 <= 1; ++i3) {
                                this.replaceAirWithLeaves(worldIn, k1 + l3 + j5, i2, l1 + k4 + i3);
                            }
                        }

                        for (int k5 = -2; k5 <= 2; ++k5) {
                            for (int l5 = -2; l5 <= 2; ++l5) {
                                if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                                    this.replaceAirWithLeaves(worldIn, k1 + l3 + k5, i2 - 1, l1 + k4 + l5);
                                }
                            }
                        }
                        setBlockState(worldIn, portalPos, ModBlocks.OTHERWORLD_PORTAL.getDefaultState());
                        for (BlockPos p = portalPos.up(); !worldIn.getBlockState(p).isAir(worldIn, p); p = p.up()) setBlockState(worldIn, p, Blocks.CAVE_AIR.getDefaultState());
                    }
                }
            }

            return true;

        } else {
            return false;
        }
    }

    private void createHollow3x3(IWorldWriter worldIn, BlockPos pos) {
        this.setBlockState(worldIn, pos, Blocks.CAVE_AIR.getDefaultState());
        this.setBlockState(worldIn, pos.north(), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.south(), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.east(), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.west(), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.add(1, 0, 1), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.add(-1, 0, 1), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.add(1, 0, -1), CrimsonKingMapleTreeFeature.LOG);
        this.setBlockState(worldIn, pos.add(-1, 0, -1), CrimsonKingMapleTreeFeature.LOG);
    }

    private void replaceAirWithLeaves(IWorld worldIn, BlockPos pos) {
        if (isAir(worldIn, pos)) {
            this.setBlockState(worldIn, pos, CrimsonKingMapleTreeFeature.LEAVES);
        }
    }

    private void replaceAirWithLeaves(IWorld worldIn, int x, int y, int z) {
        replaceAirWithLeaves(worldIn, new BlockPos(x, y, z));
    }

    private void setDirtAt(IWorld worldIn, BlockPos pos, BlockPos origin) {
        worldIn.getBlockState(pos).onPlantGrow((IWorld)worldIn, pos, origin);
    }

    private boolean isAir(IWorld worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, state -> state.isAir(worldIn, pos));
    }

    private boolean isAirOrLeaves(IWorld worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves(worldIn, pos));
    }
}
