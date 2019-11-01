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

            for (int j3 = -4; j3 <= 0; ++j3) { // X + -4~0
                for (int i4 = -4; i4 <= 0; ++i4) { // Z + -4~0
                    int l4 = -1; // (height - 1) + -1
                        this.replaceAirWithLeaves(worldIn, posX + j3, i2 + l4, posZ + i4);
                        this.replaceAirWithLeaves(worldIn, posX - j3, i2 + l4, posZ + i4);
                        this.replaceAirWithLeaves(worldIn, posX + j3, i2 + l4, posZ - i4);
                        this.replaceAirWithLeaves(worldIn, posX - j3, i2 + l4, posZ - i4);
                }
            }

            if (rand.nextBoolean()) { // top most layer of leaves
                this.replaceAirWithLeaves(worldIn, posX, i2 + 2, posZ);
                this.replaceAirWithLeaves(worldIn, posX, i2 + 2, posZ + 1);
                this.replaceAirWithLeaves(worldIn, posX, i2 + 2, posZ - 1);
                this.replaceAirWithLeaves(worldIn, posX + 1, i2 + 2, posZ);
                this.replaceAirWithLeaves(worldIn, posX + 1, i2 + 2, posZ + 1);
                this.replaceAirWithLeaves(worldIn, posX + 1, i2 + 2, posZ - 1);
                this.replaceAirWithLeaves(worldIn, posX - 1, i2 + 2, posZ);
                this.replaceAirWithLeaves(worldIn, posX - 1, i2 + 2, posZ + 1);
                this.replaceAirWithLeaves(worldIn, posX - 1, i2 + 2, posZ - 1);
            }

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
