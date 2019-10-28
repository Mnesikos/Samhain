package com.github.mnesikos.samhain.common.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public abstract class SamhainCreatureEntity extends CreatureEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(SamhainCreatureEntity.class, DataSerializers.VARINT);

    public SamhainCreatureEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Override
    public void livingTick() {
        if (this.isInDaylight())
            this.remove();

        super.livingTick();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    @Override
    public boolean writeUnlessRemoved(CompoundNBT compound) {
        compound.putInt("Variant", this.getVariant());
        return super.writeUnlessRemoved(compound);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        this.dataManager.set(VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return player.isCreative();
    }


}
