package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Ref;
import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Ref.MOD_ID)
public class ModBlocks {
    @ObjectHolder(Ref.MOD_ID + ":crimson_king_maple_log")
    public static ModBlock crimson_king_maple_log = new ModBlock("crimson_king_maple_log");

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                crimson_king_maple_log
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        Item.Properties properties = new Item.Properties().group(Samhain.proxy.itemGroup);
        event.getRegistry().registerAll(
                new BlockItem(crimson_king_maple_log, properties).setRegistryName(crimson_king_maple_log.getRegistryName())
        );
    }
}
