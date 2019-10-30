package com.github.mnesikos.samhain.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;

public class GravestoneBlock extends ModBlock implements IWaterLoggable {
    public static final EnumProperty<Style> STYLE = EnumProperty.create("style", Style.class);

    public GravestoneBlock() {
        super("gravestone", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.STONE));
        this.setDefaultState(getDefaultState().with(STYLE, Style.RIP_STONE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        super.fillStateContainer(p_206840_1_);
        p_206840_1_.add(STYLE);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public enum Style implements IStringSerializable {
        CROSS_STONE, RIP_STONE, CROSS_GOLD, RIP_GOLD;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
