package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.block.ModBlock;
import com.github.mnesikos.samhain.common.block.OtherworldPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks extends ModRegistry<Block> {
    public static final List<Item> ITEM_BLOCKS = new ArrayList<>();

    //made this a constant
    public static final ModBlock CRIMSON_KING_MAPLE_LOG = new ModBlock("crimson_king_maple_log");
    public static final ModBlock OTHERWORLD_PORTAL = new OtherworldPortalBlock();

    public static void registerItemBlocks(List<Item> registry) {
        registry.addAll(ITEM_BLOCKS);
    }
}
