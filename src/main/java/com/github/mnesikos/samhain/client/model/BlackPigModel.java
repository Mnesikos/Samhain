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
public class BlackPigModel<T extends LivingEntity> extends EntityModel<T> {
    public RendererModel body;
    public RendererModel legRight;
    public RendererModel legLeft;
    public RendererModel armRight;
    public RendererModel armLeft;
    public RendererModel head;
    public RendererModel chest;
    public RendererModel snout;
    public RendererModel maneHead;
    public RendererModel tuskLeft;
    public RendererModel tuskRight;
    public RendererModel maneBody;

    public BlackPigModel() {
        this.textureWidth = 81;
        this.textureHeight = 80;
        this.chest = new RendererModel(this, 17, 20);
        this.chest.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.chest.addBox(-6.5F, -6.0F, -10.0F, 13, 10, 11, 0.0F);
        this.legRight = new RendererModel(this, 38, 0);
        this.legRight.setRotationPoint(-3.0F, 4.0F, 7.0F);
        this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.snout = new RendererModel(this, 54, 20);
        this.snout.setRotationPoint(0.0F, 1.5F, -6.0F);
        this.snout.addBox(-2.0F, -2.5F, -3.0F, 4, 4, 3, 0.0F);
        this.armRight = new RendererModel(this, 38, 10);
        this.armRight.setRotationPoint(-3.5F, 4.0F, -8.5F);
        this.armRight.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.maneHead = new RendererModel(this, 66, 4);
        this.maneHead.setRotationPoint(0.0F, -4.0F, -6.0F);
        this.maneHead.addBox(-0.5F, -3.0F, 0.0F, 1, 3, 6, 0.0F);
        this.body = new RendererModel(this, 0, 0);
        this.body.setRotationPoint(0.0F, 14.0F, 1.0F);
        this.body.addBox(-5.0F, -4.0F, -1.0F, 10, 8, 9, 0.0F);
        this.armLeft = new RendererModel(this, 38, 10);
        this.armLeft.mirror = true;
        this.armLeft.setRotationPoint(3.5F, 4.0F, -8.5F);
        this.armLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.head = new RendererModel(this, 0, 17);
        this.head.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.tuskRight = new RendererModel(this, 0, 0);
        this.tuskRight.setRotationPoint(-1.9F, 1.0F, -1.0F);
        this.tuskRight.addBox(0.0F, -2.0F, -0.5F, 0, 2, 1, 0.0F);
        this.setRotateAngle(tuskRight, 0.0F, 0.0F, -0.2617993877991494F);
        this.maneBody = new RendererModel(this, 0, 41);
        this.maneBody.setRotationPoint(0.0F, -6.0F, -10.0F);
        this.maneBody.addBox(-1.0F, -3.0F, 0.0F, 2, 5, 16, 0.0F);
        this.legLeft = new RendererModel(this, 38, 0);
        this.legLeft.mirror = true;
        this.legLeft.setRotationPoint(3.0F, 4.0F, 7.0F);
        this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.tuskLeft = new RendererModel(this, 0, 0);
        this.tuskLeft.mirror = true;
        this.tuskLeft.setRotationPoint(1.9F, 1.0F, -1.0F);
        this.tuskLeft.addBox(0.0F, -2.0F, -0.5F, 0, 2, 1, 0.0F);
        this.setRotateAngle(tuskLeft, 0.0F, 0.0F, 0.2617993877991494F);
        this.body.addChild(this.chest);
        this.body.addChild(this.legRight);
        this.head.addChild(this.snout);
        this.body.addChild(this.armRight);
        this.head.addChild(this.maneHead);
        this.body.addChild(this.armLeft);
        this.body.addChild(this.head);
        this.snout.addChild(this.tuskRight);
        this.chest.addChild(this.maneBody);
        this.body.addChild(this.legLeft);
        this.snout.addChild(this.tuskLeft);
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
