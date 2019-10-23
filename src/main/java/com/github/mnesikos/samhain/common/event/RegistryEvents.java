package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.color.OtherworldColors;
import com.github.mnesikos.samhain.client.renderer.entity.*;
import com.github.mnesikos.samhain.common.entity.*;
import com.github.mnesikos.samhain.init.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShearableDoublePlantBlock;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Samhain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    //moved registering stuff here, so it uses only one event handler, and everything is now added to lists and registered at the same time

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        ModRegistry.register(event.getRegistry(), ModBlocks::new);
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ModRegistry<Item> registry = ModRegistry.make(ModItems::new);
        List<Item> list = registry.list;
        ModEntities.registerEggs(list);
        ModBlocks.registerItemBlocks(list);
        registry.init(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModRegistry.register(event.getRegistry(), ModEntities::new);
    }

    @SubscribeEvent
    public static void registerChunkGenerators(final RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
        ModRegistry.register(event.getRegistry(), ModChunkGenerators::new);
    }

    @SubscribeEvent
    public static void registerDimensions(final RegistryEvent.Register<ModDimension> event) {
        ModRegistry.register(event.getRegistry(), ModDimensions::new);
    }

    @SubscribeEvent
    public static void registerBlocKColors(final ColorHandlerEvent.Block event) {
        //redefine block colors to work in the otherworld without needing to change it from the biomes
        BlockColors blockcolors = event.getBlockColors();
        blockcolors.register((p_210234_0_, p_210234_1_, p_210234_2_, p_210234_3_) -> p_210234_1_ != null && p_210234_2_ != null ? OtherworldColors.getGrassColor(p_210234_1_, p_210234_0_.get(ShearableDoublePlantBlock.PLANT_HALF) == DoubleBlockHalf.UPPER ? p_210234_2_.down() : p_210234_2_) : -1, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockcolors.register((p_210225_0_, p_210225_1_, p_210225_2_, p_210225_3_) -> p_210225_1_ != null && p_210225_2_ != null ? OtherworldColors.getGrassColor(p_210225_1_, p_210225_2_) : GrassColors.get(0.5D, 1.0D), Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.GRASS, Blocks.POTTED_FERN);
        blockcolors.register((p_210227_0_, p_210227_1_, p_210227_2_, p_210227_3_) -> OtherworldColors.getSpruce(p_210227_1_, p_210227_2_), Blocks.SPRUCE_LEAVES);
        blockcolors.register((p_210232_0_, p_210232_1_, p_210232_2_, p_210232_3_) -> OtherworldColors.getBirch(p_210232_1_, p_210232_2_), Blocks.BIRCH_LEAVES);
        blockcolors.register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> p_210229_1_ != null && p_210229_2_ != null ? OtherworldColors.getFoliageColor(p_210229_1_, p_210229_2_) : FoliageColors.getDefault(), Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE);
        blockcolors.register((p_210226_0_, p_210226_1_, p_210226_2_, p_210226_3_) -> p_210226_1_ != null && p_210226_2_ != null ? OtherworldColors.getWaterColor(p_210226_1_, p_210226_2_) : -1, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.CAULDRON);
        blockcolors.register((p_210230_0_, p_210230_1_, p_210230_2_, p_210230_3_) -> p_210230_1_ != null && p_210230_2_ != null ? OtherworldColors.getGrassColor(p_210230_1_, p_210230_2_) : -1, Blocks.SUGAR_CANE);
    }

    @SubscribeEvent
    public static void registerRenders(final ModelRegistryEvent event) {
        //sidenote, this is part of the sided setup registry, so afaik, this should only be called on the client
        //moved renders from proxy to here
        RenderingRegistry.registerEntityRenderingHandler(SpiritEntity.class, SpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SidheEntity.class, SidheRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackPigEntity.class, BlackPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(LadyGwenEntity.class, LadyGwenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackHorseEntity.class, BlackHorseRenderer::new);
    }
}
