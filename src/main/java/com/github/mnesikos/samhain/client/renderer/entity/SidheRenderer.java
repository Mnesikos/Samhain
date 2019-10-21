package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.entity.model.SidheModel;
import com.github.mnesikos.samhain.common.entity.SidheEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SidheRenderer extends MobRenderer<SidheEntity, SidheModel<SidheEntity>> {
    private static final ResourceLocation blue = new ResourceLocation(Samhain.MOD_ID, "textures/entity/sidhe/sidhe_blue.png");
    private static final ResourceLocation pink = new ResourceLocation(Samhain.MOD_ID, "textures/entity/sidhe/sidhe_pink.png");
    private static final ResourceLocation yellow = new ResourceLocation(Samhain.MOD_ID, "textures/entity/sidhe/sidhe_yellow.png");

    public SidheRenderer(EntityRendererManager manager) {
        super(manager, new SidheModel<>(), 0.2F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull SidheEntity entity) {
        int var = entity.getVariant();
        switch (var) {
            case 0: default:
                return blue;
            case 1:
                return pink;
            case 2:
                return yellow;
        }
    }
}
