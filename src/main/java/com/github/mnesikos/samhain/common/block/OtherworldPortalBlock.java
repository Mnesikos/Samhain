package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class OtherworldPortalBlock extends ModBlock {
    public OtherworldPortalBlock() {
        super("otherworld_portal");
    }

    @Override
    public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
        //temp way to get to the dimension
        p_220051_4_.changeDimension(p_220051_4_.dimension == ModDimensions.OTHERWORLD ? DimensionType.OVERWORLD : ModDimensions.OTHERWORLD);
        return true;
    }

    @Override
    public void onEntityCollision(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
        super.onEntityCollision(p_196262_1_, p_196262_2_, p_196262_3_, p_196262_4_);
    }
}
