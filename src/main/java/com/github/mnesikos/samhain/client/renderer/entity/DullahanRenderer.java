package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.renderer.entity.model.DullahanModel;
import com.github.mnesikos.samhain.common.entity.DullahanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class DullahanRenderer extends MobRenderer<DullahanEntity, DullahanModel<DullahanEntity>> {
    private static final ResourceLocation dullahan_1 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/dullahan_1.png");
    private static final ResourceLocation dullahan_2 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/dullahan_2.png");
    private static final ResourceLocation dullahan_3 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/dullahan_3.png");
    private static final ResourceLocation dullahan_4 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/dullahan_4.png");
    private static final ResourceLocation dullahan_5 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/dullahan/dullahan_5.png");

    public DullahanRenderer(EntityRendererManager manager) {
        super(manager, new DullahanModel<>(0.0F, false), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(DullahanEntity entity) {
        int var = entity.getVariant();
        switch (var) {
            case 0: default:
                return dullahan_1;
            case 1:
                return dullahan_2;
            case 2:
                return dullahan_3;
            case 3:
                return dullahan_4;
            case 4:
                return dullahan_5;
        }
    }
}
