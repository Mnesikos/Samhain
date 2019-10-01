package com.github.mnesikos.samhain.client;

import com.github.mnesikos.samhain.IProxy;
import com.github.mnesikos.samhain.client.renderer.SpiritRenderer;
import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(SpiritEntity.class, SpiritRenderer::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
}
