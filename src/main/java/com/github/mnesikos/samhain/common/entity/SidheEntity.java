package com.github.mnesikos.samhain.common.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SidheEntity extends AgeableEntity implements IMob {
    protected static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(SidheEntity.class, DataSerializers.VARINT);
    private int maxVariants = 3;

    public SidheEntity(EntityType<? extends AgeableEntity> entity, World world) {
        super(entity, world);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        this.setVariant(this.getRNG().nextInt(maxVariants));
        return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    @Override
    public boolean writeUnlessRemoved(CompoundNBT nbt) {
        nbt.putInt("Variant", this.getVariant());
        return super.writeUnlessRemoved(nbt);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        this.dataManager.set(VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        return super.attackEntityAsMob(entity);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }
}
