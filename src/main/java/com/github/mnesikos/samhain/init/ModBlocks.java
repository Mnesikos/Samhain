package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.common.block.ModBlock;
import com.github.mnesikos.samhain.common.block.OtherworldPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks extends ModRegistry<Block> {
    public static final List<Item> ITEM_BLOCKS = new ArrayList<>();

    //made this a constant
    public static final ModBlock CRIMSON_KING_MAPLE_LOG = new ModBlock("crimson_king_maple_log");
    public static final ModBlock OTHERWORLD_PORTAL = new OtherworldPortalBlock();

    static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEM_BLOCKS.toArray(new Item[0]));
    }
}
