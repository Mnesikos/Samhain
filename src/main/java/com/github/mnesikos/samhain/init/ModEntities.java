package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Ref;
import com.github.mnesikos.samhain.common.entity.BlackPigEntity;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import com.github.mnesikos.samhain.common.entity.goals.SidheEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Ref.MOD_ID)
public class ModEntities {
    @ObjectHolder(Ref.MOD_ID + ":spirit")
    public static EntityType<SpiritEntity> SPIRIT;
    @ObjectHolder(Ref.MOD_ID + ":sidhe")
    public static EntityType<SidheEntity> SIDHE;
    @ObjectHolder(Ref.MOD_ID + ":black_pig")
    public static EntityType<SidheEntity> BLACK_PIG;


    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(EntityType.Builder.create(SpiritEntity::new, EntityClassification.CREATURE)
                .size(1, 1)
                .setShouldReceiveVelocityUpdates(false)
                .build("spirit").setRegistryName(Ref.MOD_ID, "spirit"));
        event.getRegistry().register(EntityType.Builder.create(SidheEntity::new, EntityClassification.CREATURE)
                .size(1, 1)
                .setShouldReceiveVelocityUpdates(false)
                .build("sidhe").setRegistryName(Ref.MOD_ID, "sidhe"));
        event.getRegistry().register(EntityType.Builder.create(BlackPigEntity::new, EntityClassification.CREATURE)
                .size(1, 1)
                .setShouldReceiveVelocityUpdates(true)
                .build("black_pig").setRegistryName(Ref.MOD_ID, "black_pig"));
    }
}
