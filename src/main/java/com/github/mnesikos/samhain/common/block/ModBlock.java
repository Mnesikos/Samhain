package com.github.mnesikos.samhain.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ModBlock extends Block {
    public ModBlock(String name) {
        super(Properties.create(Material.WOOD)
        .sound(SoundType.WOOD)
        .hardnessAndResistance(1.0F)
        );
        setRegistryName(name);
    }
}
