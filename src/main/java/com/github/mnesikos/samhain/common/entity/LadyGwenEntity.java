package com.github.mnesikos.samhain.common.entity;

import com.github.mnesikos.samhain.init.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LadyGwenEntity extends SamhainCreatureEntity {
    public LadyGwenEntity(EntityType<? extends CreatureEntity> entity, World world) {
        super(entity, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 4.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new FindPlayerGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.24D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        entityData = super.onInitialSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
        BlackPigEntity pig = (BlackPigEntity) ModEntities.BLACK_PIG.spawn(this.world, null, null, this.getPosition().add(1.0F, 1.0F, 1.0F), spawnReason, false, false);
        if (pig != null) {
            pig.setTamed(true);
            pig.setOwnerId(this.getUniqueID());
            pig.setHasLadyGwen(true);
        }

        return entityData;
    }

    @Override
    public int variantNumber() {
        return super.variantNumber() + 4;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 600));
        }
        return super.attackEntityAsMob(entityIn);
    }

    private boolean shouldAttackPlayer(PlayerEntity player) {
        ItemStack itemstack = player.inventory.armorInventory.get(3);
        if (itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
            return false;
        } else {
            Vec3d vec3d = player.getLook(1.0F).normalize();
            Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getBoundingBox().minY + (double)this.getEyeHeight() - (player.posY + (double)player.getEyeHeight()), this.posZ - player.posZ);
            double d0 = vec3d1.length();
            vec3d1 = vec3d1.normalize();
            double d1 = vec3d.dotProduct(vec3d1);
            return d1 > 1.0D - 0.025D / d0 && player.canEntityBeSeen(this);
        }
    }

    private static class FindPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        private final LadyGwenEntity ladyGwen;
        private PlayerEntity player;
        private int aggroTime;
        private final EntityPredicate entityPredicate;
        private final EntityPredicate lineOfSiteRequired = (new EntityPredicate()).setLineOfSiteRequired();

        FindPlayerGoal(LadyGwenEntity ladyGwen) {
            super(ladyGwen, PlayerEntity.class, false);
            this.ladyGwen = ladyGwen;
            this.entityPredicate = (new EntityPredicate()).setDistance(this.getTargetDistance()).setCustomPredicate((p_220790_1_) -> ladyGwen.shouldAttackPlayer((PlayerEntity)p_220790_1_));
        }

        @Override
        public boolean shouldExecute() {
            this.player = this.ladyGwen.world.getClosestPlayer(this.entityPredicate, this.ladyGwen);
            return this.player != null;
        }

        @Override
        public void startExecuting() {
            this.aggroTime = 5;
            this.ladyGwen.setLastAttackedEntity(this.player);
        }

        @Override
        public void resetTask() {
            this.player = null;
            this.aggroTime = 0;
            super.resetTask();
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.player != null) {
                if (!this.ladyGwen.shouldAttackPlayer(this.player)) {
                    return false;
                } else {
                    this.ladyGwen.faceEntity(this.player, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.nearestTarget != null && this.lineOfSiteRequired.canTarget(this.ladyGwen, this.nearestTarget) || super.shouldContinueExecuting();
            }
        }

        @Override
        public void tick() {
            if (this.player != null) {
                if (--this.aggroTime <= 0) {
                    this.nearestTarget = this.player;
                    this.player = null;
                    super.startExecuting();
                }
            } else {
                super.tick();
            }

        }
    }
}
