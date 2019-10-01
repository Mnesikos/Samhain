package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Ref.MOD_ID)
public class ModBlocks {
    public static final LogBlock crimson_king_maple_log = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD).hardnessAndResistance(5).harvestLevel(1).harvestTool(ToolType.AXE)).setRegistryName(Ref.MOD_ID, "crimson_king_maple_log")
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new BlockItem(crimson_king_maple_log, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS).maxStackSize(64)).setRegistryName(crimson_king_maple_log.getRegistryName())
        );
    }
}
