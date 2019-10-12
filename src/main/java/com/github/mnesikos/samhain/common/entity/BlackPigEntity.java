package com.github.mnesikos.samhain.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class BlackPigEntity extends TameableEntity {
    public BlackPigEntity(EntityType<? extends TameableEntity> entity, World world) {
        super(entity, world);
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        // todo: how to make goal to follow specific lady gwen entity???? owner is almost useless, partially checks for Player entities in most important places
        this.goalSelector.addGoal(6, new FollowGwenGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof BlackPigEntity) {
                BlackPigEntity pigEntity = (BlackPigEntity)target;
                if (pigEntity.isTamed() && pigEntity.getOwner() == owner)
                    return false;
            }

            if (target instanceof LadyGwenEntity || target instanceof SpiritEntity)
                return false;
            else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canAttackPlayer((PlayerEntity)target))
                return false;
            else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame())
                return false;
            else
                return !(target instanceof CatEntity) || !((CatEntity)target).isTamed();

        } else
            return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;

        else {
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        return super.processInteract(player, hand);
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageableEntity) {
        return null;
    }

    @Override
    protected boolean canBeRidden(Entity p_184228_1_) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    class FollowGwenGoal extends net.minecraft.entity.ai.goal.Goal {
        private LadyGwenEntity gwenEntity;
        protected final TameableEntity tameable;
        private LivingEntity owner;
        protected final IWorldReader world;
        private final double followSpeed;
        private final PathNavigator navigator;
        private int timeToRecalcPath;
        private final float maxDist;
        private final float minDist;
        private float oldWaterCost;

        // mostly vanilla code from FollowOwnerGoal

        public FollowGwenGoal(TameableEntity tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
            this.tameable = tameableIn;
            this.world = tameableIn.world;
            this.followSpeed = followSpeedIn;
            this.navigator = tameableIn.getNavigator();
            this.minDist = minDistIn;
            this.maxDist = maxDistIn;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(tameableIn.getNavigator() instanceof GroundPathNavigator) && !(tameableIn.getNavigator() instanceof FlyingPathNavigator)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        @Override
        public boolean shouldExecute() {
            if (gwenEntity == null) {
                return false;
            } else if (this.tameable.getOwnerId() != gwenEntity.getUniqueID()) {
                return false;
            } else if (this.tameable.getDistanceSq(gwenEntity) < (double)(this.minDist * this.minDist)) {
                return false;
            } else {
                this.owner = gwenEntity;
                return true;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return !this.navigator.noPath() && this.tameable.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist) && !this.tameable.isSitting();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
            this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.owner = null;
            this.navigator.clearPath();
            this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.tameable.getLookController().setLookPositionWithEntity(this.owner, 10.0F, (float)this.tameable.getVerticalFaceSpeed());
            if (!this.tameable.isSitting()) {
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = 10;
                    if (!this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed)) {
                        if (!this.tameable.getLeashed() && !this.tameable.isPassenger()) {
                            if (!(this.tameable.getDistanceSq(this.owner) < 144.0D)) {
                                int i = MathHelper.floor(this.owner.posX) - 2;
                                int j = MathHelper.floor(this.owner.posZ) - 2;
                                int k = MathHelper.floor(this.owner.getBoundingBox().minY);

                                for(int l = 0; l <= 4; ++l) {
                                    for(int i1 = 0; i1 <= 4; ++i1) {
                                        if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
                                            this.tameable.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.tameable.rotationYaw, this.tameable.rotationPitch);
                                            this.navigator.clearPath();
                                            return;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        protected boolean canTeleportToBlock(BlockPos pos) {
            BlockState blockstate = this.world.getBlockState(pos);
            return blockstate.canEntitySpawn(this.world, pos, this.tameable.getType()) && this.world.isAirBlock(pos.up()) && this.world.isAirBlock(pos.up(2));
        }
    }
}
