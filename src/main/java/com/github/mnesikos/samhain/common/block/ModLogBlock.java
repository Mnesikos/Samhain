package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModBlocks;
import com.github.mnesikos.samhain.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModLogBlock extends LogBlock {
    public ModLogBlock(String name, MaterialColor verticalColorIn, Block.Properties properties) {
        super(verticalColorIn, properties);
        setRegistryName(name);
        ModBlocks.ITEM_BLOCKS.add(new BlockItem(this, new Item.Properties().group(ModItems.GROUP)).setRegistryName(name));
    }
}
