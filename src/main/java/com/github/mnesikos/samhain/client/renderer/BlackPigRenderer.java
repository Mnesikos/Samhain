package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.client.model.BlackPigModel;
import com.github.mnesikos.samhain.common.entity.BlackPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BlackPigRenderer extends MobRenderer<BlackPigEntity, BlackPigModel<BlackPigEntity>> {
    private static final ResourceLocation black_pig = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/black_pig.png");

    public BlackPigRenderer(EntityRendererManager manager) {
        super(manager, new BlackPigModel<>(), 0.7F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(BlackPigEntity blackPigEntity) {
        return black_pig;
    }
}
