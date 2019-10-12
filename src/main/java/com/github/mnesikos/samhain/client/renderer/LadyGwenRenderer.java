package com.github.mnesikos.samhain.client.renderer;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.entity.LadyGwenEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LadyGwenRenderer extends MobRenderer<LadyGwenEntity, PlayerModel<LadyGwenEntity>> {
    private static final ResourceLocation lady_gwen_1 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/lady_gwen_1.png");
    private static final ResourceLocation lady_gwen_2 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/lady_gwen_2.png");
    private static final ResourceLocation lady_gwen_3 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/lady_gwen_3.png");
    private static final ResourceLocation lady_gwen_4 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/lady_gwen_4.png");
    private static final ResourceLocation lady_gwen_5 = new ResourceLocation(Samhain.MOD_ID, "textures/entity/lady_gwen/lady_gwen_5.png");

    public LadyGwenRenderer(EntityRendererManager manager) {
        super(manager, new PlayerModel<>(0.0F, true), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(LadyGwenEntity entity) {
        int var = entity.getVariant();
        switch (var) {
            case 0: default:
                return lady_gwen_1;
            case 1:
                return lady_gwen_2;
            case 2:
                return lady_gwen_3;
            case 3:
                return lady_gwen_4;
            case 4:
                return lady_gwen_5;
        }
    }
}
