package com.github.mnesikos.samhain.client.event;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.OtherworldWaterFog;
import com.github.mnesikos.samhain.init.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Samhain.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    public static void renderFog(EntityViewRenderEvent.FogColors event) {
        //rendering the water fog in the otherworld
        World world = Minecraft.getInstance().world;
        if(world.dimension.getType() == ModDimensions.TYPES.get(ModDimensions.OTHERWORLD)) {
            ActiveRenderInfo info = event.getInfo();
            IFluidState ifluidstate = info.getFluidState();
            if (ifluidstate.isTagged(FluidTags.WATER)) {
                Vector3f colors = OtherworldWaterFog.getColors(world, info);
                event.setRed(colors.getX());
                event.setGreen(colors.getY());
                event.setBlue(colors.getZ());
            }
        }
    }
}
