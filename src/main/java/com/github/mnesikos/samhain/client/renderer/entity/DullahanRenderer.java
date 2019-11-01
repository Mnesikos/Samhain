package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.client.renderer.entity.model.DullahanModel;
import com.github.mnesikos.samhain.common.entity.DullahanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DullahanRenderer extends SamhainCreatureRenderer<DullahanEntity, DullahanModel<DullahanEntity>> {
    public DullahanRenderer(EntityRendererManager manager) {
        super(manager, new DullahanModel<>(0.0F, false), 0.5F);
    }
}
