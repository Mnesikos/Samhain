package com.github.mnesikos.samhain.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class GravestoneBlock extends ModBlock implements IWaterLoggable {
    public GravestoneBlock(String name) {
        super(name, Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).sound(SoundType.STONE));
    }

    // todo
}
