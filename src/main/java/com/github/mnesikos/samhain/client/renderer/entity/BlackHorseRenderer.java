package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.entity.BlackHorseEntity;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BlackHorseRenderer extends AbstractHorseRenderer<BlackHorseEntity, HorseModel<BlackHorseEntity>> {
    private static final ResourceLocation horse = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/horse.png");
    private static final ResourceLocation horse_overlay = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/horse_overlay.png");

    //todo render overlay & add fire particles

    public BlackHorseRenderer(EntityRendererManager manager) {
        super(manager, new HorseModel<>(0.0F), 1.2F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(BlackHorseEntity entity) {
        return horse;
    }
}
