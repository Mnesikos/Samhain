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
        AgeableEntity entity = spirit.getBase();
        if(entity != null) {
            EntityRenderer<AgeableEntity> renderer = getRenderManager().getRenderer(entity);
            if (renderer != null) {
                entity.setGrowingAge(spirit.getGrowingAge());
                entity.prevRenderYawOffset = spirit.prevRenderYawOffset;
                entity.renderYawOffset = spirit.renderYawOffset;
                entity.prevRotationYawHead = spirit.prevRotationYawHead;
                entity.rotationYawHead = spirit.rotationYawHead;
                entity.prevRotationPitch = spirit.prevRotationPitch;
                entity.rotationPitch = spirit.rotationPitch;
                entity.prevLimbSwingAmount = spirit.prevLimbSwingAmount;
                entity.limbSwingAmount = spirit.limbSwingAmount;
                entity.limbSwing = spirit.limbSwing;
                GlStateManager.enableBlend();
                GlStateManager.color4f(0.49803921568f, 0.70588235294f, 1, 0.65F);
                renderer.doRender(entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
                GlStateManager.disableBlend();
            }
        }
    }

    protected ResourceLocation getEntityTexture(SpiritEntity p_110775_1_) {
        return null;
    }
}
