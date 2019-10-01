package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.common.entity.SpiritEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiritRenderer extends MobRenderer<SpiritEntity, CowModel<SpiritEntity>> {
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation("textures/entity/cow/cow.png");
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
    public static final ResourceLocation[] PARROT_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_green.png"), new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"), new ResourceLocation("textures/entity/parrot/parrot_grey.png")};
    private static final ResourceLocation FOX_SNOW_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox.png");
    private static final ResourceLocation PANDA_TEXTURE = new ResourceLocation("textures/entity/panda/worried_panda.png");
    private static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation("textures/entity/villager/villager.png");
    private static final ResourceLocation PIG_TEXTURE = new ResourceLocation("textures/entity/pig/pig.png");
    private static final ResourceLocation SHEARED_SHEEP_TEXTURE = new ResourceLocation("textures/entity/sheep/sheep.png");
    private static final ResourceLocation SHEEP_FUR = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("textures/entity/chicken.png");

    public SpiritRenderer(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_, new CowModel(), 0.7F);
    }

    @Override
    protected void preRenderCallback(SpiritEntity p_77041_1_, float p_77041_2_) {
        GlStateManager.enableBlend();
        GlStateManager.color4f(0.49803921568f, 0.70588235294f, 1, 0.65F);
    }

    protected ResourceLocation getEntityTexture(SpiritEntity p_110775_1_) {
        return COW_TEXTURES;
    }
}
