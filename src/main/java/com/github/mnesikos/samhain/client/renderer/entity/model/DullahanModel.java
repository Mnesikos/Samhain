package com.github.mnesikos.samhain.client.renderer.entity.model;

import com.github.mnesikos.samhain.common.entity.DullahanEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DullahanModel<T extends DullahanEntity> extends PlayerModel<T> {
    public boolean isLeftHanded;

    public DullahanModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
        if (isLeftHanded) {
            this.bipedLeftArm.addChild(bipedHead);
            this.bipedLeftArm.addChild(bipedHeadwear);
        } else {
            this.bipedRightArm.addChild(bipedHead);
            this.bipedRightArm.addChild(bipedHeadwear);
        }
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.isLeftHanded = entityIn.isLeftHanded();
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        if (this.isLeftHanded) {
            this.bipedLeftArm.rotateAngleX = -1.0F;
            this.bipedLeftArm.rotateAngleY = -0.2F;
        } else {
            this.bipedRightArm.rotateAngleX = -1.0F;
            this.bipedRightArm.rotateAngleY = 0.2F;
        }
        this.bipedLeftArmwear.copyModelAngles(bipedLeftArm);
        this.bipedRightArmwear.copyModelAngles(bipedRightArm);
        this.bipedHead.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.bipedHead.rotateAngleX = -1.4F;
        this.bipedHeadwear.copyModelAngles(bipedHead);
    }
}
