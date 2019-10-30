package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModBlocks;
import com.github.mnesikos.samhain.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModSaplingBlock extends SaplingBlock {
    public ModSaplingBlock(String name, Tree treeIn, Block.Properties properties) {
        super(treeIn, properties);
        setRegistryName(name);
        ModBlocks.ITEM_BLOCKS.add(new BlockItem(this, new Item.Properties().group(ModItems.GROUP)).setRegistryName(name));
    }
}
