package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiritRenderer extends EntityRenderer<SpiritEntity> {

    public SpiritRenderer(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_);
    }

    @Override
    public void doRender(SpiritEntity spirit, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        super.doRender(spirit, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        if(spirit.base != null) {
            EntityRenderer<AgeableEntity> renderer = getRenderManager().getRenderer(spirit.base);
            if (renderer != null) {
                GlStateManager.enableBlend();
                GlStateManager.color4f(0.49803921568f, 0.70588235294f, 1, 0.65F);
                renderer.doRender(spirit.base, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            }
        }
    }

    protected ResourceLocation getEntityTexture(SpiritEntity p_110775_1_) {
        return null;
    }
}
