package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.capability.DimensionCapabilityProvider;
import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Samhain.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerDimensions(RegisterDimensionsEvent event) {
        //all dimensions registered need to have their type set through here
        ModDimensions.setType(ModDimensions.OTHERWORLD, false);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) event.addCapability(DimensionCapabilityProvider.HOLDER_RESOURCE, new DimensionCapabilityProvider());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(DimensionCapabilityProvider.CAPABILITY).ifPresent(itemHolder -> {
            event.getPlayer().inventory.read(itemHolder.inventory);
            itemHolder.inventory = null;
        });
    }

    @SubscribeEvent
    public static void changeDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            World world = player.world;
            player.getCapability(DimensionCapabilityProvider.CAPABILITY).ifPresent(itemHolder -> {
                if (event.getDimension() == ModDimensions.TYPES.get(ModDimensions.OTHERWORLD)) {
                    itemHolder.inventory = player.inventory.write(new ListNBT());
                    player.inventory.clear();
                } else if(itemHolder.inventory != null) {
                    player.inventory.read(itemHolder.inventory);
                    itemHolder.inventory = null;
                }
            });
        }
    }
}
