package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.capability.DimensionCapabilityProvider;
import com.github.mnesikos.samhain.common.world.dimension.DimensionBase;
import com.github.mnesikos.samhain.init.ModDimensions;
import com.github.mnesikos.samhain.init.ModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.ModDimension;
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
        for(ModDimension registered : ModRegistry.getRegistered(ModDimensions.class)) {
            if(registered instanceof DimensionBase) {
                DimensionBase dimension = (DimensionBase) registered;
                ModDimensions.setType(dimension, dimension.getExtraData(), dimension.hasSkyLight());
            } else {
                Samhain.LOGGER.warn("Attempted to register a dimension that is not an instance of DimensionBase through Samhain's ModDimensions class, a template DimensionType will be registered and added to the ModDimensions.TYPES Map");
                ModDimensions.setType(registered, null, true);
            }
        }
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
        if(entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            player.getCapability(DimensionCapabilityProvider.CAPABILITY).ifPresent(itemHolder -> {
                if (event.getDimension() == ModDimensions.OTHERWORLD.getType()) {
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
