package com.github.mnesikos.samhain.client;

import com.github.mnesikos.samhain.IProxy;
import com.github.mnesikos.samhain.client.renderer.BlackPigRenderer;
import com.github.mnesikos.samhain.client.renderer.SidheRenderer;
import com.github.mnesikos.samhain.client.renderer.SpiritRenderer;
import com.github.mnesikos.samhain.common.entity.BlackPigEntity;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import com.github.mnesikos.samhain.common.entity.goals.SidheEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(SpiritEntity.class, SpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SidheEntity.class, SidheRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BlackPigEntity.class, BlackPigRenderer::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
}
