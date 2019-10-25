package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DullahanEntity extends CreatureEntity {
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(DullahanEntity.class, DataSerializers.VARINT);

    public DullahanEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 4.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        int maxVariants = 5;
        this.setVariant(this.getRNG().nextInt(maxVariants));

        BlackHorseEntity horse = (BlackHorseEntity) ModEntities.BLACK_HORSE.spawn(this.world, null, null, this.getPosition().add(1.0F, 1.0F, 1.0F), spawnReason, false, false);
        if (horse != null) {
            this.startRiding(horse);
        }

        return super.onInitialSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;

        else {
            Entity entity = source.getTrueSource();

            if (entity != null && !(entity instanceof AbstractArrowEntity)) {
                this.dead = true;
                // todo spawn reinforcements!
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public int getVariant() {
        return this.dataManager.get(VARIANT);
    }

    private void setVariant(int variant) {
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
        return false;
    }
}
