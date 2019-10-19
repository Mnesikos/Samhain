package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.BlackPigRenderer;
import com.github.mnesikos.samhain.client.renderer.LadyGwenRenderer;
import com.github.mnesikos.samhain.client.renderer.SidheRenderer;
import com.github.mnesikos.samhain.client.renderer.SpiritRenderer;
import com.github.mnesikos.samhain.common.entity.BlackPigEntity;
import com.github.mnesikos.samhain.common.entity.LadyGwenEntity;
import com.github.mnesikos.samhain.common.entity.SidheEntity;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import com.github.mnesikos.samhain.init.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.gen.ChunkGeneratorType;
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
    public static void registerRenders(final ModelRegistryEvent event) {
        //moved renders from proxy to here
        RenderingRegistry.registerEntityRenderingHandler(SpiritEntity.class, SpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SidheEntity.class, SidheRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackPigEntity.class, BlackPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(LadyGwenEntity.class, LadyGwenRenderer::new);
    }
}
