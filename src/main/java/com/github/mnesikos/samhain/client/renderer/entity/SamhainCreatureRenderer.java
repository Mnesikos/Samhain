package com.github.mnesikos.samhain.client.renderer.entity;

import com.github.mnesikos.samhain.Samhain;
import com.github.mnesikos.samhain.common.entity.SamhainCreatureEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class SamhainCreatureRenderer<T extends SamhainCreatureEntity, M extends EntityModel<T>> extends MobRenderer<T, M> {
    private ResourceLocation[] textures;

    SamhainCreatureRenderer(EntityRendererManager p_i50961_1_, M p_i50961_2_, float p_i50961_3_) {
        super(p_i50961_1_, p_i50961_2_, p_i50961_3_);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        if (textures == null) {
            textures = new ResourceLocation[entity.variantNumber()];
            for (int i = 0; i < entity.variantNumber(); i++)
                textures[i] = new ResourceLocation(Samhain.MOD_ID, "textures/entity/" + Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(entity.getType())).getPath() + (entity.variantNumber() > 1 ? "/texture_" + (i + 1) : "") + ".png");
        }
        return textures[entity.getVariant()];
    }
}
