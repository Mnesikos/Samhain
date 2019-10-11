package com.github.mnesikos.samhain.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Created by Coda using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class SidheModel<T extends LivingEntity> extends EntityModel<T> {
    public RendererModel body;
    public RendererModel legLeft;
    public RendererModel legRight;
    public RendererModel armLeft;
    public RendererModel armRight;
    public RendererModel head;
    public RendererModel wingLeft;
    public RendererModel wingRight;
    public RendererModel hair;

    public SidheModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.hair = new RendererModel(this, 10, 4);
        this.hair.setRotationPoint(0.0F, -1.5F, 0.5F);
        this.hair.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.legLeft = new RendererModel(this, 6, 0);
        this.legLeft.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.legLeft.addBox(0.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.legRight = new RendererModel(this, 10, 0);
        this.legRight.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.legRight.addBox(-1.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.armLeft = new RendererModel(this, 14, 0);
        this.armLeft.setRotationPoint(1.0F, -1.5F, 0.0F);
        this.armLeft.addBox(0.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.armRight = new RendererModel(this, 18, 0);
        this.armRight.setRotationPoint(-1.0F, -1.5F, 0.0F);
        this.armRight.addBox(-1.0F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
        this.head = new RendererModel(this, 22, 0);
        this.head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.head.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.wingRight = new RendererModel(this, 0, 1);
        this.wingRight.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.wingRight.addBox(0.0F, -2.0F, 0.0F, 0, 4, 3, 0.0F);
        this.setRotateAngle(wingRight, 0.0F, -1.0471975511965976F, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 19.5F, 0.0F);
        this.body.addBox(-1.0F, -1.5F, -0.5F, 2, 3, 1, 0.0F);
        this.wingLeft = new RendererModel(this, 0, 1);
        this.wingLeft.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.wingLeft.addBox(0.0F, -2.0F, 0.0F, 0, 4, 3, 0.0F);
        this.setRotateAngle(wingLeft, 0.0F, 1.0471975511965976F, 0.0F);
        this.head.addChild(this.hair);
        this.body.addChild(this.legLeft);
        this.body.addChild(this.legRight);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.armRight);
        this.body.addChild(this.head);
        this.body.addChild(this.wingRight);
        this.body.addChild(this.wingLeft);
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.body.render(f5);
    }

    @Override
    public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
