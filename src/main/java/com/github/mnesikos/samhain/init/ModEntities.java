package com.github.mnesikos.samhain.init;

import com.github.mnesikos.samhain.Ref;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
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

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(EntityType.Builder.create(SpiritEntity::new, EntityClassification.CREATURE)
                .size(1, 1)
                .setShouldReceiveVelocityUpdates(false)
                .build("spirit").setRegistryName(Ref.MOD_ID, "spirit"));
    }
}
