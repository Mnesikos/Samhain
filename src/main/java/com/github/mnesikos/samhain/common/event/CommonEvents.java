package com.github.mnesikos.samhain.common.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Samhain.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerDimensions(RegisterDimensionsEvent event) {
        ModDimensions.setType(ModDimensions.OTHERWORLD, null, false);
    }
}
