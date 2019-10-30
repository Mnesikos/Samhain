package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModBlocks;
import com.github.mnesikos.samhain.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModLeavesBlock extends LeavesBlock {
    public ModLeavesBlock(String name, Block.Properties properties) {
        super(properties);
        setRegistryName(name);
        ModBlocks.ITEM_BLOCKS.add(new BlockItem(this, new Item.Properties().group(ModItems.GROUP)).setRegistryName(name));
    }
}
