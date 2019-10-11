package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModBlocks;
import com.github.mnesikos.samhain.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModBlock extends Block {
    public ModBlock(String name) {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(1.0F));
        setRegistryName(name);
        ModBlocks.LIST.add(this);
        ModItems.LIST.add(new BlockItem(this, new Item.Properties().group(ModItems.GROUP)).setRegistryName(name));
    }
}
