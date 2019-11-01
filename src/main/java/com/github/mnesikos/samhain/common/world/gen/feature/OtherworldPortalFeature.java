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
        int height = rand.nextInt(3) + rand.nextInt(2) + 6; // 0~2 + 0~1 + 6 // max = 9, min = 6
        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();
        if (posY >= 1 && posY + height + 1 < 256) {
            BlockPos base = pos.down();
            this.create3x3(worldIn, base);
            this.setDirtAt(worldIn, base.down(), pos);
            this.setDirtAt(worldIn, base.down().east(), pos);
            this.setDirtAt(worldIn, base.down().west(), pos);
            this.setDirtAt(worldIn, base.down().north(), pos);
            this.setDirtAt(worldIn, base.down().north().east(), pos);
            this.setDirtAt(worldIn, base.down().north().west(), pos);
            this.setDirtAt(worldIn, base.down().south(), pos);
            this.setDirtAt(worldIn, base.down().south().east(), pos);
            this.setDirtAt(worldIn, base.down().south().west(), pos);

            Direction direction = Direction.Plane.HORIZONTAL.random(rand);
            //why is this all a thing? almost everything is unused
            int height1 = height - rand.nextInt(4); // height - 0~3 // max = 9, min = 3
            int j1 = 2 - rand.nextInt(3); // 2 - 0~2 // max = 2, min = 0
            int i2 = posY + height - 1;
            BlockPos portalPos = base.up();
            for (int j2 = 0; j2 < height; ++j2) {
                int k2 = posY + j2;
                BlockPos trunkPos = new BlockPos(posX, k2, posZ);
                if (isAirOrLeaves(worldIn, trunkPos)) { // trunk
                    this.create3x3(worldIn, trunkPos);
                }
            }

            for (int p = 0; p < 3; p++) { // portal & 1 random side open
                setBlockState(worldIn, portalPos.up(p), ModBlocks.OTHERWORLD_PORTAL.getDefaultState());
                setBlockState(worldIn, pos.offset(direction).up(p), Blocks.CAVE_AIR.getDefaultState());
            }

            /*for (int j3 = -2; j3 <= 0; ++j3) { // X + -2~0
                for (int i4 = -2; i4 <= 0; ++i4) { // Z + -2~0
                    int l4 = -1; // (height - 1) + -1
                    this.replaceAirWithLeaves(worldIn, posX1 + j3, i2 + l4, posZ1 + i4);
                    this.replaceAirWithLeaves(worldIn, 1 + posX1 - j3, i2 + l4, posZ1 + i4);
                    this.replaceAirWithLeaves(worldIn, posX1 + j3, i2 + l4, 1 + posZ1 - i4);
                    this.replaceAirWithLeaves(worldIn, 1 + posX1 - j3, i2 + l4, 1 + posZ1 - i4);
                    if ((j3 > -2 || i4 > -1) && (j3 != -1 || i4 != -2)) { // ???? wtf
                    // (j3 == -1~0 || i4 == 0) && (j3 == -2 or 0 || i4 == -1~0)
                        l4 = 1;
                        this.replaceAirWithLeaves(worldIn, posX1 + j3, i2 + l4, posZ1 + i4);
                        this.replaceAirWithLeaves(worldIn, 1 + posX1 - j3, i2 + l4, posZ1 + i4);
                        this.replaceAirWithLeaves(worldIn, posX1 + j3, i2 + l4, 1 + posZ1 - i4);
                        this.replaceAirWithLeaves(worldIn, 1 + posX1 - j3, i2 + l4, 1 + posZ1 - i4);
                    }
                }
            }*/

            /*if (rand.nextBoolean()) {
                this.replaceAirWithLeaves(worldIn, posX1, i2 + 2, posZ1);
                this.replaceAirWithLeaves(worldIn, posX1 + 1, i2 + 2, posZ1);
                this.replaceAirWithLeaves(worldIn, posX1 + 1, i2 + 2, posZ1 + 1);
                this.replaceAirWithLeaves(worldIn, posX1, i2 + 2, posZ1 + 1);
            }*/

            /*for (int k3 = -3; k3 <= 4; ++k3) {
                for (int j4 = -3; j4 <= 4; ++j4) {
                    if ((k3 != -3 || j4 != -3) && (k3 != -3 || j4 != 4) && (k3 != 4 || j4 != -3) && (k3 != 4 || j4 != 4) && (Math.abs(k3) < 3 || Math.abs(j4) < 3)) {
                        this.replaceAirWithLeaves(worldIn, posX1 + k3, i2, posZ1 + j4);
                    }
                }
            }*/

            /*for (int l3 = -1; l3 <= 2; ++l3) {
                for (int k4 = -1; k4 <= 2; ++k4) {
                    if ((l3 < 0 || l3 > 1 || k4 < 0 || k4 > 1) && rand.nextInt(3) <= 0) {
                        int i5 = rand.nextInt(3) + 2;

                        for (int l2 = 0; l2 < i5; ++l2) {
                            this.create3x3(worldIn, new BlockPos(posX + l3, i2 - l2 - 1, posZ + k4));
                        }

                        for (int j5 = -1; j5 <= 1; ++j5) {
                            for (int i3 = -1; i3 <= 1; ++i3) {
                                this.replaceAirWithLeaves(worldIn, posX1 + l3 + j5, i2, posZ1 + k4 + i3);
                            }
                        }

                        for (int k5 = -2; k5 <= 2; ++k5) {
                            for (int l5 = -2; l5 <= 2; ++l5) {
                                if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                                    this.replaceAirWithLeaves(worldIn, posX1 + l3 + k5, i2 - 1, posZ1 + k4 + l5);
                                }
                            }
                        }
                        // portal was here
                    }
                }
            }*/

            return true;

        } else {
            return false;
        }
    }

    private void create3x3(IWorldWriter worldIn, BlockPos pos) {
        this.setBlockState(worldIn, pos, CrimsonKingMapleTreeFeature.LOG);
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
        worldIn.getBlockState(pos).onPlantGrow(worldIn, pos, origin);
    }

    private boolean isAir(IWorld worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, state -> state.isAir(worldIn, pos));
    }

    private boolean isAirOrLeaves(IWorld worldIn, BlockPos pos) {
        return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves(worldIn, pos));
    }
}
