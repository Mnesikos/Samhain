package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LadyGwenEntity extends CreatureEntity {
    protected static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(SidheEntity.class, DataSerializers.VARINT);
    private int maxVariants = 5;

    public LadyGwenEntity(EntityType<? extends CreatureEntity> entity, World world) {
        super(entity, world);
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        this.setVariant(this.getRNG().nextInt(maxVariants));
        //todo check spawn of black pig with Lady Gwen...
        ModEntities.BLACK_PIG.spawn(this.world, null, null, this.getPosition(), spawnReason, false, false);
        return super.onInitialSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
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

}
