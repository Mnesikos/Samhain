package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.entity.*;
import com.github.mnesikos.samhain.common.capability.DimensionCapabilityProvider;
import com.github.mnesikos.samhain.common.capability.DimensionItemHolder;
import com.github.mnesikos.samhain.common.entity.*;
import com.github.mnesikos.samhain.init.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Samhain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistries {

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
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if(!BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntities.LADY_GWEN, 1, 1, 1));
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntities.DULLAHAN, 2, 1, 1));
                /*biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntities.SPIRIT, 2, 1, 2));*/

            }
        }
        //for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST)) biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntities.SIDHE, 1, 3, 6));
        Biomes.FOREST.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{ModFeatures.CRIMSON_KING_MAPLE_TREE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.01F}, ModFeatures.CRIMSON_KING_MAPLE_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
        Biomes.FOREST.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{ModFeatures.OTHERWORLD_PORTAL}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.1F}, ModFeatures.OTHERWORLD_PORTAL, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0, 1)));
    }

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModRegistry.register(event.getRegistry(), ModEntities::new);
        CapabilityManager.INSTANCE.register(DimensionItemHolder.class, new DimensionCapabilityProvider.HolderStorage(), DimensionCapabilityProvider::new);
    }

    @SubscribeEvent
    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
        ModRegistry.register(event.getRegistry(), ModFeatures::new);
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
        //sidenote, this is part of the sided setup registry, so afaik, this should only be called on the client
        //moved renders from proxy to here
        RenderingRegistry.registerEntityRenderingHandler(SpiritEntity.class, SpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SidheEntity.class, SidheRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackPigEntity.class, BlackPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(LadyGwenEntity.class, LadyGwenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackHorseEntity.class, BlackHorseRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DullahanEntity.class, DullahanRenderer::new);
    }
}
