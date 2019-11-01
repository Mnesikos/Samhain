package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.common.entity.LadyGwenEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LadyGwenRenderer extends SamhainCreatureRenderer<LadyGwenEntity, PlayerModel<LadyGwenEntity>> {
    public LadyGwenRenderer(EntityRendererManager manager) {
        super(manager, new PlayerModel<>(0.0F, true), 0.5F);
    }
}
