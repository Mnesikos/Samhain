package com.github.mnesikos.samhain.common.world.gen.feature;

import com.github.mnesikos.samhain.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;

public class CrimsonKingMapleTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
    public static final BlockState LOG = ModBlocks.CRIMSON_KING_MAPLE_LOG.getDefaultState();
    public static final BlockState LEAVES = ModBlocks.CRIMSON_KING_MAPLE_LEAVES.getDefaultState();

    public CrimsonKingMapleTreeFeature(boolean doBlockNofityOnPlace) {
        super(NoFeatureConfig::deserialize, doBlockNofityOnPlace);
        setSapling(ModBlocks.CRIMSON_KING_MAPLE_SAPLING);
        setRegistryName("crimson_king_maple_tree");
    }

    @Override
    protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        int i = rand.nextInt(3) + rand.nextInt(2) + 6;
        int j = position.getX();
        int k = position.getY();
        int l = position.getZ();
        if (k >= 1 && k + i + 1 < 256) {
            BlockPos blockpos = position.down();
            if (!isSoil(worldIn, blockpos, getSapling())) {
                return false;
            } else if (!this.func_214615_a(worldIn, position, i)) {
                return false;
            } else {
                this.setDirtAt(worldIn, blockpos, position);
                this.setDirtAt(worldIn, blockpos.east(), position);
                this.setDirtAt(worldIn, blockpos.south(), position);
                this.setDirtAt(worldIn, blockpos.south().east(), position);
                Direction direction = Direction.Plane.HORIZONTAL.random(rand);
                int i1 = i - rand.nextInt(4);
                int j1 = 2 - rand.nextInt(3);
                int k1 = j;
                int l1 = l;
                int i2 = k + i - 1;

                for(int j2 = 0; j2 < i; ++j2) {
                    if (j2 >= i1 && j1 > 0) {
                        k1 += direction.getXOffset();
                        l1 += direction.getZOffset();
                        --j1;
                    }

                    int k2 = k + j2;
                    BlockPos blockpos1 = new BlockPos(k1, k2, l1);
                    if (isAirOrLeaves(worldIn, blockpos1)) {
                        this.func_214616_a(changedBlocks, worldIn, blockpos1, boundsIn);
                        this.func_214616_a(changedBlocks, worldIn, blockpos1.east(), boundsIn);
                        this.func_214616_a(changedBlocks, worldIn, blockpos1.south(), boundsIn);
                        this.func_214616_a(changedBlocks, worldIn, blockpos1.east().south(), boundsIn);
                    }
                }

                for(int j3 = -2; j3 <= 0; ++j3) {
                    for(int i4 = -2; i4 <= 0; ++i4) {
                        int l4 = -1;
                        this.func_214617_a(worldIn, k1 + j3, i2 + l4, l1 + i4, boundsIn, changedBlocks);
                        this.func_214617_a(worldIn, 1 + k1 - j3, i2 + l4, l1 + i4, boundsIn, changedBlocks);
                        this.func_214617_a(worldIn, k1 + j3, i2 + l4, 1 + l1 - i4, boundsIn, changedBlocks);
                        this.func_214617_a(worldIn, 1 + k1 - j3, i2 + l4, 1 + l1 - i4, boundsIn, changedBlocks);
                        if ((j3 > -2 || i4 > -1) && (j3 != -1 || i4 != -2)) {
                            l4 = 1;
                            this.func_214617_a(worldIn, k1 + j3, i2 + l4, l1 + i4, boundsIn, changedBlocks);
                            this.func_214617_a(worldIn, 1 + k1 - j3, i2 + l4, l1 + i4, boundsIn, changedBlocks);
                            this.func_214617_a(worldIn, k1 + j3, i2 + l4, 1 + l1 - i4, boundsIn, changedBlocks);
                            this.func_214617_a(worldIn, 1 + k1 - j3, i2 + l4, 1 + l1 - i4, boundsIn, changedBlocks);
                        }
                    }
                }

                if (rand.nextBoolean()) {
                    this.func_214617_a(worldIn, k1, i2 + 2, l1, boundsIn, changedBlocks);
                    this.func_214617_a(worldIn, k1 + 1, i2 + 2, l1, boundsIn, changedBlocks);
                    this.func_214617_a(worldIn, k1 + 1, i2 + 2, l1 + 1, boundsIn, changedBlocks);
                    this.func_214617_a(worldIn, k1, i2 + 2, l1 + 1, boundsIn, changedBlocks);
                }

                for(int k3 = -3; k3 <= 4; ++k3) {
                    for(int j4 = -3; j4 <= 4; ++j4) {
                        if ((k3 != -3 || j4 != -3) && (k3 != -3 || j4 != 4) && (k3 != 4 || j4 != -3) && (k3 != 4 || j4 != 4) && (Math.abs(k3) < 3 || Math.abs(j4) < 3)) {
                            this.func_214617_a(worldIn, k1 + k3, i2, l1 + j4, boundsIn, changedBlocks);
                        }
                    }
                }

                for(int l3 = -1; l3 <= 2; ++l3) {
                    for(int k4 = -1; k4 <= 2; ++k4) {
                        if ((l3 < 0 || l3 > 1 || k4 < 0 || k4 > 1) && rand.nextInt(3) <= 0) {
                            int i5 = rand.nextInt(3) + 2;

                            for(int l2 = 0; l2 < i5; ++l2) {
                                this.func_214616_a(changedBlocks, worldIn, new BlockPos(j + l3, i2 - l2 - 1, l + k4), boundsIn);
                            }

                            for(int j5 = -1; j5 <= 1; ++j5) {
                                for(int i3 = -1; i3 <= 1; ++i3) {
                                    this.func_214617_a(worldIn, k1 + l3 + j5, i2, l1 + k4 + i3, boundsIn, changedBlocks);
                                }
                            }

                            for(int k5 = -2; k5 <= 2; ++k5) {
                                for(int l5 = -2; l5 <= 2; ++l5) {
                                    if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                                        this.func_214617_a(worldIn, k1 + l3 + k5, i2 - 1, l1 + k4 + l5, boundsIn, changedBlocks);
                                    }
                                }
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean func_214615_a(IWorldGenerationBaseReader baseReader, BlockPos blockPos, int i2) {
        int i = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int l = 0; l <= i2 + 1; ++l) {
            int i1 = 1;
            if (l == 0) {
                i1 = 0;
            }

            if (l >= i2 - 1) {
                i1 = 2;
            }

            for(int j1 = -i1; j1 <= i1; ++j1) {
                for(int k1 = -i1; k1 <= i1; ++k1) {
                    if (!func_214587_a(baseReader, blockpos$mutableblockpos.setPos(i + j1, j + l, k + k1))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void func_214616_a(Set<BlockPos> blockPos, IWorldGenerationReader generationReader, BlockPos blockPos2, MutableBoundingBox mutableBoundingBox) {
        if (func_214587_a(generationReader, blockPos2)) {
            this.setLogState(blockPos, generationReader, blockPos2, LOG, mutableBoundingBox);
        }

    }

    private void func_214617_a(IWorldGenerationReader generationReader, int x, int y, int z, MutableBoundingBox mutableBoundingBox, Set<BlockPos> blockPos) {
        BlockPos blockpos = new BlockPos(x, y, z);
        if (isAir(generationReader, blockpos)) {
            this.setLogState(blockPos, generationReader, blockpos, LEAVES, mutableBoundingBox);
        }
    }
}
