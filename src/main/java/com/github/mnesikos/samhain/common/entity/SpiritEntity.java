package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.common.entity.goals.FollowEntityGoal;
import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;

public class SpiritEntity extends AnimalEntity {

    private static final DataParameter<Integer> SPIRIT_TYPE = EntityDataManager.createKey(SpiritEntity.class, DataSerializers.VARINT);;

    public SpiritEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FollowEntityGoal(this, PlayerEntity.class, 30));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20D);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SPIRIT_TYPE, 1);
    }

    public void writeAdditional(CompoundNBT p_213281_1_) {
        super.writeAdditional(p_213281_1_);
        p_213281_1_.putInt("SpiritType", this.getSpiritType());
    }

    public void readAdditional(CompoundNBT p_70037_1_) {
        super.readAdditional(p_70037_1_);
        this.setCatType(p_70037_1_.getInt("SpiritType"));
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
        this.setCatType(this.rand.nextInt(10));

        return p_213386_4_;
    }

    @Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    /*protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 1F;
    }*/

    @Override
    public boolean isInWater() {
        return false;
    }

    public int getSpiritType() {
        return (Integer)this.dataManager.get(SPIRIT_TYPE);
    }

    public void setCatType(int p_213422_1_) {
        this.dataManager.set(SPIRIT_TYPE, p_213422_1_);
    }

    public SpiritEntity createChild(AgeableEntity p_90011_1_) {
        SpiritEntity lvt_2_1_ = (SpiritEntity)ModEntities.SPIRIT.create(this.world);
        if (p_90011_1_ instanceof CatEntity) {
            if (this.rand.nextBoolean()) {
                lvt_2_1_.setCatType(this.getSpiritType());
            } else {
                lvt_2_1_.setCatType(((CatEntity) p_90011_1_).getCatType());
            }
        }
        return lvt_2_1_;
    }

    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return this.isChild() ? p_213348_2_.height * 0.95F : 1.3F;
    }
}
