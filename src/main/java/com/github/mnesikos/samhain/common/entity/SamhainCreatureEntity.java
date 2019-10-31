package com.github.mnesikos.samhain.common.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

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

    private static boolean testSpawn(IWorld p_223323_0_, BlockPos p_223323_1_, Random p_223323_2_) {
        if (p_223323_0_.getLightFor(LightType.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
            return false;
        } else {
            int lvt_3_1_ = p_223323_0_.getWorld().isThundering() ? p_223323_0_.getNeighborAwareLightSubtracted(p_223323_1_, 10) : p_223323_0_.getLight(p_223323_1_);
            return lvt_3_1_ <= p_223323_2_.nextInt(8);
        }
    }

    public static boolean canSpawn(EntityType<? extends SamhainCreatureEntity> p_223325_0_, IWorld p_223325_1_, SpawnReason p_223325_2_, BlockPos p_223325_3_, Random p_223325_4_) {
        return p_223325_1_.getDifficulty() != Difficulty.PEACEFUL && testSpawn(p_223325_1_, p_223325_3_, p_223325_4_) && func_223315_a(p_223325_0_, p_223325_1_, p_223325_2_, p_223325_3_, p_223325_4_);
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
