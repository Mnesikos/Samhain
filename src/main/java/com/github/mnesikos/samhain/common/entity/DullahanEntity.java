package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DullahanEntity extends SamhainCreatureEntity {
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

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        this.setVariant(this.getRNG().nextInt(5));

        BlackHorseEntity horse = (BlackHorseEntity) ModEntities.BLACK_HORSE.spawn(this.world, null, null, this.getPosition().add(1.0F, 1.0F, 1.0F), spawnReason, false, false);
        if (horse != null) {
            this.startRiding(horse);
            horse.setHorseTamed(true);
            horse.setOwnerUniqueId(this.getUniqueID());
            horse.setHasDullahan(true);
        }

        return super.onInitialSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
    }

    @Override
    public void livingTick() {
        if (world.getClosestPlayer(this, 20.0D) != null) {
            PlayerEntity player = world.getClosestPlayer(this, 20.0D);
            if (player != null && !player.canEntityBeSeen(this) && !player.isCreative()) {
                this.spawnHorde(player, this.posX, this.posY, this.posZ);
                if (this.getRidingEntity() != null)
                    this.getRidingEntity().remove();
                this.remove();
            }
        }

        super.livingTick();
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.3;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;

        else {
            Entity entity = source.getTrueSource();

            if (entity instanceof PlayerEntity && !(source.getImmediateSource() instanceof AbstractArrowEntity)) {
                this.spawnHorde((PlayerEntity) entity, this.posX, this.posY, this.posZ);
                if (this.getRidingEntity() != null)
                    this.getRidingEntity().remove();
                this.remove();
            } else if (!(entity instanceof PlayerEntity)) {
                return false;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    private void spawnHorde(PlayerEntity player, double x, double y, double z) {
        for (int i = 0; i < rand.nextInt(6) +1; i++) {
            ZombieEntity zombies = new ZombieEntity(world);
            zombies.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
            zombies.onInitialSpawn(world, world.getDifficultyForLocation(this.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
            zombies.setAttackTarget(player);
            this.world.addEntity(zombies);
        }
        for (int j = 0; j < rand.nextInt(3) +1; j++) {
            SkeletonEntity skeletons = new SkeletonEntity(EntityType.SKELETON, world);
            skeletons.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
            skeletons.onInitialSpawn(world, world.getDifficultyForLocation(this.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
            skeletons.setAttackTarget(player);
            this.world.addEntity(skeletons);
        }
        for (int k = 0; k < rand.nextInt(2) +1; k++) {
            WitchEntity witches = new WitchEntity(EntityType.WITCH, world);
            witches.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
            witches.onInitialSpawn(world, world.getDifficultyForLocation(this.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
            witches.setAttackTarget(player);
            this.world.addEntity(witches);
        }
    }
}
