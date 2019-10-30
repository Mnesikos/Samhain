package com.github.mnesikos.samhain.common.block;

import com.github.mnesikos.samhain.init.ModBlocks;
import com.github.mnesikos.samhain.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ModBlock extends Block {
    public ModBlock(String name, Block.Properties properties) {
        this(name, properties, true);
    }

    public ModBlock(String name, Block.Properties properties, boolean group) {
        super(properties);
        setRegistryName(name);
        if(group) ModBlocks.ITEM_BLOCKS.add(new BlockItem(this, new Item.Properties().group(ModItems.GROUP)).setRegistryName(name));
    }
}
